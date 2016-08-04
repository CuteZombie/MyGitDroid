package edu.zhuoxin.mygitdroid.hotuser.userlist.view;

import java.util.List;

import edu.zhuoxin.mygitdroid.login.modle.User;

/**
 * Created by Administrator on 2016/8/2.
 * 下拉刷新接口
 */
public interface HotUserPtrView {

    /** 显示下拉刷新时的内容视图 */
    void showContentView();

    /** 显示下拉刷新时的错误视图 */
    void showErrorView(String msg);

    /** 显示下拉刷新时的空视图 */
    void showEmptyView();

    void showMessage(String msg);

    void stopRefresh();

    void refreshData(List<User> data);
}
