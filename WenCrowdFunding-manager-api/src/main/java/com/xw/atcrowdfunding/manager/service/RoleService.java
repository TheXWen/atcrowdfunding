package com.xw.atcrowdfunding.manager.service;

import com.xw.atcrowdfunding.util.Page;
import com.xw.atcrowdfunding.vo.Data;

import java.util.Map;

public interface RoleService {
    Page pageQuery(Map paramMap);

    int deleteRole(Integer id);

    int saveRolePermissionRelationship(Integer roleid, Data datas);
}
