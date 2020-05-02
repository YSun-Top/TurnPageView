package com.yoy.turnpageview.widget;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.yoy.turnpageview.data.PageBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Void on 2020/5/2 22:19
 */
public class PageAdapter extends BaseAdapter {
    private List<PageBean> items = new ArrayList<>();

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public PageBean getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    public void addPage(View view) {
        addPage(new PageBean(view));
    }

    public void addPage(PageBean bean) {
        items.add(bean);
        bean.setPosition(items.size() - 1);
    }

    public void addAllPage(List<PageBean> items) {
        this.items = items;
    }

    public boolean removePage(int index) {
        if (items.size() <= 1 || items.size() >= index) return false;
        items.remove(index);
        return true;
    }
}
