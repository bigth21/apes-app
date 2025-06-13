package me.bigth.apes.infrastructure.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.csrf.MissingCsrfTokenException;

import java.io.IOException;

@Slf4j
public class CsrfAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException ex) throws IOException, ServletException {
        log.warn("Access denied due to CSRF exception: {}", ex.getClass().getSimpleName());
        if (ex instanceof MissingCsrfTokenException) {
            response.sendRedirect("/sign-in?expired");
        } else {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        }
    }
}
