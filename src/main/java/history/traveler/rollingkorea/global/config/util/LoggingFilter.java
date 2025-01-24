package history.traveler.rollingkorea.global.config.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@Component
public class LoggingFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(LoggingFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // 요청 로그
        logger.info("Request Method: {}, Request URI: {}", httpRequest.getMethod(), httpRequest.getRequestURI());

        chain.doFilter(request, response);

        // 응답 로그
        logger.info("Response Status: {}", httpResponse.getStatus());
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 초기화 코드
        logger.info("LoggingFilter initialized");
    }

    @Override
    public void destroy() {
        // 정리 코드
        logger.info("LoggingFilter destroyed");
    }
}
