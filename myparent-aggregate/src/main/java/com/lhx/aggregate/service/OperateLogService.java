package com.lhx.aggregate.service;

import com.lhx.aggregate.dao.impl.OperateLogDao;
import com.lhx.aggregate.dao.impl.UserDao;
import com.lhx.aggregate.entity.OperateLog;
import com.lhx.aggregate.entity.User;
import com.lhx.aggregate.tools.DateUtils;
import com.lhx.aggregate.tools.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class OperateLogService {
	@Autowired
	private OperateLogDao dao;
	@Autowired
	private UserDao userDao;

	public void save(OperateLog log) {
		dao.save(log);
	}

	public PageBean<OperateLog> findPageList(int curPage, int pageSize,
			String datemin, String datemax, int creater, String keyword) {
		String hql = "from OperateLog where 1=1";
		Map<String, Object> params = new HashMap<String, Object>();
		User u = userDao.getById(creater);
		if (null != u) {
			params.put("creater", u.getId());
			hql = hql + " and creater = :creater";
		}
		if (null != keyword && keyword.trim().length() != 0) {
			params.put("keyword", "%" + keyword + "%");
			hql = hql
					+ " and (accessPath like :keyword or accessName like :keyword)";
		}
		if (null != datemin && datemin.trim().length() != 0 && null != datemax
				&& datemax.trim().length() != 0) {
			datemin = datemin + " 00:00:00";
			datemax = datemax + " 23:59:59";
			params.put("datemin",
					DateUtils.parse(datemin, "yyyy-MM-dd HH:mm:ss"));
			params.put("datemax",
					DateUtils.parse(datemax, "yyyy-MM-dd HH:mm:ss"));
			hql = hql + " and date >= :datemin and date <= :datemax";
		}
		hql = hql + " order by date desc";
		if (params.size() != 0) {
			return dao.find(hql,"OperateLog",params, curPage, pageSize);
		} else {
			return dao.find(hql,"OperateLog", curPage, pageSize);
		}
	}
}
