package com.umdvita.taxtracker.web.controller;

import com.umdvita.taxtracker.constant.ControllerConstant;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * The root controller for handling all root mappings.
 *
 * @author Eric Opoku
 * @version 1.0
 * @since 1.0
 */
@Controller
public class IndexController {

  /**
   * The method is the root mapping.
   *
   * @return the index page.
   */
  @GetMapping(path = ControllerConstant.INDEX_URL_MAPPING)
  public String root() {
    return ControllerConstant.INDEX_VIEW_NAME;
  }

  /**
   * The method is the features controller mapping.
   *
   * @return the features page.
   */
  @GetMapping(path = ControllerConstant.FEATURES_URL_MAPPING)
  public String features() {
    return ControllerConstant.FEATURES_VIEW_NAME;
  }

  /**
   * The method is the services controller mapping.
   *
   * @return what to bring page.
   */
  @GetMapping(path = ControllerConstant.WHAT_TO_BRING_URL_MAPPING)
  public String whatToBring() {
    return ControllerConstant.WHAT_TO_BRING_VIEW_NAME;
  }

  /**
   * The method is the more on taxes controller mapping.
   *
   * @return more on taxes page.
   */
  @GetMapping(path = ControllerConstant.MORE_ON_TAXES_URL_MAPPING)
  public String moreOnTaxes() {
    return ControllerConstant.MORE_ON_TAXES_VIEW_NAME;
  }
}
