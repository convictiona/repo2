package com.study.service.admin;

import com.study.domain.admin.Authority;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AuthorityService {
      public  int add(Authority authority);
      public int deleteByRoleId(Long roleId);
      public List<Authority> findListByRoleId(Long roleId);
}
