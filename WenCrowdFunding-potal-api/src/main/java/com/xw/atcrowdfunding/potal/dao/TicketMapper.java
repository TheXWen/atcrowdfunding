package com.xw.atcrowdfunding.potal.dao;

import com.xw.atcrowdfunding.bean.Member;
import com.xw.atcrowdfunding.bean.Ticket;

import java.util.List;
import java.util.Map;

public interface TicketMapper {
    int deleteByPrimaryKey(Integer id);

    Ticket selectByPrimaryKey(Integer id);

    List<Ticket> selectAll();

    Ticket getTicketByMemberId(Integer memberid);

    void saveTicket(Ticket ticket);

    void updatePstep(Ticket ticket);

    void updatePiidAndPstep(Ticket ticket);

    Member getMemberByPiid(String processInstanceId);

    void updateStatus(Member member);
}