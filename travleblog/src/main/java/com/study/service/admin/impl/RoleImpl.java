package com.study.service.admin.impl;

import com.study.dao.admin.RoleDao;
import com.study.domain.admin.Role;
import com.study.service.admin.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RoleImpl implements RoleService {
    @Autowired
    private RoleDao roleDao;
    @Override
    public int add(Role role) {
        return  roleDao.add(role);
    }

    @Override
    public int edit(Role role) {
        return roleDao.edit(role);
    }

    @Override
    public int delete(Long id) {
        return roleDao.delete(id);
    }

    @Override
    public List<Role> findList(Map<String, Object> queryMap) {
        return roleDao.findList(queryMap);
    }

    @Override
    public int total(Map<String, Object> queryMap) {
        return roleDao.total(queryMap);
    }

    @Override
    public Role find(Long id) {
        return roleDao.find(id);
    }
}
