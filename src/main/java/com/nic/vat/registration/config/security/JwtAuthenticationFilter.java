package com.nic.vat.registration.config.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.authentication.BadCredentialsException;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtAuthEntryPoint jwtAuthEntryPoint;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String path = request.getRequestURI();
        if (path.equals("/auth/login") ||
                path.equals("/auth/forgot-password") ||
                path.equals("/auth/forgot-application") ||
                (path.equals("/registration/part-a") && request.getMethod().equals("POST")) ||
                (path.equals("/registration/part-c") && request.getMethod().equals("POST"))
        ) {
            filterChain.doFilter(request, response); // ✅ skip auth
            return;
        }
        String token = extractTokenFromRequest(request);
        logger.debug("Extracted Token: {}", token);

        try {
            if (token != null) {
                try {
                    if (jwtUtil.validateToken(token)) {
                        String username = jwtUtil.getUsername(token);
                        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                        UsernamePasswordAuthenticationToken auth =
                                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(auth);
                    }
                } catch (Exception e) {
                    logger.error("JWT validation failed", e);
                    // Only throw if token is present
                    jwtAuthEntryPoint.commence(request, response, new BadCredentialsException("Invalid JWT token"));

                    return;
                }
            }
            filterChain.doFilter(request, response);


        } catch (Exception e) {
            logger.error("Exception in JWT filter: {}", e.getMessage());
            SecurityContextHolder.clearContext();
            jwtAuthEntryPoint.commence(request, response,
                    new org.springframework.security.core.AuthenticationException("JWT Authentication failed", e) {});
        }
    }

    private String extractTokenFromRequest(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        logger.debug("Authorization Header: {}", authorizationHeader);

        if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }

        return null;
    }
}
