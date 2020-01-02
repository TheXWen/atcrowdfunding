package com.xw.atcrowdfunding.manager.controller;

import com.xw.atcrowdfunding.bean.Advertisement;
import com.xw.atcrowdfunding.bean.User;
import com.xw.atcrowdfunding.manager.service.AdvertService;
import com.xw.atcrowdfunding.util.Page;
import com.xw.atcrowdfunding.util.AjaxResult;
import com.xw.atcrowdfunding.util.Const;
import com.xw.atcrowdfunding.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/advert")
public class AdvertController {

    @Autowired
    private AdvertService advertService;

    @RequestMapping("/index")
    public String index(){
        return "advert/index";
    }

    @RequestMapping("/toAdd")
    public String toAdd(){
        return "advert/add";
    }

    /**
     * 新增资质数据
     * @return
     */
    @ResponseBody
    @RequestMapping("/doAdd")
    public Object doAdd(HttpServletRequest request, Advertisement advert , HttpSession session) {
        AjaxResult result = new AjaxResult();


        try {
            MultipartHttpServletRequest mreq = (MultipartHttpServletRequest)request;

            MultipartFile mfile = mreq.getFile("advpic");

            String name = ((MultipartFile) mfile).getOriginalFilename();//java.jpg
            String extname = name.substring(name.lastIndexOf(".")); // .jpg

            String iconpath = UUID.randomUUID().toString()+extname; //232243343.jpg

            ServletContext servletContext = session.getServletContext();
            String realpath = servletContext.getRealPath("/pics");

            String path =realpath+ "\\adv\\"+iconpath;

            mfile.transferTo(new File(path));

            User user = (User)session.getAttribute(Const.LOGIN_USER);
            advert.setUserid(user.getId());
            advert.setStatus("1");
            advert.setIconpath(iconpath);

            int count = advertService.insertAdvert(advert);
            result.setSuccess(count==1);
        } catch ( Exception e ) {
            e.printStackTrace();
            result.setSuccess(false);
        }

        return result;
    }

    //条件查询
    @ResponseBody
    @RequestMapping("/doIndex")
    public Object index(@RequestParam(value = "pageno", required = false, defaultValue = "1") Integer pageno,
                        @RequestParam(value = "pagesize", required = false, defaultValue = "10") Integer pagesize,
                        String queryText){
        AjaxResult result = new AjaxResult();
        try {

            Map paramMap = new HashMap();
            paramMap.put("pageno", pageno);
            paramMap.put("pagesize", pagesize);

            if (StringUtil.isNotEmpty(queryText)){
                if (queryText.contains("%")){
                    queryText = queryText.replaceAll("%", "\\\\%");
                }
                paramMap.put("queryText", queryText);
            }
            Page page = advertService.queryPage(paramMap);

            result.setSuccess(true);
            result.setPage(page);
        } catch (Exception e) {
            result.setSuccess(false);
            e.printStackTrace();
            result.setMessage("查询数据失败!");
        }

        return result; //将对象序列化为JSON字符串,以流的形式返回.
    }

}
