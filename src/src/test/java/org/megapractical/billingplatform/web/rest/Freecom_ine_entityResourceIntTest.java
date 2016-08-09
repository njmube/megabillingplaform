package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Freecom_ine_entity;
import org.megapractical.billingplatform.repository.Freecom_ine_entityRepository;
import org.megapractical.billingplatform.service.Freecom_ine_entityService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the Freecom_ine_entityResource REST controller.
 *
 * @see Freecom_ine_entityResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Freecom_ine_entityResourceIntTest {


    @Inject
    private Freecom_ine_entityRepository freecom_ine_entityRepository;

    @Inject
    private Freecom_ine_entityService freecom_ine_entityService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFreecom_ine_entityMockMvc;

    private Freecom_ine_entity freecom_ine_entity;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Freecom_ine_entityResource freecom_ine_entityResource = new Freecom_ine_entityResource();
        ReflectionTestUtils.setField(freecom_ine_entityResource, "freecom_ine_entityService", freecom_ine_entityService);
        this.restFreecom_ine_entityMockMvc = MockMvcBuilders.standaloneSetup(freecom_ine_entityResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        freecom_ine_entity = new Freecom_ine_entity();
    }

    @Test
    @Transactional
    public void createFreecom_ine_entity() throws Exception {
        int databaseSizeBeforeCreate = freecom_ine_entityRepository.findAll().size();

        // Create the Freecom_ine_entity

        restFreecom_ine_entityMockMvc.perform(post("/api/freecom-ine-entities")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_ine_entity)))
                .andExpect(status().isCreated());

        // Validate the Freecom_ine_entity in the database
        List<Freecom_ine_entity> freecom_ine_entities = freecom_ine_entityRepository.findAll();
        assertThat(freecom_ine_entities).hasSize(databaseSizeBeforeCreate + 1);
        Freecom_ine_entity testFreecom_ine_entity = freecom_ine_entities.get(freecom_ine_entities.size() - 1);
    }

    @Test
    @Transactional
    public void getAllFreecom_ine_entities() throws Exception {
        // Initialize the database
        freecom_ine_entityRepository.saveAndFlush(freecom_ine_entity);

        // Get all the freecom_ine_entities
        restFreecom_ine_entityMockMvc.perform(get("/api/freecom-ine-entities?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(freecom_ine_entity.getId().intValue())));
    }

    @Test
    @Transactional
    public void getFreecom_ine_entity() throws Exception {
        // Initialize the database
        freecom_ine_entityRepository.saveAndFlush(freecom_ine_entity);

        // Get the freecom_ine_entity
        restFreecom_ine_entityMockMvc.perform(get("/api/freecom-ine-entities/{id}", freecom_ine_entity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(freecom_ine_entity.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingFreecom_ine_entity() throws Exception {
        // Get the freecom_ine_entity
        restFreecom_ine_entityMockMvc.perform(get("/api/freecom-ine-entities/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFreecom_ine_entity() throws Exception {
        // Initialize the database
        freecom_ine_entityService.save(freecom_ine_entity);

        int databaseSizeBeforeUpdate = freecom_ine_entityRepository.findAll().size();

        // Update the freecom_ine_entity
        Freecom_ine_entity updatedFreecom_ine_entity = new Freecom_ine_entity();
        updatedFreecom_ine_entity.setId(freecom_ine_entity.getId());

        restFreecom_ine_entityMockMvc.perform(put("/api/freecom-ine-entities")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedFreecom_ine_entity)))
                .andExpect(status().isOk());

        // Validate the Freecom_ine_entity in the database
        List<Freecom_ine_entity> freecom_ine_entities = freecom_ine_entityRepository.findAll();
        assertThat(freecom_ine_entities).hasSize(databaseSizeBeforeUpdate);
        Freecom_ine_entity testFreecom_ine_entity = freecom_ine_entities.get(freecom_ine_entities.size() - 1);
    }

    @Test
    @Transactional
    public void deleteFreecom_ine_entity() throws Exception {
        // Initialize the database
        freecom_ine_entityService.save(freecom_ine_entity);

        int databaseSizeBeforeDelete = freecom_ine_entityRepository.findAll().size();

        // Get the freecom_ine_entity
        restFreecom_ine_entityMockMvc.perform(delete("/api/freecom-ine-entities/{id}", freecom_ine_entity.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Freecom_ine_entity> freecom_ine_entities = freecom_ine_entityRepository.findAll();
        assertThat(freecom_ine_entities).hasSize(databaseSizeBeforeDelete - 1);
    }
}
