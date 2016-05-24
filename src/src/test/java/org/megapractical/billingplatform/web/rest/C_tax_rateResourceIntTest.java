package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.C_tax_rate;
import org.megapractical.billingplatform.repository.C_tax_rateRepository;
import org.megapractical.billingplatform.service.C_tax_rateService;

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
 * Test class for the C_tax_rateResource REST controller.
 *
 * @see C_tax_rateResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class C_tax_rateResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private C_tax_rateRepository c_tax_rateRepository;

    @Inject
    private C_tax_rateService c_tax_rateService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restC_tax_rateMockMvc;

    private C_tax_rate c_tax_rate;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        C_tax_rateResource c_tax_rateResource = new C_tax_rateResource();
        ReflectionTestUtils.setField(c_tax_rateResource, "c_tax_rateService", c_tax_rateService);
        this.restC_tax_rateMockMvc = MockMvcBuilders.standaloneSetup(c_tax_rateResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        c_tax_rate = new C_tax_rate();
        c_tax_rate.setName(DEFAULT_NAME);
        c_tax_rate.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createC_tax_rate() throws Exception {
        int databaseSizeBeforeCreate = c_tax_rateRepository.findAll().size();

        // Create the C_tax_rate

        restC_tax_rateMockMvc.perform(post("/api/c-tax-rates")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(c_tax_rate)))
                .andExpect(status().isCreated());

        // Validate the C_tax_rate in the database
        List<C_tax_rate> c_tax_rates = c_tax_rateRepository.findAll();
        assertThat(c_tax_rates).hasSize(databaseSizeBeforeCreate + 1);
        C_tax_rate testC_tax_rate = c_tax_rates.get(c_tax_rates.size() - 1);
        assertThat(testC_tax_rate.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testC_tax_rate.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllC_tax_rates() throws Exception {
        // Initialize the database
        c_tax_rateRepository.saveAndFlush(c_tax_rate);

        // Get all the c_tax_rates
        restC_tax_rateMockMvc.perform(get("/api/c-tax-rates?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(c_tax_rate.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getC_tax_rate() throws Exception {
        // Initialize the database
        c_tax_rateRepository.saveAndFlush(c_tax_rate);

        // Get the c_tax_rate
        restC_tax_rateMockMvc.perform(get("/api/c-tax-rates/{id}", c_tax_rate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(c_tax_rate.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingC_tax_rate() throws Exception {
        // Get the c_tax_rate
        restC_tax_rateMockMvc.perform(get("/api/c-tax-rates/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateC_tax_rate() throws Exception {
        // Initialize the database
        c_tax_rateService.save(c_tax_rate);

        int databaseSizeBeforeUpdate = c_tax_rateRepository.findAll().size();

        // Update the c_tax_rate
        C_tax_rate updatedC_tax_rate = new C_tax_rate();
        updatedC_tax_rate.setId(c_tax_rate.getId());
        updatedC_tax_rate.setName(UPDATED_NAME);
        updatedC_tax_rate.setDescription(UPDATED_DESCRIPTION);

        restC_tax_rateMockMvc.perform(put("/api/c-tax-rates")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedC_tax_rate)))
                .andExpect(status().isOk());

        // Validate the C_tax_rate in the database
        List<C_tax_rate> c_tax_rates = c_tax_rateRepository.findAll();
        assertThat(c_tax_rates).hasSize(databaseSizeBeforeUpdate);
        C_tax_rate testC_tax_rate = c_tax_rates.get(c_tax_rates.size() - 1);
        assertThat(testC_tax_rate.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testC_tax_rate.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteC_tax_rate() throws Exception {
        // Initialize the database
        c_tax_rateService.save(c_tax_rate);

        int databaseSizeBeforeDelete = c_tax_rateRepository.findAll().size();

        // Get the c_tax_rate
        restC_tax_rateMockMvc.perform(delete("/api/c-tax-rates/{id}", c_tax_rate.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<C_tax_rate> c_tax_rates = c_tax_rateRepository.findAll();
        assertThat(c_tax_rates).hasSize(databaseSizeBeforeDelete - 1);
    }
}
