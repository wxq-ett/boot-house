package com.etoak.controller;

import com.etoak.commons.VerifyCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Controller
public class CodeController {
//验证码接口
    @GetMapping("/code")
    public void code(HttpServletRequest request, HttpServletResponse response)throws IOException {
        //创建验证码对象

        VerifyCode verifyCode = new VerifyCode();
        //获取图片,写到前端页面
        BufferedImage image = verifyCode.getImage();
        //图片的表达式结果
        int result = verifyCode.getResult();
        //将验证码图片上的计算结果保存到session上
        request.getSession().setAttribute("code",result);

        //向前端写图片
        response.setHeader("Pragma","No-Cache");
        response.setHeader("Cache-Control","No-Cache");

        response.setDateHeader("Expires", 0L);

        // contentType
        response.setContentType("image/jpeg");
        ImageIO.write(image, "JPEG", response.getOutputStream());


    }



}
