package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Cfdi;
import org.megapractical.billingplatform.web.rest.dto.CfdiDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

/**
 * Service Interface for managing Cfdi.
 */
public interface CfdiService {

    /**
     * Save a cfdi.
     *
     * @param cfdi the entity to save
     * @return the persisted entity
     */
    Cfdi save(CfdiDTO cfdi);

    /**
     *  Get all the cfdis.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Cfdi> findAll(Pageable pageable);

    Page<Cfdi> findByEmitter(Integer idaccount, Pageable pageable);

    Page<Cfdi> findCustom(String folio_fiscal,
                               String rfc_receiver,
                               LocalDate fromDate,
                               LocalDate toDate,
                               String serie,
                               String folio,
                               Integer idaccount,
                               Integer idcfdi_type_doc,Integer pre, Integer send,Integer cancel, Integer receiver,
                               Pageable pageable);



    void cancelarFree_cfdi(Cfdi cfdi);


    /**
     *  Get the "id" cfdi.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Cfdi findOne(Long id);

    /**
     *  Delete the "id" cfdi.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
