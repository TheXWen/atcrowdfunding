package com.xw.atcrowdfunding.manager.dao;

import com.xw.atcrowdfunding.bean.Permission;

import java.util.List;

public interface PermissionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Permission record);

    Permission selectByPrimaryKey(Integer id);

    List<Permission> queryAllPermission();

    int updateByPrimaryKey(Permission record);

    Permission getRootPermission();

    List<Permission> getChildrenPermissionByPid(Integer id);

    List<Integer> queryPermissionidsByRoleid(Integer roleid);
}