package com.umdvita.taxtracker.backend.persistence.domain.security;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.validation.constraints.NotNull;

/**
 * The TaxTrackerAuthority implements GrantedAuthority to return the name of the authority.
 *
 * @author Eric Opoku
 * @version 1.0
 * @see GrantedAuthority
 * @since 1.0
 */
@Getter
@EqualsAndHashCode
@RequiredArgsConstructor
public final class TaxTrackerAuthority implements GrantedAuthority {

  @NotNull
  private final String authority;
}
