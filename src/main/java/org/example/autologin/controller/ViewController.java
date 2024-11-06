package org.example.autologin.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.autologin.utils.TokenUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class ViewController {
    // GET 方法返回登录页面
    @GetMapping("/login")
    public String loginPage(Model model) {
        // 可以向模板传递需要的数据
        model.addAttribute("pageTitle", "登录页面");
        return "login"; // 这里返回的是Thymeleaf模板的文件名，比如 login.html
    }
    // GET 方法返回注册页面
    @GetMapping("/register")
    public String registerPage(Model model) {
        // 可以向模板传递需要的数据
        model.addAttribute("pageTitle", "注册页面");
        return "register"; // 这里返回的是Thymeleaf模板的文件名，比如 register.html
    }
    @GetMapping("/home")
    public String home(HttpServletRequest request, Model model) {
        // 获取长令牌 Cookie
        Cookie[] cookies = request.getCookies();
        if (cookies!= null) {
            for (Cookie cookie : cookies) {
                if ("cookie_longToken".equals(cookie.getName())) {
                    String longToken = cookie.getValue();
                    String username = TokenUtils.getUsernameFromLongToken(longToken);
                    if (username!= null) {
                        model.addAttribute("welcomeMessage", "欢迎回来，" + username);
                        break;
                    }
                }
            }
        }
        return "home";
    }
}
