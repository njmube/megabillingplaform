package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.domain.C_state_event;
import org.megapractical.billingplatform.domain.Config_pathrootfile;
import org.megapractical.billingplatform.domain.User;
import org.megapractical.billingplatform.security.SecurityUtils;
import org.megapractical.billingplatform.service.*;
import org.megapractical.billingplatform.domain.Free_emitter;
import org.megapractical.billingplatform.repository.Free_emitterRepository;
import org.megapractical.utils.jutils.UCertificate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.apache.commons.io.IOUtils;

import javax.inject.Inject;
import java.io.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Free_emitter.
 */
@Service
@Transactional
public class Free_emitterServiceImpl implements Free_emitterService{

    private final Logger log = LoggerFactory.getLogger(Free_emitterServiceImpl.class);

    @Inject
    private Config_pathrootfileService config_pathrootfileService;

    @Inject
    private Free_emitterRepository free_emitterRepository;

    @Inject
    private TracemgService tracemgService;

    @Inject
    private UserService userService;


    /**
     * Save a free_emitter.
     *
     * @param free_emitter the entity to save
     * @return the persisted entity
     */
    public Free_emitter save(Free_emitter free_emitter) {

        byte[] certificate = null;
        byte[] key = null;
        byte[] logo = null;
        if(free_emitter != null){
            certificate = free_emitter.getFilecertificate();
            key = free_emitter.getFilekey();
            logo = free_emitter.getFilelogo();

            if(free_emitter.getPass_certificate() != null)
                free_emitter.setPass_certificate(SecurityUtils.Encrip(free_emitter.getPass_certificate()));


        }
        free_emitter = saveFile(free_emitter);

        Free_emitter result = free_emitterRepository.save(free_emitter);

        if (result != null){
            result.setFilecertificate(certificate);
            result.setFilekey(key);
            result.setFilelogo(logo);

        }

        return result;
    }

