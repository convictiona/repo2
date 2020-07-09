package com.study.domain.admin;

import java.io.Serializable;
import java.util.Date;

public class Comment implements Serializable {
    private  Long id;
    private String CommentName;
    private String content;
    private Date commentTime;
    private  Long articleId;
    private  Article article;

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCommentName() {
        return CommentName;
    }

    public void setCommentName(String commentName) {
        CommentName = commentName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(Date commentTime) {
        this.commentTime = commentTime;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", CommentName='" + CommentName + '\'' +
                ", content='" + content + '\'' +
                ", commentTime=" + commentTime +
                ", articleId=" + articleId +
                ", article=" + article +
                '}';
    }
}
