package com.study.controller.home;

import com.study.domain.admin.Article;
import com.study.service.admin.ArticleService;
import com.study.service.admin.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import sun.rmi.server.InactiveGroupException;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/index")
public class Home_IndexController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ArticleService articleService;
    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public ModelAndView index(ModelAndView model){

        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("offset", 0);
        queryMap.put("pageSize", 10);
        model.addObject("categoryList",categoryService.findAll());
        model.addObject("articleList",articleService.FindList(queryMap));
        model.setViewName("home/index/index");
        return  model;
    }

    @RequestMapping(value="/get_site_info",method=RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getSiteInfo(){
        Map<String, Object> retMap = new HashMap<String, Object>();
        retMap.put("type", "success");
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("offset", 0);
        queryMap.put("pageSize", 99999);
        retMap.put("totalArticle", articleService.getTotal(queryMap));
//        retMap.put("siteDays", getDays("2018-02-22"));
        return retMap;
    }

/*    private long getDays(String data){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = null;
        try {
            startDate = sdf.parse(data);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Date endDate = new Date();
        long time = (endDate.getTime() - startDate.getTime())/1000/3600/24;
        return time;
    }*/

}
