package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.Payment_method;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Payment_method entity.
 */
public interface Payment_methodRepository extends JpaRepository<Payment_method,Long> {
    Page<Payment_method> findByNameStartingWith(String filtername, Pageable pageable);
    Page<Payment_method> findByCodeStartingWith(String filtercode, Pageable pageable);

}
