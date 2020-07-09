package com.study.controller.admin;

import com.study.domain.admin.Role;
import com.study.domain.admin.User;
import com.study.page.admin.Page;
import com.study.service.admin.RoleService;
import com.study.service.admin.UserService;
import org.apache.ibatis.annotations.Delete;
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
@RequestMapping("/admin/user")
public class UserController {
    @Autowired
     private UserService userService;
    @Autowired
    private RoleService roleService;

    @RequestMapping(value="/list",method=RequestMethod.GET)
    public ModelAndView list(ModelAndView model){
        Map<String, Object> queryMap = new HashMap<String, Object>();
        model.addObject("roleList", roleService.findList(queryMap));
        model.setViewName("user/list");
        return model;
    }

    @RequestMapping(value="/list",method=RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getList(Page page,
                                       @RequestParam(name="username",required=false,defaultValue="") String username,
                                       @RequestParam(name="roleId",required=false,defaultValue = "") Long roleId,
                                       @RequestParam(name="sex",required=false,defaultValue = "") Integer sex
    ){
        Map<String, Object> ret = new HashMap<String, Object>();
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("username", username);
        queryMap.put("roleId", roleId);
        queryMap.put("sex", sex);
        queryMap.put("offset", page.getOffset());
        queryMap.put("pageSize", page.getRows());
        ret.put("rows", userService.FindList(queryMap));
        ret.put("total", userService.getTotal(queryMap));
        return ret;
    }
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> add(User user) {
        Map<String, String> ret = new HashMap<String, String>();
        if (user == null) {
            ret.put("type", "error");
            ret.put("msg", "请填写正确的用户信息！");
            return ret;
        }
        if (StringUtils.isEmpty(user.getUsername())) {
            ret.put("type", "error");
            ret.put("msg", "请填写用户名称！");
            return ret;
        }
        if (StringUtils.isEmpty(user.getUsername())) {
            ret.put("type", "error");
            ret.put("msg", "请填写密码！");
            return ret;
        }
        if(user.getRoleId() == null){
            ret.put("type", "error");
            ret.put("msg", "请选择所属角色！");
            return ret;
        }
        if(isExist(user.getUsername(), 0l)){
            ret.put("type", "error");
            ret.put("msg", "该用户名已经存在，请重新输入！");
            return ret;
        }
        if (userService.AddUser(user) <= 0) {
            ret.put("type", "error");
            ret.put("msg", "添加用户失败！请联系管理员");
            return ret;
        }
        ret.put("type", "success");
        ret.put("msg", "添加用户成功.！");
        return ret;
    }
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> edit(User user) {
        Map<String, String> ret = new HashMap<String, String>();
        if (user == null) {
            ret.put("type", "error");
            ret.put("msg", "请填写正确的用户信息！");
            return ret;
        }
        if (StringUtils.isEmpty(user.getUsername())) {
            ret.put("type", "error");
            ret.put("msg", "请填写用户名称！");
            return ret;
        }
        if(user.getRoleId() == null){
            ret.put("type", "error");
            ret.put("msg", "请选择所属角色！");
            return ret;
        }
        if(isExist(user.getUsername(),user.getId())){
            ret.put("type", "error");
            ret.put("msg", "该用户名已经存在，请重新输入！");
            return ret;
        }
        if (userService.EditUser(user) <= 0) {
            ret.put("type", "error");
            ret.put("msg", "修改用户失败！请联系管理员");
            return ret;
        }
        ret.put("type", "success");
        ret.put("msg", "添加用户成功.！");
        return ret;
    }
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> delete(@RequestParam(value = "ids",required = true) String ids){
        Map<String,String> ret=new HashMap<String, String>();
        if(StringUtils.isEmpty(ids)){
            ret.put("type","error");
            ret.put("msg","请选择要删除的用户！");
            return  ret;
        }
        if(ids.contains(",")){
            ids = ids.substring(0,ids.length()-1);
        }
        if(userService.DeleteUser(ids)<=0){
            ret.put("type","error");
            ret.put("msg","删除失败");
            return  ret;
        }
        ret.put("type","success");
        ret.put("msg","删除成功");
        return  ret;
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
    private boolean isExist(String username,Long id){
        User user = userService.findByUserName(username);
        if(user == null)return false;
        if(user.getId().longValue() == id.longValue())return false;
        return true;
    }

}
