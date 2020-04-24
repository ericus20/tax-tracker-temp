package com.umdvita.taxtracker.backend.persistence.domain.base;

import java.io.Serializable;

/**
 * This interface is to provide a generic Identity for entities.
 *
 * @author Eric Opoku
 * @version 1.0
 * @since 1.0
 * @param <T> the ID type
 */
public interface Identifiable<T extends Serializable> {
  T getId();
}
