package com.leehom.news.controller;

import com.leehom.news.enums.ResultEnum;
import com.leehom.news.exception.NewsException;
import com.leehom.news.po.User;
import com.leehom.news.service.UserService;
import com.leehom.news.utils.MD5;
import com.leehom.news.utils.ResultVOUtil;
import com.leehom.news.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping(value = "/news/show/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/register")
    public ResultVO register(@RequestBody User user){
//        user.setUserPass(MD5.MD5(user.getUserPass()));
        boolean result = userService.userRegister(user);
        if(result == false){
            throw new NewsException(ResultEnum.USER_REGISTER_FAIL);
        }

        return ResultVOUtil.success(user);
    }

    @PostMapping(value = "/login")
    public ResultVO login(@RequestBody User user, HttpServletRequest request, HttpServletResponse response){
        User result = userService.userLogin(user);
        if(result == null){
            log.error("用户登录：账号或密码错误.{}",user);
            throw new NewsException(ResultEnum.LOGIN_ERROR);
        }
        addCookie(user,request,response);
        return ResultVOUtil.success(result);
    }


    private static void addCookie(User user,HttpServletRequest request,
                                 HttpServletResponse response){
        Cookie cookie = new Cookie(user.getUserName(),user.getUserPass());
        cookie.setPath(request.getContextPath()+"/");
        cookie.setMaxAge(7*24*60*60);
        response.addCookie(cookie);
    }


    @GetMapping(value = "/cookie")
    public ResultVO cookie(@RequestParam(value = "userName") String userName,
                           HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        Map<String,String> map = new HashMap<>();
        for(Cookie cookie:cookies){
            if(cookie.getName().equals(userName)){
                String userPass = cookie.getValue();
                map.put("userName",userName);
                map.put("userPass",userPass);
            }
        }
        log.info("cookie:{}",map);
        return ResultVOUtil.success(map);
    }

}