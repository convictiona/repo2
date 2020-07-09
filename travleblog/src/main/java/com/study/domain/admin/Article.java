package com.study.domain.admin;

import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Date;

public class Article implements Serializable {
    private  Long id;
    private  Long CategoryId;
    private String title;
    private String description;
    private String tags;
    private String  photo;
    private String author;
    private String content;
    private Integer scannerNumber = 0;
    private Integer commentNumber = 0;
    private Date createTime;
    private Category category;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(Long categoryId) {
        CategoryId = categoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getScannerNumber() {
        return scannerNumber;
    }

    public void setScannerNumber(Integer scannerNumber) {
        this.scannerNumber = scannerNumber;
    }

    public Integer getCommentNumber() {
        return commentNumber;
    }

    public void setCommentNumber(Integer commentNumber) {
        this.commentNumber = commentNumber;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", CategoryId=" + CategoryId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", tags='" + tags + '\'' +
                ", photo='" + photo + '\'' +
                ", author='" + author + '\'' +
                ", content='" + content + '\'' +
                ", scannerNumber=" + scannerNumber +
                ", commentNumber=" + commentNumber +
                ", createTime=" + createTime +
                '}';
    }
}
