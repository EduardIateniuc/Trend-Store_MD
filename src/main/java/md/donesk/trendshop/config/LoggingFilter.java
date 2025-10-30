package md.donesk.trendshop.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class LoggingFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;

        long startTime = System.currentTimeMillis(); // Start time

        log.info("Incoming Request: {} {} from {}", req.getMethod(), req.getRequestURI(), req.getRemoteAddr());

        filterChain.doFilter(servletRequest, servletResponse); // Process the request

        long executionTime = System.currentTimeMillis() - startTime; // End time and calculate execution time

        log.info("Outgoing Response: Status {}. Execution time: {} ms", res.getStatus(), executionTime);
    }
}
