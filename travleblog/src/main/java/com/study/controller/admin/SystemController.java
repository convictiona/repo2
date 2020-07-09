package com.study.controller.admin;

import com.study.domain.admin.*;
import com.study.service.admin.*;
import com.study.util.CpachaUtil;
import com.study.util.MenuUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/system")
public class SystemController {
@Autowired
private UserService userService;
@Autowired
private RoleService roleService;
@Autowired
private AuthorityService authorityService;
@Autowired
private MenuService menuService;
@Autowired
private LogService logService;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView index(ModelAndView model,HttpServletRequest request) {
        List<Menu> userMenus= (List<Menu>) request.getSession().getAttribute("userMenus");
        model.addObject("topMenus", MenuUtil.get_AllTopMenu(userMenus));
        model.addObject("secondMenus", MenuUtil.get_SecondMenu(userMenus));
        model.setViewName("system/index");
        return model;
    }
    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public ModelAndView welcome(ModelAndView model) {
        model.setViewName("system/welcome");
        return model;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(ModelAndView model) {
        model.setViewName("/system/login");
        return model;
    }
    @RequestMapping(value="/login",method=RequestMethod.POST)
    @ResponseBody
    public Map<String, String> loginAct(User user,String cpacha,HttpServletRequest request) {
        Map<String, String> ret = new HashMap<String, String>();
        if (user == null) {
            ret.put("type", "error");
            ret.put("msg", "请填写用户信息！");
            return ret;
        }
        if (StringUtils.isEmpty(cpacha)) {
            ret.put("type", "error");
            ret.put("msg", "请填写验证码！");
            return ret;
        }
        if (StringUtils.isEmpty(user.getUsername())) {
            ret.put("type", "error");
            ret.put("msg", "请填写用户名！");
            return ret;
        }
        if (StringUtils.isEmpty(user.getPassword())) {
            ret.put("type", "error");
            ret.put("msg", "请填写密码！");
            return ret;
        }
        Object loginCpacha = request.getSession().getAttribute("loginCpacha");
        if (loginCpacha == null) {
            ret.put("type", "error");
            ret.put("msg", "会话超时，请刷新页面！");
            return ret;
        }
        if (!cpacha.toUpperCase().equals(loginCpacha.toString().toUpperCase())) {
            ret.put("type", "error");
            ret.put("msg", "验证码错误！");
            logService.addLog("用户名为"+user.getUsername()+"验证码输入错误");
            return ret;
        }
       User findByUsername=userService.findByUserName(user.getUsername());
        if(findByUsername ==null){
            ret.put("type", "error");
            ret.put("msg", "用户不存在！");
            logService.addLog("用户名为"+user.getUsername()+"不存在");
            return ret;
        }
        if(!user.getPassword().equals(findByUsername.getPassword())){
            ret.put("type", "error");
            ret.put("msg", "密码错误！");
            logService.addLog("用户名为"+user.getUsername()+"密码输入错误");
            return ret;
        }
        //查询用户角色权限
        Role role=roleService.find(findByUsername.getRoleId());
        List<Authority> authorityList=authorityService.findListByRoleId(role.getId());
       String menuIds="";
        for (Authority authority:authorityList){
            menuIds += authority.getMenuId()+",";
        }
        if(!StringUtils.isEmpty(menuIds)){
            menuIds =menuIds.substring(0,menuIds.length()-1);
        }
         List<Menu> userMenus=menuService.findListByIds(menuIds);

       request.getSession().setAttribute("admin",findByUsername);
       request.getSession().setAttribute("role",role);
       request.getSession().setAttribute("userMenus",userMenus);
        ret.put("type", "success");
        ret.put("msg", "登录");
        logService.addLog("用户名为"+user.getUsername()+",角色为"+role.getName()+"登陆成功");
        return ret;
    }
    @RequestMapping(value = "/log-down",method = RequestMethod.GET)
    public  String LogDown(HttpServletRequest request){
           HttpSession session= request.getSession();
           session.setAttribute("admin",null);
           session.setAttribute("role",null);
           session.setAttribute("userMenus",null);
           return "redirect:login";
    }
 @RequestMapping(value = "/get_cpacha",method = RequestMethod.GET)
    public  void generateCpacha(
            @RequestParam(name = "vl",required = false,defaultValue = "4")Integer vcodeLen,
            @RequestParam(name = "w",required = false,defaultValue = "100")Integer width,
            @RequestParam(name = "h",required = false,defaultValue = "30")Integer height,
            @RequestParam(name="type",required = true,defaultValue = "loginCpacha")String cpachaType,
            HttpServletRequest request, HttpServletResponse response){
            CpachaUtil cpachaUtil=new CpachaUtil(vcodeLen,width,height);
           String generatorVcode= cpachaUtil.generatorVCode();
            request.getSession().setAttribute(cpachaType,generatorVcode);
           BufferedImage  generatorRotateVCodeImage=cpachaUtil.generatorRotateVCodeImage(generatorVcode,true);
        try {
            ImageIO.write(generatorRotateVCodeImage,"gif",response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/edit-password", method = RequestMethod.GET)
    public ModelAndView editPassword(ModelAndView model) {
        model.setViewName("system/edit-password");
        return model;
    }

    @RequestMapping(value="/edit-password",method=RequestMethod.POST)
    @ResponseBody
    public Map<String, String> edit_password(String newPassword, String oldPassword,HttpServletRequest request) {
        Map<String, String> map = new HashMap<String, String>();
        if (StringUtils.isEmpty(newPassword)) {
            map.put("type", "error");
            map.put("msg", "请填写新密码！");
            return map;
        }
        User user=(User)request.getSession().getAttribute("admin");
        if(!user.getPassword().equals(oldPassword)){
           map.put("type", "error");
           map.put("msg", "原密码错误！");
            return map;
        }
        user.setPassword(newPassword);
        if (userService.editPassword(user)<0){
            map.put("type", "error");
            map.put("msg", "密码修改失败！");
            logService.addLog("用户名为"+user.getUsername()+"修改密码失败");
            return map;
        }
        map.put("type", "success");
        map.put("msg", "密码修改成功");
        logService.addLog("用户名为："+user.getUsername()+"修改密码成功");
        return  map;
    }

}
