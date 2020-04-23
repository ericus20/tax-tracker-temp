package com.umdvita.taxtracker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "File format not correct or not allowed")
public class InvalidFileFormatException extends RuntimeException {

  public InvalidFileFormatException(String message) {
    super(message);
  }
}
