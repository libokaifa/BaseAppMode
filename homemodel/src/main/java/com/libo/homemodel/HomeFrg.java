package com.libo.homemodel;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.libo.base.mvvm.view.BaseMvvmFragment;
import com.libo.base.util.LiboLoger;
import com.libo.homemodel.api.ListBean;
import com.libo.homemodel.databinding.HomeFrgBinding;
import com.libo.router.webview.LiboWebViewNavigation;

import java.util.List;

/**
 * author : LiBo
 * date : 2023/5/16 14:03
 * description :
 */
public class HomeFrg extends BaseMvvmFragment<HomeFrgBinding,HomeFrgVm> {
    private  HomeFrgAdapter adapter;
    @Override
    public int getLayoutId() {
        return R.layout.home_frg;
    }

    @Override
    public int initViewModeId() {
        return BR.vm;
    }

    @Override
    protected void onViewCreated() {
        adapter=new HomeFrgAdapter();
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);
        viewDataBinding.recyclerView.setLayoutManager(linearLayoutManager);
        viewDataBinding.recyclerView.setAdapter(adapter);

    }

    @Override
    public void initLiveDataLister() {
        super.initLiveDataLister();
        viewModel.listMutableLiveData.observe(this, new Observer<List<ListBean.Datas>>() {
            @Override
            public void onChanged(List<ListBean.Datas> datas) {
                adapter.setList(datas);
            }
        });
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                LiboLoger.log("lklak");
                ListBean.Datas datas= (ListBean.Datas) adapter.getData().get(position);
                LiboLoger.log("lklak"+datas.link);
                LiboWebViewNavigation.getInstance().actionStart(getActivity(),"libo",true,datas.link);
            }
        });
    }
}
