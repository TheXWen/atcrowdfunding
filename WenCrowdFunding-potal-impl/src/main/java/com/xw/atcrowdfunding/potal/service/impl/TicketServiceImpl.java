package com.xw.atcrowdfunding.potal.service.impl;

import com.xw.atcrowdfunding.bean.Member;
import com.xw.atcrowdfunding.bean.Ticket;
import com.xw.atcrowdfunding.potal.dao.TicketMapper;
import com.xw.atcrowdfunding.potal.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketMapper ticketMapper;

    @Override
    public Ticket getTicketByMemberId(Integer id) {
        return ticketMapper.getTicketByMemberId(id);
    }

    @Override
    public void saveTicket(Ticket ticket) {
        ticketMapper.saveTicket(ticket);
    }

    @Override
    public void updatePstep(Ticket ticket) {
        ticketMapper.updatePstep(ticket);
    }

    @Override
    public void updatePiidAndPstep(Ticket ticket) {
        ticketMapper.updatePiidAndPstep(ticket);
    }

    @Override
    public Member getMemberByPiid(String processInstanceId) {
        return ticketMapper.getMemberByPiid(processInstanceId);
    }

    @Override
    public void updateStatus(Member member) {
        ticketMapper.updateStatus(member);
    }

}
