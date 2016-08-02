package edu.zhuoxin.mygitdroid.hotrepo.repolist.view;

import java.util.List;

import edu.zhuoxin.mygitdroid.hotrepo.repolist.modle.Repo;

/**
 * Created by Administrator on 2016/7/28.
 *
 */
public interface RepoListPtrView {
    /** 刷新的方法 */
    //视图上，显示内容 or 错误 or 空白，三选一
    void showContentView();

    void showErrorView(String errorMsg);

    void showEmptyView();

    //显示提示信息 如toast，直接在当前页面上显示
    void showMessage(String msg);

    //刷新数据
    //将后台线程更新加载到的数据，刷新显示到视图（当前为listview）上给用户看
    void refreshData(List<Repo> data);

    /** 停止刷新 */
    void stopRefresh();
}
