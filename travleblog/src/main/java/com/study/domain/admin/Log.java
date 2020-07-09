package com.study.domain.admin;

import java.io.Serializable;
import java.util.Date;

public class Log implements Serializable {
    private int id;
    private String content;
    private Date createTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Log{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
