package com.hib.hibenatemysql.config;

import com.hib.hibenatemysql.service_impl.service.JwtService;
import com.hib.hibenatemysql.service_impl.service.UsersService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.thymeleaf.util.StringUtils;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class JwtAuthFilter extends OncePerRequestFilter {


    private final UsersService usersService;
    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        String token;
        String userId;
        if (StringUtils.isEmpty(authHeader) && StringUtils.startsWith(authHeader, "Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        token = authHeader.substring(7);
        userId = jwtService.extractUserId(token);

        if (StringUtils.isEmpty(userId) && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = usersService.userDetailsService().loadUserByUsername(userId);
            if (jwtService.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }

}
