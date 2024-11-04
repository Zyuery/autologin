//package org.example.autologin.utils;
//
//import jakarta.annotation.Resource;
//import jakarta.servlet.*;
//import jakarta.servlet.annotation.WebFilter;
//import jakarta.servlet.http.Cookie;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import org.example.autologin.repository.UserDao;
//
//import java.io.IOException;
////urlPatterns = "/user/home" 表示只对 /user/home 路径进行过滤
//@WebFilter(filterName = "AutoLoginFilter", urlPatterns ={"/user/home"})
//// 自动登录过滤器
//public class AutoLoginFilter implements Filter {
//    @Resource
//    UserDao userDao;
//// 重写过滤器的三个方法
//    // 初始化过滤器
//    @Override
//    public void init(FilterConfig filterConfig) {
//        System.out.println("调用了Init方法");
//    }
//    // 过滤请求
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        // 输出日志
//        System.out.println("调用了doFilter方法");
//        // 转换为 HttpServletRequest 和 HttpServletResponse 对象
//        HttpServletRequest request = (HttpServletRequest) servletRequest;
//        HttpServletResponse response = (HttpServletResponse) servletResponse;
//        // 检查 Session 是否存在已登录的用户,false表示不创建新的 Session
//        HttpSession session = request.getSession(false);
//        if (session!= null && session.getAttribute("loggedInUser")!= null) {
//            // 已登录，直接继续处理请求
//            filterChain.doFilter(request, response);
//        } else {
//            // 未登录，检查是否有自动登录的 Cookie
//            Cookie[] cookies = request.getCookies();
//            if (cookies!= null) {
//                for (Cookie cookie : cookies) {
//                    if ("autoLogin".equals(cookie.getName()) && "true".equals(cookie.getValue())) {
//                        // 尝试从持久化存储（如数据库）中获取用户信息并设置 Session
//                        // 这里假设你有一个方法可以根据某种标识获取用户信息
//                        String username = null;
//                        if (session != null) {
//                            username = getUserFromSomewhere(session);
//                        }
//                        if (username!= null) {
//                            session = request.getSession();
//                            session.setAttribute("loggedInUser", username);
//                            filterChain.doFilter(request, response);
//                        } else {
//                            // 没有找到用户信息，重定向到登录页面
//                            response.sendRedirect("/user/login");
//                        }
//                        break;
//                    }
//                }
//            } else {
//                // 没有 Cookie，重定向到登录页面
//                response.sendRedirect("/user/login");
//            }
//        }
//    }
//
//    // 从持久化存储中获取用户信息
//    private String getUserFromSomewhere(HttpSession session) {
//        System.out.println("调用了getUserFromSomewhere方法");
//        return  userDao.findByUname((String) session.getAttribute("loggedInUser")).getUname();
//    }
//    // 销毁过滤器
//    @Override
//    public void destroy() {
//        System.out.println("调用了Destroy方法");
//    }
//}
