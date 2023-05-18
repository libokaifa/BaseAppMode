package com.libo.baseappmodel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.libo.base.mvvm.view.BaseMvvmFragment;
import com.libo.base.util.LiboLoger;
import com.libo.baseappmodel.databinding.FragmentMainPage2Binding;
import com.libo.baseappmodel.databinding.FragmentMainPage3Binding;
import com.libo.room.Student;

import java.util.List;

public class MainPage3Fragment
        extends BaseMvvmFragment<FragmentMainPage3Binding,MainPage3FragmentVm> {
    private   ItemStuAdapter itemStuAdapter;
    @Override
    public int getLayoutId() {
        return R.layout.fragment_main_page3;
    }

    @Override
    public int initViewModeId() {
        return BR.vm;
    }

    @Override
    protected void onViewCreated() {
        viewDataBinding.setCview(this);
        itemStuAdapter=new ItemStuAdapter();
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);
        viewDataBinding.recyclerView.setLayoutManager(linearLayoutManager);
        viewDataBinding.recyclerView.setAdapter(itemStuAdapter);
        viewModel.getAllLiveDataStudent().observe(getActivity(), new Observer<List<Student>>() {
            @Override
            public void onChanged(List<Student> students) {
                itemStuAdapter.setList(students);
            }
        });

    }


    public  void  zeng(){
        int len=itemStuAdapter.getData().size();
        for (int i = len; i <len+ 10; i++) {
            viewModel.insert(new Student("libo"+i, "123", 1));
        }
    }
    public  void  shan(){
        int len=itemStuAdapter.getData().size();
        LiboLoger.log("---><><>="+(len-1));
         viewModel.delete((new Student(itemStuAdapter.getData().get(len-1).getUid(),"libo"+(len-1), "123", 1)));
    }
    public  void  gai(){
        int len=itemStuAdapter.getData().size();
        LiboLoger.log("---><><>="+itemStuAdapter.getData().get(len-1).getUid());
        viewModel.update(new Student(itemStuAdapter.getData().get(len-1).getUid(),"libo"+(len-1), "123--->", 1));
    }

}
