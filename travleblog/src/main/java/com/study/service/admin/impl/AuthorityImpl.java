package com.study.service.admin.impl;

import com.study.dao.admin.AuthorityDao;
import com.study.domain.admin.Authority;
import com.study.service.admin.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AuthorityImpl implements AuthorityService {
    @Autowired
    private AuthorityDao authorityDao;
    @Override
    public int add(Authority authority) {
        return authorityDao.add(authority);
    }

    @Override
    public int deleteByRoleId(Long roleId) {
        return authorityDao.deleteByRoleId(roleId);
    }

    @Override
    public List<Authority> findListByRoleId(Long roleId) {
        return authorityDao.findListByRoleId(roleId);
    }
}
