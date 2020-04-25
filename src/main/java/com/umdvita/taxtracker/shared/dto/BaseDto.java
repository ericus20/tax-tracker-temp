package com.umdvita.taxtracker.shared.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * The BaseDto provides base fields to be extended.
 *
 * @author Eric Opoku
 * @version 1.0
 * @since 1.0
 */
@Data
public class BaseDto {
  private Long id;
  private int version;
  private LocalDateTime createdAt;
  private String createdBy;
  private LocalDateTime updatedAt;
  private String updatedBy;
}
