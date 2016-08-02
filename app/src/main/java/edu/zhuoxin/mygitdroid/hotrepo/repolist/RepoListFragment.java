package edu.zhuoxin.mygitdroid.hotrepo.repolist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.mugen.Mugen;
import com.mugen.MugenCallbacks;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.zhuoxin.mygitdroid.R;
import edu.zhuoxin.mygitdroid.commons.ActivityUtils;
import edu.zhuoxin.mygitdroid.component.FooterView;
import edu.zhuoxin.mygitdroid.hotrepo.Language;
import edu.zhuoxin.mygitdroid.hotrepo.repolist.modle.Repo;
import edu.zhuoxin.mygitdroid.hotrepo.repolist.view.RepoListView;
import edu.zhuoxin.mygitdroid.repoinfo.RepoInfoActivity;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.header.StoreHouseHeader;

/**
 * Created by Administrator on 2016/7/27.
 * 仓库列表
 * 将显示当前语言的所有仓库，有下拉刷新、上拉加载更多
 */
public class RepoListFragment extends Fragment
        implements RepoListView {

    private static final String KEY_LANGUAGE = "key_language";

    public static RepoListFragment getInstance(Language language) {
        RepoListFragment fragment = new RepoListFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_LANGUAGE,language);
        fragment.setArguments(bundle);
        return fragment;
    }

    private Language getLanguage() {
        return (Language) getArguments().getSerializable(KEY_LANGUAGE);
    }

    @BindView(R.id.lvRepos)ListView listView;
    @BindView(R.id.emptyView)TextView emptyView;
    @BindView(R.id.errorView)TextView errorView;
    @BindView(R.id.ptrClassicFrameLayout)PtrClassicFrameLayout ptrFrameLayout;

    private RepoListAdapter adapter;

    /** 用来做当前页面业务逻辑及视图更新 */
    private RepoListPresenter presenter;

    /** 上拉加载更多的视图 */
    private FooterView footerView;

    private ActivityUtils activityUtils;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_repo_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        activityUtils = new ActivityUtils(this);
        presenter = new RepoListPresenter(this,getLanguage());

        adapter = new RepoListAdapter();
        listView.setAdapter(adapter);

        /** 按下某个仓库后，进入此仓库详情 */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Repo repo = adapter.getItem(position);
                RepoInfoActivity.open(getContext(),repo);
            }
        });

        //初始化下拉刷新
        initPullToRefresh();

        //初始化上拉加载更多
        initLoadMoreScroll();
    }

    private void initLoadMoreScroll() {
        footerView = new FooterView(getContext());
        Mugen.with(listView, new MugenCallbacks() {
            /** 当listview滚动到底部的时候触发此方法,执行上拉加载更多的业务 */
            @Override
            public void onLoadMore() {
                presenter.loadMore();
            }

            /** 内部将用此方法来判断是否触发onLoadMore */
            @Override
            public boolean isLoading() {
                return listView.getFooterViewsCount() > 0 && footerView.isLoading();
            }

            /**
             * 是否已加载完成所有数据
             * 其内部将用此方法来判断是否触发onLoadMore
             * */
            @Override
            public boolean hasLoadedAllItems() {
                return listView.getFooterViewsCount() > 0 && footerView.isComplete();
            }
        }).start();
    }

    /** 初始化下拉刷新 */
    private void initPullToRefresh() {
        //使用当前对象作为key，来记录上一次的刷新时间，
        // 如果两次下拉时间太近就不会触发刷新
        ptrFrameLayout.setLastUpdateTimeRelateObject(this);
        //关闭header所用时长
        ptrFrameLayout.setDurationToCloseHeader(1500);

        //下拉刷新时要做的事
        ptrFrameLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                //此处在后台进行获取数据操作
                presenter.refresh();
            }
        });

        /** 以下代码仅仅是修改了header的样式 */
        StoreHouseHeader header = new StoreHouseHeader(getContext());
        header.initWithString("I LIKE " + getLanguage().getName());
        header.setPadding(0,60,0,60);
        /** 修改Ptr的头部 HeaderView 效果 */
        ptrFrameLayout.setHeaderView(header);
        ptrFrameLayout.addPtrUIHandler(header);
        ptrFrameLayout.setBackgroundResource(R.color.colorRefresh);
    }

    /** -------- 下拉刷新视图实现 --------- */

    @Override
    public void showContentView() {
        ptrFrameLayout.setVisibility(View.VISIBLE);
        emptyView.setVisibility(View.GONE);
        errorView.setVisibility(View.GONE);
    }

    @Override
    public void showErrorView(String errorMsg) {
        ptrFrameLayout.setVisibility(View.GONE);
        emptyView.setVisibility(View.GONE);
        errorView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showEmptyView() {
        ptrFrameLayout.setVisibility(View.GONE);
        emptyView.setVisibility(View.VISIBLE);
        errorView.setVisibility(View.GONE);
    }

    @Override
    public void showMessage(String msg) {
        activityUtils.showToast(msg);
    }

    @Override
    public void refreshData(List<Repo> data) {
        adapter.clear();
        adapter.addAll(data);
    }

    @Override
    public void stopRefresh() {
        ptrFrameLayout.refreshComplete();
    }

    /** ------------- 上拉加载更多视图实现 ------------        */

    @Override
    public void showLoadMoreLoading() {
        if (listView.getFooterViewsCount() == 0) {
            listView.addFooterView(footerView);
        }
        footerView.showLoading();
    }

    @Override
    public void hideLoadMore() {
        listView.removeFooterView(footerView);
    }

    @Override
    public void showLoadMoreError(String msg) {
        if (listView.getFooterViewsCount() == 0) {
            listView.addFooterView(footerView);
        }
        footerView.showError(msg);
    }

    @Override
    public void addMoreData(List<Repo> datas) {
        adapter.addAll(datas);
    }
}
