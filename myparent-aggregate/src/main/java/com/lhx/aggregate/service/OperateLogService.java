package com.lhx.aggregate.service;

import com.lhx.aggregate.dao.impl.OperateLogDao;
import com.lhx.aggregate.entity.OperateLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OperateLogService {
    @Autowired
    private OperateLogDao dao;
    public void save(OperateLog log){
        dao.save(log);
    }
}
