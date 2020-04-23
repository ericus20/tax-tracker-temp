package com.umdvita.taxtracker.annotation.impl;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * Ensures that method calls can be logged with entry-exit logs in console or log file.
 *
 * @author Eric Opoku
 * @version 1.1.0
 * @since 1.1.0
 */
@Slf4j
@Aspect
@Component
public class MethodLogger {

  /**
   * - visibility modifier is * (public, protected or private)
   * - name is * (any name);
   * - arguments are .. (any arguments); and
   * - is annotated with @Loggable.
   *
   * @param joinPoint the joinPoint
   * @return the log object
   */
  @Around("execution(* *(..)) && @annotation(com.umdvita.taxtracker.annotation.Loggable)")
  public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
    Object response;
    String method = joinPoint.getSignature().toShortString();
    try {
      long start = System.currentTimeMillis();
      LOG.info("=> Starting -  {} args: {}", method, joinPoint.getArgs());
      response = joinPoint.proceed();
      LOG.info("<= {} : {} - Finished, duration: {} ms", method, response, (System.currentTimeMillis() - start));
      return response;
    } catch (Exception e) {
      LOG.error("Exception while invoking method - {}", method, e);
      throw e;
    }
  }

}
