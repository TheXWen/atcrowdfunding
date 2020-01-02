package com.xw.atcrowdfunding.interceptor;

import com.xw.atcrowdfunding.manager.service.PermissionService;
import com.xw.atcrowdfunding.util.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

public class AuthInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private PermissionService permissionService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //如果每次拦截请求都查询数据库表的所有许可,效率比较低.
       /* List<Permission> queryAllPermission = permissionService.queryAllPermission();

        Set<String> allURIs = new HashSet<String>();
        for (Permission permission : queryAllPermission) {
            allURIs.add("/" + permission.getUrl());
        }*/

        //改进效率:在服务器启动时加载所有许可路径.并存放application域中.
        Set<String> allURIs = (Set<String>)request.getSession().getServletContext().getAttribute(Const.ALL_PERMISSION_URI);

        //2.判断请求路径是否在所有许可范围内
        String servletPath = request.getServletPath();
        if (allURIs.contains(servletPath)){
            //3.判断请求路径是否在用户所拥有的权限内
            Set<String> myURIs = (Set<String>)request.getSession().getAttribute(Const.MY_URIS);
            if (myURIs.contains(servletPath)){
                return true;
            }else {
                response.sendRedirect(request.getContextPath() + "/login.htm");
                return false;
            }
        }else {//不在拦截范围内,放行
            return true;
        }
    }
}
