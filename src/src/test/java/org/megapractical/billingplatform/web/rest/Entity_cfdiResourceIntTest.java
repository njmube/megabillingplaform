package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Entity_cfdi;
import org.megapractical.billingplatform.repository.Entity_cfdiRepository;
import org.megapractical.billingplatform.service.Entity_cfdiService;

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
 * Test class for the Entity_cfdiResource REST controller.
 *
 * @see Entity_cfdiResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Entity_cfdiResourceIntTest {


    @Inject
    private Entity_cfdiRepository entity_cfdiRepository;

    @Inject
    private Entity_cfdiService entity_cfdiService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restEntity_cfdiMockMvc;

    private Entity_cfdi entity_cfdi;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Entity_cfdiResource entity_cfdiResource = new Entity_cfdiResource();
        ReflectionTestUtils.setField(entity_cfdiResource, "entity_cfdiService", entity_cfdiService);
        this.restEntity_cfdiMockMvc = MockMvcBuilders.standaloneSetup(entity_cfdiResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        entity_cfdi = new Entity_cfdi();
    }

    @Test
    @Transactional
    public void createEntity_cfdi() throws Exception {
        int databaseSizeBeforeCreate = entity_cfdiRepository.findAll().size();

        // Create the Entity_cfdi

        restEntity_cfdiMockMvc.perform(post("/api/entity-cfdis")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(entity_cfdi)))
                .andExpect(status().isCreated());

        // Validate the Entity_cfdi in the database
        List<Entity_cfdi> entity_cfdis = entity_cfdiRepository.findAll();
        assertThat(entity_cfdis).hasSize(databaseSizeBeforeCreate + 1);
        Entity_cfdi testEntity_cfdi = entity_cfdis.get(entity_cfdis.size() - 1);
    }

    @Test
    @Transactional
    public void getAllEntity_cfdis() throws Exception {
        // Initialize the database
        entity_cfdiRepository.saveAndFlush(entity_cfdi);

        // Get all the entity_cfdis
        restEntity_cfdiMockMvc.perform(get("/api/entity-cfdis?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(entity_cfdi.getId().intValue())));
    }

    @Test
    @Transactional
    public void getEntity_cfdi() throws Exception {
        // Initialize the database
        entity_cfdiRepository.saveAndFlush(entity_cfdi);

        // Get the entity_cfdi
        restEntity_cfdiMockMvc.perform(get("/api/entity-cfdis/{id}", entity_cfdi.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(entity_cfdi.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingEntity_cfdi() throws Exception {
        // Get the entity_cfdi
        restEntity_cfdiMockMvc.perform(get("/api/entity-cfdis/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEntity_cfdi() throws Exception {
        // Initialize the database
        entity_cfdiService.save(entity_cfdi);

        int databaseSizeBeforeUpdate = entity_cfdiRepository.findAll().size();

        // Update the entity_cfdi
        Entity_cfdi updatedEntity_cfdi = new Entity_cfdi();
        updatedEntity_cfdi.setId(entity_cfdi.getId());

        restEntity_cfdiMockMvc.perform(put("/api/entity-cfdis")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedEntity_cfdi)))
                .andExpect(status().isOk());

        // Validate the Entity_cfdi in the database
        List<Entity_cfdi> entity_cfdis = entity_cfdiRepository.findAll();
        assertThat(entity_cfdis).hasSize(databaseSizeBeforeUpdate);
        Entity_cfdi testEntity_cfdi = entity_cfdis.get(entity_cfdis.size() - 1);
    }

    @Test
    @Transactional
    public void deleteEntity_cfdi() throws Exception {
        // Initialize the database
        entity_cfdiService.save(entity_cfdi);

        int databaseSizeBeforeDelete = entity_cfdiRepository.findAll().size();

        // Get the entity_cfdi
        restEntity_cfdiMockMvc.perform(delete("/api/entity-cfdis/{id}", entity_cfdi.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Entity_cfdi> entity_cfdis = entity_cfdiRepository.findAll();
        assertThat(entity_cfdis).hasSize(databaseSizeBeforeDelete - 1);
    }
}
