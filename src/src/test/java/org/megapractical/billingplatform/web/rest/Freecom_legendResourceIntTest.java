package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Freecom_legend;
import org.megapractical.billingplatform.repository.Freecom_legendRepository;
import org.megapractical.billingplatform.service.Freecom_legendService;

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
 * Test class for the Freecom_legendResource REST controller.
 *
 * @see Freecom_legendResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Freecom_legendResourceIntTest {

    private static final String DEFAULT_TAX_PROVISION = "AAAAA";
    private static final String UPDATED_TAX_PROVISION = "BBBBB";
    private static final String DEFAULT_NORM = "AAAAA";
    private static final String UPDATED_NORM = "BBBBB";
    private static final String DEFAULT_TEXT_LEGEND = "AAAAA";
    private static final String UPDATED_TEXT_LEGEND = "BBBBB";

    @Inject
    private Freecom_legendRepository freecom_legendRepository;

    @Inject
    private Freecom_legendService freecom_legendService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFreecom_legendMockMvc;

    private Freecom_legend freecom_legend;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Freecom_legendResource freecom_legendResource = new Freecom_legendResource();
        ReflectionTestUtils.setField(freecom_legendResource, "freecom_legendService", freecom_legendService);
        this.restFreecom_legendMockMvc = MockMvcBuilders.standaloneSetup(freecom_legendResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        freecom_legend = new Freecom_legend();
        freecom_legend.setTax_provision(DEFAULT_TAX_PROVISION);
        freecom_legend.setNorm(DEFAULT_NORM);
        freecom_legend.setText_legend(DEFAULT_TEXT_LEGEND);
    }

    @Test
    @Transactional
    public void createFreecom_legend() throws Exception {
        int databaseSizeBeforeCreate = freecom_legendRepository.findAll().size();

        // Create the Freecom_legend

        restFreecom_legendMockMvc.perform(post("/api/freecom-legends")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_legend)))
                .andExpect(status().isCreated());

        // Validate the Freecom_legend in the database
        List<Freecom_legend> freecom_legends = freecom_legendRepository.findAll();
        assertThat(freecom_legends).hasSize(databaseSizeBeforeCreate + 1);
        Freecom_legend testFreecom_legend = freecom_legends.get(freecom_legends.size() - 1);
        assertThat(testFreecom_legend.getTax_provision()).isEqualTo(DEFAULT_TAX_PROVISION);
        assertThat(testFreecom_legend.getNorm()).isEqualTo(DEFAULT_NORM);
        assertThat(testFreecom_legend.getText_legend()).isEqualTo(DEFAULT_TEXT_LEGEND);
    }

    @Test
    @Transactional
    public void checkText_legendIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_legendRepository.findAll().size();
        // set the field null
        freecom_legend.setText_legend(null);

        // Create the Freecom_legend, which fails.

        restFreecom_legendMockMvc.perform(post("/api/freecom-legends")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_legend)))
                .andExpect(status().isBadRequest());

        List<Freecom_legend> freecom_legends = freecom_legendRepository.findAll();
        assertThat(freecom_legends).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFreecom_legends() throws Exception {
        // Initialize the database
        freecom_legendRepository.saveAndFlush(freecom_legend);

        // Get all the freecom_legends
        restFreecom_legendMockMvc.perform(get("/api/freecom-legends?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(freecom_legend.getId().intValue())))
                .andExpect(jsonPath("$.[*].tax_provision").value(hasItem(DEFAULT_TAX_PROVISION.toString())))
                .andExpect(jsonPath("$.[*].norm").value(hasItem(DEFAULT_NORM.toString())))
                .andExpect(jsonPath("$.[*].text_legend").value(hasItem(DEFAULT_TEXT_LEGEND.toString())));
    }

    @Test
    @Transactional
    public void getFreecom_legend() throws Exception {
        // Initialize the database
        freecom_legendRepository.saveAndFlush(freecom_legend);

        // Get the freecom_legend
        restFreecom_legendMockMvc.perform(get("/api/freecom-legends/{id}", freecom_legend.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(freecom_legend.getId().intValue()))
            .andExpect(jsonPath("$.tax_provision").value(DEFAULT_TAX_PROVISION.toString()))
            .andExpect(jsonPath("$.norm").value(DEFAULT_NORM.toString()))
            .andExpect(jsonPath("$.text_legend").value(DEFAULT_TEXT_LEGEND.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFreecom_legend() throws Exception {
        // Get the freecom_legend
        restFreecom_legendMockMvc.perform(get("/api/freecom-legends/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFreecom_legend() throws Exception {
        // Initialize the database
        freecom_legendService.save(freecom_legend);

        int databaseSizeBeforeUpdate = freecom_legendRepository.findAll().size();

        // Update the freecom_legend
        Freecom_legend updatedFreecom_legend = new Freecom_legend();
        updatedFreecom_legend.setId(freecom_legend.getId());
        updatedFreecom_legend.setTax_provision(UPDATED_TAX_PROVISION);
        updatedFreecom_legend.setNorm(UPDATED_NORM);
        updatedFreecom_legend.setText_legend(UPDATED_TEXT_LEGEND);

        restFreecom_legendMockMvc.perform(put("/api/freecom-legends")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedFreecom_legend)))
                .andExpect(status().isOk());

        // Validate the Freecom_legend in the database
        List<Freecom_legend> freecom_legends = freecom_legendRepository.findAll();
        assertThat(freecom_legends).hasSize(databaseSizeBeforeUpdate);
        Freecom_legend testFreecom_legend = freecom_legends.get(freecom_legends.size() - 1);
        assertThat(testFreecom_legend.getTax_provision()).isEqualTo(UPDATED_TAX_PROVISION);
        assertThat(testFreecom_legend.getNorm()).isEqualTo(UPDATED_NORM);
        assertThat(testFreecom_legend.getText_legend()).isEqualTo(UPDATED_TEXT_LEGEND);
    }

    @Test
    @Transactional
    public void deleteFreecom_legend() throws Exception {
        // Initialize the database
        freecom_legendService.save(freecom_legend);

        int databaseSizeBeforeDelete = freecom_legendRepository.findAll().size();

        // Get the freecom_legend
        restFreecom_legendMockMvc.perform(delete("/api/freecom-legends/{id}", freecom_legend.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Freecom_legend> freecom_legends = freecom_legendRepository.findAll();
        assertThat(freecom_legends).hasSize(databaseSizeBeforeDelete - 1);
    }
}
