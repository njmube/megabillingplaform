package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.Acquired_title;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Acquired_title entity.
 */
public interface Acquired_titleRepository extends JpaRepository<Acquired_title,Long> {

}
