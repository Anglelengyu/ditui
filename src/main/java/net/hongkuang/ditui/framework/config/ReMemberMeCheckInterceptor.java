package net.hongkuang.ditui.framework.config;


import net.hongkuang.ditui.project.system.user.domain.User;
import net.hongkuang.ditui.project.system.user.mapper.UserMapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ReMemberMeCheckInterceptor {
    private final static Logger logger = LoggerFactory.getLogger(ReMemberMeCheckInterceptor.class);

    @Autowired
    private UserMapper userMapper;

    /**
     * 定义一个controller切层点
     */
    @Pointcut("@annotation(net.hongkuang.ditui.framework.config.RememberMeCheck)")
    private void anyMethod() {
    }

    @Around("anyMethod()")
    public Object checkRequestHead(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        request.setCharacterEncoding("UTF-8");
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        Subject currentUser = SecurityUtils.getSubject();
        if (currentUser != null) {
            User user = (User) currentUser.getPrincipal();
            if (user != null) {
                User cuser = userMapper.selectUserById(user.getUserId());
                if (cuser == null) {
                    response.sendRedirect("/login");
                }
            } else {
                response.sendRedirect("/login");
            }
        } else {
            response.sendRedirect("/login");
        }
        return joinPoint.proceed();
    }
}
