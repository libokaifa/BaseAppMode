package com.libo.base.mvvm.view;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.blankj.utilcode.util.ToastUtils;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
import com.libo.base.eventbus.BindEventBus;
import com.libo.base.eventbus.EventBusUtils;
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
 * @Date 2021/9/6 10:55
 */
public abstract class BaseMvvmFragment<V extends ViewDataBinding, VM extends BaseMvvmViewModel> extends Fragment implements Observer, Callback.OnReloadListener {
    protected VM viewModel;
    protected V viewDataBinding;
    private CustomProgressDialog mProgressDialog;
    protected LoadService loadService;
    public abstract @LayoutRes
    int getLayoutId();
    public  VM getViewModel(){
        //适配泛型
        Type type= this.getClass().getGenericSuperclass();
        Class<BaseMvvmViewModel> modelClass;
        if (type instanceof ParameterizedType){
             modelClass= (Class<BaseMvvmViewModel>) ((ParameterizedType) type).getActualTypeArguments()[1];
        }else {
             modelClass=BaseMvvmViewModel.class;
        }

      return (VM) new ViewModelProvider(getActivity()).get(modelClass);
    }

    public abstract int initViewModeId();

    protected abstract void onViewCreated();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        viewDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        return    initLoadSir();
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showLoad();
        viewModel = getViewModel();
        viewDataBinding.setVariable(initViewModeId(), viewModel);
        getLifecycle().addObserver(viewModel);
        viewDataBinding.setLifecycleOwner(this);
        viewModel.viewStatusLiveData.observe(getViewLifecycleOwner(), this);
        onViewCreated();
        if (this.getClass().isAnnotationPresent(BindEventBus.class)) {//加上判断
            EventBusUtils.register(this);
        }
        initLiveDataLister();
    }

    public void initLiveDataLister(){
        viewModel.viewStatusLiveData.observe(getViewLifecycleOwner(),this);
        viewModel.closePage.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean){
                    closePage();
                }
            }
        });
        viewModel.errorMessage.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                ToastUtils.showShort(s);
                errBack(s);
            }
        });
    }




    protected   void closePage(){
        getActivity().finish();
    }
    protected void errBack(String err){

    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (this.getClass().isAnnotationPresent(BindEventBus.class)) {
            EventBusUtils.unregister(this);
        }
    }

    private View initLoadSir(){
        View view=getLoadSir();
         if (null!=view){
             loadService = LoadSir.getDefault().register(view, this);
             return loadService.getLoadLayout();
         }else {
             return  viewDataBinding.getRoot();
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


    public void showLoadingDialog(String hint) {
        if (!getActivity().isDestroyed()) {
            if (mProgressDialog == null) {
                mProgressDialog = new CustomProgressDialog(requireContext());
            }
            mProgressDialog.setHintText(hint);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.show();
        }
    }

    public void hideLoadingDialog() {
        if (mProgressDialog != null && !getActivity().isDestroyed())
            if (mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
    }
    @Override
    public void onChanged(Object o) {

        if (o instanceof ViewStatus) {
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
        loadService.showCallback(ErrorCallback.class);
    }


    @Override
    public void onReload(View view) {
        PostUtil.postCallback(loadService,LoadingCallback.class);
    }
}
