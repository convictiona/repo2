package com.study.dao.admin;

import com.study.domain.admin.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UserDao {
    @Select("select * from user where username=#{username}")
    public User findByUserName(String username);

    @Insert("insert into user(id,username,password,photo,roleId,sex,age,address) values(null,#{username},#{password},#{photo},#{roleId},#{sex},#{age},#{address})")
    public int AddUser(User user);

    @Update("update user set username=#{username},roleId = #{roleId},photo = #{photo},sex = #{sex},age = #{age},address = #{address} where id = #{id}")
    public int EditUser(User user);

    @Delete("delete from user where id in(${value})")
    public  int DeleteUser(String ids);

    @Select({"<script>" ,
            "select * from user",
            "where 1=1 ",
            "<if test ='username != null'>" +
            "and username like CONCAT('%',#{username},'%') ",
            "</if>",
            "<if test='roleId != null'>" +
            "and roleId =#{roleId} ",
            "</if>" ,
            "<if test ='sex != null'>" +
            "and sex =#{sex} ",
            "</if>",
            "<if test ='offset != null and pageSize != null'>" +
            "limit #{offset},#{pageSize}",
            "</if>",
            "</script>"})
    public List<User> FindList(Map<String,Object> queryMap);

    @Select({"<script>",
            "select count(*) from user",
            "<where>" ,
            "<if test='username!=null'>" +
                    "username like CONCAT('%', #{username}, '%')",
            " </if>" +
                    "<if test='roleId !=null'>" +
                    "and roleId = #{roleId}" +
                    "</if>",
            "<if test ='sex != null'>" +
                    "and sex =#{sex} ",
            "</if>",
            "</where>",
            "</script>"})
    public int getTotal(Map<String,Object> queryMap);


    @Update("update user set password = #{password} where id =#{id}")
    public int editPassword(User user);
}
