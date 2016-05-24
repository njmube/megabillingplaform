package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.File_state;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the File_state entity.
 */
public interface File_stateRepository extends JpaRepository<File_state,Long> {

}
