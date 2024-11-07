package org.example.autologin.service.servicelmpl;

import org.example.autologin.service.TokenService;
import org.example.autologin.utils.TokenUtils;
import org.springframework.stereotype.Service;

// 实现 TokenService 接口的方法
@Service
public class TokenServicelmpl implements TokenService {
    @Override
    // 验证短令牌是否有效
    public boolean isShortTokenValid(String shortToken, String fileId, String username) {
        return TokenUtils.validateShortToken(shortToken, fileId, username);
    }

    @Override
    // 验证长令牌来刷新短令牌
    public String refreshShortToken(String longToken, String fileId, String username) {
        if (TokenUtils.validateLongToken(longToken, username)) {
            return TokenUtils.generateShortToken(fileId, username);
        }
        return null;
    }
}
