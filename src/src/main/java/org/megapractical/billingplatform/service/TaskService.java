package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Task.
 */
public interface TaskService {

    /**
     * Save a task.
     *
     * @param task the entity to save
     * @return the persisted entity
     */
    Task save(Task task);

    /**
     *  Get all the tasks.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Task> findAll(Pageable pageable);

    Page<Task> findAllByName(String filtername, Pageable pageable);

    /**
     *  Get the "id" task.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Task findOne(Long id);

    /**
     *  Delete the "id" task.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
