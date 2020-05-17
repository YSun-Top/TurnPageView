package com.yoy.turnpageview.widget.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;

import com.yoy.turnpageview.data.PageLoader;
import com.yoy.turnpageview.widget.animation.HorizonPageAnim;
import com.yoy.turnpageview.widget.animation.PageAnimation;
import com.yoy.turnpageview.widget.animation.SimulationPageAnim;

/**
 * Created by Administrator on 2016/8/29 0029.
 * 原作者的GitHub Project Path:(https://github.com/PeachBlossom/treader)
 * 绘制页面显示内容的类
 */
public class PageView extends AdapterView implements PageAnimation.OnPageChangeListener {
    private final static String TAG = "BookPageWidget";
    private int mViewWidth = 0; // 当前View的宽
    private int mViewHeight = 0; // 当前View的高
    //用于手势事件中，MotionEvent.ACTION_DOWN手指按下时的坐标，用于检测手指是否移动
    private int mStartX = 0;
    private int mStartY = 0;
    private boolean isMove = false;
    //是否准备完毕
    private boolean isPrepare = false;
    //内容加载器
    private PageLoader mPageLoader;
    // 动画类
    private PageAnimation mPageAnim;

    public PageView(Context context) {
        this(context, null);
    }

    public PageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //因为viewGroup和view的区别，viewGroup默认不会调用onDraw，
        // 所以需要设置为false或给view设置背景
        setWillNotDraw(false);
    }

    @Override
    public Adapter getAdapter() {
        return null;
    }

    @Override
    public void setAdapter(Adapter adapter) {

    }

    @Override
    public View getSelectedView() {
        return null;
    }

    @Override
    public void setSelection(int position) {

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth = w;
        mViewHeight = h;
        isPrepare = true;
        if (mPageLoader != null) {
            mPageLoader.prepareDisplay(w, h);
        }
    }

    //设置翻页的模式
    public void setPageMode() {
        //视图未初始化的时候，禁止调用
        if (mViewWidth == 0 || mViewHeight == 0) return;
        mPageAnim = new SimulationPageAnim(mViewWidth, mViewHeight, this, this);
    }

    public Bitmap getNextBitmap() {
        if (mPageAnim == null) return null;
        return mPageAnim.getNextBitmap();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (!isPrepare || mPageLoader == null) return;
        //绘制动画
        mPageAnim.draw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mStartX = x;
                mStartY = y;
                isMove = false;
                mPageAnim.onTouchEvent(event);
                break;
            case MotionEvent.ACTION_MOVE:
                // 判断是否大于最小滑动值。
                int slop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
                if (!isMove) {
                    isMove = Math.abs(mStartX - x) > slop || Math.abs(mStartY - y) > slop;
                }
                // 如果滑动了，则进行翻页。
                if (isMove) {
                    mPageAnim.onTouchEvent(event);
                }
                break;
            case MotionEvent.ACTION_UP:
                mPageAnim.onTouchEvent(event);
                break;
        }
        return true;
    }

    /**
     * 判断是否存在上一页，如果有将执行翻页
     */
    private boolean hasPrevPage() {
        return mPageLoader.prev();
    }

    /**
     * 判断是否下一页存在，如果有将执行翻页
     */
    private boolean hasNextPage() {
        return mPageLoader.next();
    }

    /*
     * 取消翻页
     * */
    private void onPageCancel() {
        mPageLoader.pageCancel();
    }

    @Override
    public void computeScroll() {
        if (!isPrepare || mPageLoader == null) return;
        //进行滑动
        mPageAnim.scrollAnim();
        super.computeScroll();
    }

    public void drawNextPage() {
        if (!isPrepare) return;
        if (mPageAnim instanceof HorizonPageAnim) {
            ((HorizonPageAnim) mPageAnim).changePage();
        }
        mPageLoader.drawPage(getNextBitmap(), false);
    }

    /**
     * 绘制当前页。
     */
    public void drawCurPage(boolean isUpdate) {
        if (!isPrepare || mPageLoader == null) return;
        mPageLoader.drawPage(getNextBitmap(), isUpdate);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mPageAnim.abortAnim();
        mPageAnim.clear();
        mPageLoader = null;
        mPageAnim = null;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    /**
     * 获取 PageLoader
     */
    public PageLoader getPageLoader() {
        // 判是否已经存在
        if (mPageLoader != null) {
            return mPageLoader;
        }
        mPageLoader = new PageLoader(this);
        return mPageLoader;
    }

    @Override
    public boolean hasPrev() {
        return hasPrevPage();
    }

    @Override
    public boolean hasNext() {
        return hasNextPage();
    }

    @Override
    public void pageCancel() {
        onPageCancel();
    }
}
