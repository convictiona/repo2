package com.study.page.admin;

import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class Page implements Serializable {
    private int page =1;
    private int rows;
    private  int offset;//对应数据库中偏移量

    @Override
    public String toString() {
        return "Page{" +
                "page=" + page +
                ", rows=" + rows +
                ", offset=" + offset +
                '}';
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getOffset() {
        this.offset=(page -1)*rows;
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

}
