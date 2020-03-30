package com.umdvita.taxtracker.config.security;

import com.umdvita.taxtracker.backend.service.security.UserService;
import com.umdvita.taxtracker.constant.ProfileType;
import com.umdvita.taxtracker.constant.security.SecurityConfigConstant;
import com.umdvita.taxtracker.constant.signup.PasswordControllerConstant;
import com.umdvita.taxtracker.constant.signup.SignUpControllerConstant;
import com.umdvita.taxtracker.enums.RoleType;
import com.umdvita.taxtracker.exception.tax.TaxTrackerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static com.umdvita.taxtracker.constant.signup.PasswordControllerConstant.FORGOT_ROOT_MAPPING;

/**
 * The SecurityConfig class that Implements and provides security for all incoming requests.
 *
 * @author Eric Opoku
 * @version 1.0
 * @since 1.0
 */
@Slf4j
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  /**
   * Public URLs to be allowed without authentication.
   */
  private static final String[] PUBLIC_MATCHERS = {
          SecurityConfigConstant.WEBJARS,
          SecurityConfigConstant.CSS,
          SecurityConfigConstant.JS,
          SecurityConfigConstant.VENDOR,
          SecurityConfigConstant.VENDOR_DIST,
          SecurityConfigConstant.FONTS,
          SecurityConfigConstant.STATIC,
          SecurityConfigConstant.IMAGES,
          SecurityConfigConstant.FAVICON,
          SecurityConfigConstant.COPY,
          SecurityConfigConstant.CONTACT,
          SecurityConfigConstant.FEATURES,
          SecurityConfigConstant.WHAT_TO_BRING,
          SecurityConfigConstant.MORE_ON_TAXES,
          SecurityConfigConstant.CONSOLE,
          SecurityConfigConstant.LOGIN_URL,
          SecurityConfigConstant.SIGN_UP,
          SecurityConfigConstant.EXPIRED_URL,
          SecurityConfigConstant.ROOT,
          PasswordControllerConstant.FORGOT_ROOT_MAPPING,
          String.join("", FORGOT_ROOT_MAPPING, PasswordControllerConstant.CHANGE_PATH),
          SignUpControllerConstant.SIGN_UP_VERIFY_URL_MAPPING,
  };

  private final Environment environment;
  private final SecurityBeans securityBeans;
  private final UserService userService;

  /**
   * Constructor for SecurityConfig class.
   *  @param environment    the environment
   * @param securityBeans   the security beans
   * @param userService     the user service
   */
  @Autowired
  public SecurityConfig(Environment environment, SecurityBeans securityBeans, UserService userService) {
    this.environment = environment;
    this.securityBeans = securityBeans;
    this.userService = userService;
  }

  /**
   * Override this method to configure the {@link HttpSecurity}. Typically subclasses
   * should not invoke this method by calling super as it may override their
   * configuration. The default configuration is:
   *
   * <pre>
   * http.authorizeRequests().anyRequest().authenticated().and().formLogin().and().httpBasic();
   * </pre>
   *
   * @param http the {@link HttpSecurity} to modify
   */
  @Override
  protected void configure(final HttpSecurity http) throws Exception {

    // If we are running with dev profile, disable csrf and frame options to enable h2 to work.
    configureEnvironmentSpecificAccess(http);

    http
            .authorizeRequests()
              .antMatchers(SecurityConfigConstant.ADMIN).hasRole(RoleType.ADMIN.getRole())
              .antMatchers(SecurityConfigConstant.ACTUATOR).hasRole(RoleType.ENDPOINT_ADMIN.getRole())
              .antMatchers(SecurityConfigConstant.LEADER).hasRole(RoleType.LEADER.getRole())
              .antMatchers(PUBLIC_MATCHERS).permitAll();

    http
            .sessionManagement()
              .maximumSessions(SecurityConfigConstant.MAXIMUM_SESSIONS)
              .maxSessionsPreventsLogin(false)
              .expiredUrl(SecurityConfigConstant.EXPIRED_URL)
              .sessionRegistry(securityBeans.sessionRegistry());

    http
            .authorizeRequests()
              .anyRequest().authenticated()
            .and()
            .formLogin()
            .and()
            .logout()
              .logoutRequestMatcher(new AntPathRequestMatcher(SecurityConfigConstant.LOGOUT))
              .logoutSuccessUrl(SecurityConfigConstant.LOGIN_LOGOUT)
              .deleteCookies(SecurityConfigConstant.REMEMBER_ME).deleteCookies(SecurityConfigConstant.JSESSIONID)
            .invalidateHttpSession(true).permitAll()
            .and()
            .rememberMe()
              .tokenRepository(securityBeans.persistentRepository());
  }

  /**
   * Configured to ignore certain requests.
   *
   * @param web the webSecurity
   * @see WebSecurity
   */
  @Override
  public void configure(WebSecurity web) {
    web
            .ignoring()
            .antMatchers(SecurityConfigConstant.RESOURCES);
  }

  /**
   * Configures global user's with authentication credentials.
   *
   * @param auth to easily build in memory, LDAP, and JDBC authentication
   * @throws TaxTrackerException If anything goes wrong, there will be an exception thrown.
   */
  @Autowired
  void configureGlobal(final AuthenticationManagerBuilder auth) {
    try {
      auth
              .userDetailsService(userService)
              .passwordEncoder(securityBeans.passwordEncoder());

    } catch (Exception e) {
      LOG.error("There was an error processing auth configurer", e);
      throw new TaxTrackerException("There was an error processing auth configurer", e);
    }
  }

  /**
   * If we are running with dev profile, disable csrf and frame options to enable h2 to work.
   * If we are in just prod, look out for generated ssl to allow https.
   * If we are in aws, fully secure and require https.
   *
   * @param http the http request
   */
  private void configureEnvironmentSpecificAccess(HttpSecurity http) throws Exception {
    String[] activeProfiles = environment.getActiveProfiles();
    for (String profile : activeProfiles) {
      if (profile.equalsIgnoreCase(ProfileType.DEV) || profile.equalsIgnoreCase(ProfileType.TEST)) {
        http.headers().frameOptions().disable().and().csrf().disable();
      } else if (profile.equalsIgnoreCase(ProfileType.PROD)) {
        http.requiresChannel()
                .requestMatchers(r -> r.getHeader(SecurityConfigConstant.X_FORWARDED_PROTO) != null)
                .requiresSecure();
      }
    }
  }
}

