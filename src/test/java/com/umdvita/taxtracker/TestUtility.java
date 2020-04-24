package com.umdvita.taxtracker;

import com.umdvita.taxtracker.constant.ProfileType;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;

@Component
@ActiveProfiles(value = {ProfileType.PROD})
public class TestUtility {

  public static final String[] ENTITY_GLOBAL_FIELDS_TO_IGNORE = {
          "id", "version", "createdAt", "createdBy", "updatedAt", "updatedBy"
  };
}
