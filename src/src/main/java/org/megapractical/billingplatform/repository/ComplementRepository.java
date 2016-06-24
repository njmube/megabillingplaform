package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.Complement;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Complement entity.
 */
public interface ComplementRepository extends JpaRepository<Complement,Long> {

}
