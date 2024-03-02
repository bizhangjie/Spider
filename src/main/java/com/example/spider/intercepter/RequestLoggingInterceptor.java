package com.example.spider.intercepter;

/**
 * @Author 章杰
 * @Date 2024/3/2 16:25
 * @Version 1.0.0
 */
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class RequestLoggingInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(RequestLoggingInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String requestInfo = String.format("Request URL: %s, Method: %s", request.getRequestURL(), request.getMethod());
        logger.info(requestInfo);
        // 可以在这里记录更多的请求信息，如请求参数、请求头等
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        int statusCode = response.getStatus();
        String responseInfo = String.format("Response Status: %d", statusCode);
        logger.info(responseInfo);
        // 这里可以记录响应信息，如响应状态码、响应头等
    }
}
