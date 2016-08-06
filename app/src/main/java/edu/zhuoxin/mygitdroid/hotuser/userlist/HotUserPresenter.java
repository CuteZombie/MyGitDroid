package edu.zhuoxin.mygitdroid.hotuser.userlist;

import android.support.annotation.NonNull;

import java.util.List;

import edu.zhuoxin.mygitdroid.hotuser.userlist.modle.HotUserResult;
import edu.zhuoxin.mygitdroid.hotuser.userlist.view.HotUserListView;
import edu.zhuoxin.mygitdroid.login.modle.User;
import edu.zhuoxin.mygitdroid.network.GitHubClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2016/8/2.
 * 热门开发者业务(视图业务及数据处理业务)
 */
public class HotUserPresenter {

    private HotUserListView userListView;

    private Call<HotUserResult> userResultCall;

    private int nextPage = 0;

    public HotUserPresenter(@NonNull HotUserListView userListView) {
        this.userListView = userListView;
    }

    /** 下拉刷新 */
    public void refresh() {
        userListView.hideRefreshView();
        userListView.showRefreshView();
        nextPage = 1;//刷新最新数据
        if (userResultCall != null) userResultCall.cancel();
        userResultCall = GitHubClient.getInstance()
                .searchUsers("followers:>1000", nextPage);
        userResultCall.enqueue(ptrCallback);
    }

    /** 下拉刷新 CallBack */
    private Callback<HotUserResult> ptrCallback = new Callback<HotUserResult>() {
        @Override
        public void onResponse(Call<HotUserResult> call, Response<HotUserResult> response) {
            userListView.hideRefreshView();
            if (response.isSuccessful()) {
                HotUserResult hotUserResult = response.body();
                if (hotUserResult == null) {
                    userListView.showErrorView("结果为空！");
                    return;
                }
                //取出搜索到的所有用户
                List<User> list = hotUserResult.getAllUsers();
                userListView.refreshData(list);
                nextPage = 2;
                return;
            }
            userListView.showMessage("refresh onResponse" + response.code());
        }

        @Override
        public void onFailure(Call<HotUserResult> call, Throwable t) {
            userListView.hideRefreshView();
            userListView.showMessage("ptrCallback onFailure" + t.getMessage());
        }
    };

    /** 上拉加载更多 */
    public void LoadMore() {
        userListView.showLoadMoreLoading();
        // HTTP API处理
        if (userResultCall != null) userResultCall.cancel();
        userResultCall = GitHubClient.getInstance()
                .searchUsers("followers:>1000", nextPage);
        userResultCall.enqueue(loadMoreCallback);
    }

    /** 上拉加载更多 CallBack */
    private Callback<HotUserResult> loadMoreCallback = new Callback<HotUserResult>() {
        @Override
        public void onResponse(Call<HotUserResult> call, Response<HotUserResult> response) {
            userListView.hideLoadMore();
            if (response.isSuccessful()) {
                HotUserResult hotUserResult = response.body();
                if (hotUserResult == null) {
                    userListView.showLoadMoreErro("结果为空！");
                    return;
                }
                List<User> list = hotUserResult.getAllUsers();
                userListView.addMoreData(list);
                nextPage++;
                return;
            }
            userListView.showMessage("loadMore onResponse " + response.code());
        }

        @Override
        public void onFailure(Call<HotUserResult> call, Throwable t) {
            userListView.hideLoadMore();//视图停止刷新
            userListView.showMessage("loadMoreCallback onFailure" + t.getMessage());
        }
    };
}
