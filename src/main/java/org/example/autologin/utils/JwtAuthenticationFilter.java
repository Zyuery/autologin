//package org.example.autologin.utils;
//
//
//import io.jsonwebtoken.io.IOException;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//// 自定义过滤器，用于验证JWT令牌
//public class JwtAuthenticationFilter extends OncePerRequestFilter {
//    private static final String SECRET_KEY = "my_token_key_123zyuer";
//    private static final String AUTHORIZATION_HEADER = "Authorization";
//
//@Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//            throws ServletException, IOException {
//
//        String token = request.getHeader(AUTHORIZATION_HEADER);
//        if (token!= null && token.startsWith("Bearer "))
//        {
//            token = token.substring(7);
//            if (JwtUtil.validateToken(token))
//            {
//                // Token is valid, continue the request
//                filterChain.doFilter(request, response);
//            }
//            else
//            {
//                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
//            }
//        }
//        else
//        {
//            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "No token provided");
//        }
//    }
//}