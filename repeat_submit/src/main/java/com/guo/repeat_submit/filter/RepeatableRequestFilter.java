package com.guo.repeat_submit.filter;


import com.guo.repeat_submit.request.RepeatableRequestWrapper;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class RepeatableRequestFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        // 如果请求类型是json就需要将request包装成缓存字符类以便反复读取
        if(StringUtils.startsWithIgnoreCase(request.getContentType(),"application/json")){
            RepeatableRequestWrapper requestWrapper= new RepeatableRequestWrapper(request, (HttpServletResponse) servletResponse);
            filterChain.doFilter(requestWrapper,servletResponse);
            // 直接终止, 不走后面没有包装request的过滤链
            return;
        }
        filterChain.doFilter(servletRequest,servletResponse);

    }
}
