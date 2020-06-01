package com.yoy.turnpageview.data;

import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by Void on 2020/5/2 14:41
 */
public class PageBean {
    //显示的view
    private View mView;
    /*
     * 在列表中的位置
     * 它不需要在new PageBean时设置，添加page会自动设置，
     * 请尽量不要主动修改这个值，这可能会导致翻页顺序异常！
     * */
    private int position;

    public PageBean(View mView) {
        setView(mView);
    }

    public View getView() {
        return mView;
    }

    private void setView(View mView) {
        this.mView = mView;
        this.mView.measure(0, 0);
        mView.setLayoutParams(new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
        ));
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}