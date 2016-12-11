package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.C_pn_federal_entity;
import org.megapractical.billingplatform.repository.C_pn_federal_entityRepository;
import org.megapractical.billingplatform.service.C_pn_federal_entityService;

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
 * Test class for the C_pn_federal_entityResource REST controller.
 *
 * @see C_pn_federal_entityResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class C_pn_federal_entityResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAA";
    private static final String UPDATED_CODE = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private C_pn_federal_entityRepository c_pn_federal_entityRepository;

    @Inject
    private C_pn_federal_entityService c_pn_federal_entityService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restC_pn_federal_entityMockMvc;

    private C_pn_federal_entity c_pn_federal_entity;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        C_pn_federal_entityResource c_pn_federal_entityResource = new C_pn_federal_entityResource();
        ReflectionTestUtils.setField(c_pn_federal_entityResource, "c_pn_federal_entityService", c_pn_federal_entityService);
        this.restC_pn_federal_entityMockMvc = MockMvcBuilders.standaloneSetup(c_pn_federal_entityResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        c_pn_federal_entity = new C_pn_federal_entity();
        c_pn_federal_entity.setCode(DEFAULT_CODE);
        c_pn_federal_entity.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createC_pn_federal_entity() throws Exception {
        int databaseSizeBeforeCreate = c_pn_federal_entityRepository.findAll().size();

        // Create the C_pn_federal_entity

        restC_pn_federal_entityMockMvc.perform(post("/api/c-pn-federal-entities")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(c_pn_federal_entity)))
                .andExpect(status().isCreated());

        // Validate the C_pn_federal_entity in the database
        List<C_pn_federal_entity> c_pn_federal_entities = c_pn_federal_entityRepository.findAll();
        assertThat(c_pn_federal_entities).hasSize(databaseSizeBeforeCreate + 1);
        C_pn_federal_entity testC_pn_federal_entity = c_pn_federal_entities.get(c_pn_federal_entities.size() - 1);
        assertThat(testC_pn_federal_entity.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testC_pn_federal_entity.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = c_pn_federal_entityRepository.findAll().size();
        // set the field null
        c_pn_federal_entity.setCode(null);

        // Create the C_pn_federal_entity, which fails.

        restC_pn_federal_entityMockMvc.perform(post("/api/c-pn-federal-entities")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(c_pn_federal_entity)))
                .andExpect(status().isBadRequest());

        List<C_pn_federal_entity> c_pn_federal_entities = c_pn_federal_entityRepository.findAll();
        assertThat(c_pn_federal_entities).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllC_pn_federal_entities() throws Exception {
        // Initialize the database
        c_pn_federal_entityRepository.saveAndFlush(c_pn_federal_entity);

        // Get all the c_pn_federal_entities
        restC_pn_federal_entityMockMvc.perform(get("/api/c-pn-federal-entities?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(c_pn_federal_entity.getId().intValue())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getC_pn_federal_entity() throws Exception {
        // Initialize the database
        c_pn_federal_entityRepository.saveAndFlush(c_pn_federal_entity);

        // Get the c_pn_federal_entity
        restC_pn_federal_entityMockMvc.perform(get("/api/c-pn-federal-entities/{id}", c_pn_federal_entity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(c_pn_federal_entity.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingC_pn_federal_entity() throws Exception {
        // Get the c_pn_federal_entity
        restC_pn_federal_entityMockMvc.perform(get("/api/c-pn-federal-entities/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateC_pn_federal_entity() throws Exception {
        // Initialize the database
        c_pn_federal_entityService.save(c_pn_federal_entity);

        int databaseSizeBeforeUpdate = c_pn_federal_entityRepository.findAll().size();

        // Update the c_pn_federal_entity
        C_pn_federal_entity updatedC_pn_federal_entity = new C_pn_federal_entity();
        updatedC_pn_federal_entity.setId(c_pn_federal_entity.getId());
        updatedC_pn_federal_entity.setCode(UPDATED_CODE);
        updatedC_pn_federal_entity.setDescription(UPDATED_DESCRIPTION);

        restC_pn_federal_entityMockMvc.perform(put("/api/c-pn-federal-entities")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedC_pn_federal_entity)))
                .andExpect(status().isOk());

        // Validate the C_pn_federal_entity in the database
        List<C_pn_federal_entity> c_pn_federal_entities = c_pn_federal_entityRepository.findAll();
        assertThat(c_pn_federal_entities).hasSize(databaseSizeBeforeUpdate);
        C_pn_federal_entity testC_pn_federal_entity = c_pn_federal_entities.get(c_pn_federal_entities.size() - 1);
        assertThat(testC_pn_federal_entity.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testC_pn_federal_entity.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteC_pn_federal_entity() throws Exception {
        // Initialize the database
        c_pn_federal_entityService.save(c_pn_federal_entity);

        int databaseSizeBeforeDelete = c_pn_federal_entityRepository.findAll().size();

        // Get the c_pn_federal_entity
        restC_pn_federal_entityMockMvc.perform(delete("/api/c-pn-federal-entities/{id}", c_pn_federal_entity.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<C_pn_federal_entity> c_pn_federal_entities = c_pn_federal_entityRepository.findAll();
        assertThat(c_pn_federal_entities).hasSize(databaseSizeBeforeDelete - 1);
    }
}
