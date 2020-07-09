package com.study.controller.admin;

import com.study.domain.admin.Comment;
import com.study.page.admin.Page;
import com.study.service.admin.ArticleService;
import com.study.service.admin.CommentService;
import jdk.nashorn.internal.ir.IfNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/admin/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private ArticleService articleService;


    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public ModelAndView list(ModelAndView model){
        model.addObject("articleList",articleService.findAll());
        model.setViewName("comment/list");
        return model;
    }
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getList(Page page,
                                      @RequestParam(name = "commentName",required = false,defaultValue = "")String commentName,
                                      @RequestParam(name="content",required = false,defaultValue = "")String content){
        Map<String,Object> map=new HashMap<String, Object>();
        Map<String,Object> queryMap=new HashMap<String, Object>();

        queryMap.put("commentName",commentName);
        queryMap.put("content",content);
        queryMap.put("offset",page.getOffset());
        queryMap.put("pageSize",page.getRows());
        map.put("rows",commentService.findList(queryMap));
        map.put("total",commentService.getTotal(queryMap));
        return  map;

    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> addComment(Comment comment){
        System.out.println("-------"+comment);
        Map<String,String> map= new HashMap<String, String>();
        if (comment == null){
            map.put("type","error");
            map.put("msg","评论为空");
            return  map;
        }
        if (comment.getArticleId()==null){
            map.put("type","error");
            map.put("msg","选择要评论的文章");
            return  map;
        }
        if(StringUtils.isEmpty(comment.getCommentName())){
            map.put("type","error");
            map.put("msg","昵称为空");
            return  map;
        }
        if (StringUtils.isEmpty(comment.getContent())){
            map.put("type","error");
            map.put("msg","评论内容为空");
            return  map;
        }
        comment.setCommentTime(new Date());
        if (commentService.addComment(comment)<=0){
            map.put("type","error");
            map.put("msg","添加评论失败");
            return  map;
        }
        map.put("type","success");
        map.put("msg","评论成功");
        articleService.add_commentNumber(comment.getArticleId());
        return  map;
    }

    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    @ResponseBody
    public  Map<String,String> edit(Comment comment){
        Map<String,String> map= new HashMap<String,String>();
        if (comment == null){
            map.put("type","error");
            map.put("msg","编辑的评论为空");
            return map;
        }
        if (StringUtils.isEmpty(comment.getCommentName())){
            map.put("type","error");
            map.put("msg","编辑的评论昵称为空");
            return map;
        }
        if (StringUtils.isEmpty(comment.getContent()))
        {
            map.put("type","error");
            map.put("msg","编辑的评论内容为空");
            return map;
        }
        if (commentService.editComment(comment)<=0){
            map.put("type","error");
            map.put("msg","编辑评论失败");
            return map;
        }
        map.put("type","success");
        map.put("msg","编辑评论成功");
        return map;
    }
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> deleteComment(String ids){
        Map<String,String> map= new HashMap<String, String>();
        if (StringUtils.isEmpty(ids)){
            map.put("type","error");
            map.put("msg","选择要删除的数据");
            return map;
        }
        if(ids.contains(",")){
            ids = ids.substring(0,ids.length()-1);
        }
        if (commentService.deleteComment(ids)<=0){
            map.put("type","error");
            map.put("msg","删除失败");
            return map;
        }
        map.put("type","success");
        map.put("msg","删除成功");

        return map;
    }

}
