package com.libo.base.mvvm.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
import com.libo.base.R;
import com.libo.base.eventbus.BindEventBus;
import com.libo.base.eventbus.EventBusUtils;
import com.libo.base.loadsir.EmptyCallback;
import com.libo.base.loadsir.ErrorCallback;
import com.libo.base.loadsir.LoadingCallback;
import com.libo.base.mvvm.viewmodel.BaseMvvmViewModel;
import com.libo.base.mvvm.viewmodel.ViewStatus;
import com.libo.base.util.PostUtil;
import com.libo.base.widgets.dialog.CustomProgressDialog;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @Description TODO
 * @Author libo
 * @Date 2021/9/6 13:59
 */
public abstract class BaseMvvmActivity <V extends ViewDataBinding, VM extends BaseMvvmViewModel> extends AppCompatActivity implements Observer, Callback.OnReloadListener {
    protected Activity mActivity;
    protected VM viewModel;
    protected V viewDataBinding;
    protected LoadService loadService;


    public abstract @LayoutRes
    int getLayoutId();

    public VM getViewModel(){
        //适配泛型
        Type type= this.getClass().getGenericSuperclass();
        Class<BaseMvvmViewModel> modelClass;
        if (type instanceof ParameterizedType){
            modelClass= (Class<BaseMvvmViewModel>) ((ParameterizedType) type).getActualTypeArguments()[1];
        }else {
            modelClass=BaseMvvmViewModel.class;
        }

        return (VM) new ViewModelProvider(this).get(modelClass);
    }


    public abstract int initViewModeId();

    public abstract void onViewCreate();

    private CustomProgressDialog mProgressDialog;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (this.getClass().isAnnotationPresent(BindEventBus.class)) {//加上判断
            if (!EventBusUtils.hasRegistered(this)){
                //防止重复注册，收到多次回调
                EventBusUtils.register(this);
            }
        }

        mActivity=this;
        viewDataBinding = DataBindingUtil.setContentView(this, getLayoutId());
        initLoadSir();
        viewModel = getViewModel();
        getLifecycle().addObserver(viewModel);
        viewDataBinding.setVariable(initViewModeId(),viewModel);
        viewDataBinding.setLifecycleOwner(this);
        onViewCreate();
        initLiveDataLister();
        LogUtils.e("pageName", "当前启动的Activity名称为: "+getClass().getSimpleName());

    }



    public void initLiveDataLister(){
        viewModel.viewStatusLiveData.observe(this,this);
        viewModel.closePage.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean){
                    closePage();
                }
            }
        });
        viewModel.errorMessage.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                errBack(s);
            }
        });
    }
    @Override
    protected void onDestroy() {
        if (this.getClass().isAnnotationPresent(BindEventBus.class)) {
            EventBusUtils.unregister(this);
        }
        super.onDestroy();
    }


    public void showLoadingDialog(String hint) {
        if (!isDestroyed()) {
            if (mProgressDialog == null) {
                mProgressDialog = new CustomProgressDialog(this);
            }
            mProgressDialog.setHintText(hint);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.show();
        }
    }

    public void hideLoadingDialog() {
        if (mProgressDialog != null && !isDestroyed())
            if (mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
    }


    @Override
    public void onChanged(Object o) {

        if (o instanceof ViewStatus) {
            hideLoadingDialog();
            switch ((ViewStatus) o) {
                case LOADING:
                    showLoadingDialog("");
                    break;
                case EMPTY:
                    showEMPTY();
                    break;
                case  NETERR:
                    PostUtil.postCallback(loadService, ErrorCallback.class);
                    break;
                case SHOW_CONTENT:
                    hideLoadingDialog();
                    PostUtil.postSuccess(loadService);
                    break;
                case NO_MORE_DATA:
                    break;
                case REFRESH_ERROR:
                    break;
                case LOAD_MORE_FAILED:
                    break;
            }
        }
    }

    public  void showEMPTY(){
        loadService.showCallback(EmptyCallback.class);
    }


    private void initLoadSir(){
        View view=getLoadSir();
        if (null!=view){
            loadService = LoadSir.getDefault().register(view,this);
             showLoad();
        }
    }
    public  void showLoad(){
        if (loadService !=null){
            loadService.showCallback(LoadingCallback.class);
        }
    }

    // 预留函数
    public View getLoadSir() {
        return  null;
    }
    public  void closePage(){
        this.finish();
    }
    protected void errBack(String err){
             ToastUtils.showShort(err);
              hideLoadingDialog();
    }
    @Override
    public void onReload(View view) {
        PostUtil.postCallback(loadService,LoadingCallback.class);
    }
    @Override
    public void finish() {
        super.finish();

    }
}
