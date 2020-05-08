package com.yoy.turnpageview.data;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.View;

import com.yoy.turnpageview.widget.view.PageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by newbiechen on 17-7-1.
 */
public class PageLoader {
    // 页面显示类
    private PageView mPageView;
    // 当前显示的页
    private PageBean mCurPage;
    // 被遮盖的页，或者认为被取消显示的页
    private PageBean mCancelPage;
    //页面的视图列表
    private List<PageBean> mCurPageList = new ArrayList<>();
    //应用的宽高
    private int mDisplayWidth;
    private int mDisplayHeight;

    /*****************************init params*******************************/
    public PageLoader(PageView pageView) {
        mPageView = pageView;
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
        mCancelPage = mCurPage;
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
        mCancelPage = mCurPage;
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
        mCancelPage = mCurPage;
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

    /**
     * 取消翻页
     * 当放弃翻页时必须调用这段代码，否则会出现页序错乱的情况
     */
    public void pageCancel() {
        mCurPage = mCancelPage;
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
}