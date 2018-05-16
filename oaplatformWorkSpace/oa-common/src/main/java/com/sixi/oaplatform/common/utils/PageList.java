package com.sixi.oaplatform.common.utils;

import com.github.pagehelper.Page;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 自定义分页
 * 参考自 com.github.pagehelper;
 * @Create 2017-09-14 14:37
 */
public class PageList<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private int pageIndex;
    private int pageSize;
    private long total;
    private List<T> list;

    public PageList(List<T> list) {
        this(list, 8);
    }

    public PageList(List<T> list, int navigatePages) {
        if(list instanceof Page) {
            Page page = (Page)list;
            this.pageIndex = page.getPageNum();
            this.pageSize = page.getPageSize();
            this.list = page;
            this.total = page.getTotal();
        } else if(list instanceof Collection) {
            this.pageIndex = 1;
            this.pageSize = list.size();
            this.list = list;
            this.total = (long)list.size();
        }
    }

    public int getPageIndex() {
        return this.pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotal() {
        return this.total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getList() {
        return this.list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
