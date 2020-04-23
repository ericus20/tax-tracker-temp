package com.umdvita.taxtracker.config.core;

import com.umdvita.taxtracker.constant.ProfileType;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * The ProductionConfig class handles all the configurations needed for production environment.
 *
 * @author Eric Opoku
 * @version 1.0
 * @since 1.0
 */
@Configuration
@Profile(value = {ProfileType.PROD, ProfileType.PROD_AWS, ProfileType.DOCKER})
public class ProductionConfig {
}
