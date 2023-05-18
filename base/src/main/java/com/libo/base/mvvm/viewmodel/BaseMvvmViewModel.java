package com.libo.base.mvvm.viewmodel;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModel;
import com.libo.base.mvvm.model.BaseMvvmModel;
import io.reactivex.disposables.CompositeDisposable;


/**
 * @Description TODO
 * @Author libo
 * @Date 2021/9/6 10:55
 */
public abstract class BaseMvvmViewModel extends ViewModel implements LifecycleObserver {
    public MutableLiveData<ViewStatus> viewStatusLiveData = new MutableLiveData();
    public MutableLiveData<String> errorMessage = new MutableLiveData<>();
    public MutableLiveData<Boolean> closePage = new MutableLiveData<>();


    public BaseMvvmViewModel() {
        initDataModel();
    }



    public void initDataModel() {
        createDataModel();
        registerDataChangeListener();
        initData();
    }
    protected abstract void createDataModel() ;
    protected abstract void registerDataChangeListener() ;
    protected abstract void initData() ;
    protected abstract void onVMCleared() ;

    @Override
    protected void onCleared() {
        super.onCleared();

    }



    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private void onResume() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private void onStop(){

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private void  onDestroy(){
        onVMCleared();

    }

    protected  void cancelModel(BaseMvvmModel baseMvvmModel){
      if (baseMvvmModel!=null){
          baseMvvmModel.cancel();
      }
    }
}
