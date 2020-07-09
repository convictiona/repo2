package com.study.service.admin.impl;

import com.study.dao.admin.MenuDao;
import com.study.domain.admin.Menu;
import com.study.service.admin.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MenuImpl implements MenuService {
    @Autowired
    private MenuDao menuDao;
    @Override
    public int add(Menu menu) {
        return menuDao.add(menu);
    }

    @Override
    public List<Menu> findList(Map<String, Object> queryMap) {

        return menuDao.findList(queryMap);
    }

    @Override
    public List<Menu> findTopList() {
        return menuDao.findTopList();
    }

    @Override
    public int getTotal(Map<String, Object> queryMap) {
        return menuDao.getTotal(queryMap);
    }

    @Override
    public int edit(Menu menu) {
        return menuDao.edit(menu);
    }

    @Override
    public int delete(Long id) {
        return menuDao.delete(id);
    }

    @Override
    public List<Menu> findChildrenList(Long parentId) {
        return menuDao.findChildrenList(parentId);
    }

    @Override
    public List<Menu> findAll() {
        return menuDao.findALl();
    }

    @Override
    public List<Menu> findListByIds(String ids) {
        return menuDao.findListByIds(ids);
    }
}
