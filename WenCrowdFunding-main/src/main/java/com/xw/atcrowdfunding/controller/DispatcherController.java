package com.xw.atcrowdfunding.controller;

import com.xw.atcrowdfunding.bean.Member;
import com.xw.atcrowdfunding.bean.Permission;
import com.xw.atcrowdfunding.bean.User;
import com.xw.atcrowdfunding.manager.service.UserService;
import com.xw.atcrowdfunding.util.AjaxResult;
import com.xw.atcrowdfunding.util.Const;
import com.xw.atcrowdfunding.util.MD5Util;
import com.xw.atcrowdfunding.potal.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class DispatcherController {

    @Autowired
    private UserService userService;

    @Autowired
    private MemberService memberService;

    @RequestMapping("/index")
    public String index(){
        return "index";
    }

    @RequestMapping("/login")
    public String login(HttpServletRequest request, HttpSession session){
        //判断是否需要自动登录
        //如果之前登录过，cookie中存放了用户信息，需要获取cookie中的信息，并进行数据库的验证

        boolean needLogin = true;
        String logintype = null ;
        Cookie[] cookies = request.getCookies();
        if(cookies != null){ //如果客户端禁用了Cookie，那么无法获取Cookie信息

            for (Cookie cookie : cookies) {
                if("logincode".equals(cookie.getName())){
                    String logincode = cookie.getValue();
                    System.out.println("获取到Cookie中的键值对"+cookie.getName()+"===== " + logincode);
                    //loginacct=admin&userpwd=21232f297a57a5a743894a0e4a801fc3&logintype=member
                    String[] split = logincode.split("&");
                    if(split.length == 3){
                        String loginacct = split[0].split("=")[1];
                        String userpwd = split[1].split("=")[1];
                        logintype = split[2].split("=")[1];
                        Map<String,Object> paramMap = new HashMap<String,Object>();
                        paramMap.put("loginacct", loginacct);
                        paramMap.put("userpswd", userpwd);
                        paramMap.put("type", logintype);

                        if("user".equals(logintype)){


                            User dbLogin = userService.queryUserlogin(paramMap);

                            if(dbLogin!=null){
                                session.setAttribute(Const.LOGIN_USER, dbLogin);
                                needLogin = false ;
                            }


                            //加载当前登录用户的所拥有的许可权限.

                            //User user = (User)session.getAttribute(Const.LOGIN_USER);

                            List<Permission> myPermissions = userService.queryPermissionByUserid(dbLogin.getId()); //当前用户所拥有的许可权限

                            Permission permissionRoot = null;

                            Map<Integer,Permission> map = new HashMap<Integer,Permission>();

                            Set<String> myUris = new HashSet<String>(); //用于拦截器拦截许可权限

                            for (Permission innerpermission : myPermissions) {
                                map.put(innerpermission.getId(), innerpermission);

                                myUris.add("/"+innerpermission.getUrl());
                            }

                            session.setAttribute(Const.MY_URIS, myUris);


                            for (Permission permission : myPermissions) {
                                //通过子查找父
                                //子菜单
                                Permission child = permission ; //假设为子菜单
                                if(child.getPid() == null ){
                                    permissionRoot = permission;
                                }else{
                                    //父节点
                                    Permission parent = map.get(child.getPid());
                                    parent.getChildren().add(child);
                                }
                            }


                            session.setAttribute("permissionRoot", permissionRoot);


                        }else if("member".equals(logintype)){


                            Member dbLogin = memberService.queryMemberlogin(paramMap);

                            if(dbLogin!=null){
                                session.setAttribute(Const.LOGIN_MEMBER, dbLogin);
                                needLogin = false ;
                            }
                        }

                    }
                }
            }
        }

        if(needLogin){
            return "login";
        }else{
            if("user".equals(logintype)){
                return "redirect:/main.htm";
            }else if("member".equals(logintype)){
                return "redirect:/member.htm";
            }
        }
        return "login";
    }

    @RequestMapping("/member")
    public String member(HttpSession session){
        return "member/member";
    }

    @RequestMapping("/main")
    public String main(HttpSession session){
        return "main";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session){

        session.invalidate(); //销毁session对象,或清理session域

        return "redirect:/index.htm";
    }

    //异步请求
    //@ResponseBody 结合Jackson组件,将返回结果转换为字符串.将JSON串以流的形式返回给客户端
    @ResponseBody
    @RequestMapping("/doLogin")
    public Object doLogin(String loginacct, String userpswd, String type,
                          String rememberme, HttpSession session, HttpServletResponse response){

        AjaxResult result = new AjaxResult();

        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("loginacct", loginacct);
            paramMap.put("userpswd", MD5Util.digest(userpswd));
            paramMap.put("type", type);

            if("member".equals(type)){
                Member member = memberService.queryMemberlogin(paramMap);
                session.setAttribute(Const.LOGIN_MEMBER, member);
                if ("1".equals(rememberme)){
                    String logincode = "\"loginacct="+member.getLoginacct()+"&userpwd="+member.getUserpswd()+"&logintype=member\"";
                    //loginacct=admin&userpwd=21232f297a57a5a743894a0e4a801fc3&logintype=1
                    System.out.println("用户-存放到Cookie中的键值对：logincode::::::::::::"+logincode);
                    Cookie c = new Cookie("logincode",logincode);

                    c.setMaxAge(60*60*24*14); //2周时间Cookie过期     单位秒
                    c.setPath("/"); //表示任何请求路径都可以访问Cookie

                    response.addCookie(c);
                }
            }else if ("user".equals(type)){
                User user = userService.queryUserlogin(paramMap);

                session.setAttribute(Const.LOGIN_USER, user);

                if ("1".equals(rememberme)){
                    String logincode = "\"loginacct="+user.getLoginacct()+"&userpwd="+user.getUserpswd()+"&logintype=user\"";
                    //loginacct=admin&userpwd=21232f297a57a5a743894a0e4a801fc3&logintype=1
                    System.out.println("用户-存放到Cookie中的键值对：logincode::::::::::::"+logincode);
                    Cookie c = new Cookie("logincode",logincode);

                    c.setMaxAge(60*60*24*14); //2周时间Cookie过期     单位秒
                    c.setPath("/"); //表示任何请求路径都可以访问Cookie

                    response.addCookie(c);
                }

                //加载当前登录用户的所拥有的许可权限.

                //User user = (User) session.getAttribute(Const.LOGIN_USER);

                List<Permission> myPermission = userService.queryPermissionByUserid(user.getId()); //当前用户所拥有的许可权限

                Permission permissionRoot = null;

                Map<Integer, Permission> map = new HashMap<Integer, Permission>();
                Set<String> myUris = new HashSet<String>(); //用于拦截器拦截许可权限

                for (Permission innerpermission  : myPermission) {
                    map.put(innerpermission.getId(), innerpermission );
                    myUris.add("/" + innerpermission.getUrl());
                }

                session.setAttribute(Const.MY_URIS, myUris);

                for (Permission permission : myPermission) {
                    //通过子查找父
                    //子菜单
                    Permission child = permission; //假设为子菜单
                    if (child.getPid() == null) {
                        permissionRoot = permission;
                    } else {
                        //父节点
                        Permission parent = map.get(child.getPid());
                        parent.getChildren().add(child);
                    }

                    session.setAttribute("permissionRoot", permissionRoot);
                }
            }else {

            }

            result.setData(type);
            result.setSuccess(true);
            // {"success":true}
        } catch (Exception e) {
            result.setMessage("登录失败！");
            e.printStackTrace();
            result.setSuccess(false);
            // {"success":false,"message":"登录失败！"}
        }

        return result;
    }

    //同步请求:
    /*@RequestMapping("/doLogin")
    public String doLogin(String loginacct, String userpswd, String type, HttpSession session){
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("loginacct", loginacct);
        paramMap.put("userpswd", userpswd);
        paramMap.put("type", type);

        User user = userService.queryUserlogin(paramMap);

        session.setAttribute(Const.LOGIN_USER, user);

        return "redirect:/main.htm";
    }*/

}
