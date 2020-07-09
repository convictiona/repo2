package com.study.service.admin;

import com.study.domain.admin.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface UserService {
    public User findByUserName(String username);
    public int AddUser(User user);
    public int EditUser(User user);
    public  int DeleteUser(String ids);
    public List<User> FindList(Map<String,Object> queryMap);
    public int getTotal(Map<String,Object> queryMap);
    public int editPassword(User user);

}
