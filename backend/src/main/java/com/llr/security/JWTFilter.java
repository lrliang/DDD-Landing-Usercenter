package com.llr.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.llr.utils.JwtUtil;
import com.netflix.zuul.context.RequestContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class JWTFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenHeader = request.getHeader("token");
        if (checkToken(tokenHeader)) {
            try {
                Map<String, Object> body = JwtUtil.parseTokenToMap(tokenHeader);
                Map user = convertUser(body);
                List<GrantedAuthority> authorities = grantedAuthorities(user);
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user, null, authorities);

                RequestContext.getCurrentContext().addZuulRequestHeader("id", user.get("id").toString());
                SecurityContextHolder.getContext().setAuthentication(token);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        RequestContext.getCurrentContext().addZuulRequestHeader("token", tokenHeader);

        filterChain.doFilter(request, response);
    }

    private boolean checkToken(String tokenHeader) {
        return Objects.nonNull(tokenHeader) && !tokenHeader.isEmpty() && !"null".equals(tokenHeader);
    }

    private List<GrantedAuthority> grantedAuthorities(Map user) {
        return new ArrayList<>();
    }


    private Map convertUser(Map<String, Object> body) throws IOException {
        return new ObjectMapper().readValue(body.get("sub").toString(), HashMap.class);
    }

}
