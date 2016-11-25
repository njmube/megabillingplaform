package org.megapractical.billingplatform.service.impl;

import org.apache.commons.io.IOUtils;
import org.megapractical.billingplatform.domain.Config_pathrootfile;
import org.megapractical.billingplatform.domain.Taxpayer_account;
import org.megapractical.billingplatform.service.CfdiService;
import org.megapractical.billingplatform.domain.Cfdi;
import org.megapractical.billingplatform.repository.CfdiRepository;
import org.megapractical.billingplatform.service.Config_pathrootfileService;
import org.megapractical.billingplatform.service.Taxpayer_accountService;
import org.megapractical.billingplatform.web.rest.dto.CfdiDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.io.*;
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

    @Inject
    private Config_pathrootfileService config_pathrootfileService;

    /**
     * Save a cfdi.
     *
     * @param cfdiDTO the entity to save
     * @return the persisted entity
     */
    public Cfdi save(CfdiDTO cfdiDTO) {
        Cfdi cfdi = cfdiDTO.getCfdi();
        log.debug("Request to save Cfdi : {}", cfdi);
        cfdi = saveXMLandPDF(cfdi);
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
                if(rfc_receiver.compareTo(cfdi.getClient().getRfc())!=0){
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
            if(idaccount != 0 && receiver != 1){
                if(idaccount.toString().compareTo(cfdi.getTaxpayer_account().getId().toString())!=0){
                    g = false;
                }
            }
            if(pre == 1){
                if(cfdi.getCom_tfd() != null){
                    h = false;
                }
            }
            if(send == 1){
                if(cfdi.getCom_tfd() == null){
                    i = false;
                }
                if(cfdi.getCfdi_states().getId().toString().compareTo("2")==0){
                    i = false;
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
                    //log.debug("Revisando receiver: cuenta: " + taxpayer_account.getRfc() + " cliente: " + cfdi.getClient().getRfc());
                    if(taxpayer_account != null){
                        if(cfdi.getClient().getRfc().compareTo(taxpayer_account.getRfc())!=0){
                            //log.debug("Entro al receiver");
                            k = false;
                        }
                    }
                }
            }
            //log.debug("Variables a: " + a + "b: " +b+ "c: "+c +"d: " +d+ "e: "+e+"f: "+f+"g: "+g+"h: "+h+"i: "+i+"j: "+j+"k: "+k);
            if(a && b && c && d && e && f && g && h && i && j && k){
                if(cfdi.getFilepdf() == null)
                    cfdi = getFile(cfdi);
                //log.debug("Adicionó el cfdi: " + cfdi.getId().toString());
                list.add(cfdi);
            }
        }
        if(list.size() == 0)
        {
            return new PageImpl<Cfdi>(list,pageable,0);
        }
        return new PageImpl<Cfdi>(list,pageable,all.getTotalElements());
    }

    public void cancelarCfdi(Cfdi cfdi){

    }

    private Cfdi saveXMLandPDF(Cfdi cfdi){
        cfdi.setFilexml(null);
        cfdi.setFilepdf(null);
        cfdi.setFilepdfContentType("application/pdf");
        cfdi.setFilexmlContentType("text/xml");
        String root = "";
        //<RFC_Emisor>_<RFC_Receptor>_<Fecha_Expedicion>.<xml o pdf uno de cada uno>
        int year = cfdi.getDate_expedition().getYear();
        int month = cfdi.getDate_expedition().getMonth().getValue();
        int day = cfdi.getDate_expedition().getDayOfMonth();

        List<Config_pathrootfile> list = config_pathrootfileService.finAll();
        if (list.size()>0){
            Config_pathrootfile config = list.get(0);
            root =config.getPathrootCfdi();
        }
        if(root.contains("\\")) {
            root += cfdi.getTaxpayer_account().getRfc()+"\\"+ year + "\\" + month + "\\" + day;
        }
        else {
            root += cfdi.getTaxpayer_account().getRfc()+"/"+ year + "/" + month + "/" + day;
        }
        File diretory = new File(root);
        if(!diretory.isDirectory()){
            diretory.mkdirs();
        }
        if(root.contains("\\")) {
            root += "\\";
        }
        else {
            root += "/";
        }
        String base = root + cfdi.getTaxpayer_account().getRfc()+"_"+cfdi.getClient().getRfc()+
            "_" + cfdi.getDate_expedition().toLocalDate().getYear()+"_" +
            cfdi.getDate_expedition().toLocalDate().getMonthValue()+"_" +
            cfdi.getDate_expedition().toLocalDate().getDayOfMonth()+"T" +
            cfdi.getDate_expedition().getHour()+"_" +
            cfdi.getDate_expedition().getMinute()+"_" +
            cfdi.getDate_expedition().getSecond();
        String filepdf = root + cfdi.getTaxpayer_account().getRfc()+"_"+cfdi.getClient().getRfc()+
            "_" + cfdi.getDate_expedition().toLocalDate().getYear()+"_" +
            cfdi.getDate_expedition().toLocalDate().getMonthValue()+"_" +
            cfdi.getDate_expedition().toLocalDate().getDayOfMonth()+"T" +
            cfdi.getDate_expedition().getHour()+"_" +
            cfdi.getDate_expedition().getMinute()+"_" +
            cfdi.getDate_expedition().getSecond()+".pdf";
        String filexml = root + cfdi.getTaxpayer_account().getRfc()+"_"+cfdi.getClient().getRfc()+
            "_" + cfdi.getDate_expedition().toLocalDate().getYear()+"_" +
            cfdi.getDate_expedition().toLocalDate().getMonthValue()+"_" +
            cfdi.getDate_expedition().toLocalDate().getDayOfMonth()+"T" +
            cfdi.getDate_expedition().getHour()+"_" +
            cfdi.getDate_expedition().getMinute()+"_" +
            cfdi.getDate_expedition().getSecond()+".xml";

        log.debug("Fichero PDF: {}", filepdf);
        log.debug("Fichero XML: {}", filexml);

        //Creando PDF
        try{
            OutputStream outputStream = null;
            File newFile = new File(filepdf);
            if (!newFile.exists()) {
                boolean res = newFile.createNewFile();
                if (!res){
                    return cfdi;
                }
            } else {
                if (newFile.delete()) {
                    File otherfile = new File(filepdf);
                    boolean res = otherfile.createNewFile();
                    if (!res){
                        return cfdi;
                    }
                    newFile = otherfile;
                }
            }
            log.debug("Escribiendo pdf");
            outputStream = new FileOutputStream(newFile);
            outputStream.write(obteinPDF(cfdi));

        }catch (Exception e){
        }
        try{
            OutputStream outputStream = null;
            File newFile = new File(filexml);

            if (!newFile.exists()) {
                boolean res = newFile.createNewFile();
                if (!res){
                    return cfdi;
                }
            } else {
                if (newFile.delete()) {
                    File otherfile = new File(filexml);
                    boolean res = otherfile.createNewFile();
                    if (!res){
                        return cfdi;
                    }
                    newFile = otherfile;
                }
            }
            log.debug("Escribiendo xml");
            outputStream = new FileOutputStream(newFile);
            outputStream.write(obteinXML(cfdi));

        }catch (Exception e){
        }
        cfdi.setPath_cfdi(base);
        return cfdi;
    }
    //Se llama al servicio correspondiente
    private byte[] obteinPDF(Cfdi cfdi){
        byte[] pdf = new byte[1000000];
        pdf[0] = 1;
        return pdf;
    }
    //Se llama al servicio correspondiente
    private byte[] obteinXML(Cfdi cfdi){
        byte[] xml = new byte[1000000];
        xml[0] = 1;
        return xml;
    }

    private Cfdi getFile(Cfdi cfdi){
        log.debug("Leyendo ficheros : {}", cfdi.getPath_cfdi());

        if(cfdi.getPath_cfdi() != null){
            if(!cfdi.getPath_cfdi().isEmpty()){
                File newFile = new File(cfdi.getPath_cfdi()+".pdf");
                InputStream inputStream = null;
                log.debug("Direccion pdf: {}", cfdi.getPath_cfdi()+".pdf");
                if (newFile.exists()) {
                    log.debug("Fichero pdf existe");
                    try {
                        inputStream = new FileInputStream(newFile);
                        byte[] arre = IOUtils.toByteArray(inputStream);
                        cfdi.setFilepdf(arre);
                        log.debug("Leyendo PDF");
                    } catch (Exception e) {
                        log.debug(e.getLocalizedMessage());
                    }
                }

                File newFile1 = new File(cfdi.getPath_cfdi()+".xml");
                InputStream inputStream1 = null;

                if (newFile1.exists()) {
                    try {
                        inputStream1 = new FileInputStream(newFile1);
                        cfdi.setFilexml(IOUtils.toByteArray(inputStream1));
                        log.debug("Leyendo XML");
                    } catch (Exception e) {

                    }
                }
            }
        }
        return cfdi;
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

        List<Cfdi> list = new ArrayList<>();

        for(Cfdi cfdi: result.getContent()){
            if(cfdi.getFilepdf() == null)
                cfdi = getFile(cfdi);

            list.add(cfdi);
        }
        return new PageImpl<Cfdi>(list,pageable,result.getTotalElements());
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
        if(cfdi.getFilepdf() == null)
            cfdi = getFile(cfdi);

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
