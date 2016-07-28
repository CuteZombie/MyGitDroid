package edu.zhuoxin.mygitdroid.hotrepo.view;

import java.util.List;

/**
 * Created by Administrator on 2016/7/28.
 * 加载更多的视图抽象
 */
public interface RepoListLoadMoreView {

    void showLoadMoreLoading();

    void hideLoadMore();

    void showLoadMoreError(String msg);

    void addMoreData(List<String> datas);

}
