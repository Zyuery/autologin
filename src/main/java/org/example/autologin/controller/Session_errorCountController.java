package org.example.autologin.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Session_errorCountController {

    @GetMapping("/get_error-count")
    public ResponseEntity<?> getSessionData(HttpServletRequest request) {
        HttpSession session = request.getSession();

        // 获取 errorCount 属性，并进行空值检查
        Integer errorCountObj = (Integer) session.getAttribute("errorCount");
        int errorCount = (errorCountObj != null) ? errorCountObj.intValue() : 0;

        // 返回 JSON 响应
        String responseBody = "{ \"errorCount\": " + errorCount + " }";
        return ResponseEntity.ok().body(responseBody);
    }
}
