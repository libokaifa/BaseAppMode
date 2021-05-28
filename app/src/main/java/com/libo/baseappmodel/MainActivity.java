package com.libo.baseappmodel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;

import com.libo.base.autoservice.LiboServiceLoader;
import com.libo.baseappmodel.databinding.ActivityMainBinding;
import com.libo.common.autoservice.IWebViewService;

public class MainActivity extends AppCompatActivity {
    private  ActivityMainBinding  activityMainBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding=  DataBindingUtil.setContentView(this,R.layout.activity_main);
        activityMainBinding.tvWebview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IWebViewService webViewService= LiboServiceLoader.load(IWebViewService.class);
                if (webViewService!=null){
                    webViewService.startDemoHtml(MainActivity.this);
                }
            }
        });
    }
}