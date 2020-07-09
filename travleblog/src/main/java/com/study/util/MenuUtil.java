package com.study.util;

import com.study.domain.admin.Menu;

import java.util.ArrayList;
import java.util.List;

public class MenuUtil {
    public static  List<Menu> get_AllTopMenu(List<Menu> menuList){
        List<Menu> ret = new ArrayList<Menu>();
        for (Menu menu:menuList){
            if(menu.getParentId()==0){
                ret.add(menu);
            }
        }
        return  ret;
    }

    public  static  List<Menu> get_SecondMenu(List<Menu> menuList){

        List<Menu> ret = new ArrayList<Menu>();
        List<Menu> allTopMenu=get_AllTopMenu(menuList);
        for (Menu menu:menuList){
            for(Menu topMenu:allTopMenu){
                if (menu.getParentId()==topMenu.getId()){
                    ret.add(menu);
                    break;
                }
            }
        }
        return  ret;
    }

    public  static  List<Menu> getButton(List<Menu> menuList,Long secondMenuId){
        List<Menu> ret = new ArrayList<Menu>();
        List<Menu> allTopMenu=get_AllTopMenu(menuList);
        for (Menu menu:menuList){
            if (menu.getParentId() == secondMenuId){
                ret.add(menu);
            }
        }
        return  ret;
    }
}
