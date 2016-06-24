package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Cfdi_type_doc;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Cfdi_type_doc.
 */
public interface Cfdi_type_docService {

    /**
     * Save a cfdi_type_doc.
     * 
     * @param cfdi_type_doc the entity to save
     * @return the persisted entity
     */
    Cfdi_type_doc save(Cfdi_type_doc cfdi_type_doc);

    /**
     *  Get all the cfdi_type_docs.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Cfdi_type_doc> findAll(Pageable pageable);

    /**
     *  Get the "id" cfdi_type_doc.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Cfdi_type_doc findOne(Long id);

    /**
     *  Delete the "id" cfdi_type_doc.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
