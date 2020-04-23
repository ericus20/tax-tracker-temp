package com.umdvita.taxtracker.web.controller;

import com.umdvita.taxtracker.constant.ControllerConstant;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * The CopyController class holds request to all privacy and terms of service.
 *
 * @author Eric Opoku
 * @version 1.0
 * @since 1.0
 */
@Controller
@RequestMapping(path = ControllerConstant.COPY_ROOT_URL)
public class CopyController {

  /**
   * Get the privacy policy for the application.
   *
   * @return the privacy policy view name
   */
  @GetMapping(path = ControllerConstant.PRIVACY_POLICY_URL)
  public String privacyPolicy() {
    return ControllerConstant.PRIVACY_VIEW_NAME;
  }

  /**
   * Get the terms of service for the application.
   *
   * @return the terms of service view name
   */
  @GetMapping(path = ControllerConstant.TERMS_AND_CONDITIONS_URL)
  public String getTermsOfService() {
    return ControllerConstant.TERMS_AND_CONDITION_VIEW_NAME;
  }

  /**
   * Get the about page for the application.
   *
   * @return the about page view name
   */
  @GetMapping(path = ControllerConstant.ABOUT_US_URL)
  public String aboutUs() {
    return ControllerConstant.ABOUT_US_VIEW_NAME;
  }
}
