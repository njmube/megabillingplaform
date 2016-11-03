package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Com_ine_entity;
import org.megapractical.billingplatform.repository.Com_ine_entityRepository;
import org.megapractical.billingplatform.service.Com_ine_entityService;

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
 * Test class for the Com_ine_entityResource REST controller.
 *
 * @see Com_ine_entityResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Com_ine_entityResourceIntTest {


    @Inject
    private Com_ine_entityRepository com_ine_entityRepository;

    @Inject
    private Com_ine_entityService com_ine_entityService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCom_ine_entityMockMvc;

    private Com_ine_entity com_ine_entity;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Com_ine_entityResource com_ine_entityResource = new Com_ine_entityResource();
        ReflectionTestUtils.setField(com_ine_entityResource, "com_ine_entityService", com_ine_entityService);
        this.restCom_ine_entityMockMvc = MockMvcBuilders.standaloneSetup(com_ine_entityResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        com_ine_entity = new Com_ine_entity();
    }

    @Test
    @Transactional
    public void createCom_ine_entity() throws Exception {
        int databaseSizeBeforeCreate = com_ine_entityRepository.findAll().size();

        // Create the Com_ine_entity

        restCom_ine_entityMockMvc.perform(post("/api/com-ine-entities")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_ine_entity)))
                .andExpect(status().isCreated());

        // Validate the Com_ine_entity in the database
        List<Com_ine_entity> com_ine_entities = com_ine_entityRepository.findAll();
        assertThat(com_ine_entities).hasSize(databaseSizeBeforeCreate + 1);
        Com_ine_entity testCom_ine_entity = com_ine_entities.get(com_ine_entities.size() - 1);
    }

    @Test
    @Transactional
    public void getAllCom_ine_entities() throws Exception {
        // Initialize the database
        com_ine_entityRepository.saveAndFlush(com_ine_entity);

        // Get all the com_ine_entities
        restCom_ine_entityMockMvc.perform(get("/api/com-ine-entities?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(com_ine_entity.getId().intValue())));
    }

    @Test
    @Transactional
    public void getCom_ine_entity() throws Exception {
        // Initialize the database
        com_ine_entityRepository.saveAndFlush(com_ine_entity);

        // Get the com_ine_entity
        restCom_ine_entityMockMvc.perform(get("/api/com-ine-entities/{id}", com_ine_entity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(com_ine_entity.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCom_ine_entity() throws Exception {
        // Get the com_ine_entity
        restCom_ine_entityMockMvc.perform(get("/api/com-ine-entities/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCom_ine_entity() throws Exception {
        // Initialize the database
        com_ine_entityService.save(com_ine_entity);

        int databaseSizeBeforeUpdate = com_ine_entityRepository.findAll().size();

        // Update the com_ine_entity
        Com_ine_entity updatedCom_ine_entity = new Com_ine_entity();
        updatedCom_ine_entity.setId(com_ine_entity.getId());

        restCom_ine_entityMockMvc.perform(put("/api/com-ine-entities")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCom_ine_entity)))
                .andExpect(status().isOk());

        // Validate the Com_ine_entity in the database
        List<Com_ine_entity> com_ine_entities = com_ine_entityRepository.findAll();
        assertThat(com_ine_entities).hasSize(databaseSizeBeforeUpdate);
        Com_ine_entity testCom_ine_entity = com_ine_entities.get(com_ine_entities.size() - 1);
    }

    @Test
    @Transactional
    public void deleteCom_ine_entity() throws Exception {
        // Initialize the database
        com_ine_entityService.save(com_ine_entity);

        int databaseSizeBeforeDelete = com_ine_entityRepository.findAll().size();

        // Get the com_ine_entity
        restCom_ine_entityMockMvc.perform(delete("/api/com-ine-entities/{id}", com_ine_entity.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Com_ine_entity> com_ine_entities = com_ine_entityRepository.findAll();
        assertThat(com_ine_entities).hasSize(databaseSizeBeforeDelete - 1);
    }
}
