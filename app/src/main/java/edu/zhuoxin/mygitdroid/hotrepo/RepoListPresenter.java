package edu.zhuoxin.mygitdroid.hotrepo;

import android.os.AsyncTask;

import java.util.ArrayList;

import edu.zhuoxin.mygitdroid.hotrepo.view.RepoListLoadMoreView;
import edu.zhuoxin.mygitdroid.hotrepo.view.RepoListView;

/**
 * Created by Administrator on 2016/7/28.
 *
 */
public class RepoListPresenter {

    private RepoListView repoListView;

    private int count;

    public RepoListPresenter(RepoListView repoListView) {
        this.repoListView = repoListView;
    }

    /** 上拉加载任务 */
    public void loadMore() {
        repoListView.showLoadMoreLoading();
        new LoadMoreTask().execute();
    }

    final class LoadMoreTask extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            ArrayList<String> datas = new ArrayList<String>();
            for (int i = 0; i < 20; i ++) {
                datas.add("测试数据" + (count ++ ));
            }
            repoListView.addMoreData(datas);
            repoListView.hideLoadMore();
        }
    }

    /** 下拉刷新任务 */
    public void refresh() {
        new RefreshTask().execute();
    }

    final class RefreshTask extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            ArrayList<String> datas = new ArrayList<String>();
            for (int i = 0; i < 20; i ++) {
                datas.add("测试数据" + (count ++ ));
            }
            repoListView.stopRefresh();
            repoListView.refreshData(datas);
            repoListView.showContentView();
        }
    }
}
