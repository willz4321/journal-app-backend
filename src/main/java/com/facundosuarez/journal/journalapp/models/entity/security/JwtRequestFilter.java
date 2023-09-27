package com.facundosuarez.journal.journalapp.models.entity.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private jwt jwtUtil;

    public JwtRequestFilter( jwt jwtUtil) {
       
        this.jwtUtil = jwtUtil;
    }
    

    @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                FilterChain filterChain) throws ServletException, IOException {

            final String authorizationHeader = request.getHeader("x-token");
        
            
            System.out.println("***************url" + request.getRequestURI());
            System.out.println("Valor de content-type: " +  request.getHeader("content-type"));
            System.out.println("Valor de token: " +  request.getHeader("x-token"));
        
            String username = null;
            String jwt = null;

            if (authorizationHeader != null && !authorizationHeader.isEmpty()) {
                jwt = authorizationHeader;
                username = jwtUtil.extractUsername(jwt);
            }  
          
               if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails;
            
                try {
                    userDetails = userDetailsService.loadUserByUsername(username);
                } catch (UsernameNotFoundException e) {
                    
                    userDetails = null;
                }
                 System.out.println("rol del usuario:   " + userDetails.getUsername());
                  System.out.println("rol del usuario:   " + userDetails.getPassword());
                System.out.println("rol del usuario:   " + userDetails.getAuthorities());

                if (jwtUtil.validateToken(jwt, username) && userDetails != null) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken
                            .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
            filterChain.doFilter(request, response);
        }

}
