package com.xm.recommendation.middleware;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * An interceptor that limits the number of requests that can be made.
 */
@Component
@RequiredArgsConstructor
public class RateLimiterInterceptor implements HandlerInterceptor {

  private final RateLimiter rateLimiter;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    if (!rateLimiter.canAcquire()) {
      response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
      response.getWriter().write("Too many requests");
      return false;
    }

    return true;
  }
}
