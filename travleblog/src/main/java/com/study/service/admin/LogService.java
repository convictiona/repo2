package com.study.service.admin;

import com.study.domain.admin.Log;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface LogService {
    public  int  addLog(Log log);
    public List<Log>  FindList(Map<String ,Object> queryMap);
    public int GetTotal (Map<String,Object> queryMap);
    public int delete(String ids);
    public int addLog(String content);
}
