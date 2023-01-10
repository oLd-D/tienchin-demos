package com.guo.repeat_submit.interceptor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.guo.repeat_submit.annotation.RepeatSubmit;
import com.guo.repeat_submit.redis.RedisCache;
import com.guo.repeat_submit.request.RepeatableRequestWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.invoke.MethodHandle;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class RepeatSubmitInterceptor implements HandlerInterceptor {

    private static final String REPEAT_PARAMS="repeat_params";
    private static final String REPEAT_TIME="repeat_time";
    private static final String REPEAT_SUBMIT_KEY=" repeat_submit_key";
    private static final String HEADER="Authorization";

    @Autowired
    RedisCache redisCache;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 假如controller中的方法被执行, 那么是由HandlerMethod对象进行处理的
        if(handler instanceof HandlerMethod){
            // 拿到该HandlerMethod对象
            HandlerMethod handlerMethod= (HandlerMethod) handler;
            // 通过该HandlerMethod对象拿到相应的Method对象
            Method method = handlerMethod.getMethod();
            // 拿到这个方法上面的注解
            RepeatSubmit repeatSubmit = method.getAnnotation(RepeatSubmit.class);
            // 假如有该注解
            if(repeatSubmit != null){
                // 是重复注解就返回错误
                if(isRepeatSubmit(request,repeatSubmit)){
                    Map<Object, Object> map = new HashMap<>();
                    map.put("status",500);
                    map.put("message",repeatSubmit.message());
                    response.setContentType("application/json;charset=utf-8");
                    response.getWriter().write(new ObjectMapper().writeValueAsString(map));
                    // 不用走后面的拦截了
                    return false;
                }
            }

        }
        return true;
    }

    private boolean isRepeatSubmit(HttpServletRequest request, RepeatSubmit repeatSubmit) {
        // 用于获取当前请求的参数, 看是否和之前的请求重复
        String nowParams="";
        // 过滤器中已经将json包装, 所以判断是不是json类型的请求
        if(request instanceof RepeatableRequestWrapper){
            try {
                // 拿到json参数转化的字符串
                nowParams = ((RepeatableRequestWrapper) request).getReader().readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // nowParams为空说明是其他类型的请求, 可以通过map拿到响应的参数
        if(StringUtils.isEmpty(nowParams)){
            try {
                nowParams=new  ObjectMapper().writeValueAsString(request.getParameterMap());
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        // 本次请求的信息(参数, 时间)
        Map<String,Object> nowDataMap=new HashMap<>();
        nowDataMap.put(REPEAT_PARAMS,nowParams);
        nowDataMap.put(REPEAT_TIME,System.currentTimeMillis());
        String requestURI=request.getRequestURI();
        String header=request.getHeader(HEADER);
        String cacheKey=REPEAT_SUBMIT_KEY+requestURI+header.replace("Bearer ","");
        Object cacheObject=redisCache.getCCacheObject(cacheKey);
        // 查看redis是否之前存过相同请求, 是则本次请求为重复请求
        if(cacheObject != null){
            Map<String, Object> map = (Map<String, Object>) cacheObject;
            // 存放的信息相同并且间隔小于规定时间则为重复请求
            if(compareParams(map, nowDataMap) && compareTime(map, nowDataMap, repeatSubmit.interval())){
                return true;
            }
        }
        redisCache.setCacheObject(cacheKey,nowDataMap,repeatSubmit.interval(), TimeUnit.MILLISECONDS);
        return false;
    }

    private boolean compareTime(Map<String, Object> map, Map<String, Object> nowDataMap, int interval) {
        Long time1 = (Long) map.get(REPEAT_TIME);
        Long time2 = (Long) nowDataMap.get(REPEAT_TIME);
        if((time2 - time1)< interval){
            return true;
        }
        return false;
    }

    private boolean compareParams(Map<String, Object> map, Map<String, Object> nowDataMap) {
        String nowParams= ((String) nowDataMap.get(REPEAT_PARAMS));
        String dataParams= ((String) map.get(REPEAT_PARAMS));
        return nowParams.equals(dataParams);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
