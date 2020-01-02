package com.xw.atcrowdfunding.manager.service;

import java.util.List;
import java.util.Map;

public interface CerttypeService {
    List<Map<String, Object>> queryCertAccttype();

    int insertAcctTypeCert(Map<String, Object> paramMap);

    int deleteAcctTypeCert(Map<String, Object> paramMap);
}
