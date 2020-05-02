package com.umdvita.taxtracker.web.controller.user;

import com.umdvita.taxtracker.annotation.Loggable;
import com.umdvita.taxtracker.backend.persistence.domain.security.Role;
import com.umdvita.taxtracker.backend.service.security.EncryptionService;
import com.umdvita.taxtracker.backend.service.security.UserService;
import com.umdvita.taxtracker.constant.ControllerConstant;
import com.umdvita.taxtracker.constant.SignUpControllerConstant;
import com.umdvita.taxtracker.constant.UserConstant;
import com.umdvita.taxtracker.enums.RoleType;
import com.umdvita.taxtracker.shared.dto.UserDto;
import com.umdvita.taxtracker.shared.util.core.UserUtility;
import com.umdvita.taxtracker.shared.util.validation.InputValidationUtility;
import com.umdvita.taxtracker.web.model.request.UserRequestModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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

  /**
   * Creates a new User.
   *
   * @param model              model to transport objects to view
   * @param httpServletRequest the httpServletRequest
   * @param userRequestModel   the userRequestModel
   * @return the view name for confirmation.
   */
  @Loggable
  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping(SignUpControllerConstant.SIGN_UP_URL_MAPPING)
  public String signUp(
          @Valid @ModelAttribute final UserRequestModel userRequestModel,
          final Model model, HttpServletRequest httpServletRequest) {
    LOG.debug("In signUpController to create user with details {}", userRequestModel);

    UserDto userDto = UserUtility.getUserDto(userRequestModel);
    model.addAttribute(UserConstant.USER_MODEL_KEY, userRequestModel);
    if (isUsernameOrEmailADuplicate(userDto, model)) {
      return SignUpControllerConstant.SIGN_UP_VIEW_NAME;
    }
    // Returns false if there was an error.
    UserDto storedUserDto = assignRolesAndCreateUser(userDto);
    if (Objects.isNull(storedUserDto)) {
      LOG.error(UserConstant.ERROR_CREATING_USER + " {}.", userDto);
      model.addAttribute(ControllerConstant.ERROR, UserConstant.ERROR_CREATING_USER);
      return SignUpControllerConstant.SIGN_UP_VIEW_NAME;
    }
    // send verification email to userDto
    // TODO: 4/26/2020 Send email confirmation link
    if (!model.containsAttribute(ControllerConstant.ERROR)) {
      LOG.debug(SignUpControllerConstant.USER_CREATED_SUCCESS_MESSAGE, storedUserDto);
      model.addAttribute(SignUpControllerConstant.SIGN_UP_PENDING_KEY, true);
    }
    return ControllerConstant.LOGIN_VIEW_NAME;
  }

  /**
   * Assigns a role to the userDto then persist to database.
   *
   * @param userDto the new userDto
   * @param model   model
   * @return if userDto already exits
   */
  private boolean isUsernameOrEmailADuplicate(@ModelAttribute @Valid final UserDto userDto, final Model model) {
    // if userDto duplication has already been verified by javascript, skip this check
    if (userService.existsByUsername(userDto.getUsername())) {
      LOG.warn(SignUpControllerConstant.USERNAME_EXITS_MESSAGE);
      model.addAttribute(SignUpControllerConstant.DUPLICATED_USERNAME_KEY, true);
      return true;
    }
    if (userService.existsByEmailAndEnabled(userDto.getEmail())) {
      LOG.warn(SignUpControllerConstant.EMAIL_EXISTS_MESSAGE);
      model.addAttribute(SignUpControllerConstant.DUPLICATED_EMAIL_KEY, true);
      return true;
    }
    return false;
  }

  /**
   * Assigns a role to the userDto then persist to database.
   *
   * @param userDto the new userDto
   * @return status if an error occurred with the specified plan
   */
  private UserDto assignRolesAndCreateUser(final UserDto userDto) {
    try {
      Set<Role> roles = new HashSet<>();
      roles.add(new Role(RoleType.USER));
      UserDto persistedUser = userService.createUser(userDto, roles);
      LOG.debug("User successfully created with details {}", persistedUser);
      return persistedUser;
    } catch (Exception e) {
      LOG.error(SignUpControllerConstant.ERROR_ASSIGNING_ROLES_AND_CREATING_USER, userDto, e);
      return null;
    }
  }
}
