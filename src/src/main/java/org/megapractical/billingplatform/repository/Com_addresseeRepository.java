package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.Com_addressee;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Com_addressee entity.
 */
@SuppressWarnings("unused")
public interface Com_addresseeRepository extends JpaRepository<Com_addressee,Long> {

}
