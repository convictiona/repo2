package com.study.service.admin.impl;

import com.study.dao.admin.LogDao;
import com.study.domain.admin.Log;
import com.study.service.admin.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
@Service
public class LogImpl implements LogService {
    @Autowired
    private LogDao logDao;
    @Override
    public int addLog(Log log) {
        return logDao.addLog(log);
    }

    @Override
    public List<Log> FindList(Map<String, Object> queryMap) {
        return logDao.FindList(queryMap);
    }

    @Override
    public int GetTotal(Map<String, Object> queryMap) {
        return logDao.GetTotal(queryMap);
    }

    @Override
    public int delete(String ids) {
        return logDao.delete(ids);
    }

    @Override
    public int addLog(String content) {
        Log log =new Log();
        log.setContent(content);
        log.setCreateTime(new Date());
        return logDao.addLog(log);
    }
}
