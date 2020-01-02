package com.xw.atcrowdfunding.potal.controller;

import com.xw.atcrowdfunding.bean.Cert;
import com.xw.atcrowdfunding.bean.Member;
import com.xw.atcrowdfunding.bean.MemberCert;
import com.xw.atcrowdfunding.bean.Ticket;
import com.xw.atcrowdfunding.manager.service.CertService;
import com.xw.atcrowdfunding.potal.listener.PassListener;
import com.xw.atcrowdfunding.potal.listener.RefuseListener;
import com.xw.atcrowdfunding.potal.service.MemberService;
import com.xw.atcrowdfunding.potal.service.TicketService;
import com.xw.atcrowdfunding.util.AjaxResult;
import com.xw.atcrowdfunding.util.Const;
import com.xw.atcrowdfunding.vo.Data;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.*;

@Controller
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private CertService certService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @RequestMapping("/accttype")
    public String accttype(){
        return "member/accttype";
    }

    @RequestMapping("/basicinfo")
    public String basicinfo(){
        return "member/basicinfo";
    }

    @RequestMapping("/uploadCert")
    public String uploadCert(){
        return "member/uploadCert";
    }

    @RequestMapping("/checkemail")
    public String checkemail(){
        return "member/checkemail";
    }

    @RequestMapping("/checkauchcode")
    public String checkauchcode(){
        return "member/checkauchcode";
    }

    @RequestMapping("/apply")
    public String apply(HttpSession session){
        Member member = (Member) session.getAttribute(Const.LOGIN_MEMBER);
        Ticket ticket = ticketService.getTicketByMemberId(member.getId());

        if (ticket == null){
            ticket = new Ticket(); //封装数据
            ticket.setMemberid(member.getId());
            ticket.setPstep("apply");
            ticket.setStatus("0");

            ticketService.saveTicket(ticket);
        }else{
            String pstep = ticket.getPstep();

            if ("accttype".equals(pstep)){
                return "redirect:/member/basicinfo.htm";
            }else if ("basicinfo".equals(pstep)){
                //根据当前用户查询账户类型,然后根据账户类型查找需要上传的资质
                List<Cert> queryCertByAccttype = certService.queryCertByAccttype(member.getAccttype());
                session.setAttribute("queryCertByAccttype", queryCertByAccttype);
                return "redirect:/member/uploadCert.htm";
            }else if ("uploadcert".equals(pstep)){
                return "redirect:/member/checkemail.htm";
            }else if ("checkemail".equals(pstep)){
                return "redirect:/member/checkauchcode.htm";
            }
        }
        return "member/accttype";
    }

    @ResponseBody
    @RequestMapping("/finishApply")
    public Object finishApply(HttpSession session, String authcode) {
        AjaxResult result = new AjaxResult();

        try {

            // 获取登录会员信息
            Member loginMember = (Member)session.getAttribute(Const.LOGIN_MEMBER);

            //让当前系统用户完成:验证码审核任务.
            Ticket ticket = ticketService.getTicketByMemberId(loginMember.getId());
            if (ticket.getAuthcode().equals(authcode)){
                //完成审核验证码任务
                Task task = taskService.createTaskQuery().processInstanceId(ticket.getPiid()).taskAssignee(loginMember.getLoginacct()).singleResult();
                taskService.complete(task.getId());

                //更新用户申请状态
                loginMember.setAuthstatus("1");
                memberService.updateAuthstatus(loginMember);

                //记录流程步骤:
                ticket.setPstep("finishApply");
                ticketService.updatePstep(ticket);
                result.setSuccess(true);
            }else {
                result.setSuccess(false);
                result.setMessage("验证码不正确,请重新输入!");
            }
        } catch( Exception e ) {
            e.printStackTrace();
            result.setSuccess(false);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/startProcess")
    public Object startProcess(HttpSession session, String email) {
        AjaxResult result = new AjaxResult();

        try {

            // 获取登录会员信息
            Member loginMember = (Member)session.getAttribute(Const.LOGIN_MEMBER);

            // 如果用户输入新的邮箱,将旧的邮箱地址替换
            if (!loginMember.getEmail().equals(email)){
                loginMember.setEmail(email);
                memberService.updateEmail(loginMember);
            }

            //启动实名认证流程 - 系统自动发送邮件,生成验证码.验证邮箱地址是否合法(模拟:银行卡是否有效).
            ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionKey("auth").singleResult();

            //toEmail
            //authcode
            //loginacct
            //flag  审核实名认证时提供
            //passListener
            //refuseListener
            StringBuilder authcode = new StringBuilder();
            for (int i = 1; i <= 4; i++) {
                authcode.append(new Random().nextInt(10));
            }
            Map<String, Object> variables = new HashMap<String, Object>();
            variables.put("toEmail", email);
            variables.put("authcode", authcode);
            variables.put("loginacct", loginMember.getLoginacct());
            variables.put("passListener", new PassListener());
            variables.put("refuseListener", new RefuseListener());

            //ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("auth");
            ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinition.getId(), variables);

            //记录流程步骤:
            Ticket ticket = ticketService.getTicketByMemberId(loginMember.getId());
            ticket.setPstep("checkemail");
            ticket.setPiid(processInstance.getId());
            ticket.setAuthcode(authcode.toString());
            ticketService.updatePiidAndPstep(ticket);

            result.setSuccess(true);
        } catch( Exception e ) {
            e.printStackTrace();
            result.setSuccess(false);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/doUploadCert")
    public Object doUploadCert(HttpSession session, Data ds) {
        AjaxResult result = new AjaxResult();

        try {

            // 获取登录会员信息
            Member loginMember = (Member)session.getAttribute(Const.LOGIN_MEMBER);

            // 保存会员与资质关系数据.
            String realPath = session.getServletContext().getRealPath("/pics");
            List<MemberCert> certimgs = ds.getCertimgs();
            for (MemberCert memberCert : certimgs) {
                MultipartFile fileImg = memberCert.getFileImg();
                String extName = fileImg.getOriginalFilename().substring(fileImg.getOriginalFilename().lastIndexOf("."));
                String tmpName = UUID.randomUUID().toString() + extName;
                String fileName = realPath + "/cert/" + tmpName;

                fileImg.transferTo(new File(fileName));//资质文件上传.
                //准备数据
                memberCert.setIconpath(tmpName);//封装数据,保存数据库
                memberCert.setMemberid(loginMember.getId());
            }

            // 保存会员与资质关系数据.
            certService.saveMemberCert(certimgs);

            //记录流程步骤:
            Ticket ticket = ticketService.getTicketByMemberId(loginMember.getId());
            ticket.setPstep("uploadcert");
            ticketService.updatePstep(ticket);

            result.setSuccess(true);
        } catch( Exception e ) {
            e.printStackTrace();
            result.setSuccess(false);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/updateBasicinfo")
    public Object updateBasicinfo(HttpSession session, Member member) {
        AjaxResult result = new AjaxResult();

        try {

            // 获取登录会员信息
            Member loginMember = (Member)session.getAttribute(Const.LOGIN_MEMBER);
            loginMember.setRealname(member.getRealname());
            loginMember.setCardnum(member.getCardnum());
            loginMember.setTel(member.getTel());
            // 更新账户类型
            memberService.updateBasicinfo(loginMember);

            //记录流程步骤:
            Ticket ticket = ticketService.getTicketByMemberId(loginMember.getId());
            ticket.setPstep("basicinfo");
            ticketService.updatePstep(ticket);

            result.setSuccess(true);
        } catch( Exception e ) {
            e.printStackTrace();
            result.setSuccess(false);
        }

        return result;

    }

    /**
     * 更新账户类型
     */
    @ResponseBody
    @RequestMapping("/updateAcctType")
    public Object updateAcctType(HttpSession session, String accttype ) {
        AjaxResult result = new AjaxResult();

        try {

            // 获取登录会员信息
            Member loginMember = (Member)session.getAttribute(Const.LOGIN_MEMBER);
            loginMember.setAccttype(accttype);
            // 更新账户类型
            memberService.updateAcctType(loginMember);

            //记录流程步骤:
            Ticket ticket = ticketService.getTicketByMemberId(loginMember.getId());
            ticket.setPstep("accttype");
            ticketService.updatePstep(ticket);

            result.setSuccess(true);
        } catch( Exception e ) {
            e.printStackTrace();
            result.setSuccess(false);
        }

        return result;

    }

}
