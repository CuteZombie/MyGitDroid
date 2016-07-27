package edu.zhuoxin.mygitdroid.commons;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2016/7/27.
 * 此类包含与Activity相关的一些常用方法，例如startActivity和showToast。
 * 我使用这个类做为BaseActivity的替代方案，以避免过深的继承树引入的复杂性。
 * 有点类似于一种不太标准的委托模式。
 */
public class ActivityUtils {


    //使用弱引用，避免不恰当地持有Activity或Fragment的引用
    //持有Activity的引用会阻止Activity的内存回收，增大OOM的风险。
    private WeakReference<Activity> activityWeakReference;
    private WeakReference<Fragment> fragmentWeakReference;

    private Toast toast;

    public ActivityUtils(Activity activity) {
        activityWeakReference = new WeakReference<Activity>(activity);
    }

    public ActivityUtils(Fragment fragment) {
        fragmentWeakReference = new WeakReference<Fragment>(fragment);
    }

    /**
     * 通过弱引用获取Activity对象，此方法可能返回null，调用后需要做检查。
     * */
    private @Nullable Activity getActivity() {

        if (activityWeakReference != null) {
            return activityWeakReference.get();
        }
        if (fragmentWeakReference != null) {
            Fragment fragment = fragmentWeakReference.get();
            return fragment == null ? null : fragment.getActivity();
        }
        return null;
    }

    public void showToast(CharSequence msg) {
        Activity activity = getActivity();
        if (activity != null) {
            if (toast == null) {
                toast = Toast.makeText(activity, msg, Toast.LENGTH_SHORT);
                toast.setText(msg);
                toast.show();
            }
        }
    }

    @SuppressWarnings("SameParameterValue") public void showToast(@StringRes int resId) {
        Activity activity = getActivity();
        if (activity != null) {
            String msg = activity.getString(resId);
            showToast(msg);
        }
    }

    public void startActivity(Class<? extends Activity> clazz) {
        Activity activity = getActivity();
        if (activity == null) return;
        Intent intent = new Intent(activity,clazz);
        activity.startActivity(intent);
    }

    public int getStatusBarHeight() {
        Activity activity = getActivity();
        if (activity == null) return 0;

        Resources resources = getActivity().getResources();
        int result = 0;
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId);
        }
        LogUtils.v("getStatusBarHeight: " + result);
        return result;
    }

    public int getScreenWidth() {
        Activity activity = getActivity();
        if (activity == null) return  0;

        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }

    public int getScreenHeight() {
        Activity activity = getActivity();
        if (activity == null) return  0;

        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.heightPixels;
    }

    /** 调用系统输入法 */
    public void hideSoftKeyboard(){
        Activity activity = getActivity();
        if (activity == null) return;

        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void startBrowser(String url){
        if (getActivity() == null) return;
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.parse(url);
        intent.setData(uri);
        getActivity().startActivity(intent);
    }

}
