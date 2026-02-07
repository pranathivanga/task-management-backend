package com.pranathi.taskmanager.Jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import com.pranathi.taskmanager.entity.User;
import com.pranathi.taskmanager.service.UserService;


public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserService userService;


    public JwtAuthenticationFilter(JwtService jwtService,UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterchain) throws IOException, ServletException {
String authHeader=request.getHeader("Authorization");
if(authHeader==null||  !authHeader.startsWith("Bearer ")){
    filterchain.doFilter(request,response);
    return;
}
String token= authHeader.substring(7);

        String email = jwtService.extractEmail(token);

// Load full user from DB
        User user = userService.loadUserByEmail(email);

        if (user == null) {
            filterchain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(user, null, null);

        SecurityContextHolder.getContext().setAuthentication(authentication);

filterchain.doFilter(request,response);
    }
}
