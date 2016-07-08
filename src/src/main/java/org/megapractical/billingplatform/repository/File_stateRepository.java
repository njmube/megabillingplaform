package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.File_state;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the File_state entity.
 */
public interface File_stateRepository extends JpaRepository<File_state,Long> {
    Page<File_state> findByNameStartingWith(String filtername, Pageable pageable);

}
