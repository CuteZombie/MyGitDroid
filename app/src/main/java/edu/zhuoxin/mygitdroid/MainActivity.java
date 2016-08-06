package edu.zhuoxin.mygitdroid;

import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.zhuoxin.mygitdroid.commons.ActivityUtils;
import edu.zhuoxin.mygitdroid.favorite.FavoriteFragment;
import edu.zhuoxin.mygitdroid.gank.GankFragment;
import edu.zhuoxin.mygitdroid.hotrepo.HotRepoFragment;
import edu.zhuoxin.mygitdroid.hotuser.userlist.HotUserFragment;
import edu.zhuoxin.mygitdroid.login.LoginActivity;
import edu.zhuoxin.mygitdroid.login.UserRepo;

/**
 * 应用主页面
 *
 * */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)Toolbar toolbar;

    /** 抽屉（包含内容 + 侧滑菜单） */
    @BindView(R.id.drawerLayout)DrawerLayout drawerLayout;

    /** 侧滑菜单视图 */
    @BindView(R.id.navigationView)NavigationView navigationView;

    /** 热门仓库 fragment */
    private HotRepoFragment hotRepoFragment;

    /** 热门开发者 fragment */
    private HotUserFragment hotUserFragment;

    /** 收藏 fragment */
    private FavoriteFragment favoriteFragment;

    /** 每日干货 fragment */
    private GankFragment gankFragment;

    private Button btnLogin;

    private ImageView ivIcon;

    private ActivityUtils activityUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUtils = new ActivityUtils(this);
        //设置当前视图（若当前视图内容改变，将会触发onContentChanged()方法）
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);

        /** ActionBar 处理 */
        setSupportActionBar(toolbar);

        /** 设置 navigationView 的监听 */
        navigationView.setNavigationItemSelectedListener(this);

        /** 构建抽屉的监听 */
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout,toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        toggle.syncState();//根据drawerLayout同步其当前状态

        /** 设置抽屉的监听 */
        drawerLayout.addDrawerListener(toggle);

        btnLogin = ButterKnife.findById(
                navigationView.getHeaderView(0),
                R.id.btnLogin);
        ivIcon = ButterKnife.findById(
                navigationView.getHeaderView(0),
                R.id.ivIcon);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityUtils.startActivity(LoginActivity.class);
                finish();
            }
        });

        /** 主页面默认显示热门仓库 hotRepoFragment */
        hotRepoFragment = new HotRepoFragment();
        replaceFragment(hotRepoFragment);
    }

    @Override
    protected void onStart() {
        super.onStart();
        /** 如果没有授权 */
        if (UserRepo.isEmpty()) {
            btnLogin.setText(R.string.login_github);
            return;
        }
        btnLogin.setText(R.string.switch_account);

        /** 设置 title */
        getSupportActionBar().setTitle(UserRepo.getUser().getName());

        /** 设置用户头像 */
        String photoUrl = UserRepo.getUser().getAvatar();
        ImageLoader.getInstance().displayImage(photoUrl,ivIcon);
    }

    /** 动态替换 fragment 的方法 */
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction =fragmentManager.beginTransaction();
        transaction.replace(R.id.container,fragment);
        transaction.commit();
    }

    /** 侧滑菜单监听器 */
    public boolean onNavigationItemSelected(MenuItem item) {

        //设置默认选中项为 false
        if (item.isChecked()) {
            item.setChecked(false);
        }

        switch (item.getItemId()) {
            case R.id.github_hot_repo://最热门
                if (!hotRepoFragment.isAdded()) {
                    replaceFragment(hotRepoFragment);
                }
                break;
            case R.id.github_hot_coder://开发者
                if (hotUserFragment == null) hotUserFragment = new HotUserFragment();
                if (!hotUserFragment.isAdded()){
                    replaceFragment(hotUserFragment);
                }
                break;
            case R.id.arsenal_my_repo://我的收藏
                if (favoriteFragment == null) favoriteFragment = new FavoriteFragment();
                if (!favoriteFragment.isAdded()) {
                    replaceFragment(favoriteFragment);
                }
                break;
            case R.id.tips_daily://每日干货
                if (gankFragment == null) gankFragment = new GankFragment();
                if (!gankFragment.isAdded()) {
                    replaceFragment(gankFragment);
                }
        }
        //关闭drawerLayout
        drawerLayout.post(new Runnable() {
            @Override
            public void run() {
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
        return true;//返回true代表将该菜单项变为checked状态
    }

    private long mExitTime;

    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - mExitTime) > 1500) {
            Toast.makeText(MainActivity.this,"哎呀~再按就退出程序啦>_<",Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }
}
