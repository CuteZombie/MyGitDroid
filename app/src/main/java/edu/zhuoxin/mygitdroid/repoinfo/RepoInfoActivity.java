package edu.zhuoxin.mygitdroid.repoinfo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.zhuoxin.mygitdroid.R;
import edu.zhuoxin.mygitdroid.commons.ActivityUtils;
import edu.zhuoxin.mygitdroid.hotrepo.repolist.modle.Repo;

/**
 * Created by Administrator on 2016/8/1.
 * 仓库详情页面
 */
public class RepoInfoActivity extends AppCompatActivity
        implements RepoInfoPresenter.RepoInfoView{

    @BindView(R.id.toolbar) Toolbar toolbar;

    /** 用户头像 */
    @BindView(R.id.ivIcon) ImageView ivIcon;

    /** 仓库信息 */
    @BindView(R.id.tvRepoInfo) TextView tvRepoInfo;

    /** Starts */
    @BindView(R.id.tvRepoStars) TextView tvRepoStars;

    /** 仓库名字 */
    @BindView(R.id.tvRepoName) TextView tvRepoName;

    /** 用来展示readme */
    @BindView(R.id.webView) WebView webView;

    /** 显示加载中loading */
    @BindView(R.id.progressBar) ProgressBar progressBar;

    private ActivityUtils activityUtils;

    private RepoInfoPresenter presenter;

    /** 当前仓库 */
    private Repo repo;

    /** putExtra中的 Key */
    private static final String KEY_REPO = "key_repo";

    public static void open(Context context, @NonNull Repo repo) {
        Intent intent = new Intent(context,RepoInfoActivity.class);
        intent.putExtra(KEY_REPO,repo);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUtils = new ActivityUtils(this);
        setContentView(R.layout.activity_repo_info);

        presenter = new RepoInfoPresenter(this);
        presenter.getReadme(repo);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        //从 intent 中获取 repo
        repo = (Repo) getIntent().getSerializableExtra(KEY_REPO);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //使用仓库名作为title
        getSupportActionBar().setTitle(repo.getName());
        //设置仓库信息
        tvRepoName.setText(repo.getFullName());
        tvRepoInfo.setText(repo.getDescription());
        //显示 starts:???  fork:???
        tvRepoStars.setText(String.format(
                "start: %d  fork: %d",
                repo.getStarCount(),
                repo.getForkCount()));
        ImageLoader.getInstance().displayImage(repo.getOwner().getAvatar(),ivIcon);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showMessage(String msg) {
        activityUtils.showToast(msg);
    }

    @Override
    public void setData(String htmlContent) {
        webView.loadData(htmlContent,"text/html","UTF-8");
    }
}
