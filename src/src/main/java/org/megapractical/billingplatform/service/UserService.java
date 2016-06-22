package org.megapractical.billingplatform.service;

import org.joda.time.DateTime;
import org.megapractical.billingplatform.domain.Authority;
import org.megapractical.billingplatform.domain.PersistentToken;
import org.megapractical.billingplatform.domain.User;
import org.megapractical.billingplatform.repository.AuthorityRepository;
import org.megapractical.billingplatform.repository.PersistentTokenRepository;
import org.megapractical.billingplatform.repository.UserRepository;
import org.megapractical.billingplatform.security.SecurityUtils;
import org.megapractical.billingplatform.service.util.RandomUtil;
import org.megapractical.billingplatform.web.rest.dto.ManagedUserDTO;
import java.time.ZonedDateTime;
import java.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public Optional<User> activateRegistration(String key) {
        log.debug("Activating user for activation key {}", key);
        return userRepository.findOneByActivationKey(key)
            .map(user -> {
                // activate given user for the registration key.
                user.setActivated(true);
                user.setActivationKey(null);
                userRepository.save(user);
                log.debug("Activated user: {}", user);
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
               userRepository.save(user);
               return user;
           });
    }

    public Optional<User> requestPasswordReset(String mail) {
        return userRepository.findOneByEmail(mail)
            .filter(User::getActivated)
            .map(user -> {
                user.setResetKey(RandomUtil.generateResetKey());
                user.setResetDate(ZonedDateTime.now());
                userRepository.save(user);
                return user;
            });
    }

    public String sugestionUserLogin(String name, String firtsurname, String secondsurname){
        String suguser = "";
        ArrayList<String> list = new ArrayList<String>(10);
        boolean find = false;

        //Generacion de posibles users: name, namefs, nfirstsurname, firstsurname, nfsecondsurname, secondsurname
        if(name.isEmpty()|| name == null){
            if(firtsurname.isEmpty() || firtsurname == null){
                if(secondsurname.isEmpty()){
                    list.add("user");
                }else
                {
                    list.add(secondsurname);
                }
            }else
            {
                list.add(firtsurname);
            }
        }else {
            list.add(name);
            if(!firtsurname.isEmpty() && firtsurname != null)
            {
                list.add(name.substring(0,0)+firtsurname);
                list.add(firtsurname);
                if(!secondsurname.isEmpty() && secondsurname != null)
                {
                    list.add(name+firtsurname.substring(0,0)+secondsurname.substring(0,0));
                    list.add(name.substring(0,0)+firtsurname.substring(0,0)+secondsurname);
                    list.add(secondsurname);
                }
            }
        }

        //Verificacion de los user generados

        Optional<User> user;
        for(int i = 0;i < list.size();i++){
            user = userRepository.findOneByLogin(list.get(i));
            if(user.isPresent())
            {
                for(int ii = 1;ii<10000;ii++){
                    String tempsuguser = list.get(i) + ii;
                    user = userRepository.findOneByLogin(tempsuguser);
                    if(!user.isPresent()){
                        list.set(i,tempsuguser);
                        break;
                    }
                }
            }
        }
        if(list.size()>0){
            suguser = list.get(0);
            for(int i = 1;i < list.size();i++){
                suguser = suguser + ", "+list.get(i);
            }
        }
        return suguser;
    }

    public User createUserInformation(String login, String rfc, String password, String name,String firtsuname,String secondsurname,
                                      String email, String phone,
                                      String gender, String langKey) {

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
        // new user is not active
        newUser.setActivated(false);
        // new user gets registration key
        newUser.setActivationKey(RandomUtil.generateActivationKey());
        authorities.add(authority);
        newUser.setAuthorities(authorities);
        userRepository.save(newUser);
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
        user.setActivated(true);
        userRepository.save(user);
        log.debug("Created Information for User: {}", user);
        return user;
    }

    public void updateUserInformation(String rfc, String name, String firtsurname, String secondsurname,
                                      String email, String phone, String gender, String langKey) {
        userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).ifPresent(u -> {
            u.setRFC(rfc);
            u.setName(name);
            u.setFirtsurname(firtsurname);
            u.setSecondsurname(secondsurname);
            u.setEmail(email);
            u.setPhone(phone);
            u.setGender(gender);
            u.setLangKey(langKey);
            userRepository.save(u);
            log.debug("Changed Information for User: {}", u);
        });
    }

    public void deleteUserInformation(String login) {
        userRepository.findOneByLogin(login).ifPresent(u -> {
            userRepository.delete(u);
            log.debug("Deleted User: {}", u);
        });
    }

    public void changePassword(String password) {
        userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).ifPresent(u -> {
            String encryptedPassword = passwordEncoder.encode(password);
            u.setPassword(encryptedPassword);
            userRepository.save(u);
            log.debug("Changed password for User: {}", u);
        });
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthoritiesByLogin(String login) {
        return userRepository.findOneByLogin(login).map(u -> {
            u.getAuthorities().size();
            return u;
        });
    }

    @Transactional(readOnly = true)
    public User getUserWithAuthorities(Long id) {
        User user = userRepository.findOne(id);
        user.getAuthorities().size(); // eagerly load the association
        return user;
    }

    @Transactional(readOnly = true)
    public User getUserWithAuthorities() {
        User user = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get();
        user.getAuthorities().size(); // eagerly load the association
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
