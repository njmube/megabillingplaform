package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.Freecom_addressee;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Freecom_addressee entity.
 */
@SuppressWarnings("unused")
public interface Freecom_addresseeRepository extends JpaRepository<Freecom_addressee,Long> {

}
