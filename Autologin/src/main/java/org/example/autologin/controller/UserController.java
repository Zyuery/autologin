package org.example.autologin.controller;

import jakarta.annotation.Resource;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.autologin.domain.User;
import org.example.autologin.service.UserService;
import org.example.autologin.utils.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/login")
    public Result<String> loginController(@RequestParam String uname, @RequestParam String password,
                                          HttpServletResponse response, HttpSession session,
                                          @RequestParam(required = false) String captcha) {
        // 从 session 中获取 errorCount 属性，并进行空值检查
        Integer errorCount = (Integer) session.getAttribute("errorCount");
        // 初始化 errorCount 属性为 0
        if (errorCount == null) {
            errorCount = 0;
        }
        // 如果错误次数大于等于阈值，验证验证码
        if (errorCount >= 1) {
            String expectedCaptcha = (String) session.getAttribute("kaptcha");
            session.removeAttribute("kaptcha"); // 使用后删除 session 中的验证码属性
            if (expectedCaptcha == null ||!captcha.equalsIgnoreCase(expectedCaptcha)) {
                errorCount++;
                session.setAttribute("errorCount", errorCount);
                return Result.error("0", "验证码错误",errorCount,"redirect:/user/login");
            }
        }
        // 执行登录验证
        User user = userService.loginService(uname, password);
        if (user!= null) {
            // 登录成功，将用户信息存入 session 中
            session.setAttribute("loggedInUser", uname);
            Cookie cookie = new Cookie("loggedIn", "true");
            cookie.setMaxAge(3600 * 24 * 7); // 设置 Cookie 过期时间为 1 周
            cookie.setPath("/");
            // 添加到 response 中
            response.addCookie(cookie);
            // 登录成功，重定向到 /user/home
            return Result.success("1", "登录成功",errorCount,"redirect:/user/home");
        } else {
            // 登录失败，增加错误次数
            errorCount++;
            session.setAttribute("errorCount", errorCount);
            return Result.error("0", "账号或密码错误",errorCount,"redirect:/user/login");
        }
    }

    @PostMapping("/register")
    public Result<User> registController(@RequestBody User newUser) {
        // 执行用户注册
        User user = userService.registService(newUser);
        // 根据注册结果返回适当的响应
        if (user != null) {
            return Result.success(user, "注册成功！");
        } else {
            return Result.error("0", "用户名已存在！");
        }
    }
}


