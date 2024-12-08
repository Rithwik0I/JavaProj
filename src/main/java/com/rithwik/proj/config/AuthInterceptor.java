package com.rithwik.proj.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, 
                              HttpServletResponse response, 
                              Object handler) throws Exception {
        // Allow access to login, register, and static resources
        String[] permitAll = {"/login", "/register-student", "/homepage", "/css/", "/js/"};
        
        for (String path : permitAll) {
            if (request.getRequestURI().startsWith(path)) {
                return true;
            }
        }

        // Check if user is logged in
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loggedInUser") == null) {
            response.sendRedirect("/login");
            return false;
        }
        String requestURI = request.getRequestURI();
        String userRole = (String) session.getAttribute("userRole");

        // Strict role-based access control
        if (requestURI.contains("/student") && (!"student".equals(userRole) && !"admin".equals(userRole))) {
            response.sendRedirect("/error");
            return false;
        }

        if (requestURI.contains("/teacher") && (!"teacher".equals(userRole) && !"admin".equals(userRole)) ) {
            response.sendRedirect("/error");
            return false;
        }

        if (requestURI.contains("/admin") && !"admin".equals(userRole)) {
            response.sendRedirect("/error");
            return false;
        }
        return true;
    }
}