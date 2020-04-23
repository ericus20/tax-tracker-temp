package com.umdvita.taxtracker.web.model.exception;

import lombok.Data;

import java.time.LocalDate;

/**
 * The Exception Response format to be used by all exceptions in the application.
 *
 * @author Eric Opoku
 * @version 1.0
 * @since 1.0
 */
@Data
public class ExceptionResponse {
  private String url;
  private Integer code;
  private String message;
  private LocalDate timestamp;

  /**
   * Construction of ExceptionResponse is done with required parameters.
   *
   * @param url       the url
   * @param exception the exception
   * @param code      the http status code
   */
  public ExceptionResponse(final String url, final Exception exception, final Integer code) {
    this(url, exception, code, LocalDate.now());
  }

  private ExceptionResponse(final String url, final Exception ex, final Integer code, LocalDate time) {
    this.url = url;
    this.code = code;
    this.timestamp = time;
    this.message = ex.getLocalizedMessage();
  }
}
