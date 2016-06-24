package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Complement;
import org.megapractical.billingplatform.repository.ComplementRepository;
import org.megapractical.billingplatform.service.ComplementService;

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
 * Test class for the ComplementResource REST controller.
 *
 * @see ComplementResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class ComplementResourceIntTest {

    private static final String DEFAULT_NAME = "AAA";
    private static final String UPDATED_NAME = "BBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private ComplementRepository complementRepository;

    @Inject
    private ComplementService complementService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restComplementMockMvc;

    private Complement complement;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ComplementResource complementResource = new ComplementResource();
        ReflectionTestUtils.setField(complementResource, "complementService", complementService);
        this.restComplementMockMvc = MockMvcBuilders.standaloneSetup(complementResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        complement = new Complement();
        complement.setName(DEFAULT_NAME);
        complement.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createComplement() throws Exception {
        int databaseSizeBeforeCreate = complementRepository.findAll().size();

        // Create the Complement

        restComplementMockMvc.perform(post("/api/complements")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(complement)))
                .andExpect(status().isCreated());

        // Validate the Complement in the database
        List<Complement> complements = complementRepository.findAll();
        assertThat(complements).hasSize(databaseSizeBeforeCreate + 1);
        Complement testComplement = complements.get(complements.size() - 1);
        assertThat(testComplement.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testComplement.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = complementRepository.findAll().size();
        // set the field null
        complement.setName(null);

        // Create the Complement, which fails.

        restComplementMockMvc.perform(post("/api/complements")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(complement)))
                .andExpect(status().isBadRequest());

        List<Complement> complements = complementRepository.findAll();
        assertThat(complements).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllComplements() throws Exception {
        // Initialize the database
        complementRepository.saveAndFlush(complement);

        // Get all the complements
        restComplementMockMvc.perform(get("/api/complements?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(complement.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getComplement() throws Exception {
        // Initialize the database
        complementRepository.saveAndFlush(complement);

        // Get the complement
        restComplementMockMvc.perform(get("/api/complements/{id}", complement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(complement.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingComplement() throws Exception {
        // Get the complement
        restComplementMockMvc.perform(get("/api/complements/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateComplement() throws Exception {
        // Initialize the database
        complementService.save(complement);

        int databaseSizeBeforeUpdate = complementRepository.findAll().size();

        // Update the complement
        Complement updatedComplement = new Complement();
        updatedComplement.setId(complement.getId());
        updatedComplement.setName(UPDATED_NAME);
        updatedComplement.setDescription(UPDATED_DESCRIPTION);

        restComplementMockMvc.perform(put("/api/complements")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedComplement)))
                .andExpect(status().isOk());

        // Validate the Complement in the database
        List<Complement> complements = complementRepository.findAll();
        assertThat(complements).hasSize(databaseSizeBeforeUpdate);
        Complement testComplement = complements.get(complements.size() - 1);
        assertThat(testComplement.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testComplement.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteComplement() throws Exception {
        // Initialize the database
        complementService.save(complement);

        int databaseSizeBeforeDelete = complementRepository.findAll().size();

        // Get the complement
        restComplementMockMvc.perform(delete("/api/complements/{id}", complement.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Complement> complements = complementRepository.findAll();
        assertThat(complements).hasSize(databaseSizeBeforeDelete - 1);
    }
}
