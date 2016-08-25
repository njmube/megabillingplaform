package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.C_complement;
import org.megapractical.billingplatform.repository.C_complementRepository;
import org.megapractical.billingplatform.service.C_complementService;

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
 * Test class for the C_complementResource REST controller.
 *
 * @see C_complementResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class C_complementResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private C_complementRepository c_complementRepository;

    @Inject
    private C_complementService c_complementService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restC_complementMockMvc;

    private C_complement c_complement;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        C_complementResource c_complementResource = new C_complementResource();
        ReflectionTestUtils.setField(c_complementResource, "c_complementService", c_complementService);
        this.restC_complementMockMvc = MockMvcBuilders.standaloneSetup(c_complementResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        c_complement = new C_complement();
        c_complement.setName(DEFAULT_NAME);
        c_complement.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createC_complement() throws Exception {
        int databaseSizeBeforeCreate = c_complementRepository.findAll().size();

        // Create the C_complement

        restC_complementMockMvc.perform(post("/api/c-complements")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(c_complement)))
                .andExpect(status().isCreated());

        // Validate the C_complement in the database
        List<C_complement> c_complements = c_complementRepository.findAll();
        assertThat(c_complements).hasSize(databaseSizeBeforeCreate + 1);
        C_complement testC_complement = c_complements.get(c_complements.size() - 1);
        assertThat(testC_complement.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testC_complement.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = c_complementRepository.findAll().size();
        // set the field null
        c_complement.setName(null);

        // Create the C_complement, which fails.

        restC_complementMockMvc.perform(post("/api/c-complements")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(c_complement)))
                .andExpect(status().isBadRequest());

        List<C_complement> c_complements = c_complementRepository.findAll();
        assertThat(c_complements).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllC_complements() throws Exception {
        // Initialize the database
        c_complementRepository.saveAndFlush(c_complement);

        // Get all the c_complements
        restC_complementMockMvc.perform(get("/api/c-complements?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(c_complement.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getC_complement() throws Exception {
        // Initialize the database
        c_complementRepository.saveAndFlush(c_complement);

        // Get the c_complement
        restC_complementMockMvc.perform(get("/api/c-complements/{id}", c_complement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(c_complement.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingC_complement() throws Exception {
        // Get the c_complement
        restC_complementMockMvc.perform(get("/api/c-complements/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateC_complement() throws Exception {
        // Initialize the database
        c_complementService.save(c_complement);

        int databaseSizeBeforeUpdate = c_complementRepository.findAll().size();

        // Update the c_complement
        C_complement updatedC_complement = new C_complement();
        updatedC_complement.setId(c_complement.getId());
        updatedC_complement.setName(UPDATED_NAME);
        updatedC_complement.setDescription(UPDATED_DESCRIPTION);

        restC_complementMockMvc.perform(put("/api/c-complements")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedC_complement)))
                .andExpect(status().isOk());

        // Validate the C_complement in the database
        List<C_complement> c_complements = c_complementRepository.findAll();
        assertThat(c_complements).hasSize(databaseSizeBeforeUpdate);
        C_complement testC_complement = c_complements.get(c_complements.size() - 1);
        assertThat(testC_complement.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testC_complement.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteC_complement() throws Exception {
        // Initialize the database
        c_complementService.save(c_complement);

        int databaseSizeBeforeDelete = c_complementRepository.findAll().size();

        // Get the c_complement
        restC_complementMockMvc.perform(delete("/api/c-complements/{id}", c_complement.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<C_complement> c_complements = c_complementRepository.findAll();
        assertThat(c_complements).hasSize(databaseSizeBeforeDelete - 1);
    }
}
