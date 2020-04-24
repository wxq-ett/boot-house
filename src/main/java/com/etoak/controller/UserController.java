package com.etoak.controller;


import com.etoak.bean.User;


import com.etoak.exception.ParamException;
import com.etoak.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/toReg")
    public String toRegPage(){
        return "user/reg";
    }

    @PostMapping("/reg")
    public String reg(@RequestParam String confirmpassword, User user){
        if(!StringUtils.equals(confirmpassword,user.getPassword())){
            throw new ParamException("两次密码不一致");
        }
        userService.addUser(user);
        return "redirect:/user/toReg";
    }


}
