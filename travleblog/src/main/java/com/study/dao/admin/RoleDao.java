package com.study.dao.admin;

import com.study.domain.admin.Role;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Repository
public interface RoleDao {
    @Insert("insert into role(id,name,remark) values(null,#{name},#{remark})")
    public int add(Role role);

    @Update("update role set name=#{name},remark=#{remark} where id=#{id}")
    public  int edit(Role role);

    @Delete("delete from role where id=#{id}")
    public  int delete(Long id);

    @Select({"<script>" +
            "select * from role",
            "<where>" ,
            "<if test='name !=null'>",
            "name like CONCAT('%',#{name},'%')" ,
            "</if>",
            "<if test='offset != null and pageSize != null'>",
            "limit #{offset},#{pageSize}",
            " </if>",
            "</where>",
            "</script>"})
    public List<Role> findList(Map<String,Object> queryMap);

    @Select({"<script>",
            "select count(*) from role",
            "<where>" ,
            "<if test='name!=null'>" +
                    "name like CONCAT('%', #{name}, '%')",
            " </if>",
            "</where>",
            "</script>"})
    public int total(Map<String,Object>queryMap);

    @Select("select * from role where id=#{id}")
    public  Role find(Long id);
}
