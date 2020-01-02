package com.xw.atcrowdfunding.manager.dao;

import com.xw.atcrowdfunding.bean.Advertisement;
import com.xw.atcrowdfunding.bean.User;

import java.util.List;
import java.util.Map;

public interface AdvertisementMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Advertisement record);

    Advertisement selectByPrimaryKey(Integer id);

    List<Advertisement> selectAll();

    int updateByPrimaryKey(Advertisement record);

    List<User> pageQuery(Map paramMap);

    Integer queryCount(Map paramMap);
}