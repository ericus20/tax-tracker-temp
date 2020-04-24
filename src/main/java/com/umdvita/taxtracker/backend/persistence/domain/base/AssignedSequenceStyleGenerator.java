package com.umdvita.taxtracker.backend.persistence.domain.base;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

import java.io.Serializable;
import java.util.Objects;

/**
 * A custom sequence generator that can also accommodate manually assigned identifier.
 *
 * @author Eric Opoku
 * @version 1.0
 * @since 1.0
 */
public class AssignedSequenceStyleGenerator extends SequenceStyleGenerator {

  @Override
  public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
    if (object instanceof Identifiable) {
      Identifiable<?> identifiable = (Identifiable<?>) object;
      Serializable id = identifiable.getId();
      if (Objects.nonNull(id)) {
        return id;
      }
    }
    return super.generate(session, object);
  }
}
