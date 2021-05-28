package com.libo.webview;

import android.os.Bundle;
import android.view.View;

import com.libo.webview.databinding.ActivityWebviewBinding;
import com.libo.webview.utils.Constants;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/**
 * /**
 *
 * @Author libo
 * @create 2021/5/28 10:26 上午
 * @describe:
 */
public class WebViewActivity extends AppCompatActivity {
     private ActivityWebviewBinding mBing;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBing= DataBindingUtil.setContentView(this,R.layout.activity_webview);
        mBing.title.setText(getIntent().getStringExtra(Constants.TITLE));
        mBing.actionBar.setVisibility(getIntent().getBooleanExtra(Constants.IS_SHOW_ACTION_BAR,true)? View.VISIBLE:View.GONE);
        mBing.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebViewActivity.this.finish();;
            }
        });
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment fragment = WebViewFragment.newInstance(getIntent().getStringExtra(Constants.URL), true);
        transaction.replace(R.id.web_view_fragment, fragment).commit();
    }

    public void updateTitle(String title){
         mBing.title.setText(title);
    }
}
