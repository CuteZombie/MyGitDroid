package edu.zhuoxin.mygitdroid;

import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.zhuoxin.mygitdroid.hotrepo.HotRepoFragment;

/**
 * 应用主页面
 *
 * */
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)Toolbar toolbar;

    /** 抽屉（包含内容 + 侧滑菜单） */
    @BindView(R.id.drawerLayout)DrawerLayout drawerLayout;

    /** 侧滑菜单视图 */
    @BindView(R.id.navigationView)NavigationView navigationView;

    /** 热门仓库 fragment */
    private HotRepoFragment hotRepoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        /** 主页面默认显示热门仓库 hotRepoFragment */
        hotRepoFragment = new HotRepoFragment();
        replaceFragment(hotRepoFragment);
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
        switch (item.getItemId()) {
            case R.id.github_hot_repo://最热门

                break;
            case R.id.github_hot_coder://开发者

                break;
        }

        return true;//返回true代表将该菜单项变为checked状态
    }
}
