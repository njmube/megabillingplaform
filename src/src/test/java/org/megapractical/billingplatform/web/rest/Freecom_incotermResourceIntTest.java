package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Freecom_incoterm;
import org.megapractical.billingplatform.repository.Freecom_incotermRepository;
import org.megapractical.billingplatform.service.Freecom_incotermService;

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
 * Test class for the Freecom_incotermResource REST controller.
 *
 * @see Freecom_incotermResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Freecom_incotermResourceIntTest {

    private static final String DEFAULT_VALUE = "AAAAA";
    private static final String UPDATED_VALUE = "BBBBB";

    @Inject
    private Freecom_incotermRepository freecom_incotermRepository;

    @Inject
    private Freecom_incotermService freecom_incotermService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFreecom_incotermMockMvc;

    private Freecom_incoterm freecom_incoterm;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Freecom_incotermResource freecom_incotermResource = new Freecom_incotermResource();
        ReflectionTestUtils.setField(freecom_incotermResource, "freecom_incotermService", freecom_incotermService);
        this.restFreecom_incotermMockMvc = MockMvcBuilders.standaloneSetup(freecom_incotermResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        freecom_incoterm = new Freecom_incoterm();
        freecom_incoterm.setValue(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createFreecom_incoterm() throws Exception {
        int databaseSizeBeforeCreate = freecom_incotermRepository.findAll().size();

        // Create the Freecom_incoterm

        restFreecom_incotermMockMvc.perform(post("/api/freecom-incoterms")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_incoterm)))
                .andExpect(status().isCreated());

        // Validate the Freecom_incoterm in the database
        List<Freecom_incoterm> freecom_incoterms = freecom_incotermRepository.findAll();
        assertThat(freecom_incoterms).hasSize(databaseSizeBeforeCreate + 1);
        Freecom_incoterm testFreecom_incoterm = freecom_incoterms.get(freecom_incoterms.size() - 1);
        assertThat(testFreecom_incoterm.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_incotermRepository.findAll().size();
        // set the field null
        freecom_incoterm.setValue(null);

        // Create the Freecom_incoterm, which fails.

        restFreecom_incotermMockMvc.perform(post("/api/freecom-incoterms")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_incoterm)))
                .andExpect(status().isBadRequest());

        List<Freecom_incoterm> freecom_incoterms = freecom_incotermRepository.findAll();
        assertThat(freecom_incoterms).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFreecom_incoterms() throws Exception {
        // Initialize the database
        freecom_incotermRepository.saveAndFlush(freecom_incoterm);

        // Get all the freecom_incoterms
        restFreecom_incotermMockMvc.perform(get("/api/freecom-incoterms?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(freecom_incoterm.getId().intValue())))
                .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())));
    }

    @Test
    @Transactional
    public void getFreecom_incoterm() throws Exception {
        // Initialize the database
        freecom_incotermRepository.saveAndFlush(freecom_incoterm);

        // Get the freecom_incoterm
        restFreecom_incotermMockMvc.perform(get("/api/freecom-incoterms/{id}", freecom_incoterm.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(freecom_incoterm.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFreecom_incoterm() throws Exception {
        // Get the freecom_incoterm
        restFreecom_incotermMockMvc.perform(get("/api/freecom-incoterms/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFreecom_incoterm() throws Exception {
        // Initialize the database
        freecom_incotermService.save(freecom_incoterm);

        int databaseSizeBeforeUpdate = freecom_incotermRepository.findAll().size();

        // Update the freecom_incoterm
        Freecom_incoterm updatedFreecom_incoterm = new Freecom_incoterm();
        updatedFreecom_incoterm.setId(freecom_incoterm.getId());
        updatedFreecom_incoterm.setValue(UPDATED_VALUE);

        restFreecom_incotermMockMvc.perform(put("/api/freecom-incoterms")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedFreecom_incoterm)))
                .andExpect(status().isOk());

        // Validate the Freecom_incoterm in the database
        List<Freecom_incoterm> freecom_incoterms = freecom_incotermRepository.findAll();
        assertThat(freecom_incoterms).hasSize(databaseSizeBeforeUpdate);
        Freecom_incoterm testFreecom_incoterm = freecom_incoterms.get(freecom_incoterms.size() - 1);
        assertThat(testFreecom_incoterm.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void deleteFreecom_incoterm() throws Exception {
        // Initialize the database
        freecom_incotermService.save(freecom_incoterm);

        int databaseSizeBeforeDelete = freecom_incotermRepository.findAll().size();

        // Get the freecom_incoterm
        restFreecom_incotermMockMvc.perform(delete("/api/freecom-incoterms/{id}", freecom_incoterm.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Freecom_incoterm> freecom_incoterms = freecom_incotermRepository.findAll();
        assertThat(freecom_incoterms).hasSize(databaseSizeBeforeDelete - 1);
    }
}
