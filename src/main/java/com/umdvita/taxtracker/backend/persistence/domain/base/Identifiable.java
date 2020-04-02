package com.umdvita.taxtracker.backend.persistence.domain.base;

import java.io.Serializable;

public interface Identifiable<T extends Serializable> {
  T getId();
}
