package com.study.service.admin.impl;

import com.study.dao.admin.ArticleDao;
import com.study.domain.admin.Article;
import com.study.service.admin.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
@Service
public class ArticleImpl implements ArticleService {
    @Autowired
    private ArticleDao articleDao;
    @Override
    public int add_Article(Article article) {
        return articleDao.add_Article(article);
    }

    @Override
    public int edit_Article(Article article) {
        return articleDao.edit_Article(article);
    }

    @Override
    public int delete_Article(Long id) {
        return articleDao.delete_Article(id);
    }

    @Override
    public List<Article> FindList(Map<String, Object> queryMap) {
        return articleDao.FindList(queryMap);
    }

    @Override
    public int getTotal(Map<String, Object> queryMap) {
        return articleDao.getTotal(queryMap);
    }

    @Override
    public Article find(Long id) {
        return articleDao.find(id);
    }

    @Override
    public List<Article> findAll() {
        return articleDao.findAll();
    }

    @Override
    public int add_scannerNumber(Long id) {
        return articleDao.add_scannerNumber(id);
    }

    @Override
    public int add_commentNumber(Long id) {
        return articleDao.add_commentNumber(id);
    }

    @Override
    public List<Article> findCommentList(int pageSize) {
        return articleDao.findCommentList(pageSize);
    }

    @Override
    public List<Article> findScannerList(int pageSize) {
        return articleDao.findScannerList(pageSize);
    }


}
