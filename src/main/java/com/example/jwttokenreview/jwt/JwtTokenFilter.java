package com.example.jwttokenreview.jwt;

import com.example.jwttokenreview.domain.entity.User;
import com.example.jwttokenreview.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter {

    private final UserService userService;
    private final String secretKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Header에서 Token 꺼내기
        final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        log.info("authorizationHeader : {}", authorizationHeader);

        if(authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            log.info("Header가 null이거나 잘못되었습니다.");
            filterChain.doFilter(request, response);
            return;
        }

        String token;
        try {
            token = authorizationHeader.split(" ")[1];
            log.info("token : {}", token);
        } catch (Exception e) {
            log.info("token 추출에 실패했습니다.");
            filterChain.doFilter(request, response);
            return;
        }

        // 토큰 만료 check
        if(JwtTokenUtil.isExpired(token, secretKey)) {
            filterChain.doFilter(request, response);
            return;
        }

        // Token에서 username을 추출해 User 찾아오기
        String username = JwtTokenUtil.getUsername(token, secretKey);
        User loginUser = userService.getUserByUsername(username);
        log.info("loginUser : {}", loginUser);

        // 권한 부여
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginUser.getUsername(), null, List.of(new SimpleGrantedAuthority(loginUser.getRole().name())));
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);  // 권한 부여
        filterChain.doFilter(request, response);
    }
}
