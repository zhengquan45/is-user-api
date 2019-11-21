package org.zhq.security.isuserapi.intercepter;

import cn.hutool.core.util.ArrayUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.zhq.security.isuserapi.user.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AclInterceptor extends HandlerInterceptorAdapter {

    private String[] permitUrls = new String[]{"/users/login"};

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println(4);
        boolean result = true;

        if (!ArrayUtil.contains(permitUrls, request.getRequestURI())) {
            User user = (User) request.getAttribute("user");

            if (user == null) {
                response.setContentType("text/plain");
                response.getWriter().write("need authentication");
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                result = false;
            } else {
                String method = request.getMethod();
                if (!user.hasPermission(method)) {
                    response.setContentType("text/plain");
                    response.getWriter().write("forbidden");
                    response.setStatus(HttpStatus.FORBIDDEN.value());
                    result = false;
                }
            }
        }
        return result;
    }
}
