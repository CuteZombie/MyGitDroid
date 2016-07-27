package edu.zhuoxin.mygitdroid.splash;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.zhuoxin.mygitdroid.MainActivity;
import edu.zhuoxin.mygitdroid.R;
import edu.zhuoxin.mygitdroid.commons.ActivityUtils;

/**
 * Created by Administrator on 2016/7/26.
 * 首页面,第一次启动时进入的页面
 */
public class SplashActivity extends AppCompatActivity{

    private ActivityUtils activityUtils;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        activityUtils = new ActivityUtils(this);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnLogin)
    public void login() {

    }

    @OnClick(R.id.btnEnter)
    public void enter() {
        activityUtils.startActivity(MainActivity.class);
        finish();
    }
}
