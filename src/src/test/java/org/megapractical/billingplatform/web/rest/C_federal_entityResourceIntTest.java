package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.C_federal_entity;
import org.megapractical.billingplatform.repository.C_federal_entityRepository;
import org.megapractical.billingplatform.service.C_federal_entityService;

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
 * Test class for the C_federal_entityResource REST controller.
 *
 * @see C_federal_entityResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class C_federal_entityResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAA";
    private static final String UPDATED_CODE = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private C_federal_entityRepository c_federal_entityRepository;

    @Inject
    private C_federal_entityService c_federal_entityService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restC_federal_entityMockMvc;

    private C_federal_entity c_federal_entity;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        C_federal_entityResource c_federal_entityResource = new C_federal_entityResource();
        ReflectionTestUtils.setField(c_federal_entityResource, "c_federal_entityService", c_federal_entityService);
        this.restC_federal_entityMockMvc = MockMvcBuilders.standaloneSetup(c_federal_entityResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        c_federal_entity = new C_federal_entity();
        c_federal_entity.setCode(DEFAULT_CODE);
        c_federal_entity.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createC_federal_entity() throws Exception {
        int databaseSizeBeforeCreate = c_federal_entityRepository.findAll().size();

        // Create the C_federal_entity

        restC_federal_entityMockMvc.perform(post("/api/c-federal-entities")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(c_federal_entity)))
                .andExpect(status().isCreated());

        // Validate the C_federal_entity in the database
        List<C_federal_entity> c_federal_entities = c_federal_entityRepository.findAll();
        assertThat(c_federal_entities).hasSize(databaseSizeBeforeCreate + 1);
        C_federal_entity testC_federal_entity = c_federal_entities.get(c_federal_entities.size() - 1);
        assertThat(testC_federal_entity.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testC_federal_entity.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = c_federal_entityRepository.findAll().size();
        // set the field null
        c_federal_entity.setCode(null);

        // Create the C_federal_entity, which fails.

        restC_federal_entityMockMvc.perform(post("/api/c-federal-entities")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(c_federal_entity)))
                .andExpect(status().isBadRequest());

        List<C_federal_entity> c_federal_entities = c_federal_entityRepository.findAll();
        assertThat(c_federal_entities).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllC_federal_entities() throws Exception {
        // Initialize the database
        c_federal_entityRepository.saveAndFlush(c_federal_entity);

        // Get all the c_federal_entities
        restC_federal_entityMockMvc.perform(get("/api/c-federal-entities?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(c_federal_entity.getId().intValue())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getC_federal_entity() throws Exception {
        // Initialize the database
        c_federal_entityRepository.saveAndFlush(c_federal_entity);

        // Get the c_federal_entity
        restC_federal_entityMockMvc.perform(get("/api/c-federal-entities/{id}", c_federal_entity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(c_federal_entity.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingC_federal_entity() throws Exception {
        // Get the c_federal_entity
        restC_federal_entityMockMvc.perform(get("/api/c-federal-entities/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateC_federal_entity() throws Exception {
        // Initialize the database
        c_federal_entityService.save(c_federal_entity);

        int databaseSizeBeforeUpdate = c_federal_entityRepository.findAll().size();

        // Update the c_federal_entity
        C_federal_entity updatedC_federal_entity = new C_federal_entity();
        updatedC_federal_entity.setId(c_federal_entity.getId());
        updatedC_federal_entity.setCode(UPDATED_CODE);
        updatedC_federal_entity.setDescription(UPDATED_DESCRIPTION);

        restC_federal_entityMockMvc.perform(put("/api/c-federal-entities")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedC_federal_entity)))
                .andExpect(status().isOk());

        // Validate the C_federal_entity in the database
        List<C_federal_entity> c_federal_entities = c_federal_entityRepository.findAll();
        assertThat(c_federal_entities).hasSize(databaseSizeBeforeUpdate);
        C_federal_entity testC_federal_entity = c_federal_entities.get(c_federal_entities.size() - 1);
        assertThat(testC_federal_entity.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testC_federal_entity.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteC_federal_entity() throws Exception {
        // Initialize the database
        c_federal_entityService.save(c_federal_entity);

        int databaseSizeBeforeDelete = c_federal_entityRepository.findAll().size();

        // Get the c_federal_entity
        restC_federal_entityMockMvc.perform(delete("/api/c-federal-entities/{id}", c_federal_entity.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<C_federal_entity> c_federal_entities = c_federal_entityRepository.findAll();
        assertThat(c_federal_entities).hasSize(databaseSizeBeforeDelete - 1);
    }
}
