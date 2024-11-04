package org.example.autologin.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

// 生成和验证JWT令牌的工具类
public class JwtUtil {
// 定义JWT令牌的过期时间和密钥
    // 过期时间为24小时
    private static final long EXPIRATION_TIME = 86400000; // 24 hours in milliseconds
    // 密钥
    private static final String SECRET_KEY = "my_token_key_123zyuer";


    // 生成JWT令牌
    public static String generateToken(String username) {
        // 定义JWT令牌的声明
        // 声明为一个Map，存储JWT令牌的声明，如用户名、过期时间等
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .setClaims(claims)// 声明
                .setSubject(username)// 主题
                .setIssuedAt(new Date())// 签发时间
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))// 过期时间
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)// 签名算法和密钥
                .compact();// 生成JWT令牌
    }
    // 验证JWT令牌
    public static boolean validateToken(String token) {
        // 解析JWT令牌
        try {
            // 解析JWT令牌，验证签名和过期时间
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    // 从JWT令牌中获取用户名
    public static String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
}
