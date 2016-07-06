package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.domain.User;
import org.megapractical.billingplatform.service.Config_pathrootfileService;
import org.megapractical.billingplatform.service.Free_emitterService;
import org.megapractical.billingplatform.domain.Free_emitter;
import org.megapractical.billingplatform.repository.Free_emitterRepository;
import org.megapractical.billingplatform.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.IOUtils;
import sun.nio.ch.IOUtil;

import javax.inject.Inject;
import java.io.*;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Free_emitter.
 */
@Service
@Transactional
public class Free_emitterServiceImpl implements Free_emitterService{

    private final Logger log = LoggerFactory.getLogger(Free_emitterServiceImpl.class);

    private final String root = "D:\\PrjMng\\Java MegaPractical\\Upload\\";

    @Inject
    private Free_emitterRepository free_emitterRepository;


    @Inject
    private UserService userService;
    /**
     * Save a free_emitter.
     *
     * @param free_emitter the entity to save
     * @return the persisted entity
     */
    public Free_emitter save(Free_emitter free_emitter) {
        log.debug("Request to save Free_emitter (Service) : {}", free_emitter);
        byte[] certificate = null;
        byte[] key = null;
        byte[] logo = null;
        if(free_emitter != null){
            certificate = free_emitter.getFilecertificate();
            key = free_emitter.getFilekey();
            logo = free_emitter.getFilelogo();
        }
        free_emitter = saveFile(free_emitter);
        log.debug("Save File in Service.save  : {}", free_emitter);
        Free_emitter result = free_emitterRepository.save(free_emitter);
        if (result != null){
            result.setFilecertificate(certificate);
            result.setFilekey(key);
            result.setFilelogo(logo);
        }
        log.debug("Resultado de salvar en service : {}", free_emitter);
        return result;
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

    public Free_emitter saveFile(Free_emitter free_emitter){
        String rootDirectory = root + free_emitter.getRfc();
        log.debug("Salvando ficheros en : {}", rootDirectory);

        if(free_emitter.getFilecertificate() != null){

            try{
                OutputStream outputStream = null;
                File newFile = new File(rootDirectory + "_certificate");
                free_emitter.setPath_certificate(rootDirectory + "_certificate");
                if (!newFile.exists()) {
                    newFile.createNewFile();
                }
                else
                {
                    if(newFile.delete())
                    {
                        File otherfile = new File(rootDirectory + "_certificate");
                        otherfile.createNewFile();
                        newFile = otherfile;
                    }
                }
                outputStream = new FileOutputStream(newFile);
                outputStream.write(free_emitter.getFilecertificate());
                free_emitter.setFilecertificate(null);
            }catch (Exception e){

            }
        }
        if(free_emitter.getFilekey() != null){

            try{
                OutputStream outputStream = null;
                File newFile = new File(rootDirectory + "_key");
                free_emitter.setPath_key(rootDirectory + "_key");
                if (!newFile.exists()) {

                    newFile.createNewFile();
                }
                else
                {
                    if(newFile.delete())
                    {
                        File otherfile = new File(rootDirectory + "_key");
                        otherfile.createNewFile();
                        newFile = otherfile;
                    }
                }
                outputStream = new FileOutputStream(newFile);
                outputStream.write(free_emitter.getFilekey());
                free_emitter.setFilekey(null);
            }catch (Exception e){

            }
        }

        if(free_emitter.getFilelogo() != null){

            try{
                OutputStream outputStream = null;
                File newFile = new File(rootDirectory + "_logo");
                free_emitter.setPath_logo(rootDirectory + "_logo");
                if (!newFile.exists()) {

                    newFile.createNewFile();
                }
                else
                {
                    if(newFile.delete())
                    {
                        File otherfile = new File(rootDirectory + "_logo");
                        otherfile.createNewFile();
                        newFile = otherfile;
                    }
                }
                outputStream = new FileOutputStream(newFile);
                outputStream.write(free_emitter.getFilelogo());
                free_emitter.setFilelogo(null);
            }catch (Exception e){

            }
        }
        return free_emitter;
    }

    public Free_emitter getFile(Free_emitter free_emitter){

        log.debug("Leyendo ficheros : ", free_emitter);

        File newFile = new File(free_emitter.getPath_certificate());
        InputStream inputStream = null;

        if(newFile.exists()){
            try {
                inputStream = new FileInputStream(newFile);
                free_emitter.setFilecertificate(IOUtils.readFully(inputStream,1000000,true));

            }catch (Exception e){

            }
        }
        log.debug("File certificate : {}", free_emitter.getFilecertificate());

        File newFilekey = new File(free_emitter.getPath_key());
        InputStream inputStreamkey = null;

        if(newFilekey.exists()){
            try {
                inputStreamkey = new FileInputStream(newFilekey);
                free_emitter.setFilekey(IOUtils.readFully(inputStreamkey, 1000000, true));

            }catch (Exception e){

            }
        }
        log.debug("File key : {}", free_emitter.getFilekey());

        File newFilelogo = new File(free_emitter.getPath_logo());
        InputStream inputStreamlogo = null;

        if(newFilelogo.exists()){
            try {
                inputStreamlogo = new FileInputStream(newFilelogo);
                free_emitter.setFilelogo(IOUtils.readFully(inputStreamlogo,1000000,true));

            }catch (Exception e){

            }
        }
        log.debug("File logo : {}", free_emitter.getFilelogo());
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
