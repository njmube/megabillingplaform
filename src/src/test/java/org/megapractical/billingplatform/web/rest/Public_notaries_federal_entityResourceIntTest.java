package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Public_notaries_federal_entity;
import org.megapractical.billingplatform.repository.Public_notaries_federal_entityRepository;
import org.megapractical.billingplatform.service.Public_notaries_federal_entityService;

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
 * Test class for the Public_notaries_federal_entityResource REST controller.
 *
 * @see Public_notaries_federal_entityResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Public_notaries_federal_entityResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAA";
    private static final String UPDATED_CODE = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private Public_notaries_federal_entityRepository public_notaries_federal_entityRepository;

    @Inject
    private Public_notaries_federal_entityService public_notaries_federal_entityService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPublic_notaries_federal_entityMockMvc;

    private Public_notaries_federal_entity public_notaries_federal_entity;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Public_notaries_federal_entityResource public_notaries_federal_entityResource = new Public_notaries_federal_entityResource();
        ReflectionTestUtils.setField(public_notaries_federal_entityResource, "public_notaries_federal_entityService", public_notaries_federal_entityService);
        this.restPublic_notaries_federal_entityMockMvc = MockMvcBuilders.standaloneSetup(public_notaries_federal_entityResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        public_notaries_federal_entity = new Public_notaries_federal_entity();
        public_notaries_federal_entity.setCode(DEFAULT_CODE);
        public_notaries_federal_entity.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createPublic_notaries_federal_entity() throws Exception {
        int databaseSizeBeforeCreate = public_notaries_federal_entityRepository.findAll().size();

        // Create the Public_notaries_federal_entity

        restPublic_notaries_federal_entityMockMvc.perform(post("/api/public-notaries-federal-entities")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(public_notaries_federal_entity)))
                .andExpect(status().isCreated());

        // Validate the Public_notaries_federal_entity in the database
        List<Public_notaries_federal_entity> public_notaries_federal_entities = public_notaries_federal_entityRepository.findAll();
        assertThat(public_notaries_federal_entities).hasSize(databaseSizeBeforeCreate + 1);
        Public_notaries_federal_entity testPublic_notaries_federal_entity = public_notaries_federal_entities.get(public_notaries_federal_entities.size() - 1);
        assertThat(testPublic_notaries_federal_entity.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testPublic_notaries_federal_entity.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = public_notaries_federal_entityRepository.findAll().size();
        // set the field null
        public_notaries_federal_entity.setCode(null);

        // Create the Public_notaries_federal_entity, which fails.

        restPublic_notaries_federal_entityMockMvc.perform(post("/api/public-notaries-federal-entities")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(public_notaries_federal_entity)))
                .andExpect(status().isBadRequest());

        List<Public_notaries_federal_entity> public_notaries_federal_entities = public_notaries_federal_entityRepository.findAll();
        assertThat(public_notaries_federal_entities).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPublic_notaries_federal_entities() throws Exception {
        // Initialize the database
        public_notaries_federal_entityRepository.saveAndFlush(public_notaries_federal_entity);

        // Get all the public_notaries_federal_entities
        restPublic_notaries_federal_entityMockMvc.perform(get("/api/public-notaries-federal-entities?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(public_notaries_federal_entity.getId().intValue())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getPublic_notaries_federal_entity() throws Exception {
        // Initialize the database
        public_notaries_federal_entityRepository.saveAndFlush(public_notaries_federal_entity);

        // Get the public_notaries_federal_entity
        restPublic_notaries_federal_entityMockMvc.perform(get("/api/public-notaries-federal-entities/{id}", public_notaries_federal_entity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(public_notaries_federal_entity.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPublic_notaries_federal_entity() throws Exception {
        // Get the public_notaries_federal_entity
        restPublic_notaries_federal_entityMockMvc.perform(get("/api/public-notaries-federal-entities/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePublic_notaries_federal_entity() throws Exception {
        // Initialize the database
        public_notaries_federal_entityService.save(public_notaries_federal_entity);

        int databaseSizeBeforeUpdate = public_notaries_federal_entityRepository.findAll().size();

        // Update the public_notaries_federal_entity
        Public_notaries_federal_entity updatedPublic_notaries_federal_entity = new Public_notaries_federal_entity();
        updatedPublic_notaries_federal_entity.setId(public_notaries_federal_entity.getId());
        updatedPublic_notaries_federal_entity.setCode(UPDATED_CODE);
        updatedPublic_notaries_federal_entity.setDescription(UPDATED_DESCRIPTION);

        restPublic_notaries_federal_entityMockMvc.perform(put("/api/public-notaries-federal-entities")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedPublic_notaries_federal_entity)))
                .andExpect(status().isOk());

        // Validate the Public_notaries_federal_entity in the database
        List<Public_notaries_federal_entity> public_notaries_federal_entities = public_notaries_federal_entityRepository.findAll();
        assertThat(public_notaries_federal_entities).hasSize(databaseSizeBeforeUpdate);
        Public_notaries_federal_entity testPublic_notaries_federal_entity = public_notaries_federal_entities.get(public_notaries_federal_entities.size() - 1);
        assertThat(testPublic_notaries_federal_entity.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testPublic_notaries_federal_entity.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deletePublic_notaries_federal_entity() throws Exception {
        // Initialize the database
        public_notaries_federal_entityService.save(public_notaries_federal_entity);

        int databaseSizeBeforeDelete = public_notaries_federal_entityRepository.findAll().size();

        // Get the public_notaries_federal_entity
        restPublic_notaries_federal_entityMockMvc.perform(delete("/api/public-notaries-federal-entities/{id}", public_notaries_federal_entity.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Public_notaries_federal_entity> public_notaries_federal_entities = public_notaries_federal_entityRepository.findAll();
        assertThat(public_notaries_federal_entities).hasSize(databaseSizeBeforeDelete - 1);
    }
}
