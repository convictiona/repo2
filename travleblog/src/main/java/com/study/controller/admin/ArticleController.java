package com.study.controller.admin;

import com.study.domain.admin.Article;
import com.study.page.admin.Page;
import com.study.service.admin.ArticleService;
import com.study.service.admin.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/admin/article")
public class ArticleController {
@Autowired
private ArticleService articleService;
@Autowired
private CategoryService categoryService;


    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public ModelAndView list(ModelAndView model){
        model.addObject("CategoryList",categoryService.findAll());
        model.setViewName("article/list");
        return model;
    }


    @RequestMapping(value="/list",method=RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getList(   Page page,
            @RequestParam(name="title",required=false,defaultValue="") String title,
            @RequestParam(name="author",required=false,defaultValue="") String author,
            @RequestParam(name="categoryId",required=false) Long categoryId
    ){
        Map<String,Object> ret = new HashMap<String, Object>();
        Map<String,Object> queryMap = new HashMap<String, Object>();
        queryMap.put("title", title);
        queryMap.put("author", author);

        if((categoryId != null )&&( categoryId.longValue() != -1)){
            queryMap.put("categoryId", categoryId);
        }
        queryMap.put("offset", page.getOffset());
        queryMap.put("pageSize", page.getRows());
        System.out.println("-----------------"+queryMap.toString());
        ret.put("rows", articleService.FindList(queryMap));
        ret.put("total", articleService.getTotal(queryMap));
        System.out.println("---------------------"+ret.toString());
        return ret;
    }


    @RequestMapping(value = "/add_article",method = RequestMethod.GET)
    public ModelAndView add_article(ModelAndView model){
        model.addObject("CategoryList",categoryService.findAll());
        model.setViewName("article/add_article");
        return model;
    }
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> add(Article article){
        Map<String,Object> map= new HashMap<String, Object>();
        if (article == null){
            map.put("type","error");
            map.put("msg","提交的内容为空");
            return  map;
        }
        if (StringUtils.isEmpty(article.getTitle())){
            map.put("type","error");
            map.put("msg","提交的标题为空");
            return  map;
        }
        if (article.getCategoryId()==null){
            map.put("type","error");
            map.put("msg","提交的分类类别为空");
            return  map;
        }
        if (StringUtils.isEmpty(article.getDescription())){
            map.put("type","error");
            map.put("msg","提交的摘要为空");
            return  map;
        }
        if (StringUtils.isEmpty(article.getTags())){
            map.put("type","error");
            map.put("msg","提交的标签为空");
            return  map;
        }
        if (StringUtils.isEmpty(article.getPhoto())){
            map.put("type","error");
            map.put("msg","提交的封面图片为空");
            return  map;
        }
        if (StringUtils.isEmpty(article.getContent())){
            map.put("type","error");
            map.put("msg","提交的文章内容为空");
            return  map;
        }
        if (StringUtils.isEmpty(article.getAuthor())){
            map.put("type","error");
            map.put("msg","提交的作者为空");
            return  map;
        }
        article.setCreateTime(new Date());
        if(articleService.add_Article(article)<=0){
            map.put("type","error");
            map.put("msg","提交失败");
            return  map;
        }
        map.put("type","success");
        map.put("msg","添加成功");
        return  map;
    }


    @RequestMapping(value = "/edit_article",method = RequestMethod.GET)
    public ModelAndView edit_article(ModelAndView model,Long id){
        model.addObject("CategoryList",categoryService.findAll());
        model.addObject("article",articleService.find(id));
        model.setViewName("article/edit_article");
        return model;
    }
    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    @ResponseBody
    public  Map<String,Object> edit(Article article){
        Map<String,Object> map= new HashMap<String, Object>();

        if (article == null){
            map.put("type","error");
            map.put("msg","修改文章为空");
            return  map;
        }
        if (StringUtils.isEmpty(article.getTitle())){
            map.put("type","error");
            map.put("msg","修改标题为空");
            return  map;
        }
        if (article.getCategoryId()== null){
            map.put("type","error");
            map.put("msg","修改的类别为空");
            return  map;
        }
        if (StringUtils.isEmpty(article.getAuthor())){
            map.put("type","error");
            map.put("msg","修改的博主为空");
            return  map;
        }
        if (StringUtils.isEmpty(article.getContent())){
            map.put("type","error");
            map.put("msg","修改的内容为空");
            return  map;
        }
        if (StringUtils.isEmpty(article.getPhoto()))
        {
            map.put("type","error");
            map.put("msg","修改的文章封面为空");
            return  map;
        }
        if (articleService.edit_Article(article)<=0){
            map.put("type","error");
            map.put("msg","修改失败");
            return  map;
        }
        map.put("type","success");
        map.put("msg","修改成功");
         return  map;
    }
    @RequestMapping(value ="/delete",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> delete(Long id){
        Map<String,String> map=new HashMap<String, String>();
        if (id == null){
            map.put("type","error");
            map.put("msg","删除信息为空");
            return  map;
        }
       try {
           if (articleService.delete_Article(id) <= 0) {
               map.put("type", "error");
               map.put("msg", "删除失败");
               return map;
           }
       }catch (Exception e){
           map.put("type","error");
           map.put("msg","文章含有评论信息，不可删除");
           return  map;
       }
        map.put("type","success");
        map.put("msg","删除成功");
        return  map;
    }

    @RequestMapping(value="/upload_photo",method=RequestMethod.POST)
    @ResponseBody
    public Map<String, String> uploadPhoto(MultipartFile photo, HttpServletRequest request){
        Map<String, String> ret = new HashMap<String, String>();
        if(photo == null){
            ret.put("type", "error");
            ret.put("msg", "选择要上传的文件！");
            return ret;
        }
        if(photo.getSize() > 1024*1024*1024){
            ret.put("type", "error");
            ret.put("msg", "文件大小不能超过10M！");
            return ret;
        }
        //获取文件后缀
        String suffix = photo.getOriginalFilename().substring(photo.getOriginalFilename().lastIndexOf(".")+1,photo.getOriginalFilename().length());
        if(!"jpg,jpeg,gif,png".toUpperCase().contains(suffix.toUpperCase())){
            ret.put("type", "error");
            ret.put("msg", "请选择jpg,jpeg,gif,png格式的图片！");
            return ret;
        }
        String savePath = request.getSession().getServletContext().getRealPath("/") + "/resources/upload/";
        File savePathFile = new File(savePath);
        if(!savePathFile.exists()){
            //若不存在改目录，则创建目录
            savePathFile.mkdir();
        }
        String filename = new Date().getTime()+"."+suffix;
        try {
            //将文件保存至指定目录
            photo.transferTo(new File(savePath+filename));
        }catch (Exception e) {
            // TODO Auto-generated catch block
            ret.put("type", "error");
            ret.put("msg", "保存文件异常！");
            e.printStackTrace();
            return ret;
        }
        ret.put("type", "success");
        ret.put("msg", "上传成功！");
        ret.put("filepath",request.getSession().getServletContext().getContextPath() + "/resources/upload/" + filename );
        return ret;
    }

}
