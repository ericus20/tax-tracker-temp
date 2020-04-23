package com.umdvita.taxtracker.web.controller;

import com.umdvita.taxtracker.constant.ControllerConstant;
import com.umdvita.taxtracker.web.model.request.UserRequestModel;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@Controller
public class SecurityController {

  /**
   * Expired session gets redirected here.
   *
   * @param request  the request
   * @param response the response
   * @return the expired view name
   */
  @GetMapping(path = ControllerConstant.EXPIRED_URL)
  public String expiredSession(HttpServletRequest request, HttpServletResponse response) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (Objects.nonNull(auth)) {
      new SecurityContextLogoutHandler().logout(request, response, auth);
    } else {
      return ControllerConstant.REDIRECT_HOME;
    }
    SecurityContextHolder.getContext().setAuthentication(null);
    request.getSession().invalidate();
    return ControllerConstant.EXPIRED_VIEW_NAME;
  }

  /**
   * The login mapping.
   *
   * @param model the model
   * @return the login page.
   */
  @GetMapping(path = ControllerConstant.LOGIN_URL_MAPPING)
  public String login(Model model) {
    model.addAttribute(ControllerConstant.USER_REQUEST_MODEL_KEY_NAME, new UserRequestModel());
    return ControllerConstant.LOGIN_VIEW_NAME;
  }

}
