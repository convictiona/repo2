package com.study.dao.admin;

import com.study.domain.admin.Comment;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface CommentDao {
    @Insert("insert into comment(id,CommentName,content,commentTime,articleId) values(null,#{CommentName},#{content},#{commentTime},#{articleId})")
    int addComment(Comment comment);
    @Update("update comment set CommentName=#{CommentName},content=#{content},articleId=#{articleId} where id=#{id}")
    int editComment(Comment comment);
    @Delete("delete from comment where id in (${value})")
     int deleteComment(String ids);

    @Select("select * from comment order by commentTime desc")
     List<Comment> findAll();


    @Select({"<script>" +
            "select * from comment",
            "<where>" ,
            "<if test='commentName !=null'>",
            "CommentName like CONCAT('%',#{commentName},'%')" ,
            "</if>",
            "<if test='articleId !=null'>",
            "and articleId like CONCAT('%',#{articleId},'%')" ,
            "</if>",
            "<if test='content !=null'>",
            "and content like CONCAT('%',#{content},'%')" ,
            "</if>",
            "order by commentTime desc",
            "<if test='offset != null and pageSize != null'>",
            "limit #{offset},#{pageSize}",
            " </if>",
            "</where>",
            "</script>"})
    @Results(id = "commentMap",value = {
            @Result(id =true,column = "id",property = "id"),
            @Result(column = "CommentName",property = "CommentName"),
            @Result(column = "content",property = "content"),
            @Result(column = "commentTime",property = "commentTime"),
            @Result(column = "articleId",property = "articleId"),
            @Result(property = "article",column = "articleId",one=@One(select="com.study.dao.admin.ArticleDao.find",fetchType = FetchType.EAGER))
    })
    List<Comment> findList(Map<String,Object> queryMap);

    @Select({"<script>",
            "select count(*) from comment",
            "<where>",
            "<if test='commentName !=null'>",
            "and CommentName like CONCAT('%',#{commentName},'%')",
            "</if>",
            "<if test='content !=null'>",
            "and content like CONCAT('%',#{content},'%')",
            "</if>",
            "</where>",
            "</script>"})
    int getTotal(Map<String,Object> queryMap);

}
