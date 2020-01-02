package com.xw.atcrowdfunding.manager.service.impl;

import com.xw.atcrowdfunding.bean.Advertisement;
import com.xw.atcrowdfunding.bean.User;
import com.xw.atcrowdfunding.manager.dao.AdvertisementMapper;
import com.xw.atcrowdfunding.manager.service.AdvertService;
import com.xw.atcrowdfunding.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AdvertServiceImpl implements AdvertService {

    @Autowired
    private AdvertisementMapper advertisementMapper;

    @Override
    public int insertAdvert(Advertisement advert) {
        return advertisementMapper.insert(advert);
    }

    @Override
    public Page queryPage(Map paramMap) {
        Page advertPage = new Page((Integer) paramMap.get("pageno"), (Integer) paramMap.get("pagesize"));

        Integer startIndex = advertPage.getStartIndex();
        paramMap.put("startIndex", startIndex);
        List<User> datas = advertisementMapper.pageQuery(paramMap);
        advertPage.setDatas(datas);

        Integer totalsize = advertisementMapper.queryCount(paramMap);
        advertPage.setTotalsize(totalsize);

        return advertPage;
    }
}
