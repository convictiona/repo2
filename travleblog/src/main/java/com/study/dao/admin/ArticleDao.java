package com.study.dao.admin;

import com.study.domain.admin.Article;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ArticleDao {
    @Insert("insert into article(id,CategoryId,title,description,tags,photo,author,content,scannerNumber,commentNumber,createTime) values(null,#{CategoryId},#{title},#{description},#{tags},#{photo},#{author},#{content},#{scannerNumber},#{commentNumber},#{createTime})")
    public int add_Article(Article article);

    @Update("update article set CategoryId=#{CategoryId},title=#{title},description=#{description},tags=#{tags},photo=#{photo},author=#{author},content=#{content} where id =#{id}")
    public int edit_Article(Article article);

    @Delete("delete from article where id=#{id}")
    public  int delete_Article(Long id);

    @Select("select * from article")
    List<Article> findAll();
    @Update("update article set scannerNumber=scannerNumber +1 where id=#{id}")
    int add_scannerNumber(Long id);
    @Update("update article set commentNumber=commentNumber +1 where id=#{id}")
    int add_commentNumber(Long id);
    @Update("update article set commentNumber=commentNumber -1 where id=#{id}")
    int delete_commentNumber(Long id);
    @Select({"<script>" +
            "select * from article where 1=1 ",
            "<if test='title !=null'>",
            "and title like CONCAT('%',#{title},'%')" ,
            "</if>",
            "<if test='author !=null'>",
            "and author like CONCAT('%',#{author},'%')" ,
            "</if>",
            "<if test='categoryId !=null'>",
            "and CategoryId = #{categoryId}  ",
            "</if>",
            "order by createTime desc",
            "<if test='offset != null and pageSize != null'>",
            "limit #{offset},#{pageSize}",
            " </if>",
            "</script>"})
    @Results(id = "articleMap",value = {
            @Result(id =true,column = "id",property = "id"),
            @Result(column = "CategoryId",property = "CategoryId"),
            @Result(column = "title",property = "title"),
            @Result(column = "description",property = "description"),
            @Result(column = "tags",property = "tags"),
            @Result(column = "photo",property = "photo"),
            @Result(column = "author",property = "author"),
            @Result(column = "content",property = "content"),
            @Result(column = "scannerNumber",property = "scannerNumber"),
            @Result(column = "commentNumber",property = "commentNumber"),
            @Result(column = "createTime",property = "createTime"),
            @Result(property = "category",column = "CategoryId",one=@One(select="com.study.dao.admin.CategoryDao.findById",fetchType = FetchType.EAGER))
    })
    public List<Article> FindList(Map<String,Object> queryMap);

    @Select({"<script>" +
            "select count(*) from article",
            "<where>" ,
            "<if test='title !=null'>",
            "title like CONCAT('%',#{title},'%')" ,
            "</if>",
            "<if test='author !=null'>",
            "and author like CONCAT('%',#{author},'%')" ,
            "</if>",
            "<if test='categoryId !=null'>",
            "and CategoryId  = #{categoryId}" ,
            "</if>",
            "</where>",
            "</script>"})
    public int getTotal(Map<String,Object>queryMap);


   @Select("select * from article where id=#{id}")
   @ResultMap(value = {"articleMap"})
    public Article find(Long id);

   @Select("select * from article order by commentNumber desc limit 0,#{value}")
    List<Article> findCommentList(int pageSize);
    @Select("select * from article order by scannerNumber desc limit 0,#{value}")
    List<Article> findScannerList(int pageSize);

}
