package com.study.dao.admin;

import com.study.domain.admin.Category;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface CategoryDao {
    @Insert("insert into category(id,name,sort) values(null,#{name},#{sort})")
    public  int add_Category(Category category);
    @Update("update category set name=#{name},sort=#{sort} where id=#{id}")
    public  int edit_Category(Category category);
    @Delete("delete from category where id=#{id} ")
    public  int delete_Category(Long id);

    @Select("select * from category where id =#{value}")
    public Category findById(Long id);

    @Select({"<script>" +
            "select * from category where 1=1 ",
            "<if test='name !=null'>",
            "and name like CONCAT('%',#{name},'%')" ,
            "</if>",
            "order by sort asc",
            "<if test='offset != null and pageSize != null'>",
            "limit #{offset},#{pageSize}",
            " </if>",
            "</script>"})
    public List<Category> findList(Map<String,Object> queryMap);

    @Select({"<script>",
            "select count(*) from category",
            "<where>" ,
            "<if test='name!=null'>" +
                    "name like CONCAT('%', #{name}, '%')",
            " </if>",
            "</where>",
            "</script>"})
    public int getTotal(Map<String,Object> queryMap);

    @Select("select * from category order by sort asc")
    public  List<Category> findAll();



}
