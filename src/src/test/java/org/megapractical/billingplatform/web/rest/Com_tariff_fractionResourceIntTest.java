package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Com_tariff_fraction;
import org.megapractical.billingplatform.repository.Com_tariff_fractionRepository;
import org.megapractical.billingplatform.service.Com_tariff_fractionService;

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
 * Test class for the Com_tariff_fractionResource REST controller.
 *
 * @see Com_tariff_fractionResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Com_tariff_fractionResourceIntTest {

    private static final String DEFAULT_VALUE = "AAAAA";
    private static final String UPDATED_VALUE = "BBBBB";

    @Inject
    private Com_tariff_fractionRepository com_tariff_fractionRepository;

    @Inject
    private Com_tariff_fractionService com_tariff_fractionService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCom_tariff_fractionMockMvc;

    private Com_tariff_fraction com_tariff_fraction;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Com_tariff_fractionResource com_tariff_fractionResource = new Com_tariff_fractionResource();
        ReflectionTestUtils.setField(com_tariff_fractionResource, "com_tariff_fractionService", com_tariff_fractionService);
        this.restCom_tariff_fractionMockMvc = MockMvcBuilders.standaloneSetup(com_tariff_fractionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        com_tariff_fraction = new Com_tariff_fraction();
        com_tariff_fraction.setValue(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createCom_tariff_fraction() throws Exception {
        int databaseSizeBeforeCreate = com_tariff_fractionRepository.findAll().size();

        // Create the Com_tariff_fraction

        restCom_tariff_fractionMockMvc.perform(post("/api/com-tariff-fractions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_tariff_fraction)))
                .andExpect(status().isCreated());

        // Validate the Com_tariff_fraction in the database
        List<Com_tariff_fraction> com_tariff_fractions = com_tariff_fractionRepository.findAll();
        assertThat(com_tariff_fractions).hasSize(databaseSizeBeforeCreate + 1);
        Com_tariff_fraction testCom_tariff_fraction = com_tariff_fractions.get(com_tariff_fractions.size() - 1);
        assertThat(testCom_tariff_fraction.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_tariff_fractionRepository.findAll().size();
        // set the field null
        com_tariff_fraction.setValue(null);

        // Create the Com_tariff_fraction, which fails.

        restCom_tariff_fractionMockMvc.perform(post("/api/com-tariff-fractions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_tariff_fraction)))
                .andExpect(status().isBadRequest());

        List<Com_tariff_fraction> com_tariff_fractions = com_tariff_fractionRepository.findAll();
        assertThat(com_tariff_fractions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCom_tariff_fractions() throws Exception {
        // Initialize the database
        com_tariff_fractionRepository.saveAndFlush(com_tariff_fraction);

        // Get all the com_tariff_fractions
        restCom_tariff_fractionMockMvc.perform(get("/api/com-tariff-fractions?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(com_tariff_fraction.getId().intValue())))
                .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())));
    }

    @Test
    @Transactional
    public void getCom_tariff_fraction() throws Exception {
        // Initialize the database
        com_tariff_fractionRepository.saveAndFlush(com_tariff_fraction);

        // Get the com_tariff_fraction
        restCom_tariff_fractionMockMvc.perform(get("/api/com-tariff-fractions/{id}", com_tariff_fraction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(com_tariff_fraction.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCom_tariff_fraction() throws Exception {
        // Get the com_tariff_fraction
        restCom_tariff_fractionMockMvc.perform(get("/api/com-tariff-fractions/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCom_tariff_fraction() throws Exception {
        // Initialize the database
        com_tariff_fractionService.save(com_tariff_fraction);

        int databaseSizeBeforeUpdate = com_tariff_fractionRepository.findAll().size();

        // Update the com_tariff_fraction
        Com_tariff_fraction updatedCom_tariff_fraction = new Com_tariff_fraction();
        updatedCom_tariff_fraction.setId(com_tariff_fraction.getId());
        updatedCom_tariff_fraction.setValue(UPDATED_VALUE);

        restCom_tariff_fractionMockMvc.perform(put("/api/com-tariff-fractions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCom_tariff_fraction)))
                .andExpect(status().isOk());

        // Validate the Com_tariff_fraction in the database
        List<Com_tariff_fraction> com_tariff_fractions = com_tariff_fractionRepository.findAll();
        assertThat(com_tariff_fractions).hasSize(databaseSizeBeforeUpdate);
        Com_tariff_fraction testCom_tariff_fraction = com_tariff_fractions.get(com_tariff_fractions.size() - 1);
        assertThat(testCom_tariff_fraction.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void deleteCom_tariff_fraction() throws Exception {
        // Initialize the database
        com_tariff_fractionService.save(com_tariff_fraction);

        int databaseSizeBeforeDelete = com_tariff_fractionRepository.findAll().size();

        // Get the com_tariff_fraction
        restCom_tariff_fractionMockMvc.perform(delete("/api/com-tariff-fractions/{id}", com_tariff_fraction.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Com_tariff_fraction> com_tariff_fractions = com_tariff_fractionRepository.findAll();
        assertThat(com_tariff_fractions).hasSize(databaseSizeBeforeDelete - 1);
    }
}
