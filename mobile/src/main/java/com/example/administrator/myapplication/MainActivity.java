package com.example.administrator.myapplication;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {
    private TextView mTextView = null;
    private TextView mTextView2 = null;
    private TextView mTextView3 = null;
    private TextView mTextView4 = null;
    private View mFoucusview = null;
    private ViewWarp mViewWarp = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = (TextView) findViewById(R.id.text1);
        mTextView2 = (TextView) findViewById(R.id.text2);
        mTextView3 = (TextView) findViewById(R.id.text3);
        mTextView4 = (TextView) findViewById(R.id.text4);
        mFoucusview = (View) findViewById(R.id.foucusview);
        mTextView.setOnTouchListener(this);
        mTextView2.setOnTouchListener(this);
        mTextView3.setOnTouchListener(this);
        mTextView4.setOnTouchListener(this);
        ViewTreeObserver viewTreeObserver = mTextView.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mTextView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                mFoucusview.setVisibility(View.VISIBLE);
                mFoucusview.getLayoutParams().width = mTextView.getMeasuredWidth();
                mFoucusview.getLayoutParams().height = mTextView.getMeasuredHeight();
                mFoucusview.setX(mTextView.getX());
                mFoucusview.setY(mTextView.getY());
                mViewWarp = new ViewWarp(mTextView, mFoucusview.getLayoutParams().width, mFoucusview.getLayoutParams().height, mTextView.getX(), mTextView.getY());
            }
        });

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float lx = v.getX();
        float ly = v.getY();
        int width = v.getWidth();
        int height = v.getHeight();
        if (v != mViewWarp.view) {
            ObjectAnimator animator1 = ObjectAnimator.ofFloat(mViewWarp.view, View.SCALE_X, v.getScaleX(), 1.0F);
            ObjectAnimator animator2 = ObjectAnimator.ofFloat(mViewWarp.view, View.SCALE_Y, v.getScaleY(), 1.0F);
            AnimatorSet aset = new AnimatorSet();
            aset.playTogether(animator1, animator2);
            aset.setDuration(300).start();
        }
//            PropertyValuesHolder propertyValuesHolderX = PropertyValuesHolder.ofFloat(View.TRANSLATION_X,  mFoucusview.getLeft(), mLoacation[0]);
//            PropertyValuesHolder propertyValuesHolderY = PropertyValuesHolder.ofFloat(View.Y, mLastLoacation[1], mLoacation[1]);
//            ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(mFoucusview, propertyValuesHolderX);
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mFoucusview, View.X, mViewWarp.left, lx);
        ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(mFoucusview, View.Y, mViewWarp.top, ly);
        ObjectAnimator objectAnimator3 = ObjectAnimator.ofFloat(mFoucusview, "width", mViewWarp.width, width);
        objectAnimator3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mFoucusview.getLayoutParams().width = ((Float) animation.getAnimatedValue()).intValue();
                mFoucusview.requestLayout();
            }
        });
        ObjectAnimator objectAnimator4 = ObjectAnimator.ofFloat(mFoucusview, "height", mViewWarp.height, height);
        objectAnimator4.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mFoucusview.getLayoutParams().height = ((Float) animation.getAnimatedValue()).intValue();
                mFoucusview.requestLayout();

            }
        });
        ObjectAnimator objectAnimator5 = ObjectAnimator.ofFloat(mFoucusview, View.SCALE_X, 1.0F, 1.3F);
        ObjectAnimator objectAnimator6 = ObjectAnimator.ofFloat(mFoucusview, View.SCALE_Y, 1.0F, 1.3F);
        ObjectAnimator objectAnimator7 = ObjectAnimator.ofFloat(v, View.SCALE_X, 1.0F, 1.3F);
        ObjectAnimator objectAnimator8 = ObjectAnimator.ofFloat(v, View.SCALE_Y, 1.0F, 1.3F);
        animatorSet.playTogether(objectAnimator, objectAnimator2, objectAnimator3, objectAnimator4, objectAnimator5, objectAnimator6, objectAnimator7, objectAnimator8);
        animatorSet.setDuration(300).start();
        mViewWarp = new ViewWarp(v, width, height, lx, ly);
        return false;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (hasFocus) {
        }
        super.onWindowFocusChanged(hasFocus);
    }

    class ViewWarp {
        View view;
        int width;
        int height;
        float left;
        float top;

        public ViewWarp(View view, int width, int height, float left, float top) {
            this.view = view;
            this.width = width;
            this.height = height;
            this.left = left;
            this.top = top;
        }
    }
}

