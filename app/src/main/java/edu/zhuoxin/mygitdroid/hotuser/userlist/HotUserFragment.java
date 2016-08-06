package edu.zhuoxin.mygitdroid.hotuser.userlist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.mugen.Mugen;
import com.mugen.MugenCallbacks;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.zhuoxin.mygitdroid.R;
import edu.zhuoxin.mygitdroid.commons.ActivityUtils;
import edu.zhuoxin.mygitdroid.component.FooterView;
import edu.zhuoxin.mygitdroid.hotuser.userlist.view.HotUserListView;
import edu.zhuoxin.mygitdroid.login.modle.User;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by Administrator on 2016/8/2.
 *
 */
public class HotUserFragment extends Fragment implements HotUserListView{

    private ActivityUtils activityUtils;

    /** 列表视图 */
    @BindView(R.id.lvUsers) ListView lvUsers;

    /** 没有更多数据时的空视图 */
    @BindView(R.id.emptyView) TextView emptyView;

    /** 数据获取时的错误视图 */
    @BindView(R.id.errorView) TextView errorView;

    @BindView(R.id.ptrClassicFrameLayout) PtrClassicFrameLayout ptrClassicFrameLayout;

    /** 上拉加载更多的视图 */
    private FooterView footerView;
    private HotUserPresenter presenter;
    private HotUserAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hot_user,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        activityUtils = new ActivityUtils(this);
        presenter = new HotUserPresenter(this);
        //
        adapter = new HotUserAdapter();
        lvUsers.setAdapter(adapter);
        // 初始化下拉刷新
        initPtrRefresh();
        // 初始化上拉加载更多
        initLoadMore();
        // 如果当前页面没有数据，开始自动刷新
        if (adapter.getCount() == 0) {
            ptrClassicFrameLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ptrClassicFrameLayout.autoRefresh();
                }
            }, 200);
        }
    }

    /** 下拉刷新 */
    private void initPtrRefresh() {
        ptrClassicFrameLayout.setLastUpdateTimeRelateObject(this);
        ptrClassicFrameLayout.setDurationToCloseHeader(1500);
        ptrClassicFrameLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                presenter.refresh();
            }
        });
    }

    /** 上拉加载更多 */
    private void initLoadMore() {
        footerView = new FooterView(getContext());
        Mugen.with(lvUsers, new MugenCallbacks() {
            @Override
            public void onLoadMore() {
                presenter.LoadMore();
            }

            @Override
            public boolean isLoading() {
                return lvUsers.getFooterViewsCount() > 0 && footerView.isLoading();
            }

            @Override
            public boolean hasLoadedAllItems() {
                return lvUsers.getFooterViewsCount() > 0 && footerView.isComplete();
            }
        }).start();
    }

    @Override
    public void showLoadMoreLoading() {
        if (lvUsers.getFooterViewsCount() == 0) {
            lvUsers.addFooterView(footerView);
        }
        footerView.showLoading();
    }

    @Override
    public void hideLoadMore() {
        lvUsers.removeFooterView(footerView);
    }

    @Override
    public void showLoadMoreErro(String erroMsg) {
        if (lvUsers.getFooterViewsCount() == 0) {
            lvUsers.addFooterView(footerView);
        }
        footerView.showError(erroMsg);
    }

    @Override
    public void addMoreData(List<User> datas) {
        adapter.addAll(datas);
    }

    @Override
    public void showRefreshView() {
        ptrClassicFrameLayout.setVisibility(View.VISIBLE);
        emptyView.setVisibility(View.GONE);
        errorView.setVisibility(View.GONE);
    }

    @Override
    public void showErrorView(String msg) {
        ptrClassicFrameLayout.setVisibility(View.GONE);
        emptyView.setVisibility(View.GONE);
        errorView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideRefreshView() {
        ptrClassicFrameLayout.refreshComplete();
    }

    @Override
    public void showMessage(String msg) {
        activityUtils.showToast(msg);
    }

    @Override
    public void refreshData(List<User> data) {
        adapter.clear();
        adapter.addAll(data);
    }
}
