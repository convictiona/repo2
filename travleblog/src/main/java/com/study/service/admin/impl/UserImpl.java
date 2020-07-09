package com.study.service.admin.impl;

import com.study.dao.admin.UserDao;
import com.study.domain.admin.User;
import com.study.service.admin.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserImpl implements UserService {
    @Autowired
    private UserDao userdao;
    @Override
    public User findByUserName(String username) {
        return userdao.findByUserName(username);
    }

    @Override
    public int AddUser(User user) {
        return userdao.AddUser(user);
    }

    @Override
    public int EditUser(User user) {
        return userdao.EditUser(user);
    }

    @Override
    public int DeleteUser(String ids) {
        return userdao.DeleteUser(ids);
    }


    @Override
    public List<User> FindList(Map<String, Object> queryMap) {
        return userdao.FindList(queryMap);
    }

    @Override
    public int getTotal(Map<String, Object> queryMap) {
        return userdao.getTotal(queryMap);
    }

    @Override
    public int editPassword(User user) {
        return  userdao.editPassword(user);
    }
}
