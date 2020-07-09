package com.study.dao.admin;

import com.study.domain.admin.Menu;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
public interface MenuDao {
    @Insert("insert into menu(id,parentId,name,url,icon) values(null,#{parentId},#{name},#{url},#{icon}) ")
    public int add(Menu menu);

    @Select({"<script>" +
                    "select * from menu",
                    "<where>" ,
            "<if test='parentId != null'>" +
                    " parentId = #{parentId} ",
            " </if>",
                    "<if test='name !=null'>",
            "name like CONCAT('%',#{name},'%')" ,
            "</if>",
            "<if test='offset != null and pageSize != null'>",
                "limit #{offset},#{pageSize}",
           " </if>",
            "</where>",
                    "</script>"})
    public  List<Menu> findList(Map<String,Object> queryMap);

    @Select("select * from menu")
    public List<Menu> findALl();

    @Select("select  * from menu where parentId =0")
   public  List<Menu> findTopList();
 @Select("select * from menu where parentId=#{parentId}")
    public  List<Menu> findChildrenList(Long parentId);


   @Select({"<script>",
           "select count(*) from menu",
           "<where>" ,
           "<if test='parentId != null'>" +
                   " parentId = #{parentId} ",
           " </if>",
           "<if test='name!=null'>" +
                   "name like CONCAT('%', #{name}, '%')",
           " </if>",
           "</where>",
           "</script>"})
       public int getTotal(Map<String,Object>queryMap);

  @Update("update menu set name=#{name},parentId=#{parentId},url = #{url},icon = #{icon} where id = #{id}")
   public  int edit(Menu menu);

  @Delete("delete from menu where id=#{id}")
  public  int delete(Long id);

  @Select("select * from menu where id in (${value})")
    public  List<Menu> findListByIds(String ids);
}

