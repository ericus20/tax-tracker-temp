package com.umdvita.taxtracker.config.security;

import com.umdvita.taxtracker.constant.ControllerConstant;
import org.springframework.http.ResponseCookie;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.util.WebUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * The TaxTrackerSecurityFilter class provides a fine grained filter for the application.
 *
 * @author Eric Opoku
 * @version 1.0
 * @since 1.0
 */
public class TaxTrackerSecurityFilter extends GenericFilterBean {

  /**
   * The <code>doFilter</code> method of the Filter is called by the container
   * each time a request/response pair is passed through the chain due to a
   * client request for a resource at the end of the chain. The FilterChain
   * passed in to this method allows the Filter to pass on the request and
   * response to the next entity in the chain.
   *
   * @param request  The request to process
   * @param response The response associated with the request
   * @param chain    Provides access to the next filter in the chain for this
   *                 filter to pass the request and response to for further
   *                 processing
   * @throws IOException      if an I/O error occurs during this filter's
   *                          processing of the request
   * @throws ServletException if the processing fails for any other reason
   */
  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
          throws IOException, ServletException {

    HttpServletResponse resp = (HttpServletResponse) response;
    HttpServletRequest req = (HttpServletRequest) request;

    Cookie rememberMe = WebUtils.getCookie(req, ControllerConstant.REMEMBER_ME);
    if (Objects.nonNull(rememberMe)) {
      resp.addHeader(ControllerConstant.SET_COOKIE, getSecuredCookie(rememberMe, request));
    }

    Cookie jsessionId = WebUtils.getCookie(req, ControllerConstant.JSESSIONID);
    if (Objects.nonNull(jsessionId)) {
      resp.addHeader(ControllerConstant.SET_COOKIE, getSecuredCookie(jsessionId, request));
    }
    chain.doFilter(req, resp);
  }

  private String getSecuredCookie(Cookie cookie, ServletRequest request) {
    return ResponseCookie.from(cookie.getName(), cookie.getValue())
            .maxAge(cookie.getMaxAge())
            .domain(cookie.getDomain())
            .sameSite("strict")
            .httpOnly(true)
            .secure(request.isSecure())
            .path("/")
            .build()
            .toString();
  }
}
