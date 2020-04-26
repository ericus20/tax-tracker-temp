package com.umdvita.taxtracker.web.controller.user;

import com.umdvita.taxtracker.backend.service.security.EncryptionService;
import com.umdvita.taxtracker.backend.service.security.UserService;
import com.umdvita.taxtracker.constant.ControllerConstant;
import com.umdvita.taxtracker.constant.UserConstant;
import com.umdvita.taxtracker.web.model.request.UserRequestModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Handles all request from browser relating to user's sign-up journey.
 *
 * @author Eric Opoku
 * @version 1.0
 * @since 1.0
 */
@Slf4j
@Controller
public class SignUpController {

  private final UserService userService;
  private final EncryptionService encryptionService;

  /**
   * Controller for dependency injection.
   *
   * @param userService       the user service
   * @param encryptionService the text encryption service
   */
  @Autowired
  public SignUpController(
          UserService userService,
          EncryptionService encryptionService) {
    this.userService = userService;
    this.encryptionService = encryptionService;
  }

  /**
   * Redirects user to the sign-up form to complete registration.
   *
   * @param model the model to transfer objects to view
   * @return the view of the user registration page
   */
  @GetMapping(ControllerConstant.SIGN_UP_URL_MAPPING)
  public String signUp(final Model model) {
    model.addAttribute(UserConstant.USER_MODEL_KEY, new UserRequestModel());
    return ControllerConstant.SIGN_UP_VIEW_NAME;
  }
}
