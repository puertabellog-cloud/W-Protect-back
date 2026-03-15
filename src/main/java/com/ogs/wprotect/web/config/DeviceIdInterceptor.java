package com.ogs.wprotect.web.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.ogs.wprotect.domain.Wuser;
import com.ogs.wprotect.domain.service.WuserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.UUID;

@Component
public class DeviceIdInterceptor implements HandlerInterceptor {
    private static final Logger log = LoggerFactory.getLogger(DeviceIdInterceptor.class);
    private static final String REQUEST_ID_KEY = "requestId";
    private static final String START_TIME_ATTR = "requestStartTimeMs";

    @Autowired
    private WuserService wuserService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestId = request.getHeader("X-Request-Id");
        if (requestId == null || requestId.isBlank()) {
            requestId = UUID.randomUUID().toString();
        }
        MDC.put(REQUEST_ID_KEY, requestId);
        request.setAttribute(START_TIME_ATTR, System.currentTimeMillis());

        String method = request.getMethod();
        String path = request.getRequestURI();

        if ("OPTIONS".equalsIgnoreCase(method)) {
            log.debug("Bypassing device validation for preflight request method={} path={}", method, path);
            return true;
        }

        String deviceId = request.getHeader("X-Device-Id");
        String userIdStr = request.getHeader("X-User-Id");

        log.info(
                "Incoming protected request method={} path={} xUserId={} xDeviceIdPresent={} origin={} userAgent={}",
                method,
                path,
                userIdStr,
                deviceId != null && !deviceId.isBlank(),
                request.getHeader("Origin"),
                request.getHeader("User-Agent")
        );

        if (deviceId == null || userIdStr == null) {
            log.warn("Rejecting request: missing required headers xUserIdPresent={} xDeviceIdPresent={}",
                    userIdStr != null, deviceId != null);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Missing deviceId or userId");
            return false;
        }

        int userId;
        try {
            userId = Integer.parseInt(userIdStr);
        } catch (NumberFormatException e) {
            log.warn("Rejecting request: invalid X-User-Id value={}", userIdStr);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid userId");
            return false;
        }

        Wuser user = wuserService.getById(userId).orElse(null);
        boolean valid = user != null && user.getDeviceId() != null && user.getDeviceId().equals(deviceId);
        if (!valid) {
            log.warn("Rejecting request: device/user mismatch userExists={} userDeviceIdPresent={}",
                    user != null,
                    user != null && user.getDeviceId() != null);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid deviceId or userId");
            return false;
        }

        log.debug("Device validation passed for userId={}", userId);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        long tookMs = -1;
        Object startAttr = request.getAttribute(START_TIME_ATTR);
        if (startAttr instanceof Long) {
            tookMs = System.currentTimeMillis() - (Long) startAttr;
        }

        if (ex != null) {
            log.error("Request completed with exception method={} path={} status={} tookMs={}",
                    request.getMethod(),
                    request.getRequestURI(),
                    response.getStatus(),
                    tookMs,
                    ex);
        } else {
            log.info("Request completed method={} path={} status={} tookMs={}",
                    request.getMethod(),
                    request.getRequestURI(),
                    response.getStatus(),
                    tookMs);
        }

        MDC.remove(REQUEST_ID_KEY);
    }
}