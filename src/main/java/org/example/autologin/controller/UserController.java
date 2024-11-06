package org.example.autologin.controller;

import jakarta.annotation.Resource;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.autologin.domain.User;
import org.example.autologin.repository.UserDao;
import org.example.autologin.service.UserService;
import org.example.autologin.utils.Result;
import org.example.autologin.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;
    @Resource
    private  TokenUtils tokenUtils;
    @Autowired
    private UserDao userDao;

    // 登录
    @PostMapping("/login")
    public Result<String> loginController(@RequestParam String uname, @RequestParam String password,
                                          HttpServletResponse response, HttpSession session,
                                          HttpServletRequest request,
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
                return Result.error("0", "验证码错误",errorCount,"/user/login");
            }
        }
        // 执行登录验证
        User user = userService.loginService(uname, password);
        if (user!= null) {
            errorCount=0;
            session.setAttribute("errorCount", errorCount);
            // 登录成功，将用户信息存入 session 中
            session.setAttribute("loggedInUser", user.getUname());
            // 登录成功，生成长令牌并存储在 cookie 中
            String longToken = tokenUtils.generateLongToken(userDao.findByUname(uname).getUname());
            // Cookie 为键值对数据格式
            Cookie cookie_longToken = new Cookie("cookie_longToken", longToken);
            cookie_longToken.setMaxAge(3600 * 24 * 7); // 设置 Cookie 过期时间为 1 周
            // 表示当前项目下都携带这个cookie
            cookie_longToken.setPath(request.getContextPath());
            // 添加到 response 中
            response.addCookie(cookie_longToken);
            // 登录成功，重定向到 /user/home
            return Result.success("user", "登录成功",errorCount,"/user/home");
        } else {
            // 登录失败，增加错误次数
            errorCount++;
            session.setAttribute("errorCount", errorCount);
            return Result.error("0", "账号或密码错误",errorCount,"/user/login");
        }
    }
    // 退出登录
    @GetMapping(value = "/logout")
    public Result logout(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        // 删除session里面的用户信息
        session.removeAttribute("loggedInUser");
        // 删除cookie，实现退出登录
        Cookie cookie_longToken = new Cookie("cookie_longToken", "");
        // 设置cookie的持久化时间，0
        cookie_longToken.setMaxAge(0);
        // 设置为当前项目下都携带这个cookie
        cookie_longToken.setPath(request.getContextPath());
        // 向客户端发送cookie
        response.addCookie(cookie_longToken);
        return Result.success("0", "退出成功！","/user/login");
    }
    // 注册
    @PostMapping("/register")
    public Result<User> registController(@RequestBody User newUser) {
        // 执行用户注册
        User user = userService.registService(newUser);
        // 根据注册结果返回适当的响应
        if (user != null) {
            return Result.success(user, "注册成功！");
        } else {
            return Result.error("1", "用户名已存在！");
        }
    }

}