    public Free_emitter findOneByRfc(String rfc){
        Free_emitter free = free_emitterRepository.findOneByRfc(rfc);

        return free;
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

    public Free_emitter InfoCertificate(Free_emitter free_emitter){
        try {
            Date date = UCertificate.getCertExpirationDate(free_emitter.getFilecertificate());
            Date created = UCertificate.getCertCreationDate(free_emitter.getFilecertificate());
            log.debug("Expiracion del certificado " + date.toString());
            log.debug("Creacion del certificado " + created.toString());

            LocalDate expirate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate createddate = created.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            free_emitter.setNumber_certificate(UCertificate.getCertNumber(free_emitter.getFilecertificate()));
            free_emitter.setDate_certificate(expirate);
            free_emitter.setRfc_certificate(UCertificate.getCertPersonRFC(free_emitter.getFilecertificate()));
            free_emitter.setBussines_name_cert(UCertificate.getCertPersonName(free_emitter.getFilecertificate()));
            free_emitter.setDate_created_cert(createddate.toString());
            free_emitter.setDate_expiration_cert(expirate.toString());
            free_emitter.setValid_days_cert(UCertificate.getCertValidDay(free_emitter.getFilecertificate()).toString());
        }catch (Exception ex){

        }
        return free_emitter;
    }

    /**
     *  Get all the free_emitters.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Free_emitter> findAll(Pageable pageable) {
        log.debug("Request to get all Free_emitters");
        Page<Free_emitter> result = free_emitterRepository.findAll(pageable);
        return result;
    }

    private LocalDate convertDate(Date date){
        String datestring = date.toString();
        String [] cadenas = datestring.split(" ");
        if(cadenas.length == 6){
            int year = Integer.parseInt(cadenas[5]);
            int day = Integer.parseInt(cadenas[2]);
            //DateTimeFormatter format = DateT
        }else
            return LocalDate.MIN;
        return LocalDate.now();
    }

    public Free_emitter saveFile(Free_emitter free_emitter){

        String root = "";
        String rootlogo = "";
        List<Config_pathrootfile> list = config_pathrootfileService.finAll();
        if (list.size()>0){
            Config_pathrootfile config = list.get(0);
            root =config.getPathrootFreeCertificate();
            rootlogo= config.getPathrootFreeLogo();
        }

        String rootDirectory = root + free_emitter.getRfc();
        String rootDirectoryLogo = rootlogo + free_emitter.getRfc();

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

        String directoriologo = rootDirectoryLogo;
        if(rootDirectoryLogo.contains("\\"))
            directoriologo+= "\\logo";
        else
            directoriologo+= "/logo";

        File file1 = new File(directoriologo);
        if(!file1.isDirectory()){
            file1.mkdirs();
        }

        String rootdirectoryCerFile = directoriocerfile;
        String rootdirectoryCerKey = directoriocerkey;
        String rootdirectoryLogo = directoriologo;
        if(rootDirectory.contains("\\")){
            directoriocerfile+= "\\";
            directoriocerkey+= "\\";
        }
        else{

            directoriocerfile+= "/";
            directoriocerkey+= "/";
        }

        if(rootDirectoryLogo.contains("\\"))
            directoriologo+= "\\";
        else
            directoriologo+= "/";

        boolean actualizaCer = true;
        if(free_emitter.getPath_certificate()!= null) {
            if (free_emitter.getPath_certificate().contains("\\") || free_emitter.getPath_certificate().contains("/"))
                actualizaCer = false;
        }
        if(free_emitter.getFilecertificateContentType() != null && actualizaCer){
            try{
                log.debug("Directorio base de file: {}",rootdirectoryCerFile);

                OutputStream outputStream = null;
                File newFile = new File(directoriocerfile + free_emitter.getPath_certificate());
                free_emitter.setPath_certificate(directoriocerfile + free_emitter.getPath_certificate());
                if (!newFile.exists()) {
                        newFile.createNewFile();
                }
                outputStream = new FileOutputStream(newFile);
                outputStream.write(free_emitter.getFilecertificate());
                free_emitter.setFilecertificate(null);
                //Se setean los datos del certificado a free_emitter
                log.debug("Sacando info del certificado");
                free_emitter.setValid_certificate(UCertificate.isEnabled(newFile));
                Date date = UCertificate.getCertExpirationDate(newFile);
                Date created = UCertificate.getCertCreationDate(newFile);
                log.debug("Expiracion del certificado " + date.toString());
                log.debug("Creacion del certificado " + created.toString());

                LocalDate expirate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate createddate = created.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                free_emitter.setDate_certificate(expirate);
                free_emitter.setRfc_certificate(UCertificate.getCertPersonRFC(newFile));
                free_emitter.setBussines_name_cert(UCertificate.getCertPersonName(newFile));
                free_emitter.setDate_created_cert(createddate.toString());
                free_emitter.setDate_expiration_cert(expirate.toString());
                free_emitter.setValid_days_cert(UCertificate.getCertValidDay(newFile).toString());

                free_emitter.setInfo_certificate(null);

            }catch (Exception e){
                log.debug("Exception de salvar fichero {}", e.toString());
            }
        }else {
            try{
                if(free_emitter.getFilecertificateContentType() == null) {
                    if (free_emitter.getPath_certificate() != null) {
                        if (!free_emitter.getPath_certificate().isEmpty()) {
                            free_emitter.setPath_certificate(null);
                            free_emitter.setValid_certificate(false);
                            free_emitter.setDate_certificate(null);
                            free_emitter.setInfo_certificate(null);
                            free_emitter.setRfc_certificate(null);
                            free_emitter.setBussines_name_cert(null);
                            free_emitter.setDate_created_cert(null);
                            free_emitter.setDate_expiration_cert(null);
                            free_emitter.setValid_days_cert(null);
                        }
                    }
                }
            }catch (Exception e){

            }
        }
        boolean actualizaKey = true;
        if(free_emitter.getPath_key()!= null) {
            if (free_emitter.getPath_key().contains("\\") || free_emitter.getPath_key().contains("/"))
                actualizaKey = false;
        }
        if(free_emitter.getFilekeyContentType()!= null && actualizaKey){

            try{
                    OutputStream outputStream = null;
                    File newFile = new File(directoriocerkey + free_emitter.getPath_key());
                    free_emitter.setPath_key(directoriocerkey + free_emitter.getPath_key());
                    if (!newFile.exists()) {
                        newFile.createNewFile();
                    }
                    outputStream = new FileOutputStream(newFile);
                    outputStream.write(free_emitter.getFilekey());
                    free_emitter.setFilekey(null);

            }catch (Exception e){

            }
        }else {
            try{
                if(free_emitter.getFilekeyContentType()== null) {
                    if (free_emitter.getPath_key() != null) {
                        if (!free_emitter.getPath_key().isEmpty()) {
                            free_emitter.setPath_key(null);
                        }
                    }
                }
            }catch (Exception e){

            }
        }

        boolean actualizaLogo = true;
        if(free_emitter.getPath_logo()!= null) {
            if (free_emitter.getPath_logo().contains("\\") || free_emitter.getPath_logo().contains("/"))
                actualizaLogo = false;
        }
        if(free_emitter.getFilelogoContentType() != null && actualizaLogo){

            try{
                    OutputStream outputStream = null;
                    File newFile = new File(directoriologo + free_emitter.getPath_logo());
                    free_emitter.setPath_logo(directoriologo + free_emitter.getPath_logo());
                    if (!newFile.exists()) {
                        newFile.createNewFile();
                    }
                    outputStream = new FileOutputStream(newFile);
                    outputStream.write(free_emitter.getFilelogo());
                    free_emitter.setFilelogo(null);

            }catch (Exception e){

            }
        }
        else {
            try{
                if(free_emitter.getFilelogoContentType() == null) {
                    if (free_emitter.getPath_logo() != null) {
                        if (!free_emitter.getPath_logo().isEmpty()) {
                            free_emitter.setPath_logo(null);
                        }
                    }
                }
            }catch (Exception e){

            }
        }
        return free_emitter;
    }

    private boolean deleteChildren(File dir) {
        boolean childrenDeleted = true;
        File[] ficheros =dir.listFiles();
        File f=null;
        if(dir.exists())
        {
            for (int x=0;x<ficheros.length;x++)
            {
                f= new File(ficheros[x].toString());
                if(!f.delete())
                   return false;
            }
        }

        return childrenDeleted;
    }

    public Free_emitter getFile(Free_emitter free_emitter){

        log.debug("Leyendo ficheros : ", free_emitter);

        if(free_emitter.getPath_certificate() != null){
            if(!free_emitter.getPath_certificate().isEmpty()) {

                File newFile = new File(free_emitter.getPath_certificate());
                InputStream inputStream = null;

                if (newFile.exists()) {
                    try {
                        inputStream = new FileInputStream(newFile);
                        free_emitter.setFilecertificate(IOUtils.toByteArray(inputStream));

                    } catch (Exception e) {

                    }
                }
                log.debug("File certificate : {}", free_emitter.getFilecertificate());
            }
        }

        if(free_emitter.getPath_key() != null) {
            if (!free_emitter.getPath_key().isEmpty()) {

                File newFilekey = new File(free_emitter.getPath_key());
                InputStream inputStreamkey = null;

                if (newFilekey.exists()) {
                    try {
                        inputStreamkey = new FileInputStream(newFilekey);
                        free_emitter.setFilekey(IOUtils.toByteArray(inputStreamkey));

                    } catch (Exception e) {

                    }
                }
                log.debug("File key : {}", free_emitter.getFilekey());
            }
        }

        if(free_emitter.getPath_logo() != null) {
            if (!free_emitter.getPath_logo().isEmpty()) {

                File newFilelogo = new File(free_emitter.getPath_logo());
                InputStream inputStreamlogo = null;

                if (newFilelogo.exists()) {
                    try {
                        inputStreamlogo = new FileInputStream(newFilelogo);
                        free_emitter.setFilelogo(IOUtils.toByteArray(inputStreamlogo));

                    } catch (Exception e) {

                    }
                }
                log.debug("File logo : {}", free_emitter.getFilelogo());
            }
        }
        return free_emitter;
    }

    /**
     *  Get one free_emitter by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Free_emitter findOne(Long id) {
        log.debug("Request to get Free_emitter : {}", id);
        Free_emitter free_emitter = free_emitterRepository.findOne(id);
        free_emitter = getFile(free_emitter);

        return free_emitter;
    }

    /**
     *  Delete the  free_emitter by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Free_emitter : {}", id);
        free_emitterRepository.delete(id);
    }

    @Override
    public Free_emitter findOneByUser(User user) {
        Free_emitter free_emitter = free_emitterRepository.findOneByUser(user);
        if(free_emitter != null) {

            return getFile(free_emitter);
        }
        return  free_emitter;
    }
}
