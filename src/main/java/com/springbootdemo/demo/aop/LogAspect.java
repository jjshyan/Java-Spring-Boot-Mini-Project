package com.springbootdemo.demo.aop;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

@Slf4j
@Aspect
@Component
public class LogAspect {
    @Autowired
    private ObjectMapper mapper;
    private Long visitTime;
    private Class clazz;

    @Pointcut("execution(public * com.springbootdemo.demo.controller.*.*(..))")
    private void aspect(){}

    @Before("aspect()")
    public void before(JoinPoint joinPoint) throws NoSuchMethodException {
        // Get current time
        visitTime = System.currentTimeMillis();
        Date date = new Date(visitTime);
        clazz = joinPoint.getTarget().getClass(); // Get class method
        String methodName = joinPoint.getSignature().getName(); // Get method name
        Object[] args = joinPoint.getArgs(); // Get parameters

        // Get all methods of the target class
        Method[] methods = clazz.getMethods();
        Method targetMethod = null;

        // Traverse the method list of the target class and find methods that match the method name and parameter type
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                if (isParameterTypesMatch(parameterTypes, args)) {
                    targetMethod = method;
                    break;
                }
            }
        }

        // Check if a matching method is found, if not found, throw an exception
        if (targetMethod == null) {
            throw new NoSuchMethodException("Method not found: " + methodName);
        }

        // Get HttpServletRequest object
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        log.info("==================== Request Content Start ====================");
        log.info("Request Time: " + new SimpleDateFormat().format(date));
        log.info("Request IP: " + request.getRemoteAddr());
        log.info("Request Address: " + request.getRequestURI());
        log.info("Request Method: " + request.getMethod());
        log.info("Request Class Method: " + joinPoint.getSignature());
        log.info("Request Class Method Parameter: " + Arrays.toString(joinPoint.getArgs()));
        log.info("==================== Request Content End ====================");

    }

    private boolean isParameterTypesMatch(Class<?>[] parameterTypes, Object[] args) {
        if (parameterTypes.length != args.length) {
            return false;
        }
        for (int i = 0; i < parameterTypes.length; i++) {
            if (!parameterTypes[i].isAssignableFrom(args[i].getClass())) {
                return false;
            }
        }
        return true;
    }

    @AfterReturning(returning = "obj",pointcut = "aspect()")
    public void methodAfterReturning(Object obj){
        long time = System.currentTimeMillis() - visitTime;
        log.info("Operation Time:{}s", time);
        log.info("==================== Return Content ====================");
        try{
            log.info(mapper.writeValueAsString(obj));
        }catch (JsonProcessingException e){
            e.printStackTrace();
            log.error("An error occurred", e);
        }
        log.info("==================== Return Content ====================");
    }
    private void url(String[] classValue, boolean b, String[] value){
        String url;
        if (b && value != null && value.length > 0) {
            url = classValue[0] = value[0];
            log.info("url:{}", url);
        }
    }

}
