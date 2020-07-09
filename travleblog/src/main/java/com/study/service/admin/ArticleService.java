package com.study.service.admin;

import com.study.domain.admin.Article;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface ArticleService {
  int add_Article(Article article);
   int edit_Article(Article article);
    int delete_Article(Long id);
   List<Article> FindList(Map<String,Object> queryMap);
    int getTotal(Map<String,Object>queryMap);
    Article find(Long id);
     List<Article> findAll();
    int add_scannerNumber(Long id);
    int add_commentNumber(Long id);
    List<Article> findCommentList(int pageSize);
 List<Article> findScannerList(int pageSize);



}
