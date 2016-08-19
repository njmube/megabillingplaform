package org.megapractical.billingplatform.service;

import org.joda.time.DateTime;
import org.megapractical.billingplatform.domain.Authority;
import org.megapractical.billingplatform.domain.General_data;
import org.megapractical.billingplatform.domain.PersistentToken;
import org.megapractical.billingplatform.domain.User;
import org.megapractical.billingplatform.repository.AuthorityRepository;
import org.megapractical.billingplatform.repository.PersistentTokenRepository;
import org.megapractical.billingplatform.repository.UserRepository;
import org.megapractical.billingplatform.security.SecurityUtils;
import org.megapractical.billingplatform.service.util.RandomUtil;
import org.megapractical.billingplatform.web.rest.dto.ManagedUserDTO;

import java.io.*;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.io.IOUtils;

import java.time.ZonedDateTime;
import javax.inject.Inject;
import java.util.*;

/**
 * Service class for managing users.
 */
@Service
@Transactional
public class UserService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);


    @Inject
    private PasswordEncoder passwordEncoder;

    @Inject
    private UserRepository userRepository;


    @Inject
    private PersistentTokenRepository persistentTokenRepository;

    @Inject
    private AuthorityRepository authorityRepository;

    @Inject
    private General_dataService general_dataService;

    public Optional<User> activateRegistration(String key) {
        log.debug("Activating user for activation key {}", key);
        return userRepository.findOneByActivationKey(key)
            .map(user -> {
                // activate given user for the registration key.
                user.setActivated(true);
                user.setActivationKey(null);
                user = saveFile(user);
                userRepository.save(user);
                log.debug("Activated user: {}", user);
                user = getFile(user);
                return user;
            });
    }

    public Optional<User> completePasswordReset(String newPassword, String key) {
       log.debug("Reset user password for reset key {}", key);

       return userRepository.findOneByResetKey(key)
            .filter(user -> {
                ZonedDateTime oneDayAgo = ZonedDateTime.now().minusHours(24);
                return user.getResetDate().isAfter(oneDayAgo);
            })
           .map(user -> {
               user.setPassword(passwordEncoder.encode(newPassword));
               user.setResetKey(null);
               user.setResetDate(null);
               user.setActivated(true);
               user = saveFile(user);
               userRepository.save(user);
               user = getFile(user);
               return user;
           });
    }

    public Optional<User> requestPasswordReset(String mail) {
        return userRepository.findOneByEmail(mail)
            .filter(User::getActivated)
            .map(user -> {
                user.setResetKey(RandomUtil.generateResetKey());
                user.setResetDate(ZonedDateTime.now());
                user.setActivated(true);
                user = saveFile(user);
                userRepository.save(user);
                user = getFile(user);
                return user;
            });
    }

    public User createUserInformation(String login, String rfc, String password, String name,String firtsuname,String secondsurname,
                                      String email, String phone,
                                      String gender, String langKey, String creator,byte[]filephoto,
                                      String filephotoContentType, String path_photo) {

        User newUser = new User();
        Authority authority = authorityRepository.findOne("ROLE_USER");
        Set<Authority> authorities = new HashSet<>();
        String encryptedPassword = passwordEncoder.encode(password);
        newUser.setLogin(login);
        newUser.setRFC(rfc);
        // new user gets initially a generated password
        newUser.setPassword(encryptedPassword);
        newUser.setName(name);
        newUser.setFirtsurname(firtsuname);
        newUser.setSecondsurname(secondsurname);
        newUser.setEmail(email);
        newUser.setPhone(phone);
        newUser.setGender(gender);
        newUser.setLangKey(langKey);
        newUser.setCreator(creator);
        newUser.setFilephoto(filephoto);
        newUser.setFilephotoContentType(filephotoContentType);
        newUser.setPath_photo(path_photo);
        // new user is not active
        newUser.setActivated(false);
        // new user gets registration key
        newUser.setActivationKey(RandomUtil.generateActivationKey());
        authorities.add(authority);
        newUser.setAuthorities(authorities);
        newUser = saveFile(newUser);
        userRepository.save(newUser);
        newUser = getFile(newUser);
        log.debug("Created Information for User: {}", newUser);
        return newUser;
    }

    public User createUser(ManagedUserDTO managedUserDTO) {
        User user = new User();
        user.setLogin(managedUserDTO.getLogin());
        user.setRFC(managedUserDTO.getRFC());
        user.setName(managedUserDTO.getName());
        user.setFirtsurname(managedUserDTO.getFirtsurname());
        user.setSecondsurname(managedUserDTO.getSecondsurname());
        user.setEmail(managedUserDTO.getEmail());
        user.setPhone(managedUserDTO.getPhone());
        user.setGender(managedUserDTO.getGender());
        user.setCreator(managedUserDTO.getCreator());
        user.setFilephoto(managedUserDTO.getFilephoto());
        user.setFilephotoContentType(managedUserDTO.getFilephotoContentType());
        user.setPath_photo(managedUserDTO.getPath_photo());
        if (managedUserDTO.getLangKey() == null) {
            user.setLangKey("en"); // default language
        } else {
            user.setLangKey(managedUserDTO.getLangKey());
        }
        if (managedUserDTO.getAuthorities() != null) {
            Set<Authority> authorities = new HashSet<>();
            managedUserDTO.getAuthorities().stream().forEach(
                authority -> authorities.add(authorityRepository.findOne(authority))
            );
            user.setAuthorities(authorities);
        }
        String encryptedPassword = passwordEncoder.encode(RandomUtil.generatePassword());
        user.setPassword(encryptedPassword);
        user.setResetKey(RandomUtil.generateResetKey());
        user.setResetDate(ZonedDateTime.now());
        user.setActivated(managedUserDTO.isActivated());
        user = saveFile(user);
        userRepository.save(user);
        user = getFile(user);
        log.debug("Created Information for User: {}", user);
        return user;
    }

    public void updateUserInformation(String rfc, String name, String firtsurname, String secondsurname,
                                      String email, String phone, String gender, String langKey, String creator,
                                      byte[]filephoto, String filephotoContentType, String path_photo) {
        userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).ifPresent(u -> {
            u.setRFC(rfc);
            u.setName(name);
            u.setFirtsurname(firtsurname);
            u.setSecondsurname(secondsurname);
            u.setEmail(email);
            u.setPhone(phone);
            u.setGender(gender);
            u.setLangKey(langKey);
            u.setCreator(creator);
            u.setFilephoto(filephoto);
            u.setFilephotoContentType(filephotoContentType);
            u.setPath_photo(path_photo);
            u = saveFile(u);

            userRepository.save(u);
            log.debug("Changed Information for User: {}", u);
        });
    }

    public User saveFile(User user){

        String root = "";
        String rootlogo = "";
        List<General_data> list = general_dataService.findAll();
        if (list.size()>0){
            General_data config = list.get(0);
            root =config.getPath_root();
        }

        String rootDirectory = root + user.getLogin();

        String directoriophotofile = rootDirectory;
        if(rootDirectory.contains("\\")) {
            directoriophotofile += "\\";
        }
        else {
            directoriophotofile += "/";
        }

        File file = new File(directoriophotofile);
        if(!file.isDirectory()){
            file.mkdirs();
        }

        boolean actualizaPhoto = true;
        if(user.getPath_photo()!= null) {
            if (user.getPath_photo().contains("\\") || user.getPath_photo().contains("/"))
                actualizaPhoto = false;
        }

        if(user.getFilephotoContentType() != null && actualizaPhoto){

            try{
                OutputStream outputStream = null;
                File newFile = new File(directoriophotofile + user.getPath_photo());
                user.setPath_photo(directoriophotofile + user.getPath_photo());
                if (!newFile.exists()) {
                    newFile.createNewFile();
                }
                outputStream = new FileOutputStream(newFile);
                outputStream.write(user.getFilephoto());
                user.setFilephoto(null);

            }catch (Exception e){

            }
        }
        else {
            try{
                if(user.getFilephotoContentType() == null) {
                    if (user.getPath_photo() != null) {
                        if (!user.getPath_photo().isEmpty()) {
                            user.setPath_photo(null);
                        }
                    }
                }
            }catch (Exception e){

            }
        }
        return user;
    }

    public User getFile(User user){

        log.debug("Leyendo fichero del user en: {}", user.getPath_photo());

        String root = "";
        List<General_data> list = general_dataService.findAll();
        if (list.size()>0){
            General_data config = list.get(0);
            root =config.getPath_root();
        }

        String rootDirectory = root + "default_photo.png";

        if(user.getPath_photo() != null){
            if (!user.getPath_photo().isEmpty()) {

                File newFile = new File(user.getPath_photo());
                InputStream inputStream = null;

                if (newFile.exists()) {
                    log.debug("Existe el fichero");
                    try {
                        inputStream = new FileInputStream(newFile);
                        user.setFilephoto(IOUtils.toByteArray(inputStream));
                    } catch (Exception e) {
                        log.debug("Exception de lectura: " + e.toString());
                    }
                }
                else {
                    File newFile1 = new File(rootDirectory);
                    InputStream inputStream1 = null;

                    if (newFile.exists()) {
                        log.debug("Existe el fichero");
                        try {
                            inputStream1 = new FileInputStream(newFile1);
                            user.setFilephoto(IOUtils.toByteArray(inputStream1));
                            user.setFilephotoContentType("png");
                        } catch (Exception e) {
                            log.debug("Exception de lectura: " + e.toString());
                        }
                    }
                }
                log.debug("File photo : {}", user.getFilephoto());
            }
            else {
                File newFile = new File(rootDirectory);
                InputStream inputStream = null;

                if (newFile.exists()) {
                    log.debug("Existe el fichero");
                    try {
                        inputStream = new FileInputStream(newFile);
                        user.setFilephoto(IOUtils.toByteArray(inputStream));
                        user.setFilephotoContentType("png");
                    } catch (Exception e) {
                        log.debug("Exception de lectura: " + e.toString());
                    }
                }
            }
        }
        else{
            File newFile = new File(rootDirectory);
            InputStream inputStream = null;

            if (newFile.exists()) {
                log.debug("Existe el fichero");
                try {
                    inputStream = new FileInputStream(newFile);
                    user.setFilephoto(IOUtils.toByteArray(inputStream));
                    user.setFilephotoContentType("png");
                } catch (Exception e) {
                    log.debug("Exception de lectura: " + e.toString());
                }
            }
        }

        return user;
    }

    public void deleteUserInformation(String login) {
        userRepository.findOneByLogin(login).ifPresent(u -> {
            userRepository.delete(u);
            log.debug("Deleted User: {}", u);
        });
    }

    public boolean changePassword(String password) {
        String [] temp = password.split("   ");
        if(temp.length == 2){
            Optional<User> user1 = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin());
            String encry = user1.get().getPassword();

            boolean result = passwordEncoder.matches(temp[0],encry);
            if(!result){
                return false;
            }

            userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).ifPresent(u -> {
                String encryptedPassword = passwordEncoder.encode(temp[1]);
                u.setPassword(encryptedPassword);
                u = saveFile(u);
                userRepository.save(u);

                log.debug("Changed password for User: {}", u);
            });
            return true;
        }
        else
            return false;
    }

    public void DeletePersistenTokenByUser(User user){
        List<PersistentToken> list = persistentTokenRepository.findByUser(user);
        persistentTokenRepository.delete(list);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthoritiesByLogin(String login) {
        return userRepository.findOneByLogin(login).map(u -> {
            u.getAuthorities().size();
            u = getFile(u);
            //log.debug("Este es el user: ", u);
            return u;
        });
    }

    @Transactional(readOnly = true)
    public User getUserWithAuthorities(Long id) {
        User user = userRepository.findOne(id);
        user.getAuthorities().size(); // eagerly load the association
        user = getFile(user);
        return user;
    }

    @Transactional(readOnly = true)
    public User getUserWithAuthorities() {
        User user = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get();
        user.getAuthorities().size(); // eagerly load the association
        user = getFile(user);
        return user;
    }

    /**
     * Persistent Token are used for providing automatic authentication, they should be automatically deleted after
     * 30 days.
     * <p>
     * This is scheduled to get fired everyday, at midnight.
     * </p>
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void removeOldPersistentTokens() {
        LocalDate now = LocalDate.now();
        persistentTokenRepository.findByTokenDateBefore(now.minusMonths(1)).stream().forEach(token -> {
            log.debug("Deleting token {}", token.getSeries());
            User user = token.getUser();
            user.getPersistentTokens().remove(token);
            persistentTokenRepository.delete(token);
        });
    }

    /**
     * Not activated users should be automatically deleted after 7 days.
     * <p>
     * This is scheduled to get fired everyday, at 01:00 (am).
     * </p>
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void removeNotActivatedUsers() {
        ZonedDateTime now = ZonedDateTime.now();
        List<User> users = userRepository.findAllByActivatedIsFalseAndCreatedDateBefore(now.minusDays(7));
        for (User user : users) {
            log.debug("Deleting not activated user {}", user.getLogin());
            userRepository.delete(user);
        }
    }
}
