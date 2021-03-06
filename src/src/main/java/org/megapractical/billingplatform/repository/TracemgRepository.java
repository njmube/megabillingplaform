package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.Tracemg;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Spring Data JPA repository for the Tracemg entity.
 */
public interface TracemgRepository extends JpaRepository<Tracemg,Long> {
    Page<Tracemg> findByTimestampBetweenOrderByIdDesc(ZonedDateTime from, ZonedDateTime to, Pageable pageable);
    //principal
    Page<Tracemg> findByPrincipalAndTimestampBetweenOrderByIdDesc(String principal, ZonedDateTime from, ZonedDateTime to, Pageable pageable);
    List<Tracemg> findByPrincipalOrderByIdDesc(String principal);
    //ip
    Page<Tracemg> findByIpAndTimestampBetweenOrderByIdDesc(String ip, ZonedDateTime from, ZonedDateTime to, Pageable pageable);

    List<Tracemg> findByIpAndTimestampBetweenOrderByIdDesc(String ip, ZonedDateTime from, ZonedDateTime to);
    //ip and principal
    Page<Tracemg> findByIpAndPrincipalAndTimestampBetweenOrderByIdDesc(String ip,String principal, ZonedDateTime from, ZonedDateTime to, Pageable pageable);

}
