package com.xw.atcrowdfunding.potal.service;

import com.xw.atcrowdfunding.bean.Member;

import java.util.List;
import java.util.Map;

public interface MemberService {
    Member queryMemberlogin(Map<String, Object> paramMap);

    void updateAcctType(Member loginMember);

    void updateBasicinfo(Member loginMember);

    void updateEmail(Member loginMember);

    void updateAuthstatus(Member loginMember);

    Member getMemberById(Integer memberid);

    List<Map<String, Object>> queryCertByMemberid(Integer memberid);
}
