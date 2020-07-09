package com.study.controller.home;

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
@RequestMapping(value = "/home/search")
public class Home_SearchController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ArticleService articleService;

    @RequestMapping(value = "/SearchList",method = RequestMethod.GET)
    public ModelAndView  SearchList(ModelAndView model,
                                    @RequestParam(name = "Word",required = false,defaultValue = "") String Word,
                                    Page page){
        Map<String,Object> queryMap=new HashMap<String, Object>();
        queryMap.put("offset",0);
        queryMap.put("pageSize",10);
        queryMap.put("title",Word);
        model.addObject("categoryList",categoryService.findAll());
        model.addObject("articleList",articleService.FindList(queryMap));
        model.addObject("title",Word+"关键字查询文章信息");
        model.setViewName("home/search/searchList");
        model.addObject("Word",Word);
        return  model;
    }

    @RequestMapping(value = "/get_searchList",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> GetSearchList(Page page,
                                            @RequestParam(name = "Word",required = false,defaultValue = "") String Word){
        Map<String,Object> map=new HashMap<String,Object>();
        Map<String,Object> queryMap=new HashMap<String,Object>();
        queryMap.put("offset",page.getOffset());
        queryMap.put("pageSize",page.getRows());
        queryMap.put("title",Word+"关键字查询文章信息");

        map.put("type","success");
        map.put("articleList",categoryService.findList(queryMap));
        return map;
    }
}
