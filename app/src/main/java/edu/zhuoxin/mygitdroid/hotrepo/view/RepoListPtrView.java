package edu.zhuoxin.mygitdroid.hotrepo.view;

import java.util.List;

/**
 * Created by Administrator on 2016/7/28.
 *
 */
public interface RepoListPtrView {
    /** 刷新的方法 */
    //视图上，显示内容 or 错误 or 空白，三选一
    public void showContentView();

    public void showErrorView(String errorMsg);

    public void showEmptyView();

    //显示提示信息 如toast，直接在当前页面上显示
    public void showMessage(String msg);

    //刷新数据
    //将后台线程更新加载到的数据，刷新显示到视图（当前为listview）上给用户看
    public void refreshData(List<String> data);

    /** 停止刷新 */
    public void stopRefresh();
}
