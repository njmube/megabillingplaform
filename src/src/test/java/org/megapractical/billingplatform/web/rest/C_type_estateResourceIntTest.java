package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.C_type_estate;
import org.megapractical.billingplatform.repository.C_type_estateRepository;
import org.megapractical.billingplatform.service.C_type_estateService;

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
 * Test class for the C_type_estateResource REST controller.
 *
 * @see C_type_estateResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class C_type_estateResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAA";
    private static final String UPDATED_CODE = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private C_type_estateRepository c_type_estateRepository;

    @Inject
    private C_type_estateService c_type_estateService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restC_type_estateMockMvc;

    private C_type_estate c_type_estate;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        C_type_estateResource c_type_estateResource = new C_type_estateResource();
        ReflectionTestUtils.setField(c_type_estateResource, "c_type_estateService", c_type_estateService);
        this.restC_type_estateMockMvc = MockMvcBuilders.standaloneSetup(c_type_estateResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        c_type_estate = new C_type_estate();
        c_type_estate.setCode(DEFAULT_CODE);
        c_type_estate.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createC_type_estate() throws Exception {
        int databaseSizeBeforeCreate = c_type_estateRepository.findAll().size();

        // Create the C_type_estate

        restC_type_estateMockMvc.perform(post("/api/c-type-estates")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(c_type_estate)))
                .andExpect(status().isCreated());

        // Validate the C_type_estate in the database
        List<C_type_estate> c_type_estates = c_type_estateRepository.findAll();
        assertThat(c_type_estates).hasSize(databaseSizeBeforeCreate + 1);
        C_type_estate testC_type_estate = c_type_estates.get(c_type_estates.size() - 1);
        assertThat(testC_type_estate.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testC_type_estate.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = c_type_estateRepository.findAll().size();
        // set the field null
        c_type_estate.setCode(null);

        // Create the C_type_estate, which fails.

        restC_type_estateMockMvc.perform(post("/api/c-type-estates")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(c_type_estate)))
                .andExpect(status().isBadRequest());

        List<C_type_estate> c_type_estates = c_type_estateRepository.findAll();
        assertThat(c_type_estates).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllC_type_estates() throws Exception {
        // Initialize the database
        c_type_estateRepository.saveAndFlush(c_type_estate);

        // Get all the c_type_estates
        restC_type_estateMockMvc.perform(get("/api/c-type-estates?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(c_type_estate.getId().intValue())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getC_type_estate() throws Exception {
        // Initialize the database
        c_type_estateRepository.saveAndFlush(c_type_estate);

        // Get the c_type_estate
        restC_type_estateMockMvc.perform(get("/api/c-type-estates/{id}", c_type_estate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(c_type_estate.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingC_type_estate() throws Exception {
        // Get the c_type_estate
        restC_type_estateMockMvc.perform(get("/api/c-type-estates/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateC_type_estate() throws Exception {
        // Initialize the database
        c_type_estateService.save(c_type_estate);

        int databaseSizeBeforeUpdate = c_type_estateRepository.findAll().size();

        // Update the c_type_estate
        C_type_estate updatedC_type_estate = new C_type_estate();
        updatedC_type_estate.setId(c_type_estate.getId());
        updatedC_type_estate.setCode(UPDATED_CODE);
        updatedC_type_estate.setDescription(UPDATED_DESCRIPTION);

        restC_type_estateMockMvc.perform(put("/api/c-type-estates")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedC_type_estate)))
                .andExpect(status().isOk());

        // Validate the C_type_estate in the database
        List<C_type_estate> c_type_estates = c_type_estateRepository.findAll();
        assertThat(c_type_estates).hasSize(databaseSizeBeforeUpdate);
        C_type_estate testC_type_estate = c_type_estates.get(c_type_estates.size() - 1);
        assertThat(testC_type_estate.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testC_type_estate.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteC_type_estate() throws Exception {
        // Initialize the database
        c_type_estateService.save(c_type_estate);

        int databaseSizeBeforeDelete = c_type_estateRepository.findAll().size();

        // Get the c_type_estate
        restC_type_estateMockMvc.perform(delete("/api/c-type-estates/{id}", c_type_estate.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<C_type_estate> c_type_estates = c_type_estateRepository.findAll();
        assertThat(c_type_estates).hasSize(databaseSizeBeforeDelete - 1);
    }
}
