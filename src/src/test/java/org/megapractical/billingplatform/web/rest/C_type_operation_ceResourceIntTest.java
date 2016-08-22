package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.C_type_operation_ce;
import org.megapractical.billingplatform.repository.C_type_operation_ceRepository;
import org.megapractical.billingplatform.service.C_type_operation_ceService;

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
 * Test class for the C_type_operation_ceResource REST controller.
 *
 * @see C_type_operation_ceResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class C_type_operation_ceResourceIntTest {

    private static final String DEFAULT_VALUE = "AAAAA";
    private static final String UPDATED_VALUE = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private C_type_operation_ceRepository c_type_operation_ceRepository;

    @Inject
    private C_type_operation_ceService c_type_operation_ceService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restC_type_operation_ceMockMvc;

    private C_type_operation_ce c_type_operation_ce;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        C_type_operation_ceResource c_type_operation_ceResource = new C_type_operation_ceResource();
        ReflectionTestUtils.setField(c_type_operation_ceResource, "c_type_operation_ceService", c_type_operation_ceService);
        this.restC_type_operation_ceMockMvc = MockMvcBuilders.standaloneSetup(c_type_operation_ceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        c_type_operation_ce = new C_type_operation_ce();
        c_type_operation_ce.setValue(DEFAULT_VALUE);
        c_type_operation_ce.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createC_type_operation_ce() throws Exception {
        int databaseSizeBeforeCreate = c_type_operation_ceRepository.findAll().size();

        // Create the C_type_operation_ce

        restC_type_operation_ceMockMvc.perform(post("/api/c-type-operation-ces")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(c_type_operation_ce)))
                .andExpect(status().isCreated());

        // Validate the C_type_operation_ce in the database
        List<C_type_operation_ce> c_type_operation_ces = c_type_operation_ceRepository.findAll();
        assertThat(c_type_operation_ces).hasSize(databaseSizeBeforeCreate + 1);
        C_type_operation_ce testC_type_operation_ce = c_type_operation_ces.get(c_type_operation_ces.size() - 1);
        assertThat(testC_type_operation_ce.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testC_type_operation_ce.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = c_type_operation_ceRepository.findAll().size();
        // set the field null
        c_type_operation_ce.setValue(null);

        // Create the C_type_operation_ce, which fails.

        restC_type_operation_ceMockMvc.perform(post("/api/c-type-operation-ces")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(c_type_operation_ce)))
                .andExpect(status().isBadRequest());

        List<C_type_operation_ce> c_type_operation_ces = c_type_operation_ceRepository.findAll();
        assertThat(c_type_operation_ces).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllC_type_operation_ces() throws Exception {
        // Initialize the database
        c_type_operation_ceRepository.saveAndFlush(c_type_operation_ce);

        // Get all the c_type_operation_ces
        restC_type_operation_ceMockMvc.perform(get("/api/c-type-operation-ces?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(c_type_operation_ce.getId().intValue())))
                .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getC_type_operation_ce() throws Exception {
        // Initialize the database
        c_type_operation_ceRepository.saveAndFlush(c_type_operation_ce);

        // Get the c_type_operation_ce
        restC_type_operation_ceMockMvc.perform(get("/api/c-type-operation-ces/{id}", c_type_operation_ce.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(c_type_operation_ce.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingC_type_operation_ce() throws Exception {
        // Get the c_type_operation_ce
        restC_type_operation_ceMockMvc.perform(get("/api/c-type-operation-ces/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateC_type_operation_ce() throws Exception {
        // Initialize the database
        c_type_operation_ceService.save(c_type_operation_ce);

        int databaseSizeBeforeUpdate = c_type_operation_ceRepository.findAll().size();

        // Update the c_type_operation_ce
        C_type_operation_ce updatedC_type_operation_ce = new C_type_operation_ce();
        updatedC_type_operation_ce.setId(c_type_operation_ce.getId());
        updatedC_type_operation_ce.setValue(UPDATED_VALUE);
        updatedC_type_operation_ce.setDescription(UPDATED_DESCRIPTION);

        restC_type_operation_ceMockMvc.perform(put("/api/c-type-operation-ces")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedC_type_operation_ce)))
                .andExpect(status().isOk());

        // Validate the C_type_operation_ce in the database
        List<C_type_operation_ce> c_type_operation_ces = c_type_operation_ceRepository.findAll();
        assertThat(c_type_operation_ces).hasSize(databaseSizeBeforeUpdate);
        C_type_operation_ce testC_type_operation_ce = c_type_operation_ces.get(c_type_operation_ces.size() - 1);
        assertThat(testC_type_operation_ce.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testC_type_operation_ce.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteC_type_operation_ce() throws Exception {
        // Initialize the database
        c_type_operation_ceService.save(c_type_operation_ce);

        int databaseSizeBeforeDelete = c_type_operation_ceRepository.findAll().size();

        // Get the c_type_operation_ce
        restC_type_operation_ceMockMvc.perform(delete("/api/c-type-operation-ces/{id}", c_type_operation_ce.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<C_type_operation_ce> c_type_operation_ces = c_type_operation_ceRepository.findAll();
        assertThat(c_type_operation_ces).hasSize(databaseSizeBeforeDelete - 1);
    }
}
