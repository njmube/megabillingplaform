package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Freecom_spei;
import org.megapractical.billingplatform.repository.Freecom_speiRepository;
import org.megapractical.billingplatform.service.Freecom_speiService;

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
 * Test class for the Freecom_speiResource REST controller.
 *
 * @see Freecom_speiResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Freecom_speiResourceIntTest {


    @Inject
    private Freecom_speiRepository freecom_speiRepository;

    @Inject
    private Freecom_speiService freecom_speiService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFreecom_speiMockMvc;

    private Freecom_spei freecom_spei;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Freecom_speiResource freecom_speiResource = new Freecom_speiResource();
        ReflectionTestUtils.setField(freecom_speiResource, "freecom_speiService", freecom_speiService);
        this.restFreecom_speiMockMvc = MockMvcBuilders.standaloneSetup(freecom_speiResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        freecom_spei = new Freecom_spei();
    }

    @Test
    @Transactional
    public void createFreecom_spei() throws Exception {
        int databaseSizeBeforeCreate = freecom_speiRepository.findAll().size();

        // Create the Freecom_spei

        restFreecom_speiMockMvc.perform(post("/api/freecom-speis")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_spei)))
                .andExpect(status().isCreated());

        // Validate the Freecom_spei in the database
        List<Freecom_spei> freecom_speis = freecom_speiRepository.findAll();
        assertThat(freecom_speis).hasSize(databaseSizeBeforeCreate + 1);
        Freecom_spei testFreecom_spei = freecom_speis.get(freecom_speis.size() - 1);
    }

    @Test
    @Transactional
    public void getAllFreecom_speis() throws Exception {
        // Initialize the database
        freecom_speiRepository.saveAndFlush(freecom_spei);

        // Get all the freecom_speis
        restFreecom_speiMockMvc.perform(get("/api/freecom-speis?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(freecom_spei.getId().intValue())));
    }

    @Test
    @Transactional
    public void getFreecom_spei() throws Exception {
        // Initialize the database
        freecom_speiRepository.saveAndFlush(freecom_spei);

        // Get the freecom_spei
        restFreecom_speiMockMvc.perform(get("/api/freecom-speis/{id}", freecom_spei.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(freecom_spei.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingFreecom_spei() throws Exception {
        // Get the freecom_spei
        restFreecom_speiMockMvc.perform(get("/api/freecom-speis/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFreecom_spei() throws Exception {
        // Initialize the database
        freecom_speiService.save(freecom_spei);

        int databaseSizeBeforeUpdate = freecom_speiRepository.findAll().size();

        // Update the freecom_spei
        Freecom_spei updatedFreecom_spei = new Freecom_spei();
        updatedFreecom_spei.setId(freecom_spei.getId());

        restFreecom_speiMockMvc.perform(put("/api/freecom-speis")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedFreecom_spei)))
                .andExpect(status().isOk());

        // Validate the Freecom_spei in the database
        List<Freecom_spei> freecom_speis = freecom_speiRepository.findAll();
        assertThat(freecom_speis).hasSize(databaseSizeBeforeUpdate);
        Freecom_spei testFreecom_spei = freecom_speis.get(freecom_speis.size() - 1);
    }

    @Test
    @Transactional
    public void deleteFreecom_spei() throws Exception {
        // Initialize the database
        freecom_speiService.save(freecom_spei);

        int databaseSizeBeforeDelete = freecom_speiRepository.findAll().size();

        // Get the freecom_spei
        restFreecom_speiMockMvc.perform(delete("/api/freecom-speis/{id}", freecom_spei.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Freecom_spei> freecom_speis = freecom_speiRepository.findAll();
        assertThat(freecom_speis).hasSize(databaseSizeBeforeDelete - 1);
    }
}
