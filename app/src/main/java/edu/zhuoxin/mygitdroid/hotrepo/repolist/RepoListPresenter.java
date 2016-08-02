package edu.zhuoxin.mygitdroid.hotrepo.repolist;

import java.util.List;

import edu.zhuoxin.mygitdroid.hotrepo.Language;
import edu.zhuoxin.mygitdroid.hotrepo.repolist.modle.Repo;
import edu.zhuoxin.mygitdroid.hotrepo.repolist.modle.RepoResult;
import edu.zhuoxin.mygitdroid.hotrepo.repolist.view.RepoListView;
import edu.zhuoxin.mygitdroid.network.GitHubClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2016/7/28.
 *
 */
public class RepoListPresenter {

    /** 视图的接口 */
    private RepoListView repoListView;
    private int nextPage = 0;
    private Language language;

    private Call<RepoResult> repoCall;

    public RepoListPresenter(RepoListView repoListView, Language language) {
        this.repoListView = repoListView;
        this.language = language;
    }

    /** 下拉刷新处理 */
    public void refresh() {
        repoListView.hideLoadMore();// 隐藏loadmore
        repoListView.showContentView();//显示内容
        nextPage = 1; // 永远刷新最新数据
        repoCall = GitHubClient.getInstance().searchRepos(
                "language:" + language.getPath()
                , nextPage);
        repoCall.enqueue(repoCallback);
    }

    /** 下拉加载更多 */
    public void loadMore() {
        repoListView.showLoadMoreLoading();//显示加载中
        repoCall = GitHubClient.getInstance().searchRepos(
                "language:" + language.getPath()
                , nextPage);
        repoCall.enqueue(loadMoreCallback);
    }

    private final Callback<RepoResult> loadMoreCallback = new Callback<RepoResult>(){

        @Override
        public void onResponse(Call<RepoResult> call, Response<RepoResult> response) {
            repoListView.hideLoadMore();
            // 得到响应结果
            RepoResult repoResult = response.body();
            if (repoResult == null) {
                repoListView.showLoadMoreError("结果为空!");
                return;
            }
            // 取出当前语言下的所有仓库
            List<Repo> repoList = repoResult.getRepoList();
            repoListView.addMoreData(repoList);
            nextPage++;
        }

        @Override
        public void onFailure(Call<RepoResult> call, Throwable t) {
            // 视图停止刷新
            repoListView.hideLoadMore();
            repoListView.showMessage("repoCallback onFailure" + t.getMessage());
        }
    };

    private final Callback<RepoResult> repoCallback = new Callback<RepoResult>() {
        @Override public void onResponse(Call<RepoResult> call, Response<RepoResult> response) {
            // 视图停止刷新
            repoListView.stopRefresh();
            // 得到响应结果
            RepoResult repoResult = response.body();
            if (repoResult == null) {
                repoListView.showErrorView("结果为空!");
                return;
            }
            // 当前搜索的语言,没有仓库
            if (repoResult.getTotalCount() <= 0) {
                repoListView.refreshData(null);
                repoListView.showEmptyView();
                return;
            }
            // 取出当前语言下的所有仓库
            List<Repo> repoList = repoResult.getRepoList();
            repoListView.refreshData(repoList);
            // 下拉刷新成功时为 1 , 下一页则更新为 2
            nextPage = 2;
        }

        @Override public void onFailure(Call<RepoResult> call, Throwable t) {
            // 视图停止刷新
            repoListView.stopRefresh();
            repoListView.showMessage("repoCallback onFailure" + t.getMessage());
        }
    };
}
