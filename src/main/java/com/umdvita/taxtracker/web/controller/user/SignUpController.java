package com.umdvita.taxtracker.web.controller.user;

import com.umdvita.taxtracker.annotation.Loggable;
import com.umdvita.taxtracker.backend.persistence.domain.security.Role;
import com.umdvita.taxtracker.backend.service.mail.EmailService;
import com.umdvita.taxtracker.backend.service.security.EncryptionService;
import com.umdvita.taxtracker.backend.service.security.PasswordTokenService;
import com.umdvita.taxtracker.backend.service.security.UserService;
import com.umdvita.taxtracker.constant.ControllerConstant;
import com.umdvita.taxtracker.constant.ProfileControllerConstant;
import com.umdvita.taxtracker.constant.SignUpControllerConstant;
import com.umdvita.taxtracker.constant.UserConstant;
import com.umdvita.taxtracker.enums.RoleType;
import com.umdvita.taxtracker.exception.UnAuthorizedActionException;
import com.umdvita.taxtracker.exception.user.InvalidTokenException;
import com.umdvita.taxtracker.shared.dto.PasswordTokenDto;
import com.umdvita.taxtracker.shared.dto.UserDto;
import com.umdvita.taxtracker.shared.dto.UserUpdateDto;
import com.umdvita.taxtracker.shared.util.core.UserUtility;
import com.umdvita.taxtracker.shared.util.core.WebUtility;
import com.umdvita.taxtracker.web.model.feedback.EmailFormat;
import com.umdvita.taxtracker.web.model.request.UserRequestModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import static com.umdvita.taxtracker.constant.ControllerConstant.SIGN_UP_VERIFY_URL_MAPPING;
import static com.umdvita.taxtracker.shared.util.core.WebUtility.createPasswordUrl;

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
  private final EmailService emailService;
  private final EncryptionService encryptionService;
  private final PasswordTokenService passwordTokenService;

  /**
   * Controller for dependency injection.
   *
   * @param userService          the user service
   * @param emailService         the email service
   * @param encryptionService    the text encryption service
   * @param passwordTokenService the password token service
   */
  @Autowired
  public SignUpController(
          UserService userService,
          EmailService emailService,
          EncryptionService encryptionService,
          PasswordTokenService passwordTokenService) {
    this.userService = userService;
    this.emailService = emailService;
    this.encryptionService = encryptionService;
    this.passwordTokenService = passwordTokenService;
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

    UserDto userDto = UserUtility.getUserDto(userRequestModel);
    model.addAttribute(UserConstant.USER_MODEL_KEY, userRequestModel);
    if (!isUsernameOrEmailADuplicate(userDto, model)) {
      UserDto storedUserDto = assignRolesAndCreateUser(userDto);
      if (Objects.nonNull(storedUserDto)) {
        // send verification email to userDto
        sendVerificationLink(storedUserDto, model, httpServletRequest);

        if (!model.containsAttribute(ControllerConstant.ERROR)) {
          LOG.debug(SignUpControllerConstant.USER_CREATED_SUCCESS_MESSAGE, storedUserDto);
          model.addAttribute(SignUpControllerConstant.SIGN_UP_PENDING_KEY, true);
          return ControllerConstant.LOGIN_VIEW_NAME;
        }
      }
    }
    model.addAttribute(ControllerConstant.ERROR, UserConstant.ERROR_CREATING_USER);
    return SignUpControllerConstant.SIGN_UP_VIEW_NAME;
  }

  /**
   * Continuation of sign up is handled by this mapping.
   *
   * @param token              the token.
   * @param model              the model.
   * @param userId             the userId
   * @param httpServletRequest the httpServletRequest
   * @param redirectAttributes the redirectAttributes
   * @return the view mapping for login.
   * @throws InvalidTokenException if the token passed is not valid
   */
  @Loggable
  @GetMapping(SIGN_UP_VERIFY_URL_MAPPING)
  public String completeSignUp(
          @RequestParam final String token, @RequestParam final String userId,
          final Model model, RedirectAttributes redirectAttributes, HttpServletRequest httpServletRequest) {
    try {
      UserDto userDto = updateUserStatus(token, userId, model);
      if (!Objects.isNull(userDto) && !model.containsAttribute(SignUpControllerConstant.SIGN_UP_ERROR)) {
        // automatically authenticate the userDto since there will be a redirection to profile page
        UserUtility.authenticateUser(userService, userDto.getEmail());

        model.addAttribute(SignUpControllerConstant.SIGN_UP_SUCCESS_KEY, true);
        model.addAttribute(ControllerConstant.USER_REQUEST_MODEL_KEY_NAME, new UserRequestModel());
        redirectAttributes.addFlashAttribute(ControllerConstant.NEW_PROFILE, true);

        // send an account confirmation to the userUpdateDto.
        sendConfirmationEmail(userDto, httpServletRequest);
        return ControllerConstant.REDIRECT_TO_PROFILE;
      }
    } catch (Exception e) {
      LOG.error(UserConstant.ERROR_CREATING_USER, e);
      model.addAttribute(ControllerConstant.ERROR, UserConstant.ERROR_CREATING_USER);
    }
    model.addAttribute(UserConstant.USER_MODEL_KEY, new UserRequestModel());
    return SignUpControllerConstant.SIGN_UP_VIEW_NAME;
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
      LOG.debug(UserConstant.USER_CREATED_MESSAGE_LOG, persistedUser);
      return persistedUser;
    } catch (Exception e) {
      LOG.error(SignUpControllerConstant.ERROR_ASSIGNING_ROLES_AND_CREATING_USER, userDto, e);
      return null;
    }
  }

  /**
   * Handles the submission of generated link to the userDto to verify account.
   *
   * @param userDto the userDto
   * @param model   the model
   */
  private void sendVerificationLink(final UserDto userDto, final Model model, HttpServletRequest httpServletRequest) {
    try {
      String token = UUID.randomUUID().toString();
      passwordTokenService.deleteByUser(userDto);
      passwordTokenService.savePasswordTokenWithUser(token, userDto.getEmail());
      LOG.debug(SignUpControllerConstant.USER_TOKEN_CREATED_SUCCESSFULLY, token);

      String encryptedToken = encryptionService.encryptRequestUri(token);
      String encryptUserId = encryptionService.encryptRequestUri(userDto.getToken());
      String link = createPasswordUrl(encryptUserId, encryptedToken, SIGN_UP_VERIFY_URL_MAPPING, httpServletRequest);
      LOG.debug("Account verification link created as {}", link);

      // get the links used in the email
      Map<String, String> links = WebUtility.getDefaultUrls(httpServletRequest);
      EmailFormat emailFormat = EmailFormat.builder().to(userDto.getEmail())
              .template(SignUpControllerConstant.EMAIL_VERIFY_TEMPLATE).urls(links).receiver(userDto)
              .link(link).subject(SignUpControllerConstant.CONFIRMATION_PENDING_EMAIL_SUBJECT)
              .build();
      emailService.sendHtmlEmail(WebUtility.prepareEmailFormat(emailFormat));
    } catch (Exception e) {
      LOG.error(UserConstant.ERROR_CREATING_USER, e);
      model.addAttribute(ControllerConstant.ERROR, UserConstant.ERROR_CREATING_USER);
    }
  }

  /**
   * Update the user at this point then send an email after an update.
   *
   * @param token  the token
   * @param userId the userId
   * @return the user dto
   */
  private UserUpdateDto updateUserStatus(final String token, final String userId, final Model model) {
    try {
      String decryptedToken = encryptionService.decryptRequestUri(token);
      String decryptedUserId = encryptionService.decryptRequestUri(userId);
      PasswordTokenDto passwordTokenDto = passwordTokenService.validateUserToken(decryptedToken, decryptedUserId);
      if (Objects.isNull(passwordTokenDto)) {
        model.addAttribute(SignUpControllerConstant.SIGN_UP_ERROR, SignUpControllerConstant.INVALID_TOKEN);
      } else {
        UserUpdateDto userUpdateDto = passwordTokenDto.getUser();
        if (userUpdateDto.isEnabled()) {
          model.addAttribute(SignUpControllerConstant.SIGN_UP_ERROR, SignUpControllerConstant.USER_EXISTS_MESSAGE);
        } else {
          boolean status = userService.enableUser(userUpdateDto.getEmail());
          LOG.debug(UserConstant.USER_UPDATED_MESSAGE_LOG, status);
          return userUpdateDto;
        }
      }
    } catch (UnAuthorizedActionException e) {
      LOG.warn(SignUpControllerConstant.INVALID_TOKEN, e);
      model.addAttribute(SignUpControllerConstant.SIGN_UP_ERROR, SignUpControllerConstant.INVALID_TOKEN);
    }
    return null;
  }

  /**
   * Handles the submission of confirmation email of users account creation.
   *
   * @param userDto the userDto
   */
  private void sendConfirmationEmail(final UserDto userDto, HttpServletRequest httpServletRequest) {
    String link = WebUtility.getGenericUri(httpServletRequest, ProfileControllerConstant.USER_PROFILE_URL_MAPPING);
    Map<String, String> links = WebUtility.getDefaultUrls(httpServletRequest);
    EmailFormat emailFormat = EmailFormat.builder().template(SignUpControllerConstant.EMAIL_WELCOME_TEMPLATE)
            .link(link).urls(links).receiver(userDto).to(userDto.getEmail())
            .message(SignUpControllerConstant.CONFIRMATION_SUCCESS_EMAIL_TEXT)
            .subject(SignUpControllerConstant.CONFIRMATION_SUCCESS_EMAIL_SUBJECT)
            .build();
    emailService.sendHtmlEmail(WebUtility.prepareEmailFormat(emailFormat));
  }
}
