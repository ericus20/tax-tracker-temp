package com.umdvita.taxtracker.web.controller.advice;

import com.umdvita.taxtracker.exception.tax.TaxGroupNotEmptyException;
import com.umdvita.taxtracker.exception.tax.TaxTrackerException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * The controller advice for handling all web mvc exceptions.
 *
 * @author Eric Opoku
 * @version 1.0
 * @since 1.0
 */
@ControllerAdvice
public class TaxTrackerControllerAdvice extends ResponseEntityExceptionHandler {

  @ExceptionHandler(value = {TaxGroupNotEmptyException.class})
  protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
    String bodyOfResponse = ex.getMessage();
    return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
  }

  @ExceptionHandler(value = {TaxTrackerException.class})
  protected ResponseEntity<Object> handleGlobal(RuntimeException ex, WebRequest request) {
    String bodyOfResponse = ex.getMessage();
    return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
  }
}
