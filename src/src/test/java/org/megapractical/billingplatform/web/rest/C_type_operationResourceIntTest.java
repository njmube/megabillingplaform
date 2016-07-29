package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.C_type_operation;
import org.megapractical.billingplatform.repository.C_type_operationRepository;
import org.megapractical.billingplatform.service.C_type_operationService;

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
 * Test class for the C_type_operationResource REST controller.
 *
 * @see C_type_operationResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class C_type_operationResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private C_type_operationRepository c_type_operationRepository;

    @Inject
    private C_type_operationService c_type_operationService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restC_type_operationMockMvc;

    private C_type_operation c_type_operation;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        C_type_operationResource c_type_operationResource = new C_type_operationResource();
        ReflectionTestUtils.setField(c_type_operationResource, "c_type_operationService", c_type_operationService);
        this.restC_type_operationMockMvc = MockMvcBuilders.standaloneSetup(c_type_operationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        c_type_operation = new C_type_operation();
        c_type_operation.setName(DEFAULT_NAME);
        c_type_operation.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createC_type_operation() throws Exception {
        int databaseSizeBeforeCreate = c_type_operationRepository.findAll().size();

        // Create the C_type_operation

        restC_type_operationMockMvc.perform(post("/api/c-type-operations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(c_type_operation)))
                .andExpect(status().isCreated());

        // Validate the C_type_operation in the database
        List<C_type_operation> c_type_operations = c_type_operationRepository.findAll();
        assertThat(c_type_operations).hasSize(databaseSizeBeforeCreate + 1);
        C_type_operation testC_type_operation = c_type_operations.get(c_type_operations.size() - 1);
        assertThat(testC_type_operation.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testC_type_operation.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = c_type_operationRepository.findAll().size();
        // set the field null
        c_type_operation.setName(null);

        // Create the C_type_operation, which fails.

        restC_type_operationMockMvc.perform(post("/api/c-type-operations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(c_type_operation)))
                .andExpect(status().isBadRequest());

        List<C_type_operation> c_type_operations = c_type_operationRepository.findAll();
        assertThat(c_type_operations).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllC_type_operations() throws Exception {
        // Initialize the database
        c_type_operationRepository.saveAndFlush(c_type_operation);

        // Get all the c_type_operations
        restC_type_operationMockMvc.perform(get("/api/c-type-operations?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(c_type_operation.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getC_type_operation() throws Exception {
        // Initialize the database
        c_type_operationRepository.saveAndFlush(c_type_operation);

        // Get the c_type_operation
        restC_type_operationMockMvc.perform(get("/api/c-type-operations/{id}", c_type_operation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(c_type_operation.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingC_type_operation() throws Exception {
        // Get the c_type_operation
        restC_type_operationMockMvc.perform(get("/api/c-type-operations/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateC_type_operation() throws Exception {
        // Initialize the database
        c_type_operationService.save(c_type_operation);

        int databaseSizeBeforeUpdate = c_type_operationRepository.findAll().size();

        // Update the c_type_operation
        C_type_operation updatedC_type_operation = new C_type_operation();
        updatedC_type_operation.setId(c_type_operation.getId());
        updatedC_type_operation.setName(UPDATED_NAME);
        updatedC_type_operation.setDescription(UPDATED_DESCRIPTION);

        restC_type_operationMockMvc.perform(put("/api/c-type-operations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedC_type_operation)))
                .andExpect(status().isOk());

        // Validate the C_type_operation in the database
        List<C_type_operation> c_type_operations = c_type_operationRepository.findAll();
        assertThat(c_type_operations).hasSize(databaseSizeBeforeUpdate);
        C_type_operation testC_type_operation = c_type_operations.get(c_type_operations.size() - 1);
        assertThat(testC_type_operation.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testC_type_operation.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteC_type_operation() throws Exception {
        // Initialize the database
        c_type_operationService.save(c_type_operation);

        int databaseSizeBeforeDelete = c_type_operationRepository.findAll().size();

        // Get the c_type_operation
        restC_type_operationMockMvc.perform(delete("/api/c-type-operations/{id}", c_type_operation.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<C_type_operation> c_type_operations = c_type_operationRepository.findAll();
        assertThat(c_type_operations).hasSize(databaseSizeBeforeDelete - 1);
    }
}
