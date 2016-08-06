package edu.zhuoxin.mygitdroid.hotuser.userlist.view;

import java.util.List;

import edu.zhuoxin.mygitdroid.login.modle.User;

/**
 * Created by Administrator on 2016/8/2.
 * 下拉刷新接口
 */
public interface HotUserPtrView {

    /** 显示下拉刷新时的内容视图 */
    void showRefreshView();

    /** 显示下拉刷新时的错误视图 */
    void showErrorView(String msg);

    /** 隐藏下拉刷新的视图 */
    void hideRefreshView();

    void showMessage(String msg);

    void refreshData(List<User> data);
}
