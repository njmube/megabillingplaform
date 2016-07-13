package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.Process_type;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Process_type entity.
 */
public interface Process_typeRepository extends JpaRepository<Process_type,Long> {

}
