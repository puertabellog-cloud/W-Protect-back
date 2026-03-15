package com.ogs.wprotect.web.security;

import com.ogs.wprotect.domain.Wuser;
import com.ogs.wprotect.domain.service.WuserService;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class RequireAdminAspect {
    private static final Logger log = LoggerFactory.getLogger(RequireAdminAspect.class);

    @Autowired
    private WuserService wuserService;

    @Around("@annotation(com.ogs.wprotect.web.security.RequireAdmin)")
    public Object checkAdmin(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attrs == null) {
            log.warn("Rejecting admin check: no request attributes available");
            return new ResponseEntity<>("Request context not available", HttpStatus.FORBIDDEN);
        }

        HttpServletRequest request = attrs.getRequest();
        String method = request.getMethod();
        String path = request.getRequestURI();
        String userIdStr = request.getHeader("X-User-Id");

        log.info("RequireAdmin check method={} path={} xUserId={}", method, path, userIdStr);

        if (userIdStr == null) {
            log.warn("Rejecting admin check: missing X-User-Id header");
            return new ResponseEntity<>("Missing userId header", HttpStatus.FORBIDDEN);
        }

        int userId;
        try {
            userId = Integer.parseInt(userIdStr);
        } catch (NumberFormatException e) {
            log.warn("Rejecting admin check: invalid X-User-Id value={}", userIdStr);
            return new ResponseEntity<>("Invalid userId", HttpStatus.FORBIDDEN);
        }

        Wuser user = wuserService.getById(userId).orElse(null);
        if (user == null) {
            log.warn("Rejecting admin check: user not found userId={}", userId);
            return new ResponseEntity<>("Access denied: not ADMIN", HttpStatus.FORBIDDEN);
        }

        if (!"ADMIN".equalsIgnoreCase(user.getProfile())) {
            log.warn("Rejecting admin check: user profile is not ADMIN userId={} profile={}", userId, user.getProfile());
            return new ResponseEntity<>("Access denied: not ADMIN", HttpStatus.FORBIDDEN);
        }

        log.debug("RequireAdmin passed userId={} profile={}", userId, user.getProfile());
        return joinPoint.proceed();
    }
}