package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.domain.Config_pathrootfile;
import org.megapractical.billingplatform.domain.Free_emitter;
import org.megapractical.billingplatform.service.Config_pathrootfileService;
import org.megapractical.billingplatform.service.Free_cfdiService;
import org.megapractical.billingplatform.domain.Free_cfdi;
import org.megapractical.billingplatform.repository.Free_cfdiRepository;
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
 * Service Implementation for managing Free_cfdi.
 */
@Service
@Transactional
public class Free_cfdiServiceImpl implements Free_cfdiService{

    private final Logger log = LoggerFactory.getLogger(Free_cfdiServiceImpl.class);

    @Inject
    private Free_cfdiRepository free_cfdiRepository;

    @Inject
    private Config_pathrootfileService config_pathrootfileService;

    /**
     * Save a free_cfdi.
     *
     * @param free_cfdi the entity to save
     * @return the persisted entity
     */
    public Free_cfdi save(Free_cfdi free_cfdi) {
        log.debug("Request to save Free_cfdi : {}", free_cfdi);
        free_cfdi = saveXMLandPDF(free_cfdi);
        Free_cfdi result = free_cfdiRepository.save(free_cfdi);
        return result;
    }

    private Free_cfdi saveXMLandPDF(Free_cfdi free_cfdi){
        String root = "";
        //<RFC_Emisor>_<RFC_Receptor>_<Fecha_Expedicion>.<xml o pdf uno de cada uno>
        int year = free_cfdi.getDate_expedition().getYear();
        int month = free_cfdi.getDate_expedition().getMonth().getValue();
        int day = free_cfdi.getDate_expedition().getDayOfMonth();

        List<Config_pathrootfile> list = config_pathrootfileService.finAll();
        if (list.size()>0){
            Config_pathrootfile config = list.get(0);
            root =config.getPathrootFreeCfdi();
        }
        if(root.contains("\\")) {
            root += year + "\\" + month + "\\" + day;
        }
        else {
            root += year + "/" + month + "/" + day;
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
        String base = root + free_cfdi.getFree_emitter().getRfc()+"_"+free_cfdi.getFree_receiver().getRfc()+
            "_" + free_cfdi.getDate_expedition().toLocalDate().toString();
        String filepdf = root + free_cfdi.getFree_emitter().getRfc()+"_"+free_cfdi.getFree_receiver().getRfc()+
            "_" + free_cfdi.getDate_expedition().toLocalDate().toString()+".pdf";
        String filexml = root + free_cfdi.getFree_emitter().getRfc()+"_"+free_cfdi.getFree_receiver().getRfc()+
            "_" + free_cfdi.getDate_expedition().toLocalDate().toString()+".xml";
        log.debug("Fichero PDF: {}", filepdf);
        log.debug("Fichero XML: {}", filexml);

        //Creando PDF
        try{
            OutputStream outputStream = null;
            File newFile = new File(filepdf);
            if (!newFile.exists()) {
                boolean res = newFile.createNewFile();
                if (!res){
                    return free_cfdi;
                }
            } else {
                if (newFile.delete()) {
                    File otherfile = new File(filepdf);
                    boolean res = otherfile.createNewFile();
                    if (!res){
                        return free_cfdi;
                    }
                    newFile = otherfile;
                }
            }
            log.debug("Escribiendo pdf");
            outputStream = new FileOutputStream(newFile);
            outputStream.write(obteinPDF(free_cfdi));

        }catch (Exception e){
        }
        try{
            OutputStream outputStream = null;
            File newFile = new File(filexml);

            if (!newFile.exists()) {
                boolean res = newFile.createNewFile();
                if (!res){
                    return free_cfdi;
                }
            } else {
                if (newFile.delete()) {
                    File otherfile = new File(filexml);
                    boolean res = otherfile.createNewFile();
                    if (!res){
                        return free_cfdi;
                    }
                    newFile = otherfile;
                }
            }
            log.debug("Escribiendo xml");
            outputStream = new FileOutputStream(newFile);
            outputStream.write(obteinXML(free_cfdi));

        }catch (Exception e){
        }
        free_cfdi.setPath_cfdi(base);
        return free_cfdi;
    }
    //Se llama al servicio correspondiente
    private byte[] obteinPDF(Free_cfdi free_cfdi){
        byte[] pdf = new byte[1000000];
        pdf[0] = 1;
        return pdf;
    }
    //Se llama al servicio correspondiente
    private byte[] obteinXML(Free_cfdi free_cfdi){
        byte[] xml = new byte[1000000];
        xml[0] = 1;
        return xml;
    }
    public Page<Free_cfdi> findByFree_emitter(Free_emitter free_emitter, Pageable pageable){
        List<Free_cfdi> listaAll = free_cfdiRepository.findAll();
        List<Free_cfdi> result = new ArrayList<>();
        for(int i=0;i<listaAll.size();i++){
            if(listaAll.get(i).getFree_emitter().getRfc().compareTo(free_emitter.getRfc())==0){
                result.add(listaAll.get(i));
            }
        }
        return new PageImpl<Free_cfdi>(result, pageable, result.size());
    }

    public Page<Free_cfdi> findCustom(Integer idFree_cfdi, String folio_fiscal, String rfc_receiver,
                               LocalDate fromDate,LocalDate toDate,Integer idState,String serie,String folio,Free_emitter free_emitter,
                               Pageable pageable){
        Page<Free_cfdi> page = null;
        List<Free_cfdi> all;
        if(idFree_cfdi != 0)
        {
            if(idFree_cfdi != -1) {
                Free_cfdi free_cfdi = free_cfdiRepository.findOne(new Long(idFree_cfdi));
                all = new ArrayList<>();
                all.add(free_cfdi);
                page = new PageImpl<Free_cfdi>(all, pageable, 1);
                return page;
            }else
            {
                return findByFree_emitter(free_emitter,pageable);
            }
        }
        else {
            List<Free_cfdi> listaAll = free_cfdiRepository.findAll();
            List<Free_cfdi> result = new ArrayList<>();
            for(int i=0;i<listaAll.size();i++){
                boolean a = true;
                boolean b = true;
                boolean c = true;
                boolean d = true;
                boolean e = true;
                boolean f = true;
                boolean g = true;
                if(listaAll.get(i).getFree_emitter().getRfc().compareTo(free_emitter.getRfc())!=0){
                    a = false;
                }
                log.debug("folio_fiscal: "+ folio_fiscal + " folio_fiscal cfdi: "+ listaAll.get(i).getFolio_fiscal_orig());
                if(folio_fiscal.compareTo(" ") != 0){
                    if(folio_fiscal.compareTo(listaAll.get(i).getFolio_fiscal_orig())!=0){
                        b = false;
                    }
                }
                log.debug("rfc_receiver: "+ rfc_receiver + " rfc_receiver cfdi: "+ listaAll.get(i).getFree_receiver().getRfc());
                if(rfc_receiver.compareTo(" ") != 0){
                    if(rfc_receiver.compareTo(listaAll.get(i).getFree_receiver().getRfc())!=0){
                        c = false;
                    }
                }
                log.debug("idState: "+ idState + " idState cfdi: "+ listaAll.get(i).getCfdi_states().getId().toString());
                if(idState != 0){
                    if(idState.toString().compareTo(listaAll.get(i).getCfdi_states().getId().toString())!=0){
                        d = false;
                    }
                }
                log.debug("Serie: "+ serie + " Serie cfdi: "+ listaAll.get(i).getSerial());
                if(serie.compareTo(" ") != 0){
                    if(serie.compareTo(listaAll.get(i).getSerial())!=0){
                        e = false;
                    }
                }
                log.debug("Folio: "+ folio + " folio cfdi: "+ listaAll.get(i).getFolio());
                if(folio.compareTo(" ") != 0){
                    if(folio.compareTo(listaAll.get(i).getFolio())!=0){
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
                    LocalDate actual = listaAll.get(i).getDate_expedition().toLocalDate();

                    log.debug("Inicio ajustado: "+ inicio.toString() + " final ajustado: "+ datefinal.toString());
                    log.debug("Fecha Actual: "+ actual.toString());

                    if(inicio.isAfter(actual)){
                        g = false;
                    }
                    if(datefinal.isBefore(actual)){
                        g = false;
                    }
                }
                if(a && b && c && d && e && f && g){
                    result.add(listaAll.get(i));
                }
            }
            page = new PageImpl<Free_cfdi>(result, pageable, result.size());
        }

        return page;
    }

    /**
     *  Get all the free_cfdis.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Free_cfdi> findAll(Pageable pageable) {
        log.debug("Request to get all Free_cfdis");
        Page<Free_cfdi> result = free_cfdiRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one free_cfdi by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Free_cfdi findOne(Long id) {
        log.debug("Request to get Free_cfdi : {}", id);
        Free_cfdi free_cfdi = free_cfdiRepository.findOne(id);
        return free_cfdi;
    }

    /**
     *  Delete the  free_cfdi by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Free_cfdi : {}", id);
        free_cfdiRepository.delete(id);
    }
}
