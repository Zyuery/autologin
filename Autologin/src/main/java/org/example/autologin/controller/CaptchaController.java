package org.example.autologin.controller;

import com.google.code.kaptcha.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.http.HttpServletResponse;
import javax.imageio.ImageIO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

// 验证码控制器
@Controller
public class CaptchaController {
    // 注入验证码生成器
    @Autowired
    private Producer kaptchaProducer;
    // 生成验证码图片
    @GetMapping("/vc.jpg")
    public void getVerifyCode(HttpServletRequest request, HttpServletResponse response) {
        try {
            // 获取 HttpSession 对象
            HttpSession session = request.getSession();
            // 1.生成验证码文本
            String text = kaptchaProducer.createText();
            System.out.println("验证码文本: " + text);
            // 2.将验证码放入 session
            session.setAttribute("kaptcha", text);
            // 3.生成验证码图片
            BufferedImage bi = kaptchaProducer.createImage(text);
            System.out.println("验证码图片生成成功");
            // 设置响应内容类型为图片格式
            response.setContentType("image/jpeg");
            // 将图片写入响应输出流
            ImageIO.write(bi, "jpg", response.getOutputStream());
            response.getOutputStream().flush();
        } catch (IOException e) {
            // 处理 IO 异常
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            System.out.println("IO 异常: " + e.getMessage());
        } catch (Exception e) {
            // 处理其他异常
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            System.out.println("其他异常: " + e.getMessage());
        }
    }
}
