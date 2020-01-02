package com.xw.atcrowdfunding.manager.dao;

import com.xw.atcrowdfunding.bean.Role;
import com.xw.atcrowdfunding.bean.RolePermission;
import com.xw.atcrowdfunding.bean.User;

import java.util.List;
import java.util.Map;

public interface RoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    Role selectByPrimaryKey(Integer id);

    List<Role> selectAll();

    int updateByPrimaryKey(Role record);

    List<User> pageQuery(Map paramMap);

    Integer queryCount(Map paramMap);

    void deleteRolePermissionRelationship(Integer roleid);

    int insertRolePermission(RolePermission rp);
}