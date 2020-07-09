package com.study.controller.home;

import com.study.domain.admin.Article;
import com.study.domain.admin.Category;
import com.study.page.admin.Page;
import com.study.service.admin.ArticleService;
import com.study.service.admin.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/home/article")
public class Home_ArticleController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ArticleService articleService;

    @RequestMapping(value = "/CategoryList",method = RequestMethod.GET)
    public ModelAndView GetCategoryList(ModelAndView model,
            @RequestParam(name = "Id",required = true) Long Id,
            Page page){
        Map<String,Object>  queryMap=new HashMap<String,Object>();
        queryMap.put("offset",0);
        queryMap.put("pageSize",10);
        queryMap.put("categoryId",Id);


        model.addObject("articleList",articleService.FindList(queryMap));
        model.addObject("categoryList",categoryService.findAll());
        Category category= categoryService.findById(Id);
        model.addObject("category",category);
        model.addObject("title",category.getName()+"分类下的文章");

        model.setViewName("home/article/categoryList");
        return  model;
    }

    @RequestMapping(value = "/article_detail",method = RequestMethod.GET)
    public ModelAndView article(ModelAndView model,Long id){
        Article article = articleService.find(id);
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("offset", 0);
        queryMap.put("pageSize", 10);
        model.addObject("articleList",articleService.FindList(queryMap));
        model.addObject("categoryList", categoryService.findAll());
        model.addObject("article", article);
        model.addObject("title", article.getTitle());
        model.addObject("tags", article.getTags().split(","));
        model.addObject("article",articleService.find(id));
        model.setViewName("home/article/article");
        articleService.add_scannerNumber(id);
        return model;
    }

    @RequestMapping(value = "/tagsList",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> tagsList(){
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("type","success");
        map.put("articleList",articleService.findScannerList(1));
        return map;
    }


    @RequestMapping(value = "/commentList",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> CommentList(){
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("type","success");
        map.put("articleList",articleService.findCommentList(10));
        return map;
    }

    @RequestMapping(value = "/scannerList",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> ScannerList(){
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("type","success");
        map.put("articleList",articleService.findScannerList(2));
        return map;
    }

    @RequestMapping(value = "/categoryList",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> GetCategoryList(Page page,
                                              @RequestParam(name = "Id",required = false)Long Id){
        Map<String,Object> map=new HashMap<String,Object>();
        Map<String,Object> queryMap=new HashMap<String,Object>();
        queryMap.put("offset",page.getOffset());
        queryMap.put("pageSize",page.getRows());
        queryMap.put("categoryId",Id);

        map.put("type","success");
        map.put("articleList",categoryService.findList(queryMap));
        return map;
    }

}
