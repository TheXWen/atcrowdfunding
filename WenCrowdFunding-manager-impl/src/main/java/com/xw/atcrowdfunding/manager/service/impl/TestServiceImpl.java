package com.xw.atcrowdfunding.manager.service.impl;

import java.util.HashMap;
import java.util.Map;

import com.xw.atcrowdfunding.manager.dao.TestDao;
import com.xw.atcrowdfunding.manager.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService {
	
	@Autowired
	private TestDao testDao;

	@Override
	public void insert() {
		Map map = new HashMap();
		map.put("name", "zhang3");		
		testDao.insert(map);
	}

}
