package com.epam.newsportal.security;

import com.epam.newsportal.persistence.entity.User;
import com.epam.newsportal.persistence.enumeration.Role;
import com.epam.newsportal.web.SecurityController;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.Arrays;

@Aspect
@Component
@Transactional
public class SecurityAspect {

    @Autowired
    private HttpSession httpSession;
    @Autowired
    private SecurityController securityController;

    @Around("execution(* com.epam.newsportal.web.*.*(..))")
    private Object beforeInit(ProceedingJoinPoint proceedingJoinPoint) {
        Class type = proceedingJoinPoint.getSignature().getDeclaringType();
        Method method = ((MethodSignature) proceedingJoinPoint.getSignature()).getMethod();
        if (type.isAnnotationPresent(Secured.class)) {
            if (method.isAnnotationPresent(Secured.class)) {
                return process(proceedingJoinPoint, method.getAnnotation(Secured.class));
            } else {
                return process(proceedingJoinPoint, (Secured) type.getAnnotation(Secured.class));
            }
        }
        try {
            return proceedingJoinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return securityController.error();
        }
    }

    private Object process(ProceedingJoinPoint proceedingJoinPoint, Secured secured) {
        User user = (User) httpSession.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setRole(Role.GUEST);
        }
        if (Arrays.asList(secured.value()).contains(user.getRole())) {
            try {
                return proceedingJoinPoint.proceed();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
                return securityController.error();
            }
        } else {
            return securityController.accessDenied();
        }
    }
}
