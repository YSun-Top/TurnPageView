package com.yoy.turnpageview.widget.animation;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Scroller;

/**
 * Created by newbiechen on 17-7-24.
 * 翻页动画抽象类
 */
public abstract class PageAnimation {
    //正在使用的View
    View mView;
    //滑动装置
    Scroller mScroller;
    //监听器
    OnPageChangeListener mListener;
    //移动方向
    Direction mDirection = Direction.NONE;
    boolean isRunning = false;

    //屏幕的尺寸
    int mScreenWidth;
    int mScreenHeight;
    //视图的尺寸
    int mViewWidth;
    int mViewHeight;
    //起始点
    float mStartX;
    float mStartY;
    //触碰点
    float mTouchX;
    float mTouchY;

    PageAnimation(int w, int h, int marginWidth, int marginHeight, View view, OnPageChangeListener listener) {
        mScreenWidth = w;
        mScreenHeight = h;

        //屏幕的间距

        mViewWidth = mScreenWidth - marginWidth * 2;
        mViewHeight = mScreenHeight - marginHeight * 2;

        mView = view;
        mListener = listener;

        mScroller = new Scroller(mView.getContext(), new LinearInterpolator());
    }

    public void setStartPoint(float x, float y) {
        mStartX = x;
        mStartY = y;
    }

    public void setTouchPoint(float x, float y) {
        mTouchX = x;
        mTouchY = y;
    }

    public boolean isRunning() {
        return isRunning;
    }

    /**
     * 开启翻页动画
     */
    public void startAnim() {
        if (isRunning) {
            return;
        }
        isRunning = true;
    }

    public void setDirection(Direction direction) {
        mDirection = direction;
    }

    public Direction getDirection() {
        return mDirection;
    }

    public void clear() {
        mView = null;
    }

    /**
     * 点击事件的处理
     */
    public abstract void onTouchEvent(MotionEvent event);

    /**
     * 绘制图形
     */
    public abstract void draw(Canvas canvas);

    /**
     * 滚动动画
     * 必须放在computeScroll()方法中执行
     */
    public abstract void scrollAnim();

    /**
     * 取消动画
     */
    public abstract void abortAnim();

    /**
     * 获取内容显示版面
     */
    public abstract Bitmap getNextBitmap();

    public enum Direction {
        NONE(true), NEXT(true), PRE(true);

        public final boolean isHorizontal;

        Direction(boolean isHorizontal) {
            this.isHorizontal = isHorizontal;
        }
    }

    public interface OnPageChangeListener {
        boolean hasPrev();

        boolean hasNext();
    }
}
