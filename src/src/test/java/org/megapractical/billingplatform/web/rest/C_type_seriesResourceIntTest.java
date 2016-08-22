package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.C_type_series;
import org.megapractical.billingplatform.repository.C_type_seriesRepository;
import org.megapractical.billingplatform.service.C_type_seriesService;

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
 * Test class for the C_type_seriesResource REST controller.
 *
 * @see C_type_seriesResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class C_type_seriesResourceIntTest {

    private static final String DEFAULT_VALUE = "AAAAA";
    private static final String UPDATED_VALUE = "BBBBB";

    @Inject
    private C_type_seriesRepository c_type_seriesRepository;

    @Inject
    private C_type_seriesService c_type_seriesService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restC_type_seriesMockMvc;

    private C_type_series c_type_series;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        C_type_seriesResource c_type_seriesResource = new C_type_seriesResource();
        ReflectionTestUtils.setField(c_type_seriesResource, "c_type_seriesService", c_type_seriesService);
        this.restC_type_seriesMockMvc = MockMvcBuilders.standaloneSetup(c_type_seriesResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        c_type_series = new C_type_series();
        c_type_series.setValue(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createC_type_series() throws Exception {
        int databaseSizeBeforeCreate = c_type_seriesRepository.findAll().size();

        // Create the C_type_series

        restC_type_seriesMockMvc.perform(post("/api/c-type-series")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(c_type_series)))
                .andExpect(status().isCreated());

        // Validate the C_type_series in the database
        List<C_type_series> c_type_series = c_type_seriesRepository.findAll();
        assertThat(c_type_series).hasSize(databaseSizeBeforeCreate + 1);
        C_type_series testC_type_series = c_type_series.get(c_type_series.size() - 1);
        assertThat(testC_type_series.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = c_type_seriesRepository.findAll().size();
        // set the field null
        c_type_series.setValue(null);

        // Create the C_type_series, which fails.

        restC_type_seriesMockMvc.perform(post("/api/c-type-series")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(c_type_series)))
                .andExpect(status().isBadRequest());

        List<C_type_series> c_type_series = c_type_seriesRepository.findAll();
        assertThat(c_type_series).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllC_type_series() throws Exception {
        // Initialize the database
        c_type_seriesRepository.saveAndFlush(c_type_series);

        // Get all the c_type_series
        restC_type_seriesMockMvc.perform(get("/api/c-type-series?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(c_type_series.getId().intValue())))
                .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())));
    }

    @Test
    @Transactional
    public void getC_type_series() throws Exception {
        // Initialize the database
        c_type_seriesRepository.saveAndFlush(c_type_series);

        // Get the c_type_series
        restC_type_seriesMockMvc.perform(get("/api/c-type-series/{id}", c_type_series.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(c_type_series.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingC_type_series() throws Exception {
        // Get the c_type_series
        restC_type_seriesMockMvc.perform(get("/api/c-type-series/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateC_type_series() throws Exception {
        // Initialize the database
        c_type_seriesService.save(c_type_series);

        int databaseSizeBeforeUpdate = c_type_seriesRepository.findAll().size();

        // Update the c_type_series
        C_type_series updatedC_type_series = new C_type_series();
        updatedC_type_series.setId(c_type_series.getId());
        updatedC_type_series.setValue(UPDATED_VALUE);

        restC_type_seriesMockMvc.perform(put("/api/c-type-series")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedC_type_series)))
                .andExpect(status().isOk());

        // Validate the C_type_series in the database
        List<C_type_series> c_type_series = c_type_seriesRepository.findAll();
        assertThat(c_type_series).hasSize(databaseSizeBeforeUpdate);
        C_type_series testC_type_series = c_type_series.get(c_type_series.size() - 1);
        assertThat(testC_type_series.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void deleteC_type_series() throws Exception {
        // Initialize the database
        c_type_seriesService.save(c_type_series);

        int databaseSizeBeforeDelete = c_type_seriesRepository.findAll().size();

        // Get the c_type_series
        restC_type_seriesMockMvc.perform(delete("/api/c-type-series/{id}", c_type_series.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<C_type_series> c_type_series = c_type_seriesRepository.findAll();
        assertThat(c_type_series).hasSize(databaseSizeBeforeDelete - 1);
    }
}
