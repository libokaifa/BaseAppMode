package com.libo.base.mvvm.viewmodel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import com.libo.base.mvvm.model.BaseMvvmModel;


/**
 * /**
 *
 * @Author libo
 * @create 2021/10/16 7:57 下午
 * @describe:
 */
public abstract class ItemViewModel  implements LifecycleObserver{

   protected   AppCompatActivity context;

    public ItemViewModel( AppCompatActivity context) {
        this.context = context;
        this.context.getLifecycle().addObserver(this);
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private void onResume() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private void onStop(){

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private void  onDestroy(){
        onClean();
    }
    protected  abstract void onClean();

    protected  void cancelModel(BaseMvvmModel baseMvvmModel){
        if (baseMvvmModel!=null){
            baseMvvmModel.cancel();
        }
    }
}
