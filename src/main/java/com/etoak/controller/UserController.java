package com.etoak.controller;


import com.etoak.bean.User;


import com.etoak.commons.CommonResult;
import com.etoak.exception.ParamException;
import com.etoak.exception.UserLoginException;
import com.etoak.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/user")
@Slf4j
public class UserController {

    // 用户的激活状态
    public static final int ACTIVE_STATE = 1;

    @Autowired
    UserService userService;

    //跳转到注册页面
    @GetMapping("/toReg")
    public String toRegPage(){
        return "user/reg";
    }

    @PostMapping("/reg")
    public String reg(@RequestParam String confirmPassword, User user){
        // 检查两次密码是否一致
        if(!StringUtils.equals(confirmPassword,user.getPassword())){
            throw new ParamException("两次密码不一致");
        }
        userService.addUser(user);
        return "redirect:/user/toReg";
    }

    //校验用户名唯一性
    @GetMapping("/validateName")
    @ResponseBody
    public String validateName(@RequestParam String name){
       log.info("param name - {}",name);
       User user = userService.queryByName(name);
       //如果用户null,返回true表示用户名可以使用
        //如果用户不为null,返回false表示用户名已存在,不可以使用
        return user == null ? "true":"false";
    }

    //跳转到登录页面
    @GetMapping("/toLogin")
    public String toLogin(){
        return "user/login";
    }

    //登录请求
    @PostMapping("/login")
    @ResponseBody
    public CommonResult login(@RequestParam String name,
                              @RequestParam String password,
                              @RequestParam String code,
                              HttpServletRequest request){

        /*验证码是否正确
        先根据用户查询用户
        * 验证密码
        * */
        HttpSession session = request.getSession();
        String sessionCode = String.valueOf(session.getAttribute("code"));
        if(!StringUtils.equals(code,sessionCode)){
            throw new UserLoginException("验证码错误");
        }
        //根据name查询用户
        User user = userService.queryByName(name);
        if(ObjectUtils.isEmpty(user)){
            log.error("未查询到用户");
            throw new UserLoginException("用户名或密码错误");
        }

        // 判断用户状态
       if(!(user.getState() == ACTIVE_STATE)) {
            throw new UserLoginException("用户状态异常");
        }

        //给密码加密 进行密码比对
        password = DigestUtils.md5Hex(password);
        if(!StringUtils.equals(password,user.getPassword())){
            log.error("密码错误"); //千万不要把密码记录到日志
            throw new UserLoginException("用户名或密码错误");


        }
        //销毁之前的session
        session.invalidate();
        session = request.getSession();

        user.setPassword(null);
        session.setAttribute("user",user);

        return new CommonResult(CommonResult.SUCCESS_CODE,CommonResult.SUCCESS_MSG);



    }

    //退出
    @RequestMapping("/logout")
    public String logout(HttpServletRequest request){
        request.getSession().invalidate();
        return "redirect:/user/toLogin";
    }





}
