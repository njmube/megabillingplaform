package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.File;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the File entity.
 */
@SuppressWarnings("unused")
public interface FileRepository extends JpaRepository<File,Long> {
    Page<File> findByNameStartingWith(String filtername, Pageable pageable);
}
