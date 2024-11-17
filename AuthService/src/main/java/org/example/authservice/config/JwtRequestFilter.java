package org.example.authservice.config;
import org.example.authservice.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
//This annotation indicates that this class is a Spring component, allowing it to be
//automatically detected and managed by Spring's application context.
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    // Automatically inject the JwtUtil class, which provides utility methods for
    // working with JWTs, such as extracting information and validating tokens.
    @Autowired
    private JwtUtil jwtUtil;

    // Automatically inject the UserDetailsService, which is used to retrieve user
    // details from the database based on the username.
    @Autowired
    private UserDetailsService userDetailsService;

    // The doFilterInternal method is overridden to perform filtering logic on each
    // incoming HTTP request.
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        // Retrieve the "Authorization" header from the incoming request.
        final String authorizationHeader = request.getHeader("Authorization");

        // Initialize variables to hold the username and JWT token.
        String username = null;
        String jwt = null;

        // Check if the Authorization header is present and starts with "Bearer ".
        // This is the standard format for passing JWTs.
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            // Extract the JWT token from the header (removing the "Bearer " prefix).
            jwt = authorizationHeader.substring(7);
            // Use the JwtUtil class to extract the username from the JWT.
            username = jwtUtil.extractUsername(jwt);
        }

        // If a username is extracted and there is no existing authentication in the context,
        // proceed to validate the token and set the authentication.
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Load user details from the database using the username.
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            // Validate the token to ensure it is still valid for the user.
            if (jwtUtil.validateToken(jwt, userDetails.getUsername())) {
                // If the token is valid, create an authentication object.
                var authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                // Set additional details in the authentication object (e.g., IP address).
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // Set the authentication object in the SecurityContext, allowing
                // Spring Security to recognize the user as authenticated.
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        // Continue the filter chain, allowing the request to proceed to the next filter
        // or the requested resource.
        chain.doFilter(request, response);
    }
}
