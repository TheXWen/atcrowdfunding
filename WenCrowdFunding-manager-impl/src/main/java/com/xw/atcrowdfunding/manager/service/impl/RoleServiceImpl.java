package com.xw.atcrowdfunding.manager.service.impl;

import com.xw.atcrowdfunding.bean.RolePermission;
import com.xw.atcrowdfunding.bean.User;
import com.xw.atcrowdfunding.manager.service.RoleService;
import com.xw.atcrowdfunding.manager.dao.RoleMapper;
import com.xw.atcrowdfunding.util.Page;
import com.xw.atcrowdfunding.vo.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public Page pageQuery(Map paramMap) {
        Page rolePage = new Page((Integer) paramMap.get("pageno"), (Integer) paramMap.get("pagesize"));

        Integer startIndex = rolePage.getStartIndex();
        paramMap.put("startIndex", startIndex);
        List<User> datas = roleMapper.pageQuery(paramMap);
        rolePage.setDatas(datas);

        Integer totalsize = roleMapper.queryCount(paramMap);
        rolePage.setTotalsize(totalsize);

        return rolePage;
    }

    @Override
    public int deleteRole(Integer id) {
        return roleMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int saveRolePermissionRelationship(Integer roleid, Data datas) {
        roleMapper.deleteRolePermissionRelationship(roleid);

        int totalCount = 0;
        List<Integer> ids = datas.getIds();
        for (Integer permissionid : ids) {
            RolePermission rp = new RolePermission();
            rp.setRoleid(roleid);
            rp.setPermissionid(permissionid);
            int count = roleMapper.insertRolePermission(rp);
            totalCount += count;
        }
        return totalCount;
    }
}
