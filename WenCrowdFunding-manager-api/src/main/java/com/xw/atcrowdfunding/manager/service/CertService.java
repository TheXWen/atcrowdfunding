package com.xw.atcrowdfunding.manager.service;

import com.xw.atcrowdfunding.bean.Cert;
import com.xw.atcrowdfunding.bean.MemberCert;

import java.util.List;

public interface CertService {
    List<Cert> queryAllCert();

    List<Cert> queryCertByAccttype(String accttype);

    void saveMemberCert(List<MemberCert> certimgs);
}
