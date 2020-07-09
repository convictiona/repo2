package com.study.dao.admin;

import com.study.domain.admin.Log;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface LogDao {
    @Insert("insert into log(id,content, createTime) values(null,#{content},#{createTime})")
    public  int  addLog(Log log);
    @Select({"<script>" +
            "select * from log",
            "<where>" ,
            "<if test='content !=null'>",
            "content like CONCAT('%',#{content},'%')" ,
            "</if>",
            "order by createTime desc",
            "<if test='offset != null and pageSize != null'>",
            "limit #{offset},#{pageSize}",
            " </if>",
            "</where>",
            "</script>"})
    public List<Log> FindList(Map<String ,Object> queryMap);
    @Select({"<script>",
            "select count(*) from log",
            "<where>" ,
            "<if test='content!=null'>" +
                    "content like CONCAT('%', #{content}, '%')",
            " </if>",
            "</where>",
            "</script>"})
    public int GetTotal (Map<String,Object> queryMap);

    @Delete("delete from log where id in (${value})")
    public int delete(String ids);
}
