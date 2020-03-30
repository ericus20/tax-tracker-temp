package com.umdvita.taxtracker.shared;

import com.umdvita.taxtracker.backend.service.i18n.I18NService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Locale;

import static com.umdvita.taxtracker.constant.signup.PasswordControllerConstant.CHANGE_PATH_KEY;
import static com.umdvita.taxtracker.constant.signup.PasswordControllerConstant.NOT_AUTHORIZED;
import static com.umdvita.taxtracker.constant.signup.PasswordControllerConstant.TOKEN_EXPIRED;
import static com.umdvita.taxtracker.constant.signup.PasswordControllerConstant.TOKEN_INVALID;

@Component
public class ClientConfig {

  private final I18NService i18NService;

  @Autowired
  public ClientConfig(I18NService i18NService) {
    this.i18NService = i18NService;
  }

  public String getChangePwdPath(String key) {
    return getStringProperty(key, Locale.getDefault());
  }

  public String getChangePwdPath() {
    return getChangePwdPath(CHANGE_PATH_KEY);
  }

  public String getNotAuthorizedMessage() {
    return getStringProperty(NOT_AUTHORIZED, Locale.getDefault());
  }

  public String getTokenExpiredMessage() {
    return getStringProperty(TOKEN_EXPIRED, Locale.getDefault());
  }

  public String getTokenInvalidMessage() {
    return getStringProperty(TOKEN_INVALID, Locale.getDefault());
  }

  public String getStringProperty(String key) {
    return getStringProperty(key, Locale.getDefault());
  }

  public String getStringProperty(String key, Locale locale) {
    return i18NService.getMessage(key, new Object[]{}, locale);
  }

}
