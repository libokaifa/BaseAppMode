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
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;

import com.blankj.utilcode.util.ToastUtils;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
import com.libo.base.eventbus.BindEventBus;
import com.libo.base.eventbus.EventBusUtils;
import com.libo.base.loadsir.EmptyCallback;
import com.libo.base.loadsir.ErrorCallback;
import com.libo.base.loadsir.LoadingCallback;
import com.libo.base.mvvm.viewmodel.BaseMvvmViewModel;
import com.libo.base.mvvm.viewmodel.ViewStatus;
import com.libo.base.util.PostUtil;
import com.libo.base.widgets.dialog.CustomProgressDialog;


import java.util.List;

/**
 * @Description TODO
 * @Author libo
 * @Date 2021/9/6 10:55
 */
public abstract class BaseLazyMvvmFragment<V extends ViewDataBinding, VM extends BaseMvvmViewModel> extends Fragment implements Observer, Callback.OnReloadListener {
    protected VM viewModel;
    protected V viewDataBinding;
    private boolean isViewCreated = false; // View加载了
    private boolean isVisibleStateUP = false; // 记录上一次可见的状态
    protected boolean dataHasLoad=false;//保持当前页面只会加载一次数据
    public abstract @LayoutRes
    int getLayoutId();
    private CustomProgressDialog mProgressDialog;
    protected LoadService loadService;

    public abstract VM getViewModel();

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
        return initLoadSir();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (this.getClass().isAnnotationPresent(BindEventBus.class)) {//加上判断
            if (!EventBusUtils.hasRegistered(this)){
                //防止重复注册，收到多次回调
                EventBusUtils.register(this);
            }
        }
        showLoad();
        viewModel = getViewModel();
        viewDataBinding.setVariable(initViewModeId(), viewModel);
        getLifecycle().addObserver(viewModel);
        viewDataBinding.setLifecycleOwner(this);
        viewModel.viewStatusLiveData.observe(getViewLifecycleOwner(), this);
        onViewCreated();
        isViewCreated=true;
        initLiveDataLister();

    }

    public void showLoadingDialog(String hint) {
        if (!getActivity().isDestroyed()) {
            if (mProgressDialog == null) {
                mProgressDialog = new CustomProgressDialog(getContext());
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

    public  void showLoad(){
          if (loadService !=null){
              loadService.showCallback(LoadingCallback.class);
          }
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
                hideLoadingDialog();
                ToastUtils.showShort(s);
                errBack(s);
            }
        });
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isViewCreated) {
            // 记录上一次可见的状态: && isVisibleStateUP
            if (isVisibleToUser && !isVisibleStateUP) {
                dispatchUserVisibleHint(true);
            } else if (!isVisibleToUser && isVisibleStateUP) {
                dispatchUserVisibleHint(false);
            }

        }

    }




    // 分发 可见 不可见 的动作
    private void dispatchUserVisibleHint(boolean visibleState) {

        // 记录上一次可见的状态 实时更新状态
        this.isVisibleStateUP = visibleState;

        if (visibleState && isParentInvisible()) {
            return;
        }

        if (visibleState) {
            // 加载数据
            if (!dataHasLoad){
                onFragmentLoad();
            }
            dispatchChildVisibleState(true);

        } else {
            // 停止一切操作
            onFragmentLoadStop();

            dispatchChildVisibleState(false);
        }
    }

    protected void dispatchChildVisibleState(boolean state) {
        FragmentManager fragmentManager = getChildFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        if (fragments != null) {
            for (Fragment fragment: fragments) { // 循环遍历 嵌套里面的 子 Fragment 来分发事件操作
                if (fragment instanceof BaseLazyMvvmFragment &&
                        !fragment.isHidden() &&
                        fragment.getUserVisibleHint()) {
                    ((BaseLazyMvvmFragment)fragment).dispatchUserVisibleHint(state);
                }
            }
        }
    }
    private boolean isParentInvisible() {
        Fragment parentFragment = getParentFragment();
        if (parentFragment instanceof BaseLazyMvvmFragment) {
            BaseLazyMvvmFragment fragment = (BaseLazyMvvmFragment) parentFragment;
            return !fragment.isVisibleStateUP;
        }
        return false;
    }
    /**
     * 停止数据加载
     */
    public void onFragmentLoadStop() {

    }

    /**
     * 加载网络数据
     */
    public void onFragmentLoad() {

    }
    @Override
    public void onResume() {
        super.onResume();
        // 不可见 到 可见 变化过程  说明可见
        if (getUserVisibleHint() && !isVisibleStateUP) {
            dispatchUserVisibleHint(true);
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        // 可见 到 不可见  变化过程  说明 不可见
        if (getUserVisibleHint() && isVisibleStateUP) {
            dispatchUserVisibleHint(false);
        }
    }


    @Override
    public void onChanged(Object o) {
        hideLoadingDialog();
        if (o instanceof ViewStatus) {
            switch ((ViewStatus) o) {
                case LOADING:
                    break;
                case EMPTY:
                    showEMPTY();
                    break;
                case  NETERR:
                    PostUtil.postCallback(loadService, ErrorCallback.class);
                    break;
                case SHOW_CONTENT:
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
    private View initLoadSir(){
        View view=getLoadSir();
        if (null!=view){
            loadService = LoadSir.getDefault().register(view, this);
            return loadService.getLoadLayout();
        }else {
            return  viewDataBinding.getRoot();
        }
    }

    @Override
    public void onDestroy() {
        if (this.getClass().isAnnotationPresent(BindEventBus.class)) {
            EventBusUtils.unregister(this);
        }
        super.onDestroy();
    }

    // 预留函数
    public View getLoadSir() {
        return  null;
    }
    protected   void closePage(){
        getActivity().finish();
    }
    protected void errBack(String err){

    }
    @Override
    public void onReload(View view) {
        PostUtil.postCallback(loadService,LoadingCallback.class);
    }
}
