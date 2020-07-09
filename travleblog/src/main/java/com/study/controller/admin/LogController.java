package com.study.controller.admin;

import com.study.domain.admin.Log;
import com.study.page.admin.Page;
import com.study.service.admin.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.awt.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/admin/log")
public class LogController {
    @Autowired
    private LogService logService;

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public ModelAndView list(ModelAndView model){
        model.setViewName("/log/list");
        return  model;
    }
@RequestMapping(value = "/list",method = RequestMethod.POST)
@ResponseBody
    public Map<String,Object> LogList(Page page,
                                      @RequestParam(name="content",required = false,defaultValue = "")String content
                                      ){
     Map<String,Object> ret = new HashMap<String, Object>();
     Map<String,Object> queryMap=new HashMap<String, Object>();
     queryMap.put("offset",page.getOffset());
     queryMap.put("pageSize",page.getRows());
     queryMap.put("content",content);

     ret.put("rows",logService.FindList(queryMap));
     ret.put("total",logService.GetTotal(queryMap));

     return  ret;
    }
@RequestMapping(value = "/add",method = RequestMethod.POST)
@ResponseBody
    public  Map<String,String> add(Log log){
        Map<String,String> map= new HashMap<String,String >();
        if(log == null){
            map.put("type","error");
            map.put("msg","请填写正确的日志信息");
            return  map;
        }
        if(StringUtils.isEmpty(log.getContent())){
            map.put("type","error");
            map.put("msg","请检查日志内容是否填写？");
            return  map;
        }
        log.setCreateTime(new Date());
        if(logService.addLog(log)<=0){
            map.put("type","error");
            map.put("msg","添加日志失败");
            return  map;
        }
        map.put("type","success");
        map.put("msg","添加日志成功！");
        return  map;
    }
@RequestMapping(value = "/delete",method = RequestMethod.POST)
@ResponseBody
    public  Map<String,String> deleteLog(String ids){
        Map<String,String> map=new HashMap<String, String>();
        if(StringUtils.isEmpty(ids)){
            map.put("type","error");
            map.put("msg","删除日志失败！");
            return map;
        }
        if(ids.contains(",")){
            ids=ids.substring(0,ids.length()-1);
        }
        if(logService.delete(ids)<=0){
            map.put("type","error");
            map.put("msg","删除日志失败！");
            return map;
        }
        map.put("type","success");
        map.put("msg","删除日志成功！");
        return map;
    }
}
