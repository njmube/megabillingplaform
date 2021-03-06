package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.User;

import java.time.ZonedDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the User entity.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findOneByActivationKey(String activationKey);

    List<User> findAllByActivatedIsFalseAndCreatedDateBefore(ZonedDateTime dateTime);

    Optional<User> findOneByResetKey(String resetKey);

    Optional<User> findOneByEmail(String email);

    Optional<User> findOneByLogin(String login);

    Optional<User> findOneByRfc(String rfc);

    Optional<User> findOneById(Long userId);

    Page<User> findByLoginNotLikeAndLoginNotLike(String login1, String login2,Pageable pageable);

    Page<User> findByLoginNotLikeAndLoginNotLikeAndRfcStartingWithAndActivated(String login1, String login2, String filterrfc, boolean activated, Pageable pageable);

    Page<User> findByLoginNotLikeAndLoginNotLikeAndRfcStartingWith(String login1, String login2, String filterrfc, Pageable pageable);

    Page<User> findByLoginNotLikeAndLoginNotLikeAndActivated(String login1, String login2,boolean activated, Pageable pageable);

    @Override
    void delete(User t);

}
