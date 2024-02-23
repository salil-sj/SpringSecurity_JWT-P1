package com.example.security.jwt.Filters;

import com.example.security.jwt.service.impl.UserServiceImpl;
import com.example.security.jwt.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private UserServiceImpl userService;

    //
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        // 1. Read token from Header:
        String token = request.getHeader("Authorization");

        if (token != null) {
            //2. do validation:
            String username = jwtUtils.getUsername(token);

            // username should not be empty
            if (username != null
                    && SecurityContextHolder.getContext().getAuthentication() == null
            ) {
                // Obj of spring security:
                UserDetails userDetails = userService.loadUserByUsername(username);

                // validate token:
                boolean isValid = jwtUtils.validateToken(token, username);
                if (isValid) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            username, userDetails.getPassword(), userDetails.getAuthorities()
                    );

                    // Final authentication token objec stored in SecurityContext

                    SecurityContextHolder.getContext().setAuthentication(authToken);


                }
            }
        }
        filterChain.doFilter(request, response);

    }
}
