package edu.zhuoxin.mygitdroid.splash;


import android.animation.ArgbEvaluator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import butterknife.ButterKnife;
import edu.zhuoxin.mygitdroid.R;
import me.relex.circleindicator.CircleIndicator;

/**
 * Created by Administrator on 2016/7/26.
 *
 */
public class SplashPagerFragment extends android.support.v4.app.Fragment{

    private ViewPager viewPager;
    private SplashPagerAdapter adapter;
    private CircleIndicator indicator;//指示器
    private FrameLayout frameLayout;//当前页面layout（为了更新背景颜色）

    private FrameLayout layoutPhone;//屏幕中央的“手机”
    private ImageView ivPhoneFront;

    private int colorGreen;
    private int colorRed;
    private int colorYellow;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_splash_pager,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(view);
        colorGreen = getResources().getColor(R.color.colorGreen);
        colorRed = getResources().getColor(R.color.colorRed);
        colorYellow = getResources().getColor(R.color.colorYellow);

        layoutPhone = (FrameLayout) view.findViewById(R.id.layoutPhone);
        ivPhoneFront = (ImageView) view.findViewById(R.id.ivPhoneFont);

        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        indicator = (CircleIndicator) view.findViewById(R.id.indicator);
        frameLayout = (FrameLayout) view.findViewById(R.id.content);

        adapter = new SplashPagerAdapter(getContext());
        viewPager.setAdapter(adapter);
        indicator.setViewPager(viewPager);

        /** 使用add可以添加多个监听，能同时响应多个监听 **/
        viewPager.addOnPageChangeListener(pageColorListener);
        viewPager.addOnPageChangeListener(phoneViewListener);
    }

    /** 背景颜色渐变处理 */
    private ViewPager.OnPageChangeListener pageColorListener = new ViewPager.OnPageChangeListener() {

        final ArgbEvaluator argbEvaluator = new ArgbEvaluator();

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            /** 在第一个和第二个页面之间 */
            if (position == 0) {
                int color = (int) argbEvaluator.evaluate(positionOffset,colorGreen,colorRed);
                frameLayout.setBackgroundColor(color);
                return;
            }

            /** 在第二个和第三个页面之间 */
            if (position == 1) {
                int color = (int) argbEvaluator.evaluate(positionOffset,colorRed,colorYellow);
                frameLayout.setBackgroundColor(color);
                return;
            }
        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    /** “手机”动画效果处理（平移、缩放、透明度变化） */
    private ViewPager.OnPageChangeListener phoneViewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            /** 第一个页面与第二个页面之间 */
            if (position == 0) {
                /** “手机”图片的缩放处理 */
                float scale = 0.4f + positionOffset * 0.6f;
                layoutPhone.setScaleX(scale);
                layoutPhone.setScaleY(scale);
                /** “手机”图片的平移处理 */
                int scroll = (int) ((positionOffset - 1) * 220);
                layoutPhone.setTranslationX(scroll);
                /** “手机”图片字体的渐变 */
                ivPhoneFront.setAlpha(positionOffset);
                return;
            }

            /** 在第二个页面和第三个页面之间 */
            if (position == 1) {
                layoutPhone.setTranslationX(-positionOffsetPixels);
            }

        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

}
