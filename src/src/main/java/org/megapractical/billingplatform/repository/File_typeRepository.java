package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.File_type;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the File_type entity.
 */
public interface File_typeRepository extends JpaRepository<File_type,Long> {

}
