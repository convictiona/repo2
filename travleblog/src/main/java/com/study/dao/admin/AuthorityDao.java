package com.study.dao.admin;

import com.study.domain.admin.Authority;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorityDao {

    @Insert("insert into authority(id,roleId,menuId) values(null,#{roleId},#{menuId})")
    public  int add(Authority authority);

    @Delete("delete from authority where roleId = #{roleId}")
    public int deleteByRoleId(Long roleId);

    @Select({"<script>",
            "select * from authority",
            "<where>" ,
            "<if test='_parameter != null'>" +
                    " roleId = #{roleId} " +
                    "</if>",
            "</where>",
            "</script>"})
    public List<Authority> findListByRoleId(Long roleId);
}
