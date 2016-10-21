package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.Taxpayer_series_folio;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Taxpayer_series_folio entity.
 */
@SuppressWarnings("unused")
public interface Taxpayer_series_folioRepository extends JpaRepository<Taxpayer_series_folio,Long> {

}
