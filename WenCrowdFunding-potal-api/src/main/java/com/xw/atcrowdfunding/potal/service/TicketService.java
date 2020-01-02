package com.xw.atcrowdfunding.potal.service;

import com.xw.atcrowdfunding.bean.Member;
import com.xw.atcrowdfunding.bean.Ticket;

public interface TicketService {
    Ticket getTicketByMemberId(Integer id);

    void saveTicket(Ticket ticket);

    void updatePstep(Ticket ticket);

    void updatePiidAndPstep(Ticket ticket);

    Member getMemberByPiid(String processInstanceId);

    void updateStatus(Member member);
}
