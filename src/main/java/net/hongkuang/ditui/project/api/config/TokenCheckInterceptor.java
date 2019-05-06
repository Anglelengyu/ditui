package net.hongkuang.ditui.project.api.config;


import net.hongkuang.ditui.common.exception.TokenCheckException;
import net.hongkuang.ditui.common.utils.DateUtils;
import net.hongkuang.ditui.common.utils.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 */
@Aspect
@Component
public class TokenCheckInterceptor {
    private final static Logger logger = LoggerFactory.getLogger(TokenCheckInterceptor.class);


    /**
     * 定义一个controller切层点
     */
    @Pointcut("@annotation(net.hongkuang.ditui.project.api.config.AccessCheck)")
    private void anyMethod() {
    }

    @Around("anyMethod()")
    public Object checkRequestHead(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        request.setCharacterEncoding("UTF-8");
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();

        String token = request.getParameter("token");
        String userId = request.getParameter("userId");
        logger.debug("CheckRequestHead:  token = {}, userId:{}", token, userId);
        if (StringUtils.isBlank(token) || StringUtils.isBlank(userId)) {
            logger.error("CheckRequestHead:  token is empty or userId is empty");
            throw new TokenCheckException(999, "请求出错，请重新登录");
        }
        // 判断token与userId 是否一致
        UserTokenInfo userTokenInfo = null;
        try {
            userTokenInfo = UserTokenInfo.parse(token);
        } catch (Exception e) {
            throw new TokenCheckException(999, "请重新登录");
        }
        if (!userTokenInfo.getUserId().equals(userId)) {
            throw new TokenCheckException(999, "会话失效，请重新登录");
        }
        if (userTokenInfo.getExpireTime() < DateUtils.getNowDate().getTime()) {
            throw new TokenCheckException(999, "会话失效，请重新登录");
        }
        return joinPoint.proceed();
    }

}
