package edu.zhuoxin.mygitdroid.login;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.zhuoxin.mygitdroid.MainActivity;
import edu.zhuoxin.mygitdroid.R;
import edu.zhuoxin.mygitdroid.commons.ActivityUtils;
import edu.zhuoxin.mygitdroid.network.GitHubApi;
import pl.droidsonroids.gif.GifImageView;

/**
 * Created by Administrator on 2016/7/29.
 * 授权登录
 * 1. GitHub会有一个授权url （用webview）
 * 2. 同意授权后，将重定向到另一个URL,带出临时授权码code
 * 3. 用code去授权,得到token
 *
 * 4. 使用token就能访问用户接口,得到用户数据
 * 5. 。。。
 * 6. 。。。
 * 7. 。。。
 *
 */
public class LoginActivity extends AppCompatActivity implements LoginView{

    @BindView(R.id.webView)WebView webView;
    @BindView(R.id.toolbar)Toolbar toolbar;
    @BindView(R.id.gifImageView)GifImageView gifImageView;

    private ActivityUtils activityUtils;
    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUtils = new ActivityUtils(this);
        setContentView(R.layout.activity_login);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loginPresenter = new LoginPresenter(this);
        initWebView();
    }

    private void initWebView() {
        /** 清除所有cookie，清除以前的登录记录 */
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();

        /** 授权登录URL */
        webView.loadUrl(GitHubApi.AUTH_URL);
        webView.setFocusable(true);
        webView.setFocusableInTouchMode(true);

        /** 监听进度 */
        webView.setWebChromeClient(webChromeClient);

        /** 监听webview（url会刷新） */
        webView.setWebViewClient(webViewClient);
    }

    /** 监听进度 */
    private WebChromeClient webChromeClient = new WebChromeClient(){
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress >= 100) {
                gifImageView.setVisibility(View.GONE);
            }
        }
    };

    private WebViewClient webViewClient = new WebViewClient(){
        /**
         * 每当webview"刷新"时,此方法将触发
         * 密码输错了时！输对了时！等等情况web页面都会刷新变化
         * */
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            /** 检测是不是我的callBack URL */
            Uri uri = Uri.parse(url);
            if (GitHubApi.CALL_BACK.equals(uri.getScheme())) {

                /** 获取code */
                String code = uri.getQueryParameter("code");

                /** 用code做登陆业务工作 */
                loginPresenter.login(code);
                return true;
            }
            return super.shouldOverrideUrlLoading(view,url);
        }
    };

    @Override
    public void showProgress() {
        gifImageView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showMessage(String msg) {
        activityUtils.showToast(msg);
    }

    @Override
    public void resetWeb() {
        initWebView();
    }

    @Override
    public void navigateToMain() {
        activityUtils.startActivity(MainActivity.class);
        finish();
    }
}
