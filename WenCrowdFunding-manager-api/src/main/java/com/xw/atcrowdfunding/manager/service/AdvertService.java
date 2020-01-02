package com.xw.atcrowdfunding.manager.service;

import com.xw.atcrowdfunding.bean.Advertisement;
import com.xw.atcrowdfunding.util.Page;

import java.util.Map;

public interface AdvertService {
    int insertAdvert(Advertisement advert);

    Page queryPage(Map paramMap);
}
