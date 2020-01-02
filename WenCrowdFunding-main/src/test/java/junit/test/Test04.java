package junit.test;

import com.xw.atcrowdfunding.bean.User;
import com.xw.atcrowdfunding.manager.service.UserService;
import com.xw.atcrowdfunding.util.MD5Util;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test04 {

    public static void main(String[] ages){
        ApplicationContext ioc = new ClassPathXmlApplicationContext("spring/spring*.xml");
        UserService userService = ioc.getBean(UserService.class);

        for (int i = 1; i <= 100; i++) {
            User user = new User();
            user.setLoginacct("test" + i);
            user.setUserpswd(MD5Util.digest("123"));
            user.setUsername("test" + i);
            user.setEmail("test" + i + "@xw.com");
            user.setCreatetime("2019-12-23 15:05:00");
            userService.saveUser(user);
        }
    }

}
