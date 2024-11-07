package org.example.autologin.controller;

import jakarta.annotation.Resource;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.autologin.repository.UserDao;
import org.example.autologin.utils.Result;
import org.example.autologin.utils.TokenUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShortTokenController {
    @Resource
    UserDao userDao;

    @RequestMapping("/shortToken")
    public Result shortToken(HttpServletRequest request, HttpServletResponse response,
                             HttpSession session, @RequestParam String fileId, @RequestParam String fileFormat) {

        // 从 session 中获取用户名
        String uname =(String)session.getAttribute("loggedInUser");
        // 生成短令牌，传入文件 ID 和用户名
        String shortToken = TokenUtils.generateShortToken(fileId, userDao.findByUname(uname).getUname());
        // Cookie 为键值对数据格式
        Cookie cookie_shortToken = new Cookie("cookie_shortToken", shortToken);
        cookie_shortToken.setMaxAge(60); // 设置 Cookie 过期时间为60s
        // 表示当前项目下都携带这个cookie
        cookie_shortToken.setPath(request.getContextPath());
        // 添加到 response 中
        response.addCookie(cookie_shortToken);
        // 存储文件 ID 到 session 中，以便后续使用
        session.setAttribute("fileId", fileId);
        session.setAttribute("fileFormat", fileFormat);

        return Result.success("短令牌生成成功（有效时间1min）", shortToken);
    }

}
