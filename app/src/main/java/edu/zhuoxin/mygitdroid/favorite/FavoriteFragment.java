package edu.zhuoxin.mygitdroid.favorite;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.zhuoxin.mygitdroid.R;
import edu.zhuoxin.mygitdroid.commons.ActivityUtils;
import edu.zhuoxin.mygitdroid.favorite.dao.DBHelper;
import edu.zhuoxin.mygitdroid.favorite.dao.LocalRepoDao;
import edu.zhuoxin.mygitdroid.favorite.dao.RepoGroupDao;
import edu.zhuoxin.mygitdroid.favorite.model.LocalRepo;
import edu.zhuoxin.mygitdroid.favorite.model.RepoGroupTable;

/**
 * Created by Administrator on 2016/8/3.
 * 本地收藏页面 fragment
 */
public class FavoriteFragment extends Fragment
        implements PopupMenu.OnMenuItemClickListener {

    /** 显示仓库类型 */
    @BindView(R.id.tvGroupType)TextView tvGroupType;

    /** 菜单按钮 */
    @BindView(R.id.btnFilter)ImageButton btnFilter;

    /** 显示每个仓库的数据 */
    @BindView(R.id.listView)ListView listView;

    private FavoriteAdapter adapter;

    private ActivityUtils activityUtils;

    /** 仓库类别DAO(数据的添删改查) */
    private RepoGroupDao repoGroupDao;

    /** 本地仓库DAO(数据的添删改查) */
    private LocalRepoDao localRepoDao;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUtils = new ActivityUtils(this);
        repoGroupDao = new RepoGroupDao(
                DBHelper.getInstance(getContext()));
        localRepoDao = new LocalRepoDao(
                DBHelper.getInstance(getContext()));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(
                R.layout.fragment_favorite,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);

        adapter = new FavoriteAdapter();
        listView.setAdapter(adapter);
        //默认显示全部仓库
        setData(R.id.repo_group_all);
        /** 注册上下文菜单(需要指明注册在谁的身上) */
        registerForContextMenu(listView);
    }

    /** 显示菜单项 */
    @OnClick(R.id.btnFilter)
    public void showPopUpMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(getContext(), view);
        //布局填充（上面只有“全部”和“未分类”）
        popupMenu.inflate(R.menu.menu_popup_repo_groups);
        //添加自己的menu项
        //拿到menu
        Menu menu = popupMenu.getMenu();
        //获取数据
        List<RepoGroupTable> repoGroupTables = repoGroupDao.queryForAll();
        //挨个添加
        for (RepoGroupTable repoGroupTable : repoGroupTables) {
            //add(int groupId, int itemId, int order, @StringRes int titleRes);
            menu.add(Menu.NONE, repoGroupTable.getId(),
                    Menu.NONE, repoGroupTable.getName());
        }
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(this);
    }

    /** 当前仓库类别 */
    private int currentRepoGroupId;

    /** 菜单项点击监听 */
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        tvGroupType.setText(item.getTitle().toString());
        //保存当前选择的类别
        currentRepoGroupId = item.getItemId();
        setData(currentRepoGroupId);
        return true;
    }

    /** 在 adapter 里上添加数据 */
    private void setData(int groupId) {
        switch (groupId) {
            case R.id.repo_group_all:
                adapter.setData(localRepoDao.queryForAll());
                break;
            case R.id.repo_group_no:
                adapter.setData(localRepoDao.queryForNoGroup());
                break;
            default:
                adapter.setData(localRepoDao.queryForGroupId(groupId));
                break;
        }
    }

    /** 当前操作的仓库(上下文菜单) */
    private LocalRepo currentLocalRepo;

    /** 创建上下文菜单 */
    @Override
    public void onCreateContextMenu(
            ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        if (v.getId() == R.id.listView) {//判断长按是否作用在listview上面
            //得到当前listview的上下文菜单在选择时选择的位置
            AdapterView.AdapterContextMenuInfo adapterContextMenuInfo =
                    (AdapterView.AdapterContextMenuInfo) menuInfo;
            int position = adapterContextMenuInfo.position;
            currentLocalRepo = adapter.getItem(position);
            MenuInflater menuInflater = getActivity().getMenuInflater();
            menuInflater.inflate(R.menu.menu_context_favorite,menu);
            // 拿到子菜单,添加内容
            SubMenu subMenu = menu.findItem(R.id.sub_menu_move).getSubMenu();
            List<RepoGroupTable> repoGroups = repoGroupDao.queryForAll();
            // 都添加到menu_group_move这个组上
            for (RepoGroupTable groupTable : repoGroups) {
                subMenu.add(R.id.sub_menu_move, groupTable.getId(),
                        Menu.NONE,groupTable.getName());
            }
        }
    }

    /** 菜单选中监听 */
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        /** 删除 */
        int id = item.getItemId();
        if (id == R.id.delete) {
            //删除当前选中的本地仓库（长按listview某一个item后，将弹出contextmenu）
            localRepoDao.delete(currentLocalRepo);
            //重置当前选择的分类下的本地仓库列表
            setData(currentRepoGroupId);
            return true;
        }

        /** 移动至 */
        int groupId = item.getGroupId();
        if (groupId == R.id.menu_group_move) {
            //未分类
            if (id == R.id.repo_group_no) {
                //将当前仓库的group设置为空，即未分类
                currentLocalRepo.setRepoGroup(null);
            }
            //其他分类 id = 1,2,3,4,5,6
            else {
                // 得到“其它分类”的类别对象,将当前选择的本地仓库类别重置为当前类别
                currentLocalRepo.setRepoGroup(repoGroupDao.queryForId(id));
            }
            //更新数据库
            localRepoDao.createOrUpdate(currentLocalRepo);
            setData(currentRepoGroupId);
            return true;
        }
        return super.onContextItemSelected(item);
    }
}
