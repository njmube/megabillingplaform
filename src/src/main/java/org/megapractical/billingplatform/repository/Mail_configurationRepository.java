package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.Mail_configuration;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Mail_configuration entity.
 */
public interface Mail_configurationRepository extends JpaRepository<Mail_configuration,Long> {

}
