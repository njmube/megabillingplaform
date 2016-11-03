package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.Com_donees;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Com_donees entity.
 */
@SuppressWarnings("unused")
public interface Com_doneesRepository extends JpaRepository<Com_donees,Long> {

}
