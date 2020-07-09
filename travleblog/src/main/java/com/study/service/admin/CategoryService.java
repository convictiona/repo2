package com.study.service.admin;

import com.study.domain.admin.Category;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface CategoryService {
    public  int add_Category(Category category);
    public  int edit_Category(Category category);
    public  int delete_Category(Long id);
    public List<Category> findList(Map<String,Object>queryMap);
    public int getTotal(Map<String,Object> queryMap);
    public  List<Category> findAll();
    Category findById(Long id);
}
