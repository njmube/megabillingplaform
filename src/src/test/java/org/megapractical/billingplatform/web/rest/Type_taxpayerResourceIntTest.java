package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Type_taxpayer;
import org.megapractical.billingplatform.repository.Type_taxpayerRepository;
import org.megapractical.billingplatform.service.Type_taxpayerService;

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
 * Test class for the Type_taxpayerResource REST controller.
 *
 * @see Type_taxpayerResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Type_taxpayerResourceIntTest {

    private static final String DEFAULT_NAME = "AAA";
    private static final String UPDATED_NAME = "BBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private Type_taxpayerRepository type_taxpayerRepository;

    @Inject
    private Type_taxpayerService type_taxpayerService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restType_taxpayerMockMvc;

    private Type_taxpayer type_taxpayer;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Type_taxpayerResource type_taxpayerResource = new Type_taxpayerResource();
        ReflectionTestUtils.setField(type_taxpayerResource, "type_taxpayerService", type_taxpayerService);
        this.restType_taxpayerMockMvc = MockMvcBuilders.standaloneSetup(type_taxpayerResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        type_taxpayer = new Type_taxpayer();
        type_taxpayer.setName(DEFAULT_NAME);
        type_taxpayer.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createType_taxpayer() throws Exception {
        int databaseSizeBeforeCreate = type_taxpayerRepository.findAll().size();

        // Create the Type_taxpayer

        restType_taxpayerMockMvc.perform(post("/api/type-taxpayers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(type_taxpayer)))
                .andExpect(status().isCreated());

        // Validate the Type_taxpayer in the database
        List<Type_taxpayer> type_taxpayers = type_taxpayerRepository.findAll();
        assertThat(type_taxpayers).hasSize(databaseSizeBeforeCreate + 1);
        Type_taxpayer testType_taxpayer = type_taxpayers.get(type_taxpayers.size() - 1);
        assertThat(testType_taxpayer.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testType_taxpayer.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = type_taxpayerRepository.findAll().size();
        // set the field null
        type_taxpayer.setName(null);

        // Create the Type_taxpayer, which fails.

        restType_taxpayerMockMvc.perform(post("/api/type-taxpayers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(type_taxpayer)))
                .andExpect(status().isBadRequest());

        List<Type_taxpayer> type_taxpayers = type_taxpayerRepository.findAll();
        assertThat(type_taxpayers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllType_taxpayers() throws Exception {
        // Initialize the database
        type_taxpayerRepository.saveAndFlush(type_taxpayer);

        // Get all the type_taxpayers
        restType_taxpayerMockMvc.perform(get("/api/type-taxpayers?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(type_taxpayer.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getType_taxpayer() throws Exception {
        // Initialize the database
        type_taxpayerRepository.saveAndFlush(type_taxpayer);

        // Get the type_taxpayer
        restType_taxpayerMockMvc.perform(get("/api/type-taxpayers/{id}", type_taxpayer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(type_taxpayer.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingType_taxpayer() throws Exception {
        // Get the type_taxpayer
        restType_taxpayerMockMvc.perform(get("/api/type-taxpayers/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateType_taxpayer() throws Exception {
        // Initialize the database
        type_taxpayerService.save(type_taxpayer);

        int databaseSizeBeforeUpdate = type_taxpayerRepository.findAll().size();

        // Update the type_taxpayer
        Type_taxpayer updatedType_taxpayer = new Type_taxpayer();
        updatedType_taxpayer.setId(type_taxpayer.getId());
        updatedType_taxpayer.setName(UPDATED_NAME);
        updatedType_taxpayer.setDescription(UPDATED_DESCRIPTION);

        restType_taxpayerMockMvc.perform(put("/api/type-taxpayers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedType_taxpayer)))
                .andExpect(status().isOk());

        // Validate the Type_taxpayer in the database
        List<Type_taxpayer> type_taxpayers = type_taxpayerRepository.findAll();
        assertThat(type_taxpayers).hasSize(databaseSizeBeforeUpdate);
        Type_taxpayer testType_taxpayer = type_taxpayers.get(type_taxpayers.size() - 1);
        assertThat(testType_taxpayer.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testType_taxpayer.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteType_taxpayer() throws Exception {
        // Initialize the database
        type_taxpayerService.save(type_taxpayer);

        int databaseSizeBeforeDelete = type_taxpayerRepository.findAll().size();

        // Get the type_taxpayer
        restType_taxpayerMockMvc.perform(delete("/api/type-taxpayers/{id}", type_taxpayer.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Type_taxpayer> type_taxpayers = type_taxpayerRepository.findAll();
        assertThat(type_taxpayers).hasSize(databaseSizeBeforeDelete - 1);
    }
}
