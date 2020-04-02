package com.umdvita.taxtracker.backend.persistence.repository.tax;

import com.umdvita.taxtracker.backend.persistence.domain.security.user.User;
import com.umdvita.taxtracker.backend.persistence.domain.tax.TaxReturn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * The repository or DAO for tax return domain model.
 *
 * @author Eric Opoku
 * @version 1.0
 * @since 1.0
 */
@Repository
@Transactional(readOnly = true)
public interface TaxReturnRepository extends JpaRepository<TaxReturn, Long> {

  /**
   * Retrieve all taxReturn objects.
   *
   * @return list of taxReturn
   */
  @NonNull
  List<TaxReturn> findAll();

  /**
   * Checks if a taxReturn exists with the user and year specified.
   *
   * @param user the user
   * @param year the year
   * @return <code>true</code> if exists
   */
  boolean existsByUserAndYear(User user, String year);

}
