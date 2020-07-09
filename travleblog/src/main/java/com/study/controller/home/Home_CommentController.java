package com.study.controller.home;

import com.study.domain.admin.Comment;
import com.study.page.admin.Page;
import com.study.service.admin.ArticleService;
import com.study.service.admin.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/home/comment")
public class Home_CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private ArticleService articleService;
    @RequestMapping(value = "/saveComment",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object>SaveComment(Comment comment){
        Map<String,Object> map=new HashMap<String, Object>();
        if (comment == null){
            map.put("type","error");
            map.put("msg","提交的评论为空");
            return  map;
        }
        if (StringUtils.isEmpty(comment.getCommentName())){
            map.put("type","error");
            map.put("msg","提交的评论昵称为空");
            return  map;
        }
        if (comment.getArticleId()==null){
            map.put("type","error");
            map.put("msg","请选择要评论的文章");
            return  map;
        }
        if (StringUtils.isEmpty(comment.getContent())){
            map.put("type","error");
            map.put("msg","提交的评论内容为空");
            return  map;
        }
        comment.setCommentTime(new Date());
        if (commentService.addComment(comment)<=0){
            map.put("type","error");
            map.put("msg","评论失败");
            return  map;
        }
        articleService.add_commentNumber(comment.getArticleId());

        map.put("type","success");
        map.put("commentTime",comment.getCommentTime());
        return  map;
    }

    @RequestMapping(value = "/commentList",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> commentList(Page page,Long articleId){
        Map<String,Object> queryMap=new HashMap<String,Object>();
        Map<String,Object> map=new HashMap<String, Object>();
        queryMap.put("offset",page.getOffset());
        queryMap.put("pageSize",page.getRows());
        queryMap.put("articleId",articleId);
        map.put("type","success");
        map.put("commentList",commentService.findList(queryMap));
        return  map;
    }
}
