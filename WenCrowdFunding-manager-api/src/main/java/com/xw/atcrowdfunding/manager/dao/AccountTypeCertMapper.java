package com.xw.atcrowdfunding.manager.dao;

import com.xw.atcrowdfunding.bean.AccountTypeCert;

import java.util.List;
import java.util.Map;

public interface AccountTypeCertMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AccountTypeCert record);

    AccountTypeCert selectByPrimaryKey(Integer id);

    List<AccountTypeCert> selectAll();

    int updateByPrimaryKey(AccountTypeCert record);

    List<Map<String, Object>> queryCertAccttype();

    int insertAcctTypeCert(Map<String, Object> paramMap);

    int deleteAcctTypeCert(Map<String, Object> paramMap);
}