package com.study.service.admin;

import com.study.domain.admin.Comment;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface CommentService {
    public  int addComment(Comment comment);
    public  int editComment(Comment comment);
    public  int deleteComment(String ids);

    public List<Comment> findAll();
    public List<Comment> findList(Map<String,Object> queryMap);
    public int getTotal(Map<String,Object> queryMap);


}
