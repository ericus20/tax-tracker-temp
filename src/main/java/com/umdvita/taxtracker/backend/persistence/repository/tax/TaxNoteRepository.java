package com.umdvita.taxtracker.backend.persistence.repository.tax;

import com.umdvita.taxtracker.backend.persistence.domain.tax.TaxReturnNote;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * The repository or DAO for taxReturnNote domain model.
 *
 * @author Eric Opoku
 * @version 1.0
 * @since 1.0
 */
@Repository
@Transactional(readOnly = true)
public interface TaxNoteRepository extends CrudRepository<TaxReturnNote, Long> {
}
