package com.study.service.admin.impl;


import com.study.dao.admin.CategoryDao;
import com.study.domain.admin.Category;
import com.study.service.admin.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CategoryImpl  implements CategoryService {
    @Autowired
    private CategoryDao categoryDao;
    @Override
    public int add_Category(Category category) {
        return categoryDao.add_Category(category);
    }

    @Override
    public int edit_Category(Category category) {
        return categoryDao.edit_Category(category);
    }

    @Override
    public int delete_Category(Long id) {
        return categoryDao.delete_Category(id);
    }

    @Override
    public List<Category> findList(Map<String, Object> queryMap) {
        return categoryDao.findList(queryMap);
    }

    @Override
    public int getTotal(Map<String, Object> queryMap) {
        return categoryDao.getTotal(queryMap);
    }

    @Override
    public List<Category> findAll() {
        return categoryDao.findAll();
    }

    @Override
    public Category findById(Long id) {
        return categoryDao.findById(id);
    }
}
