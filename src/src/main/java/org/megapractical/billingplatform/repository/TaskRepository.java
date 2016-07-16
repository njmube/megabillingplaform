package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.Task;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Task entity.
 */
public interface TaskRepository extends JpaRepository<Task,Long> {
    Page<Task> findByNameStartingWith(String filtername, Pageable pageable);
}
