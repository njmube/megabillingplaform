package org.megapractical.billingplatform.service.impl;

import org.apache.commons.io.IOUtils;
import org.megapractical.billingplatform.domain.Config_pathrootfile;
import org.megapractical.billingplatform.security.SecurityUtils;
import org.megapractical.billingplatform.service.Config_pathrootfileService;
import org.megapractical.billingplatform.service.Taxpayer_certificateService;
import org.megapractical.billingplatform.domain.Taxpayer_certificate;
import org.megapractical.billingplatform.repository.Taxpayer_certificateRepository;
import org.megapractical.utils.jutils.UCertificate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.io.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

/**
 * Service Implementation for managing Taxpayer_certificate.
 */
@Service
@Transactional
public class Taxpayer_certificateServiceImpl implements Taxpayer_certificateService{

    private final Logger log = LoggerFactory.getLogger(Taxpayer_certificateServiceImpl.class);

    @Inject
    private Taxpayer_certificateRepository taxpayer_certificateRepository;

    @Inject
    private Config_pathrootfileService config_pathrootfileService;

    /**
     * Save a taxpayer_certificate.
     *
     * @param taxpayer_certificate the entity to save
     * @return the persisted entity
     */
    public Taxpayer_certificate save(Taxpayer_certificate taxpayer_certificate, String rfc) {
        log.debug("Request to save Taxpayer_certificate : {}", taxpayer_certificate);

        taxpayer_certificate = saveFile(taxpayer_certificate, rfc);
        //taxpayer_certificate.setPass_certificate(SecurityUtils.Encrip(taxpayer_certificate.getPass_certificate()));
        Taxpayer_certificate result = taxpayer_certificateRepository.save(taxpayer_certificate);

        return result;
    }

    public String[] validateCertificate(byte[] cert, byte[]key, String pass){
        String[] response = new String[2];
        response[0] = "3";
        response[1] = "Error de validación";

        try{
            response = UCertificate.validate(cert, key, pass);
            if(response[0].compareTo("0")==0){
                response[1] = "El certificado, la llave y la contraseña coinciden.";
            }
            if(response[0].compareTo("1")==0){
                response[1] = "El certificado no se corresponde con la llave.";
            }
            if(response[0].compareTo("2")==0){
                response[1] = "La contraseña no se corresponde con la llave.";
            }
            return response;
        }catch (Exception ex){
            return response;
        }
    }

    public Taxpayer_certificate InfoCertificate(Taxpayer_certificate taxpayer_certificate){
        try {
            Date date = UCertificate.getCertExpirationDate(taxpayer_certificate.getFilecertificate());
            Date created = UCertificate.getCertCreationDate(taxpayer_certificate.getFilecertificate());
            log.debug("Expiracion del certificado " + date.toString());
            log.debug("Creacion del certificado " + created.toString());

            LocalDate expirate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate createddate = created.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            taxpayer_certificate.setNumber_certificate(UCertificate.getCertNumber(taxpayer_certificate.getFilecertificate()));
            taxpayer_certificate.setDate_certificate(expirate);
            taxpayer_certificate.setRfc_certificate(UCertificate.getCertPersonRFC(taxpayer_certificate.getFilecertificate()));
            taxpayer_certificate.setBussines_name_cert(UCertificate.getCertPersonName(taxpayer_certificate.getFilecertificate()));
            taxpayer_certificate.setDate_created_cert(createddate);
            taxpayer_certificate.setDate_expiration_cert(expirate);
            taxpayer_certificate.setValid_days_cert(UCertificate.getCertValidDay(taxpayer_certificate.getFilecertificate()).toString());
        }catch (Exception ex){

        }
        return taxpayer_certificate;
    }

