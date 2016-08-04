package edu.zhuoxin.mygitdroid.hotuser.userlist.view;

import java.util.List;

import edu.zhuoxin.mygitdroid.login.modle.User;

/**
 * Created by Administrator on 2016/8/2.
 * 上拉加载更多接口
 */
public interface HotUserLoadMoreView {

    /** 显示上拉加载时的加载中视图 */
    void showLoadMoreLoading();

    /** 隐藏上拉加载时的加载中视图 */
    void hideLoadMore();

    /** 隐藏上拉加载时的错误视图 */
    void showLoadMoreErro(String erroMsg);

    void addMoreData(List<User> datas);

}
