package com.study.service.admin.impl;

import com.study.dao.admin.CommentDao;
import com.study.domain.admin.Comment;
import com.study.service.admin.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
@Service
public class CommentImpl implements CommentService {
    @Autowired
    private CommentDao commentDao;

    @Override
    public int addComment(Comment comment) {
        return commentDao.addComment(comment) ;
    }

    @Override
    public int editComment(Comment comment) {
        return commentDao.editComment(comment);
    }

    @Override
    public int deleteComment(String ids) {
        return commentDao.deleteComment(ids);
    }

    @Override
    public List<Comment> findAll() {
        return commentDao.findAll();
    }


    @Override
    public List<Comment> findList(Map<String, Object> queryMap) {
        return commentDao.findList(queryMap);
    }

    @Override
    public int getTotal(Map<String, Object> queryMap) {
        return commentDao.getTotal(queryMap);
    }


}
