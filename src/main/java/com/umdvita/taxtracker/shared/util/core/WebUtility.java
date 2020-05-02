package com.umdvita.taxtracker.shared.util.core;

import com.umdvita.taxtracker.constant.ControllerConstant;
import com.umdvita.taxtracker.shared.util.validation.InputValidationUtility;
import com.umdvita.taxtracker.web.model.feedback.EmailFormat;
import org.apache.commons.lang3.StringUtils;
import org.thymeleaf.context.Context;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * This utility class holds all common methods used in the web layer.
 *
 * @author Eric Opoku
 * @version 1.0
 * @since 1.0
 */
public abstract class WebUtility {

  private static final String BASE_URL = "https://umd-vita.com";
  private static final String DASHBOARD_LINK = "dashboardLink";
  private static final String ABOUT_US_LINK = "aboutUsLink";
  private static final String HELP_LINK = "helpLink";
  private static final String COPY_ABOUT_US = "/copy/about-us";

  private WebUtility() {
    throw new AssertionError("Non instantiable");
  }

  /**
   * Generates password url given parameters.
   *
   * @param userId  the user.
   * @param token   the token.
   * @param path    the path.
   * @param request the request
   * @return the constructed url.
   */
  public static String createPasswordUrl(
          String userId, String token, String path, HttpServletRequest request) {
    InputValidationUtility.validateInputs(WebUtility.class, userId, token, path);

    String url = getGenericUri(request, path);
    if (StringUtils.isNotBlank(url)) {
      return url + "?userId=" + userId + "&token=" + token;
    }
    return null;
  }

  /**
   * Generates a uri dynamically by constructing url using http servlet request.
   *
   * @param httpServletRequest the http servlet request
   * @param path               the custom path
   * @return a dynamically formulated uri
   */
  public static String getGenericUri(final HttpServletRequest httpServletRequest, String path) {
    return httpServletRequest.getScheme() + // http or https
            "://"
            + httpServletRequest.getServerName() + // localhost or other
            ":"
            + httpServletRequest.getServerPort() + // Server port 8080
            httpServletRequest.getContextPath() + // Root context "/"
            path;
  }

  /**
   * Get general links used in email definitions.
   *
   * @param httpServletRequest the httpServletRequest
   * @return default links
   */
  public static Map<String, String> getDefaultUrls(final HttpServletRequest httpServletRequest) {
    return getDefaultUrls(null, httpServletRequest);
  }

  /**
   * Get general links used in email definitions.
   *
   * @param baseUrl            the baseUrl
   * @param httpServletRequest the httpServletRequest
   * @return default links
   */
  public static Map<String, String> getDefaultUrls(final String baseUrl, HttpServletRequest httpServletRequest) {
    return new HashMap<>();
  }

  /**
   * Prepares a context with current content.
   *
   * @param emailFormat the emailFormat
   * @return emailFormat with context
   */
  public static EmailFormat prepareEmailFormat(final EmailFormat emailFormat) {
    return prepareEmailFormat(emailFormat, Collections.emptyMap());
  }

  public static EmailFormat prepareEmailFormat(final EmailFormat emailFormat, Map<String, String> contextVariables) {
    Context context = new Context();
    for (Map.Entry<String, String> entry : contextVariables.entrySet()) {
      context.setVariable(entry.getKey(), entry.getValue());
    }
    context.setVariable(ControllerConstant.URLS, emailFormat.getUrls());
    // set the appropriate to and from based on the details available
    configureSenderAndReceiverDetails(emailFormat, context);

    emailFormat.setSubject(emailFormat.getSubject());
    if (Objects.nonNull(emailFormat.getLink())) {
      context.setVariable(ControllerConstant.EMAIL_LINK, emailFormat.getLink());
    }
    if (Objects.nonNull(emailFormat.getMessage())) {
      context.setVariable(ControllerConstant.MESSAGE, emailFormat.getMessage());
    }
    // TODO: 4/26/2020 Setup document details
    // setup the needed details from the tax return object if present
    configureTaxReturnDetails(emailFormat, context);
    emailFormat.setContext(context);
    return emailFormat;
  }

  /**
   * set the appropriate to and from based on the details available.
   *
   * @param emailFormat the emailFormat
   * @param context     the context
   */
  private static void configureSenderAndReceiverDetails(EmailFormat emailFormat, Context context) {

  }

  /**
   * Setup the needed details from the tax return object if present.
   *
   * @param emailFormat the email format
   * @param context     the thyemeleaf context
   */
  public static void configureTaxReturnDetails(EmailFormat emailFormat, Context context) {

  }

}
