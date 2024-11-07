package org.example.autologin.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.autologin.service.TokenService;
import org.example.autologin.utils.TokenUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.*;
import java.net.URLEncoder;

@Controller
public class DownloadController {
    private final TokenService tokenService;
    Logger log = LoggerFactory.getLogger(DownloadController.class);

    @Autowired
    public DownloadController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @RequestMapping("/download")
    public void download(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        try {
            String fileId = session.getAttribute("fileId").toString();
            String fileFormat = session.getAttribute("fileFormat").toString();
            System.out.println(fileId+fileFormat);
            // 打印进入下载方法的信息
            log.info("Entering download method.");
            // 从请求中获取短令牌
            String shortToken = getShortTokenFromRequest(request);
            // 检查短令牌是否为空
            if (shortToken == null) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Short token not provided.");
                return;
            }
            log.info("Short token received: " + shortToken);

            // 从请求中获取长令牌
            String longToken = getLongTokenFromRequest(request);
            if (longToken == null) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Long token not provided.");
                return;
            }
            log.info("Long token received: " + longToken);

            // 从长令牌中获取用户名
            String username = TokenUtils.getUsernameFromLongToken(longToken);
            if (username == null) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid long token.");
                return;
            }
            log.info("Username extracted from long token: " + username);

            // 验证短令牌是否有效
            if (!tokenService.isShortTokenValid(shortToken, fileId,username)) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid short token.");
                return;
            }
            log.info("Short token is valid.");

            // 下载文件
            File file = new File("src/main/resources/static/downloadfile/" + fileId + ".pdf");
            // 打印文件路径
            log.info(file.getPath());
            // 从请求中获取文件名
            String filename = file.getName();
            // 打印文件名
            String ext = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
            log.info("文件后缀名：" + ext);
            // 将文件写入输入流
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStream fis = new BufferedInputStream(fileInputStream);
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            // 设置response的Header
            response.setCharacterEncoding("UTF-8");
            //Content-Disposition的作用：告知浏览器以何种方式显示响应返回的文件，用浏览器打开还是以附件的形式下载到本地保存
            //attachment表示以附件方式下载   inline表示在线打开   "Content-Disposition: inline; filename=文件名.mp3"
            // filename表示文件的默认名称，因为网络传输只支持URL编码的相关支付，因此需要将文件名URL编码后进行传输,前端收到后需要反编码才能获取到真正的名称
            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
            response.addHeader("Content-Length", "" + file.length());
            OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            outputStream.write(buffer);
            outputStream.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    // 从请求中获取长令牌
    private String getLongTokenFromRequest(HttpServletRequest request) {
        // 从请求中获取长令牌的逻辑（假设长令牌存储在 Cookie 中）
        Cookie[] cookies = request.getCookies();
        if (cookies!= null) {
            for (Cookie cookie : cookies) {
                if ("cookie_longToken".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
    //从请求中获取短令牌
    private String getShortTokenFromRequest(HttpServletRequest request) {
        // 从请求中获取长令牌的逻辑（假设长令牌存储在 Cookie 中）
        Cookie[] cookies = request.getCookies();
        if (cookies!= null) {
            for (Cookie cookie : cookies) {
                if ("cookie_shortToken".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}