package com.course.controller;

import com.course.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j;
import org.apache.log4j.PropertyConfigurator;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


@RestController
@Log4j
@Api(value = "v1",description = "用户管理系统接口测试")
@RequestMapping("v1")

public class userManager {
   // private Logger log = Logger.getLogger(userManager.class.getName());

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;


    @ApiOperation(value="登陆接口",httpMethod = "POST")
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Boolean login(HttpServletResponse httpServletResponse, @RequestBody User user){

        int i=sqlSessionTemplate.selectOne("login",user);
        System.out.println("获取登陆信息是！！！！！！！！！！！！！！！！："+i);
        Cookie cookie=new Cookie("login","true");
        httpServletResponse.addCookie(cookie);
        //String log4jConfPath = "D://Workspace/MyAutoAPI/ApiDevlep/src/main/resources/log4j.properties";
       // PropertyConfigurator.configure(log4jConfPath);

        log.info("查询到的结果是："+i);
        if(i==1){
            log.info("登陆成功,登陆用户是："+user.getUserName());
            System.out.println("登陆成功,登陆用户是：888888888888"+user.getUserName());
            return  true;
        }
        System.out.println("获取不到9999999999999999");

        return  false;

    }

    @ApiOperation(value="添加用户", httpMethod = "POST")
    @RequestMapping(value = "/addUser" ,method = RequestMethod.POST)
    public Boolean addUser(HttpServletRequest request, @RequestBody User user){
        Boolean x=verifyCookies(request);

        int result=0;
        if(x!=null){
            result=sqlSessionTemplate.insert("addUser",user);

        }
        if(result>0){
            log.info("添加的用户数量是："+result);
            return   true;
        }
        return  false;
    }
    @ApiOperation(value="获取用户信息", httpMethod = "POST")
    @RequestMapping(value = "getUserInfo" ,method = RequestMethod.POST)
    public List<User> getUserInfo(HttpServletRequest request, @RequestBody User user) {
        Boolean x = verifyCookies(request);
        if(x==true){
            List<User> users=sqlSessionTemplate.selectList("getUserInfo",user);
            log.info("获取的用户数量是："+users.size());
            System.out.println("获取的用户数量是：>>>>>>>>>>>>>"+users.size());
            return users;

        }
        System.out.println("获取的用户数量是：***************");

        return null;


    }
    @ApiOperation(value="更新/删除用户数据", httpMethod = "POST")
    @RequestMapping(value = "updateUserInfo" ,method = RequestMethod.POST)
    public int updateUserInfo(HttpServletRequest request, @RequestBody User user) {
        Boolean x = verifyCookies(request);
        int i =0;
        if(x==true){
            i=sqlSessionTemplate.update("updateUserInfo",user);

        }
        log.info("更新的用户数量是："+i);
       return i;
    }




    public Boolean verifyCookies(HttpServletRequest request){
        Cookie[] cookies=request.getCookies();
        if(cookies==null){
               log.info("cookies为空");

            System.out.println("cookies为空))))))))))))))))))");
               return false;
        }
        for (Cookie ck:cookies){
           if(ck.getName().equals("login")&&ck.getValue().equals("true")){
               log.info("cookies验证通过");
               System.out.println("cookies验证通过11111111111111111111111");
               return  true;
           }
        }
        return false;

    }











}
