package com.libo.homemodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.libo.base.mvvm.model.BaseMvvmModel;
import com.libo.base.mvvm.model.IBaseModelListener;
import com.libo.base.mvvm.model.PagingResult;
import com.libo.base.mvvm.viewmodel.BaseMvvmViewModel;
import com.libo.base.util.LiboLoger;
import com.libo.homemodel.api.ListBean;
import com.libo.homemodel.api.NewsListModel;

import java.util.List;

/**
 * author : LiBo
 * date : 2023/5/16 14:03
 * description :
 */
public class HomeFrgVm extends BaseMvvmViewModel {
     private NewsListModel newsListModel;
     public MutableLiveData<List<ListBean.Datas>> listMutableLiveData;
    @Override
    protected void createDataModel() {
     newsListModel=new NewsListModel();
     listMutableLiveData=new MutableLiveData<>();
    }

    @Override
    protected void registerDataChangeListener() {
     newsListModel.register(new IBaseModelListener<List<ListBean.Datas>>() {
         @Override
         public void onLoadSuccess(List<ListBean.Datas> datas, PagingResult result) {
             LiboLoger.log(datas.size()+"1===<><>");
             listMutableLiveData.setValue(datas);
         }

         @Override
         public void onLoadFail(String message, PagingResult result) {

         }
     });
    }

    @Override
    protected void initData() {
       newsListModel.load();
    }

    @Override
    protected void onVMCleared() {

    }
}
