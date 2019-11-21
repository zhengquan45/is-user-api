package org.zhq.security.isuserapi.filter;

import cn.hutool.core.util.StrUtil;
import com.lambdaworks.crypto.SCryptUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.zhq.security.isuserapi.user.User;
import org.zhq.security.isuserapi.user.UserRepository;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
@Order(2)
public class BasicAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        System.out.println(2);
        String authHeader = httpServletRequest.getHeader("Authorization");
        if(StrUtil.isNotBlank(authHeader)){
            String token64 = StrUtil.subAfter(authHeader, "Basic ",false);
            String token = new String(Base64Utils.decodeFromString(token64));
            String[] items = StrUtil.split(token, ":");
            String username = items[0];
            String password = items[1];
            User user = userRepository.findByUsername(username);
            if(user!=null && SCryptUtil.check(password,user.getPassword())){
                httpServletRequest.getSession().setAttribute("user",user.buildInfo());
                httpServletRequest.getSession().setAttribute("temp",true);
            }
        }

        try{
            filterChain.doFilter(httpServletRequest,httpServletResponse);
        }finally {
            HttpSession session = httpServletRequest.getSession();
            if((Boolean) session.getAttribute("temp")){
                session.invalidate();
            }
        }
    }
}
