package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.C_municipality;
import org.megapractical.billingplatform.repository.C_municipalityRepository;
import org.megapractical.billingplatform.service.C_municipalityService;

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
 * Test class for the C_municipalityResource REST controller.
 *
 * @see C_municipalityResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class C_municipalityResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_CODE = "AAA";
    private static final String UPDATED_CODE = "BBB";

    @Inject
    private C_municipalityRepository c_municipalityRepository;

    @Inject
    private C_municipalityService c_municipalityService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restC_municipalityMockMvc;

    private C_municipality c_municipality;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        C_municipalityResource c_municipalityResource = new C_municipalityResource();
        ReflectionTestUtils.setField(c_municipalityResource, "c_municipalityService", c_municipalityService);
        this.restC_municipalityMockMvc = MockMvcBuilders.standaloneSetup(c_municipalityResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        c_municipality = new C_municipality();
        c_municipality.setName(DEFAULT_NAME);
        c_municipality.setCode(DEFAULT_CODE);
    }

    @Test
    @Transactional
    public void createC_municipality() throws Exception {
        int databaseSizeBeforeCreate = c_municipalityRepository.findAll().size();

        // Create the C_municipality

        restC_municipalityMockMvc.perform(post("/api/c-municipalities")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(c_municipality)))
                .andExpect(status().isCreated());

        // Validate the C_municipality in the database
        List<C_municipality> c_municipalities = c_municipalityRepository.findAll();
        assertThat(c_municipalities).hasSize(databaseSizeBeforeCreate + 1);
        C_municipality testC_municipality = c_municipalities.get(c_municipalities.size() - 1);
        assertThat(testC_municipality.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testC_municipality.getCode()).isEqualTo(DEFAULT_CODE);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = c_municipalityRepository.findAll().size();
        // set the field null
        c_municipality.setCode(null);

        // Create the C_municipality, which fails.

        restC_municipalityMockMvc.perform(post("/api/c-municipalities")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(c_municipality)))
                .andExpect(status().isBadRequest());

        List<C_municipality> c_municipalities = c_municipalityRepository.findAll();
        assertThat(c_municipalities).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllC_municipalities() throws Exception {
        // Initialize the database
        c_municipalityRepository.saveAndFlush(c_municipality);

        // Get all the c_municipalities
        restC_municipalityMockMvc.perform(get("/api/c-municipalities?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(c_municipality.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())));
    }

    @Test
    @Transactional
    public void getC_municipality() throws Exception {
        // Initialize the database
        c_municipalityRepository.saveAndFlush(c_municipality);

        // Get the c_municipality
        restC_municipalityMockMvc.perform(get("/api/c-municipalities/{id}", c_municipality.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(c_municipality.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingC_municipality() throws Exception {
        // Get the c_municipality
        restC_municipalityMockMvc.perform(get("/api/c-municipalities/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateC_municipality() throws Exception {
        // Initialize the database
        c_municipalityService.save(c_municipality);

        int databaseSizeBeforeUpdate = c_municipalityRepository.findAll().size();

        // Update the c_municipality
        C_municipality updatedC_municipality = new C_municipality();
        updatedC_municipality.setId(c_municipality.getId());
        updatedC_municipality.setName(UPDATED_NAME);
        updatedC_municipality.setCode(UPDATED_CODE);

        restC_municipalityMockMvc.perform(put("/api/c-municipalities")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedC_municipality)))
                .andExpect(status().isOk());

        // Validate the C_municipality in the database
        List<C_municipality> c_municipalities = c_municipalityRepository.findAll();
        assertThat(c_municipalities).hasSize(databaseSizeBeforeUpdate);
        C_municipality testC_municipality = c_municipalities.get(c_municipalities.size() - 1);
        assertThat(testC_municipality.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testC_municipality.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    public void deleteC_municipality() throws Exception {
        // Initialize the database
        c_municipalityService.save(c_municipality);

        int databaseSizeBeforeDelete = c_municipalityRepository.findAll().size();

        // Get the c_municipality
        restC_municipalityMockMvc.perform(delete("/api/c-municipalities/{id}", c_municipality.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<C_municipality> c_municipalities = c_municipalityRepository.findAll();
        assertThat(c_municipalities).hasSize(databaseSizeBeforeDelete - 1);
    }
}
