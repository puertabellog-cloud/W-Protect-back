package com.ogs.wprotect.web.security;

import com.ogs.wprotect.domain.Wuser;
import com.ogs.wprotect.domain.service.WuserService;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class RequireAdminAspect {
    @Autowired
    private WuserService wuserService;

    @Around("@annotation(com.ogs.wprotect.web.security.RequireAdmin)")
    public Object checkAdmin(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attrs.getRequest();
        String userIdStr = request.getHeader("X-User-Id");
        if (userIdStr == null) {
            return new ResponseEntity<>("Missing userId header", HttpStatus.FORBIDDEN);
        }
        int userId;
        try {
            userId = Integer.parseInt(userIdStr);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>("Invalid userId", HttpStatus.FORBIDDEN);
        }
        Wuser user = wuserService.getById(userId).orElse(null);
        if (user == null || !"ADMIN".equalsIgnoreCase(user.getProfile())) {
            return new ResponseEntity<>("Access denied: not ADMIN", HttpStatus.FORBIDDEN);
        }
        return joinPoint.proceed();
    }
}
