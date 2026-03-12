package com.ogs.wprotect.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.ogs.wprotect.domain.Wuser;
import com.ogs.wprotect.domain.service.WuserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class DeviceIdInterceptor implements HandlerInterceptor {
    @Autowired
    private WuserService wuserService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String deviceId = request.getHeader("X-Device-Id");
        String userIdStr = request.getHeader("X-User-Id");
        if (deviceId == null || userIdStr == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Missing deviceId or userId");
            return false;
        }
        int userId;
        try {
            userId = Integer.parseInt(userIdStr);
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid userId");
            return false;
        }
        Wuser user = wuserService.getById(userId).orElse(null);
        if (user == null || user.getDeviceId() == null || !user.getDeviceId().equals(deviceId)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid deviceId or userId");
            return false;
        }
        return true;
    }
}
