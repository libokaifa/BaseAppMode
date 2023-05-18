package com.libo.baseappmodel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.libo.base.mvvm.view.BaseMvvmFragment;
import com.libo.baseappmodel.databinding.FragmentMainPage2Binding;
import com.libo.baseappmodel.databinding.FragmentMainPage3Binding;

public class MainPage3Fragment
        extends BaseMvvmFragment<FragmentMainPage3Binding,MainPage3FragmentVm> {

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

    }
}
