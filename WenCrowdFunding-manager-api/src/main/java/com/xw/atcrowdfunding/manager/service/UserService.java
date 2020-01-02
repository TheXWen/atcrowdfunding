package com.xw.atcrowdfunding.manager.service;

import com.xw.atcrowdfunding.bean.Permission;
import com.xw.atcrowdfunding.bean.Role;
import com.xw.atcrowdfunding.bean.User;
import com.xw.atcrowdfunding.util.Page;
import com.xw.atcrowdfunding.vo.Data;

import java.util.List;
import java.util.Map;

public interface UserService {
    User queryUserlogin(Map<String, Object> paramMap);

    //@Deprecated
    //Page queryPage(Integer pageno, Integer pagesize);

    int saveUser(User user);

    Page queryPage(Map<String, Object> paramMap);

    User getUserById(Integer id);

    int updateUser(User user);

    int deleteUser(Integer id);

    int deleteBatchUser(Integer[] ids);

    int deleteBatchUserByVo(Data data);

    List<Role> queryAllRole();

    List<Integer> queryRoleByUserid(Integer id);

    int saveUserRoleRelationship(Integer userid, Data data);

    int deleteUserRoleRelationship(Integer userid, Data data);

    List<Permission> queryPermissionByUserid(Integer id);
}
