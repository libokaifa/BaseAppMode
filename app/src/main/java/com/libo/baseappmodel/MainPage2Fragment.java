package com.libo.baseappmodel;
import android.app.PendingIntent;
import android.content.Intent;
import android.view.View;

import androidx.core.app.NotificationCompat;

import com.blankj.utilcode.util.NotificationUtils;
import com.blankj.utilcode.util.Utils;
import com.libo.base.autoservice.LiboServiceLoader;
import com.libo.base.mvvm.view.BaseMvvmFragment;

import com.libo.baseappmodel.databinding.FragmentMainPage2Binding;
import com.libo.common.autoservice.IWebViewService;


public class MainPage2Fragment
        extends BaseMvvmFragment<FragmentMainPage2Binding,MainPage2FragmentVm> {

    @Override
    public int getLayoutId() {
        return R.layout.fragment_main_page2;
    }

    @Override
    public int initViewModeId() {
        return BR.vm;
    }

    @Override
    protected void onViewCreated() {
        viewDataBinding.tvWebview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IWebViewService webViewService= LiboServiceLoader.load(IWebViewService.class);
                if (webViewService!=null){
                    webViewService.startDemoHtml(getContext());
                }
            }
        });
        viewDataBinding.notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNotification();
            }
        });
    }

    public void onNotification(){
        Intent intentClick = new Intent(getContext(),
                NotificationBroadCastReceiver.class);
        intentClick.setAction("notification_clicked");
        intentClick.putExtra(NotificationBroadCastReceiver.TYPE, 10);
        intentClick.putExtra("MESSAGE","消息");
        PendingIntent pendingIntentClick = PendingIntent.getBroadcast(getContext(), 0, intentClick, PendingIntent.FLAG_ONE_SHOT);
        NotificationUtils.notify(1, new Utils.Consumer<NotificationCompat.Builder>() {
            @Override
            public void accept(NotificationCompat.Builder builder) {
                builder.setSmallIcon(R.mipmap.app_ic)
                        .setContentTitle("title")
                        .setContentText("content text: $id")
                        .setContentIntent(pendingIntentClick)
                        .setAutoCancel(true);
            }

        });
    }
}
