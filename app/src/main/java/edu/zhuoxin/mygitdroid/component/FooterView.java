package edu.zhuoxin.mygitdroid.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.zhuoxin.mygitdroid.R;

/**
 * Created by Administrator on 2016/7/28.
 * 上拉加载更多视图
 * 当滚动到尾部时，通过addFooterView方法把该视图添加到listview底部
 * 同时在后台加载更多数据
 *
 * 三种状态：显示loading   显示错误信息   加载完成
 */
public class FooterView extends FrameLayout{

    private static final int STATE_LOADING = 0;
    private static final int STATE_COMPLETE = 1;
    private static final int STATE_ERROR = 2;

    /** 状态默认为加载中 */
    private int state = STATE_LOADING;

    @BindView(R.id.progressBar)ProgressBar progressBar;
    @BindView(R.id.tv_error)TextView tv_error;

    public FooterView(Context context) {
        this(context,null);
    }

    public FooterView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public FooterView(Context context, AttributeSet attrs,
                      int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext())
                .inflate(R.layout.content_load_footer,this,true);
        ButterKnife.bind(this);
    }

    /** 是否正在加载中 */
    public boolean isLoading() {
        return state == STATE_LOADING;
    }

    /** 是否加载完成 */
    public boolean isComplete() {
        return state == STATE_COMPLETE;
    }

    /** 显示加载中 */
    public void showLoading() {
        state = STATE_LOADING;
        progressBar.setVisibility(View.VISIBLE);
        tv_error.setVisibility(View.GONE);
    }

    /** 显示加载完成 */
    public void showComplete() {
        state = STATE_COMPLETE;
        progressBar.setVisibility(View.GONE);
        tv_error.setVisibility(View.GONE);
    }

    /** 显示错误信息 */
    public void showError(String errorMsg) {
        state = STATE_ERROR;
        progressBar.setVisibility(View.GONE);
        tv_error.setVisibility(View.VISIBLE);
    }

    public void setErrorClickListener(OnClickListener onClickListener) {
        tv_error.setOnClickListener(onClickListener);
    }

}
