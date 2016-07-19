package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Tax_regime;
import org.megapractical.billingplatform.repository.Tax_regimeRepository;
import org.megapractical.billingplatform.service.Tax_regimeService;

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
 * Test class for the Tax_regimeResource REST controller.
 *
 * @see Tax_regimeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Tax_regimeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private Tax_regimeRepository tax_regimeRepository;

    @Inject
    private Tax_regimeService tax_regimeService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restTax_regimeMockMvc;

    private Tax_regime tax_regime;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Tax_regimeResource tax_regimeResource = new Tax_regimeResource();
        ReflectionTestUtils.setField(tax_regimeResource, "tax_regimeService", tax_regimeService);
        this.restTax_regimeMockMvc = MockMvcBuilders.standaloneSetup(tax_regimeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        tax_regime = new Tax_regime();
        tax_regime.setName(DEFAULT_NAME);
        tax_regime.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createTax_regime() throws Exception {
        int databaseSizeBeforeCreate = tax_regimeRepository.findAll().size();

        // Create the Tax_regime

        restTax_regimeMockMvc.perform(post("/api/tax-regimes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(tax_regime)))
                .andExpect(status().isCreated());

        // Validate the Tax_regime in the database
        List<Tax_regime> tax_regimes = tax_regimeRepository.findAll();
        assertThat(tax_regimes).hasSize(databaseSizeBeforeCreate + 1);
        Tax_regime testTax_regime = tax_regimes.get(tax_regimes.size() - 1);
        assertThat(testTax_regime.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTax_regime.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllTax_regimes() throws Exception {
        /*
        // Initialize the database
        tax_regimeRepository.saveAndFlush(tax_regime);

        // Get all the tax_regimes
        restTax_regimeMockMvc.perform(get("/api/tax-regimes?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(tax_regime.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));*/
    }

    @Test
    @Transactional
    public void getTax_regime() throws Exception {
        // Initialize the database
        tax_regimeRepository.saveAndFlush(tax_regime);

        // Get the tax_regime
        restTax_regimeMockMvc.perform(get("/api/tax-regimes/{id}", tax_regime.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(tax_regime.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTax_regime() throws Exception {
        // Get the tax_regime
        restTax_regimeMockMvc.perform(get("/api/tax-regimes/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTax_regime() throws Exception {
        // Initialize the database
        tax_regimeService.save(tax_regime);

        int databaseSizeBeforeUpdate = tax_regimeRepository.findAll().size();

        // Update the tax_regime
        Tax_regime updatedTax_regime = new Tax_regime();
        updatedTax_regime.setId(tax_regime.getId());
        updatedTax_regime.setName(UPDATED_NAME);
        updatedTax_regime.setDescription(UPDATED_DESCRIPTION);

        restTax_regimeMockMvc.perform(put("/api/tax-regimes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedTax_regime)))
                .andExpect(status().isOk());

        // Validate the Tax_regime in the database
        List<Tax_regime> tax_regimes = tax_regimeRepository.findAll();
        assertThat(tax_regimes).hasSize(databaseSizeBeforeUpdate);
        Tax_regime testTax_regime = tax_regimes.get(tax_regimes.size() - 1);
        assertThat(testTax_regime.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTax_regime.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteTax_regime() throws Exception {
        // Initialize the database
        tax_regimeService.save(tax_regime);

        int databaseSizeBeforeDelete = tax_regimeRepository.findAll().size();

        // Get the tax_regime
        restTax_regimeMockMvc.perform(delete("/api/tax-regimes/{id}", tax_regime.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Tax_regime> tax_regimes = tax_regimeRepository.findAll();
        assertThat(tax_regimes).hasSize(databaseSizeBeforeDelete - 1);
    }
}
