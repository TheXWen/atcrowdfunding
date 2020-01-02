package com.xw.atcrowdfunding.manager.service.impl;

import com.xw.atcrowdfunding.manager.dao.AccountTypeCertMapper;
import com.xw.atcrowdfunding.manager.service.CerttypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CerttypeServiceImpl implements CerttypeService {

    @Autowired
    private AccountTypeCertMapper accountTypeCertMapper;

    @Override
    public List<Map<String, Object>> queryCertAccttype() {
        return accountTypeCertMapper.queryCertAccttype();
    }

    @Override
    public int insertAcctTypeCert(Map<String, Object> paramMap) {
        return accountTypeCertMapper.insertAcctTypeCert(paramMap);
    }

    @Override
    public int deleteAcctTypeCert(Map<String, Object> paramMap) {
        return accountTypeCertMapper.deleteAcctTypeCert(paramMap);
    }

}
