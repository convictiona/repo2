package com.study.service.admin;

import com.study.domain.admin.Role;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface RoleService {
    public int add(Role role);
    public  int edit(Role role);
    public  int delete(Long id);
    public List<Role> findList(Map<String,Object> queryMap);
    public int total(Map<String,Object> queryMap);
    public  Role find(Long id);
}

