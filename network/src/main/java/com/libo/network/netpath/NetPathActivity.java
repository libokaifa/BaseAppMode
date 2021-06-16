package com.libo.network.netpath;

import androidx.appcompat.app.AppCompatActivity;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;


import android.app.Application;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;


import com.libo.base.util.LiboResourseUtil;
import com.libo.network.R;

public class NetPathActivity extends AppCompatActivity {


    public static final String NETWORK_ENVIRONMENT_PREF_KEY = "network_environment_type";
    private static String sCurrentNetworkEnvironment = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_path);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, new MyPreFerenceFragment())
                .commit();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        sCurrentNetworkEnvironment = prefs.getString(NETWORK_ENVIRONMENT_PREF_KEY, "1");
    }


    public  static  class  MyPreFerenceFragment extends PreferenceFragmentCompat implements Preference.OnPreferenceChangeListener {



        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            if (!sCurrentNetworkEnvironment.equalsIgnoreCase(String.valueOf(newValue))) {
                if (String.valueOf(newValue).equals(LiboResourseUtil.getArrayData(R.array.environmentValues,0))){
                    Toast.makeText(getContext(), "您已经更改了正式网络环境，再您退出当前页面的时候APP将会重启切换环境！", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getContext(), "您已经更改了测试网络环境，再您退出当前页面的时候APP将会重启切换环境！", Toast.LENGTH_SHORT).show();
                }

            }
            return true;
        }

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            addPreferencesFromResource(R.xml.environment_preference);
            findPreference(NETWORK_ENVIRONMENT_PREF_KEY).setOnPreferenceChangeListener(this);
        }

    }

    @Override
    public void onBackPressed() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String newValue = prefs.getString(NETWORK_ENVIRONMENT_PREF_KEY, "1");
        if (!sCurrentNetworkEnvironment.equalsIgnoreCase(newValue)) {
            android.os.Process.killProcess(android.os.Process.myPid());
        } else {
            finish();
        }
    }
    public static boolean isOfficialEnvironment(Application application) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(application);
        String environment = prefs.getString(NETWORK_ENVIRONMENT_PREF_KEY, "1");
        return "1".equalsIgnoreCase(environment);
    }
}