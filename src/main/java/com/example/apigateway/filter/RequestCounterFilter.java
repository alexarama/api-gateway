package com.example.apigateway.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class RequestCounterFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(RequestCounterFilter.class);
    private final AtomicLong requestCount = new AtomicLong(0);

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        long count = requestCount.incrementAndGet();
        log.info("Request #{}: {} {}", count, request.getMethod(), request.getRequestURI());
        response.addHeader("X-Request-Count", String.valueOf(count));
        response.addHeader("X-Gateway-Source", "api-gateway");
        filterChain.doFilter(request, response);
    }
}