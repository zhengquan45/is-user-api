package org.zhq.security.isuserapi.filter;

import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(1)
@Slf4j
public class RateLimitFilter extends OncePerRequestFilter {

    private RateLimiter rateLimiter = RateLimiter.create(1);

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        log.info("ratelimit request 1/s");
        if(rateLimiter.tryAcquire()){
                filterChain.doFilter(httpServletRequest,httpServletResponse);
            }else{
                httpServletResponse.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
                httpServletResponse.getWriter().write("too many request!!!");
                httpServletResponse.getWriter().flush();
            }
    }
}
