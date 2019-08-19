package com.zxz.freedomlogging.aspect;

import com.zxz.freedomlogging.annotation.Log;
import com.zxz.freedomlogging.dto.LogDO;
import com.zxz.freedomlogging.redis.RedisUtil;
import com.zxz.freedomlogging.utils.DateUtil;
import com.zxz.freedomlogging.utils.IPUtils;
import com.zxz.freedomlogging.utils.JSONUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

@Aspect
@Component
public class LogAspect {
    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);
    @Autowired
    RedisUtil redisUtil;

    @Pointcut("@annotation(com.zxz.freedomlogging.annotation.Log)")
    public void logPointCut() {
    }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        // 执行方法
        Object result = point.proceed();
        // 执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;
        //异步保存日志
        saveLog(point, time);
        return result;
    }

    private void saveLog(ProceedingJoinPoint joinPoint, long time) throws InterruptedException {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        LogDO sysLog = new LogDO();
        Log syslog = method.getAnnotation(Log.class);
        if (syslog != null) {
            // 注解上的描述
            sysLog.setOperation(syslog.value());
        }
        // 请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        sysLog.setMethod(className + "." + methodName + "()");
        // 请求的参数
        Object[] args = joinPoint.getArgs();
        if(args != null && args.length >0){
            try {
                String params = JSONUtils.beanToJson(args[0]).substring(0, 4999);
                sysLog.setParams(params);
            } catch (Exception e) {
                logger.error("获取请求的参数出现错误：");
                logger.error(e.getMessage());
            }
        }
        // 获取request
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(servletRequestAttributes != null){
            HttpServletRequest request = servletRequestAttributes.getRequest();
            // 设置IP地址
            sysLog.setIp(IPUtils.getIpAddr(request));
        }
        sysLog.setTime((int) time);
        // 系统当前时间
        Date date = new Date();
        sysLog.setGmtCreate(date);
        // 保存系统日志
        /*
         * 格式： 2019-8 :{ 1:[], ...,18: []}
         */
        DateUtil dateUtil = new DateUtil();
        String key = dateUtil.getCurrDate("yyyy-MM");
        String item = dateUtil.getCurrDate("dd");
        try {
            redisUtil.hset(key, item, sysLog,604800); // 保存7天
        }catch (Exception e){
            logger.error("日志入库出现错误：");
            logger.error(e.getMessage());
        }
    }
}

