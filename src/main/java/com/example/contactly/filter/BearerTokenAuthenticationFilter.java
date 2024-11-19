/*
package com.example.contactly.filter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BearerTokenAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public BearerTokenAuthenticationFilter(AuthenticationManager authenticationManager) {
        super("/contacts/**");  // Only intercept requests to /contacts/** (or any other endpoints you want)
        setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException {

        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            throw new AuthenticationException("Missing or invalid Authorization header") {};
        }

        // Extract the session ID from the "Authorization" header
        String sessionId = header.substring(7);
        HttpSession session = request.getSession(false);

        // Validate session
        if (session == null || !session.getId().equals(sessionId)) {
            throw new AuthenticationException("Invalid or expired session ID") {};
        }

        // Retrieve username from session attributes
        String username = (String) session.getAttribute("username");
        if (username == null) {
            throw new AuthenticationException("Invalid or expired session ID") {};
        }

        SecurityContext securityContext = (SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
        if (securityContext == null) {
            throw new AuthenticationException("Session has no authentication context") {};
        }

        // Get authentication from the SecurityContext
        Authentication authentication = securityContext.getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AuthenticationException("Session is not authenticated") {};
        }

        return authentication;

    }



    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException, ServletException {

        // Continue filter chain after successful authentication
        chain.doFilter(request, response);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException {
        response.setContentType("application/json");
        response.getWriter().write("{\"error\": \"Unauthorized\"}");

        if(failed.getMessage() == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
        else{

            String errorMessage = failed.getMessage() != null ? failed.getMessage() : "Unauthorized";
            response.getWriter().write("{\"error\": \"" + errorMessage + "\"}");

        }
    }
}
*/
