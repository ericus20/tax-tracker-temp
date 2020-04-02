package com.umdvita.taxtracker.backend.pojo;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class SeparateDateFormat implements Serializable {
  private static final long serialVersionUID = 4816694102146369185L;

  private long seconds;
  private long minutes;
  private long hours;
  private long days;
}
