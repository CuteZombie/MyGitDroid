package edu.zhuoxin.mygitdroid.splash.pager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.zhuoxin.mygitdroid.R;

/**
 * Created by Administrator on 2016/7/26.
 *
 */
public class Pager2 extends FrameLayout{

    @BindView(R.id.ivBubble1)ImageView ivBubble1;
    @BindView(R.id.ivBubble2)ImageView ivBubble2;
    @BindView(R.id.ivBubble3)ImageView ivBubble3;

    public Pager2(Context context) {
        this(context,null);
    }

    public Pager2(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public Pager2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext())
                .inflate(R.layout.content_pager_2,this,true);
        ButterKnife.bind(this);

        ivBubble1.setVisibility(View.GONE);
        ivBubble2.setVisibility(View.GONE);
        ivBubble3.setVisibility(View.GONE);
    }

    public void showAnimation() {
        if (ivBubble1.getVisibility() == View.GONE) {
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    ivBubble1.setVisibility(View.VISIBLE);
                    YoYo.with(Techniques.StandUp).duration(500)
                    .playOn(ivBubble1);
                }
            },100);
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    ivBubble2.setVisibility(View.VISIBLE);
                    YoYo.with(Techniques.StandUp).duration(500)
                            .playOn(ivBubble2);
                }
            },500);
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    ivBubble3.setVisibility(View.VISIBLE);
                    YoYo.with(Techniques.StandUp).duration(500)
                            .playOn(ivBubble3);
                }
            },900);
        }
    }
}
