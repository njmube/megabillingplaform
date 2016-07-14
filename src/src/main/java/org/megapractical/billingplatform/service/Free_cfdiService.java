package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Free_cfdi;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

/**
 * Service Interface for managing Free_cfdi.
 */
public interface Free_cfdiService {

    /**
     * Save a free_cfdi.
     *
     * @param free_cfdi the entity to save
     * @return the persisted entity
     */
    Free_cfdi save(Free_cfdi free_cfdi);

    /**
     *  Get all the free_cfdis.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Free_cfdi> findAll(Pageable pageable);

    List<Free_cfdi> findCustom(Integer idFree_cfdi,
                               String folio_fiscal,
                               String rfc_receiver,
                               LocalDate fromDate,
                               LocalDate toDate,
                               Integer idState,
                               String serie,
                               String folio);

    /**
     *  Get the "id" free_cfdi.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Free_cfdi findOne(Long id);

    /**
     *  Delete the "id" free_cfdi.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
