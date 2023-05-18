package com.libo.homemodel;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.libo.homemodel.api.ListBean;
import com.libo.homemodel.databinding.HomeItemBinding;

/**
 * author : LiBo
 * date : 2023/5/17 09:43
 * description :
 */
public class HomeFrgAdapter extends BaseQuickAdapter<ListBean.Datas, BaseDataBindingHolder<HomeItemBinding>> {

    public HomeFrgAdapter() {
        super(R.layout.home_item);
    }

    @Override
    protected void convert(@NonNull BaseDataBindingHolder<HomeItemBinding> homeItemBindingBaseDataBindingHolder, ListBean.Datas datas) {
          HomeItemBinding homeItemBinding=homeItemBindingBaseDataBindingHolder.getDataBinding();
          homeItemBinding.tvEmpty.setText(datas.title);
    }
}
