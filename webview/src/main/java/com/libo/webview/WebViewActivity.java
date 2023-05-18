package com.libo.webview;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.libo.router.webview.WebViewPath;
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
@Route(path= WebViewPath.Web_ACTIVITY)
public class WebViewActivity extends AppCompatActivity {
     private ActivityWebviewBinding mBing;
     @Autowired
     public  String  title;
     @Autowired
     public  boolean isShowActionBar;
     @Autowired
     public  String  url;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
        mBing= DataBindingUtil.setContentView(this,R.layout.activity_webview);
        mBing.title.setText(title);
        mBing.actionBar.setVisibility(isShowActionBar? View.VISIBLE:View.GONE);
        mBing.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebViewActivity.this.finish();;
            }
        });
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment fragment = WebViewFragment.newInstance(url, true);
        transaction.replace(R.id.web_view_fragment, fragment).commit();
    }

    public void updateTitle(String title){
         mBing.title.setText(title);
    }
}
