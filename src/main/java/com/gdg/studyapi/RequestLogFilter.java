package com.gdg.studyapi;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * 들어온 요청을 한 줄씩 로그로 남긴다.
 *
 *   [REQ] GET /studies?status=RECRUITING -> 200 (12ms)
 *
 * Spring은 기본으로 요청 로그를 남기지 않는다. 이 필터 덕분에
 * 짝이 내 서버를 부르면 Render Logs에 실시간으로 한 줄이 뜬다 (STEP 6).
 *
 * /actuator/** 는 남기지 않는다 — Render가 헬스체크로 몇 초마다 부르기 때문에
 * 남기면 로그가 헬스체크로 도배된다.
 */
@Component
public class RequestLogFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(RequestLogFilter.class);

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return request.getRequestURI().startsWith("/actuator");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        long start = System.currentTimeMillis();
        try {
            filterChain.doFilter(request, response);
        }
        finally {
            String query = request.getQueryString();
            String path = request.getRequestURI() + (query != null ? "?" + query : "");
            log.info("[REQ] {} {} -> {} ({}ms)", request.getMethod(), path,
                    response.getStatus(), System.currentTimeMillis() - start);
        }
    }
}
