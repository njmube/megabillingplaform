package org.megapractical.billingplatform.service.impl;

import org.apache.commons.io.IOUtils;
import org.megapractical.billingplatform.domain.Config_pathrootfile;
import org.megapractical.billingplatform.domain.User;
import org.megapractical.billingplatform.service.Config_pathrootfileService;
import org.megapractical.billingplatform.service.Taxpayer_accountService;
import org.megapractical.billingplatform.domain.Taxpayer_account;
import org.megapractical.billingplatform.repository.Taxpayer_accountRepository;
import org.megapractical.utils.jutils.UCertificate;
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
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Service Implementation for managing Taxpayer_account.
 */
@Service
@Transactional
public class Taxpayer_accountServiceImpl implements Taxpayer_accountService{

    private final Logger log = LoggerFactory.getLogger(Taxpayer_accountServiceImpl.class);

    @Inject
    private Taxpayer_accountRepository taxpayer_accountRepository;

    @Inject
    private Config_pathrootfileService config_pathrootfileService;

    /**
     * Save a taxpayer_account.
     *
     * @param taxpayer_account the entity to save
     * @return the persisted entity
     */
    public Taxpayer_account save(Taxpayer_account taxpayer_account) {
        log.debug("Request to save Taxpayer_account : {}", taxpayer_account);
        taxpayer_account = saveLogo(taxpayer_account);
        Taxpayer_account result = taxpayer_accountRepository.save(taxpayer_account);
        if(result != null)
            result = getLogo(result);
        return result;
    }

    public Page<Taxpayer_account> findCustom(User user,Pageable pageable){
        Page<Taxpayer_account> result = taxpayer_accountRepository.findAll(pageable);
        List<Taxpayer_account> list = new ArrayList<>();
        for(Taxpayer_account taxpayer_account: result.getContent()){
            boolean existe = false;
            for (User item : taxpayer_account.getUsers()) {
                if (item.getLogin().compareTo(user.getLogin()) == 0) {
                    existe = true;
                }
            }
            if(existe){
                list.add(taxpayer_account);
            }
        }
        Page<Taxpayer_account> page = new PageImpl<Taxpayer_account>(list,pageable,result.getTotalElements());
        return page;
    }

    public List<Taxpayer_account> findCustomList(User user){
        List<Taxpayer_account> result = taxpayer_accountRepository.findAll();
        List<Taxpayer_account> list = new ArrayList<>();
        for(Taxpayer_account taxpayer_account: result){
            boolean existe = false;
            for (User item : taxpayer_account.getUsers()) {
                if (item.getLogin().compareTo(user.getLogin()) == 0) {
                    existe = true;
                }
            }
            if(existe){
                list.add(taxpayer_account);
            }
        }

        return list;
    }

    public Taxpayer_account saveLogo(Taxpayer_account taxpayer_account){

        String root = "";
        List<Config_pathrootfile> list = config_pathrootfileService.finAll();
        if (list.size()>0){
            Config_pathrootfile config = list.get(0);
            root =config.getPathrootLogo();
        }

        String rootDirectory = root + taxpayer_account.getRfc();

        String directoriologofile = rootDirectory;
        if(rootDirectory.contains("\\")) {
            directoriologofile += "\\logo";
        }
        else {
            directoriologofile += "/logo";
        }

        File file = new File(directoriologofile);
        if(!file.isDirectory()){
            file.mkdirs();
        }

        String rootdirectoryLogoFile = directoriologofile;

        if(rootDirectory.contains("\\")){
            directoriologofile+= "\\";
        }
        else{

            directoriologofile+= "/";
        }

        boolean actualizaLogo = true;
        if(taxpayer_account.getPath_logo()!= null) {
            if (taxpayer_account.getPath_logo().contains("\\") || taxpayer_account.getPath_logo().contains("/"))
                actualizaLogo = false;
        }
        if(taxpayer_account.getFile_logoContentType() != null && actualizaLogo){
            try{
                log.debug("Directorio base de file: {}",rootdirectoryLogoFile);

                OutputStream outputStream = null;
                File newFile = new File(directoriologofile + taxpayer_account.getPath_logo());
                taxpayer_account.setPath_logo(directoriologofile + taxpayer_account.getPath_logo());
                if (!newFile.exists()) {
                    newFile.createNewFile();
                }
                outputStream = new FileOutputStream(newFile);
                outputStream.write(taxpayer_account.getFile_logo());
                taxpayer_account.setFile_logo(null);
                //Se setean los datos del logo

            }catch (Exception e){
                log.debug("Exception de salvar fichero {}", e.toString());
            }
        }else {
            try{
                if(taxpayer_account.getFile_logoContentType() == null) {
                    if (taxpayer_account.getPath_logo() != null) {
                        if (!taxpayer_account.getPath_logo().isEmpty()) {
                            taxpayer_account.setPath_logo(null);
                        }
                    }
                }
            }catch (Exception e){

            }
        }


        return taxpayer_account;
    }

    public Taxpayer_account getLogo(Taxpayer_account taxpayer_account){

        log.debug("Leyendo ficheros : {}", taxpayer_account);

        if(taxpayer_account.getPath_logo() != null){
            if(!taxpayer_account.getPath_logo().isEmpty()) {

                File newFile = new File(taxpayer_account.getPath_logo());
                InputStream inputStream = null;

                if (newFile.exists()) {
                    try {
                        inputStream = new FileInputStream(newFile);
                        taxpayer_account.setFile_logo(IOUtils.toByteArray(inputStream));

                    } catch (Exception e) {

                    }
                }
                log.debug("File logo : {}", taxpayer_account.getFile_logo());
            }
        }

        return taxpayer_account;
    }

    /**
     *  Get all the taxpayer_accounts.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Taxpayer_account> findAll(Pageable pageable) {
        log.debug("Request to get all Taxpayer_accounts");
        Page<Taxpayer_account> result = taxpayer_accountRepository.findAll(pageable);
        for(Taxpayer_account taxpayer_account: result){
            taxpayer_account = getLogo(taxpayer_account);
        }
        return result;
    }

    /**
     *  Get one taxpayer_account by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Taxpayer_account findOne(Long id) {
        log.debug("Request to get Taxpayer_account : {}", id);
        Taxpayer_account taxpayer_account = taxpayer_accountRepository.findOneWithEagerRelationships(id);
        if(taxpayer_account != null)
            taxpayer_account = getLogo(taxpayer_account);

        return taxpayer_account;
    }

    /**
     *  Delete the  taxpayer_account by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Taxpayer_account : {}", id);
        taxpayer_accountRepository.delete(id);
    }
}
