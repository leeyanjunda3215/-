package com.back.dto;

import java.util.List;

public class PageBean<T>{
    private List<T> rows;
    private int TotalCount;

    @Override
    public String toString() {
        return "PageBean{" +
                "rows=" + rows +
                ", TotalCount=" + TotalCount +
                '}';
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public int getTotalCount() {
        return TotalCount;
    }

    public void setTotalCount(int totalCount) {
        TotalCount = totalCount;
    }
}
