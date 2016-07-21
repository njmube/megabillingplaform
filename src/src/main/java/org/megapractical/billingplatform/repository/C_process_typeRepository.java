package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.C_process_type;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the C_process_type entity.
 */
public interface C_process_typeRepository extends JpaRepository<C_process_type,Long> {

}
