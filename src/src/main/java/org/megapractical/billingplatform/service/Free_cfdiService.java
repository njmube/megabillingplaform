package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Free_cfdi;
import org.megapractical.billingplatform.domain.Free_emitter;
import org.megapractical.billingplatform.web.rest.dto.FreeCfdiDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

/**
 * Service Interface for managing Free_cfdi.
 */
public interface Free_cfdiService {

    /**
     * Save a free_cfdi.
     *
     * @param free_cfdi_dto the entity to save
     * @return the persisted entity
     */
    Free_cfdi save(FreeCfdiDTO free_cfdi_dto);

    /**
     *  Get all the free_cfdis.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Free_cfdi> findAll(Pageable pageable);

    Page<Free_cfdi> findByFree_emitter(Free_emitter free_emitter, Pageable pageable);

    Page<Free_cfdi> findCustom(Integer idFree_cfdi,
                               String folio_fiscal,
                               String rfc_receiver,
                               LocalDate fromDate,
                               LocalDate toDate,
                               Integer idState,
                               String serie,
                               String folio,
                               Free_emitter free_emitter,
                               Pageable pageable);

    Page<Free_cfdi> findCustomAdmin(Integer idFree_cfdi,
                               String folio_fiscal,
                               String rfc_receiver,
                               LocalDate fromDate,
                               LocalDate toDate,
                               Integer idState,
                               String serie,
                               String folio,
                               Pageable pageable);

    void cancelarFree_cfdi(Free_cfdi free_cfdi);

    byte[] getZip(Integer id);

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
