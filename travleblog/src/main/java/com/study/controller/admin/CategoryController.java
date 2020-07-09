package com.study.controller.admin;

import com.study.domain.admin.Category;
import com.study.page.admin.Page;
import com.study.service.admin.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/admin/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public ModelAndView list(ModelAndView model){
        model.setViewName("category/list");
        return  model;
    }
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object>Findlist(Page page,
                                      @RequestParam(name = "name",required = false,defaultValue = "")String name){
        Map<String,Object> map=new HashMap<String, Object>();
        Map<String,Object> queryMap=new HashMap<String, Object>();
        queryMap.put("name",name);
        queryMap.put("offset",page.getOffset());
        queryMap.put("pageSize",page.getRows());


        map.put("rows",categoryService.findList(queryMap));
        map.put("total",categoryService.getTotal(queryMap));
        return  map;
    }
@RequestMapping(value = "/add",method = RequestMethod.POST)
@ResponseBody
    public Map<String,Object>add(Category category){
        Map<String,Object> map=new HashMap<String, Object>();
        if (category == null){
            map.put("type","error");
            map.put("msg","请填写要添加的分类");
            return  map;
        }
        if (StringUtils.isEmpty(category.getName())){
            map.put("type","error");
            map.put("msg","请填写分类名称!");
            return  map;
        }
        if (categoryService.add_Category(category)<=0){
            map.put("type","error");
            map.put("msg","添加分类失败");
            return  map;
        }
        map.put("type","success");
        map.put("msg","添加分类成功");
        return  map;
    }
    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object>edit(Category category){
        Map<String,Object> map=new HashMap<String, Object>();
        if (category == null){
            map.put("type","error");
            map.put("msg","请选择要修改的分类");
            return  map;
        }
        if (StringUtils.isEmpty(category.getName())){
            map.put("type","error");
            map.put("msg","请填写分类名称!");
            return  map;
        }
        if (categoryService.edit_Category(category)<=0){
            map.put("type","error");
            map.put("msg","修改分类失败");
            return  map;
        }
        map.put("type","success");
        map.put("msg","修改分类成功");
        return  map;
    }
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object>delete(Long id){
        Map<String,Object> map=new HashMap<String, Object>();
        if (id == null){
            map.put("type","error");
            map.put("msg","请选择删除的分类");
            return  map;
        }
        try {
            if (categoryService.delete_Category(id)<=0) {
                map.put("type", "error");
                map.put("msg", "删除分类失败");
                return map;
            }
        }catch (Exception e){
            map.put("type","error");
            map.put("msg","分类下有信息，不可删除");
            return  map;
        }
        map.put("type","success");
        map.put("msg","删除分类成功");
        return  map;
    }
}
