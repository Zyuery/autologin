package org.example.autologin.controller;

import jakarta.servlet.http.HttpSession;
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
        return "/login"; // 这里返回的是Thymeleaf模板的文件名，比如 login.html
    }
    // GET 方法返回注册页面
    @GetMapping("/register")
    public String registerPage(Model model) {
        // 可以向模板传递需要的数据
        model.addAttribute("pageTitle", "注册页面");
        return "/register"; // 这里返回的是Thymeleaf模板的文件名，比如 register.html
    }
    // GET 方法返回主页页面
    @GetMapping("/home")
    public String homePage(Model model, HttpSession session) {
        // 可以向模板传递需要的数据
        model.addAttribute("pageTitle", "主页页面");
        String username = (String) session.getAttribute("loggedInUser");
        if (username!= null) {
            model.addAttribute("username", username);
        }
        return "/home";
    }
}
