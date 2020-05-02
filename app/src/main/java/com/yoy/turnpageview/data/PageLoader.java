package com.yoy.turnpageview.data;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

import com.yoy.turnpageview.widget.view.PageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by newbiechen on 17-7-1.
 */
public class PageLoader {
    private Context mContext;
    // 页面显示类
    private PageView mPageView;
    // 当前显示的页
    private PageBean mCurPage;
    //页面的视图列表
    private List<PageBean> mCurPageList = new ArrayList<>();
    //应用的宽高
    private int mDisplayWidth;
    private int mDisplayHeight;

    /*****************************init params*******************************/
    public PageLoader(PageView pageView) {
        mPageView = pageView;
        mContext = pageView.getContext();
        mPageView.drawCurPage(false);
        mPageView.setPageMode();
    }

    /**
     * 打开默认页面。
     * 没有指定都是第一页
     */
    public void openChapter() {
        mCurPage = getCurPage(0);
        mPageView.drawCurPage(false);
    }

    public void drawPage(Bitmap bitmap, boolean isUpdate) {
        if (!isUpdate) {
            drawContent(bitmap);
        }
        //更新绘制
        mPageView.invalidate();
    }

    private void drawContent(Bitmap bitmap) {
        if (mCurPage == null) return;
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);
        mCurPage.getView().layout(0, 0, mDisplayWidth, mDisplayHeight);
        mCurPage.getView().draw(canvas);
    }

    public void prepareDisplay(int w, int h) {
        // 获取PageView的宽高
        mDisplayWidth = w;
        mDisplayHeight = h;
        mPageView.setPageMode();
        mPageView.drawCurPage(false);
    }

    /**
     * 翻到上一页
     */
    public boolean prev() {
        PageBean prevPage = getPrevPage();
        if (prevPage == null) return false;
        mCurPage = prevPage;
        mPageView.drawNextPage();
        return true;
    }

    /**
     * 翻到下一页
     */
    public boolean next() {
        PageBean nextPage = getNextPage();
        if (nextPage == null) return false;
        mCurPage = nextPage;
        mPageView.drawNextPage();
        return true;
    }

    /**
     * @return 获取初始显示的页面
     */
    private PageBean getCurPage(int pos) {
        return mCurPageList.get(pos);
    }

    /**
     * @return 获取上一个页面
     */
    private PageBean getPrevPage() {
        int pos = mCurPage.getPosition() - 1;
        if (pos < 0) {
            return null;
        }
        return mCurPageList.get(pos);
    }

    /**
     * @return 获取下一的页面
     */
    private PageBean getNextPage() {
        int pos = mCurPage.getPosition() + 1;
        if (pos >= mCurPageList.size()) {
            return null;
        }
        return mCurPageList.get(pos);
    }

    //region  页面列表管理
    public void addPage(View view) {
        addPage(new PageBean(view));
    }

    public void addPage(PageBean bean) {
        mCurPageList.add(bean);
        bean.setPosition(mCurPageList.size() - 1);
    }

    public boolean removePage(int index) {
        if (mCurPageList.size() <= 1 || mCurPageList.size() >= index) return false;
        mCurPageList.remove(index);
        return true;
    }
    //endregion

    /**
     * 单位转换，dp->px
     */
    private int dpToPx(int dp) {
        DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, metrics);
    }

    /**
     * 单位转换，sp->px
     */
    private int spToPx(int sp) {
        DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, metrics);
    }
}