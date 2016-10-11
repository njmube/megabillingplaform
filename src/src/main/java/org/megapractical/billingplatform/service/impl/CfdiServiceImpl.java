package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.domain.Config_pathrootfile;
import org.megapractical.billingplatform.domain.Taxpayer_account;
import org.megapractical.billingplatform.service.CfdiService;
import org.megapractical.billingplatform.domain.Cfdi;
import org.megapractical.billingplatform.repository.CfdiRepository;
import org.megapractical.billingplatform.service.Taxpayer_accountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Service Implementation for managing Cfdi.
 */
@Service
@Transactional
public class CfdiServiceImpl implements CfdiService{

    private final Logger log = LoggerFactory.getLogger(CfdiServiceImpl.class);

    @Inject
    private CfdiRepository cfdiRepository;

    @Inject
    private Taxpayer_accountService taxpayer_accountService;

    /**
     * Save a cfdi.
     *
     * @param cfdi the entity to save
     * @return the persisted entity
     */
    public Cfdi save(Cfdi cfdi) {
        log.debug("Request to save Cfdi : {}", cfdi);
        Cfdi result = cfdiRepository.save(cfdi);
        return result;
    }

    public Page<Cfdi> findByEmitter(Integer idaccount, Pageable pageable){

        return null;
    }

    public Page<Cfdi> findCustom(String folio_fiscal,
                          String rfc_receiver,
                          LocalDate fromDate,
                          LocalDate toDate,
                          String serie,
                          String folio,
                          Integer idaccount,
                          Integer idcfdi_type_doc,Integer pre, Integer send,Integer cancel, Integer receiver,
                          Pageable pageable){

        Page<Cfdi> all = cfdiRepository.findAll(pageable);
        List<Cfdi> list = new ArrayList<>();

        for(Cfdi cfdi: all.getContent()){
            boolean a = true;//Folio fiscal
            boolean b = true;//RFC receiver
            boolean c = true;//FromDate
            boolean d = true;//idcfdi_type_doc
            boolean e = true;//serie
            boolean f = true;//folio
            boolean g = true;//idaccount
            boolean h = true;//pre
            boolean i = true;//send
            boolean j = true;//cancel
            boolean k = true;//receiver

            if(folio_fiscal.compareTo(" ") != 0){
                if(folio_fiscal.compareTo(cfdi.getFolio_fiscal_orig())!=0){
                    a = false;
                }
            }
            if(rfc_receiver.compareTo(" ") != 0){
                if(rfc_receiver.compareTo(cfdi.getTaxpayer_client().getRfc())!=0){
                    b = false;
                }
            }
            if(serie.compareTo(" ") != 0){
                if(serie.compareTo(cfdi.getSerial())!=0){
                    e = false;
                }
            }
            if(folio.compareTo(" ") != 0){
                if(folio.compareTo(cfdi.getFolio())!=0){
                    f = false;
                }
            }
            log.debug("Inicio: "+ fromDate.toString() + " final: "+ toDate.toString());
            if(fromDate.toString().compareTo("0001-01-01") != 0 || toDate.toString().compareTo("0001-01-01") != 0){
                LocalDate inicio = fromDate;
                LocalDate datefinal;
                if(fromDate.isBefore(toDate)){
                    datefinal = toDate;
                }else {
                    datefinal = LocalDate.now();
                }
                LocalDate actual = cfdi.getDate_expedition().toLocalDate();

                log.debug("Inicio ajustado: "+ inicio.toString() + " final ajustado: "+ datefinal.toString());
                log.debug("Fecha Actual: "+ actual.toString());

                if(inicio.isAfter(actual)){
                    c = false;
                }
                if(datefinal.isBefore(actual)){
                    c = false;
                }
            }
            if (idcfdi_type_doc != 0){
                if(idcfdi_type_doc.toString().compareTo(cfdi.getCfdi_type_doc().getId().toString())!=0){
                    d = false;
                }
            }
            if(idaccount != 0){
                if(idaccount.toString().compareTo(cfdi.getTaxpayer_account().getId().toString())!=0){
                    g = false;
                }
            }
            if(cancel == 1){
                if(cfdi.getCfdi_states().getId().toString().compareTo("2")!=0){
                    j = false;
                }
            }

            if(receiver == 1){
                if(idaccount != 0){
                    Taxpayer_account taxpayer_account = taxpayer_accountService.findOne(new Long(idaccount.toString()));
                    if(taxpayer_account != null){
                        if(cfdi.getTaxpayer_client().getRfc().compareTo(taxpayer_account.getRfc())!=0){
                            k = false;
                        }
                    }
                }
            }
            if(a && b && c && d && e && f && g && h && i && j && k){
                list.add(cfdi);
            }
        }

        return new PageImpl<Cfdi>(list,pageable,all.getTotalElements());
    }

    public void cancelarFree_cfdi(Cfdi cfdi){

    }


    /**
     *  Get all the cfdis.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Cfdi> findAll(Pageable pageable) {
        log.debug("Request to get all Cfdis");
        Page<Cfdi> result = cfdiRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one cfdi by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Cfdi findOne(Long id) {
        log.debug("Request to get Cfdi : {}", id);
        Cfdi cfdi = cfdiRepository.findOne(id);
        return cfdi;
    }

    /**
     *  Delete the  cfdi by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Cfdi : {}", id);
        cfdiRepository.delete(id);
    }
}
