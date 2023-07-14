package com.example.securitytokenpractice.security.filter;

import com.example.securitytokenpractice.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
@RequiredArgsConstructor
public class TokenCheckFilter extends OncePerRequestFilter {

    // 현재 사용자가 로그인한 사용자안자 체크하는 로그인 체크용 필터와 유사하게
    // JWT 토큰을 검사하는 역할을 위해서 사용한다.

    private final JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();

        if(!path.startsWith("/api/")){
            filterChain.doFilter(request,response);
            return;
        }

        log.info("Token Check Filter........................");
        log.info("JWTUtil: " + jwtUtil);

        filterChain.doFilter(request, response);
    }
}