    public Taxpayer_certificate saveFile(Taxpayer_certificate taxpayer_certificate, String rfc){

        String root = "";
        List<Config_pathrootfile> list = config_pathrootfileService.finAll();
        if (list.size()>0){
            Config_pathrootfile config = list.get(0);
            root =config.getPathrootCertificate();
        }

        String rootDirectory = root + rfc;

        String directoriocerfile = rootDirectory;
        String directoriocerkey = rootDirectory;
        if(rootDirectory.contains("\\")) {
            directoriocerfile += "\\certificate\\file";
            directoriocerkey += "\\certificate\\key";
        }
        else {
            directoriocerfile += "/certificate";
            directoriocerkey += "/key";
        }

        File file = new File(directoriocerfile);
        if(!file.isDirectory()){
            file.mkdirs();
        }
        File file2 = new File(directoriocerkey);
        if(!file2.isDirectory()){
            file2.mkdirs();
        }

        String rootdirectoryCerFile = directoriocerfile;
        String rootdirectoryCerKey = directoriocerkey;

        if(rootDirectory.contains("\\")){
            directoriocerfile+= "\\";
            directoriocerkey+= "\\";
        }
        else{

            directoriocerfile+= "/";
            directoriocerkey+= "/";
        }

        boolean actualizaCer = true;
        if(taxpayer_certificate.getPath_certificate()!= null) {
            if (taxpayer_certificate.getPath_certificate().contains("\\") || taxpayer_certificate.getPath_certificate().contains("/"))
                actualizaCer = false;
        }
        if(taxpayer_certificate.getFilecertificateContentType() != null && actualizaCer){
            try{
                log.debug("Directorio base de file: {}",rootdirectoryCerFile);

                OutputStream outputStream = null;
                File newFile = new File(directoriocerfile + taxpayer_certificate.getPath_certificate());
                taxpayer_certificate.setPath_certificate(directoriocerfile + taxpayer_certificate.getPath_certificate());
                if (!newFile.exists()) {
                    newFile.createNewFile();
                }
                outputStream = new FileOutputStream(newFile);
                outputStream.write(taxpayer_certificate.getFilecertificate());
                taxpayer_certificate.setFilecertificate(null);
                //Se setean los datos del certificado a free_emitter
                log.debug("Sacando info del certificado");
                taxpayer_certificate.setValid_certificate(UCertificate.isEnabled(newFile));
                Date date = UCertificate.getCertExpirationDate(newFile);
                Date created = UCertificate.getCertCreationDate(newFile);
                log.debug("Expiracion del certificado " + date.toString());
                log.debug("Creacion del certificado " + created.toString());

                LocalDate expirate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate createddate = created.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                taxpayer_certificate.setDate_certificate(expirate);
                taxpayer_certificate.setRfc_certificate(UCertificate.getCertPersonRFC(newFile));
                taxpayer_certificate.setBussines_name_cert(UCertificate.getCertPersonName(newFile));
                taxpayer_certificate.setDate_created_cert(createddate);
                taxpayer_certificate.setDate_expiration_cert(expirate);
                taxpayer_certificate.setValid_days_cert(UCertificate.getCertValidDay(newFile).toString());

                taxpayer_certificate.setInfo_certificate(null);

            }catch (Exception e){
                log.debug("Exception de salvar fichero {}", e.toString());
            }
        }else {
            try{
                if(taxpayer_certificate.getFilecertificateContentType() == null) {
                    if (taxpayer_certificate.getPath_certificate() != null) {
                        if (!taxpayer_certificate.getPath_certificate().isEmpty()) {
                            taxpayer_certificate.setPath_certificate(null);
                            taxpayer_certificate.setValid_certificate(false);
                            taxpayer_certificate.setDate_certificate(null);
                            taxpayer_certificate.setInfo_certificate(null);
                            taxpayer_certificate.setRfc_certificate(null);
                            taxpayer_certificate.setBussines_name_cert(null);
                            taxpayer_certificate.setDate_created_cert(null);
                            taxpayer_certificate.setDate_expiration_cert(null);
                            taxpayer_certificate.setValid_days_cert(null);
                        }
                    }
                }
            }catch (Exception e){

            }
        }
        boolean actualizaKey = true;
        if(taxpayer_certificate.getPath_key()!= null) {
            if (taxpayer_certificate.getPath_key().contains("\\") || taxpayer_certificate.getPath_key().contains("/"))
                actualizaKey = false;
        }
        if(taxpayer_certificate.getFilekeyContentType()!= null && actualizaKey){

            try{
                OutputStream outputStream = null;
                File newFile = new File(directoriocerkey + taxpayer_certificate.getPath_key());
                taxpayer_certificate.setPath_key(directoriocerkey + taxpayer_certificate.getPath_key());
                if (!newFile.exists()) {
                    newFile.createNewFile();
                }
                outputStream = new FileOutputStream(newFile);
                outputStream.write(taxpayer_certificate.getFilekey());
                taxpayer_certificate.setFilekey(null);

            }catch (Exception e){

            }
        }else {
            try{
                if(taxpayer_certificate.getFilekeyContentType()== null) {
                    if (taxpayer_certificate.getPath_key() != null) {
                        if (!taxpayer_certificate.getPath_key().isEmpty()) {
                            taxpayer_certificate.setPath_key(null);
                        }
                    }
                }
            }catch (Exception e){

            }
        }

        return taxpayer_certificate;
    }

    public Taxpayer_certificate getFile(Taxpayer_certificate taxpayer_certificate){

        log.debug("Leyendo ficheros : {}", taxpayer_certificate);

        if(taxpayer_certificate.getPath_certificate() != null){
            if(!taxpayer_certificate.getPath_certificate().isEmpty()) {

                File newFile = new File(taxpayer_certificate.getPath_certificate());
                InputStream inputStream = null;

                if (newFile.exists()) {
                    try {
                        inputStream = new FileInputStream(newFile);
                        taxpayer_certificate.setFilecertificate(IOUtils.toByteArray(inputStream));

                    } catch (Exception e) {

                    }
                }
                log.debug("File certificate : {}", taxpayer_certificate.getFilecertificate());
            }
        }

        if(taxpayer_certificate.getPath_key() != null) {
            if (!taxpayer_certificate.getPath_key().isEmpty()) {

                File newFilekey = new File(taxpayer_certificate.getPath_key());
                InputStream inputStreamkey = null;

                if (newFilekey.exists()) {
                    try {
                        inputStreamkey = new FileInputStream(newFilekey);
                        taxpayer_certificate.setFilekey(IOUtils.toByteArray(inputStreamkey));

                    } catch (Exception e) {

                    }
                }
                log.debug("File key : {}", taxpayer_certificate.getFilekey());
            }
        }

        return taxpayer_certificate;
    }

    /**
     *  Get all the taxpayer_certificates.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Taxpayer_certificate> findAll(Pageable pageable) {
        log.debug("Request to get all Taxpayer_certificates");
        Page<Taxpayer_certificate> result = taxpayer_certificateRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one taxpayer_certificate by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Taxpayer_certificate findOne(Long id) {
        log.debug("Request to get Taxpayer_certificate : {}", id);
        Taxpayer_certificate taxpayer_certificate = taxpayer_certificateRepository.findOne(id);
        taxpayer_certificate = getFile(taxpayer_certificate);
        return taxpayer_certificate;
    }

    /**
     *  Delete the  taxpayer_certificate by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Taxpayer_certificate : {}", id);
        taxpayer_certificateRepository.delete(id);
    }
}
