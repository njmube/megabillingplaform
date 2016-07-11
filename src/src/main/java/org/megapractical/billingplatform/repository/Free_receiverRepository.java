package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.Free_receiver;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Free_receiver entity.
 */
@SuppressWarnings("unused")
public interface Free_receiverRepository extends JpaRepository<Free_receiver,Long> {

}
