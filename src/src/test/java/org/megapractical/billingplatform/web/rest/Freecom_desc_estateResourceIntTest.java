package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Freecom_desc_estate;
import org.megapractical.billingplatform.repository.Freecom_desc_estateRepository;
import org.megapractical.billingplatform.service.Freecom_desc_estateService;

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
 * Test class for the Freecom_desc_estateResource REST controller.
 *
 * @see Freecom_desc_estateResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Freecom_desc_estateResourceIntTest {

    private static final String DEFAULT_STREET = "AAAAA";
    private static final String UPDATED_STREET = "BBBBB";
    private static final String DEFAULT_NOEXT = "AAAAA";
    private static final String UPDATED_NOEXT = "BBBBB";
    private static final String DEFAULT_NOINT = "AAAAA";
    private static final String UPDATED_NOINT = "BBBBB";
    private static final String DEFAULT_LOCALE = "AAAAA";
    private static final String UPDATED_LOCALE = "BBBBB";
    private static final String DEFAULT_REFERENCE = "AAAAA";
    private static final String UPDATED_REFERENCE = "BBBBB";

    @Inject
    private Freecom_desc_estateRepository freecom_desc_estateRepository;

    @Inject
    private Freecom_desc_estateService freecom_desc_estateService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFreecom_desc_estateMockMvc;

    private Freecom_desc_estate freecom_desc_estate;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Freecom_desc_estateResource freecom_desc_estateResource = new Freecom_desc_estateResource();
        ReflectionTestUtils.setField(freecom_desc_estateResource, "freecom_desc_estateService", freecom_desc_estateService);
        this.restFreecom_desc_estateMockMvc = MockMvcBuilders.standaloneSetup(freecom_desc_estateResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        freecom_desc_estate = new Freecom_desc_estate();
        freecom_desc_estate.setStreet(DEFAULT_STREET);
        freecom_desc_estate.setNoext(DEFAULT_NOEXT);
        freecom_desc_estate.setNoint(DEFAULT_NOINT);
        freecom_desc_estate.setLocale(DEFAULT_LOCALE);
        freecom_desc_estate.setReference(DEFAULT_REFERENCE);
    }

    @Test
    @Transactional
    public void createFreecom_desc_estate() throws Exception {
        int databaseSizeBeforeCreate = freecom_desc_estateRepository.findAll().size();

        // Create the Freecom_desc_estate

        restFreecom_desc_estateMockMvc.perform(post("/api/freecom-desc-estates")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_desc_estate)))
                .andExpect(status().isCreated());

        // Validate the Freecom_desc_estate in the database
        List<Freecom_desc_estate> freecom_desc_estates = freecom_desc_estateRepository.findAll();
        assertThat(freecom_desc_estates).hasSize(databaseSizeBeforeCreate + 1);
        Freecom_desc_estate testFreecom_desc_estate = freecom_desc_estates.get(freecom_desc_estates.size() - 1);
        assertThat(testFreecom_desc_estate.getStreet()).isEqualTo(DEFAULT_STREET);
        assertThat(testFreecom_desc_estate.getNoext()).isEqualTo(DEFAULT_NOEXT);
        assertThat(testFreecom_desc_estate.getNoint()).isEqualTo(DEFAULT_NOINT);
        assertThat(testFreecom_desc_estate.getLocale()).isEqualTo(DEFAULT_LOCALE);
        assertThat(testFreecom_desc_estate.getReference()).isEqualTo(DEFAULT_REFERENCE);
    }

    @Test
    @Transactional
    public void checkStreetIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_desc_estateRepository.findAll().size();
        // set the field null
        freecom_desc_estate.setStreet(null);

        // Create the Freecom_desc_estate, which fails.

        restFreecom_desc_estateMockMvc.perform(post("/api/freecom-desc-estates")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_desc_estate)))
                .andExpect(status().isBadRequest());

        List<Freecom_desc_estate> freecom_desc_estates = freecom_desc_estateRepository.findAll();
        assertThat(freecom_desc_estates).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFreecom_desc_estates() throws Exception {
        // Initialize the database
        freecom_desc_estateRepository.saveAndFlush(freecom_desc_estate);

        // Get all the freecom_desc_estates
        restFreecom_desc_estateMockMvc.perform(get("/api/freecom-desc-estates?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(freecom_desc_estate.getId().intValue())))
                .andExpect(jsonPath("$.[*].street").value(hasItem(DEFAULT_STREET.toString())))
                .andExpect(jsonPath("$.[*].noext").value(hasItem(DEFAULT_NOEXT.toString())))
                .andExpect(jsonPath("$.[*].noint").value(hasItem(DEFAULT_NOINT.toString())))
                .andExpect(jsonPath("$.[*].locale").value(hasItem(DEFAULT_LOCALE.toString())))
                .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE.toString())));
    }

    @Test
    @Transactional
    public void getFreecom_desc_estate() throws Exception {
        // Initialize the database
        freecom_desc_estateRepository.saveAndFlush(freecom_desc_estate);

        // Get the freecom_desc_estate
        restFreecom_desc_estateMockMvc.perform(get("/api/freecom-desc-estates/{id}", freecom_desc_estate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(freecom_desc_estate.getId().intValue()))
            .andExpect(jsonPath("$.street").value(DEFAULT_STREET.toString()))
            .andExpect(jsonPath("$.noext").value(DEFAULT_NOEXT.toString()))
            .andExpect(jsonPath("$.noint").value(DEFAULT_NOINT.toString()))
            .andExpect(jsonPath("$.locale").value(DEFAULT_LOCALE.toString()))
            .andExpect(jsonPath("$.reference").value(DEFAULT_REFERENCE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFreecom_desc_estate() throws Exception {
        // Get the freecom_desc_estate
        restFreecom_desc_estateMockMvc.perform(get("/api/freecom-desc-estates/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFreecom_desc_estate() throws Exception {
        // Initialize the database
        freecom_desc_estateService.save(freecom_desc_estate);

        int databaseSizeBeforeUpdate = freecom_desc_estateRepository.findAll().size();

        // Update the freecom_desc_estate
        Freecom_desc_estate updatedFreecom_desc_estate = new Freecom_desc_estate();
        updatedFreecom_desc_estate.setId(freecom_desc_estate.getId());
        updatedFreecom_desc_estate.setStreet(UPDATED_STREET);
        updatedFreecom_desc_estate.setNoext(UPDATED_NOEXT);
        updatedFreecom_desc_estate.setNoint(UPDATED_NOINT);
        updatedFreecom_desc_estate.setLocale(UPDATED_LOCALE);
        updatedFreecom_desc_estate.setReference(UPDATED_REFERENCE);

        restFreecom_desc_estateMockMvc.perform(put("/api/freecom-desc-estates")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedFreecom_desc_estate)))
                .andExpect(status().isOk());

        // Validate the Freecom_desc_estate in the database
        List<Freecom_desc_estate> freecom_desc_estates = freecom_desc_estateRepository.findAll();
        assertThat(freecom_desc_estates).hasSize(databaseSizeBeforeUpdate);
        Freecom_desc_estate testFreecom_desc_estate = freecom_desc_estates.get(freecom_desc_estates.size() - 1);
        assertThat(testFreecom_desc_estate.getStreet()).isEqualTo(UPDATED_STREET);
        assertThat(testFreecom_desc_estate.getNoext()).isEqualTo(UPDATED_NOEXT);
        assertThat(testFreecom_desc_estate.getNoint()).isEqualTo(UPDATED_NOINT);
        assertThat(testFreecom_desc_estate.getLocale()).isEqualTo(UPDATED_LOCALE);
        assertThat(testFreecom_desc_estate.getReference()).isEqualTo(UPDATED_REFERENCE);
    }

    @Test
    @Transactional
    public void deleteFreecom_desc_estate() throws Exception {
        // Initialize the database
        freecom_desc_estateService.save(freecom_desc_estate);

        int databaseSizeBeforeDelete = freecom_desc_estateRepository.findAll().size();

        // Get the freecom_desc_estate
        restFreecom_desc_estateMockMvc.perform(delete("/api/freecom-desc-estates/{id}", freecom_desc_estate.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Freecom_desc_estate> freecom_desc_estates = freecom_desc_estateRepository.findAll();
        assertThat(freecom_desc_estates).hasSize(databaseSizeBeforeDelete - 1);
    }
}
