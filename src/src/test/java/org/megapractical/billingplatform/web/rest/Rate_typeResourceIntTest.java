package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Rate_type;
import org.megapractical.billingplatform.repository.Rate_typeRepository;
import org.megapractical.billingplatform.service.Rate_typeService;

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
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the Rate_typeResource REST controller.
 *
 * @see Rate_typeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Rate_typeResourceIntTest {

    private static final String DEFAULT_NAME = "AAA";
    private static final String UPDATED_NAME = "BBB";

    private static final BigDecimal DEFAULT_VALUE = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALUE = new BigDecimal(2);
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private Rate_typeRepository rate_typeRepository;

    @Inject
    private Rate_typeService rate_typeService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restRate_typeMockMvc;

    private Rate_type rate_type;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Rate_typeResource rate_typeResource = new Rate_typeResource();
        ReflectionTestUtils.setField(rate_typeResource, "rate_typeService", rate_typeService);
        this.restRate_typeMockMvc = MockMvcBuilders.standaloneSetup(rate_typeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        rate_type = new Rate_type();
        rate_type.setName(DEFAULT_NAME);
        rate_type.setValue(DEFAULT_VALUE);
        rate_type.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createRate_type() throws Exception {
        int databaseSizeBeforeCreate = rate_typeRepository.findAll().size();

        // Create the Rate_type

        restRate_typeMockMvc.perform(post("/api/rate-types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(rate_type)))
                .andExpect(status().isCreated());

        // Validate the Rate_type in the database
        List<Rate_type> rate_types = rate_typeRepository.findAll();
        assertThat(rate_types).hasSize(databaseSizeBeforeCreate + 1);
        Rate_type testRate_type = rate_types.get(rate_types.size() - 1);
        assertThat(testRate_type.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRate_type.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testRate_type.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = rate_typeRepository.findAll().size();
        // set the field null
        rate_type.setName(null);

        // Create the Rate_type, which fails.

        restRate_typeMockMvc.perform(post("/api/rate-types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(rate_type)))
                .andExpect(status().isBadRequest());

        List<Rate_type> rate_types = rate_typeRepository.findAll();
        assertThat(rate_types).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = rate_typeRepository.findAll().size();
        // set the field null
        rate_type.setValue(null);

        // Create the Rate_type, which fails.

        restRate_typeMockMvc.perform(post("/api/rate-types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(rate_type)))
                .andExpect(status().isBadRequest());

        List<Rate_type> rate_types = rate_typeRepository.findAll();
        assertThat(rate_types).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRate_types() throws Exception {
        // Initialize the database
        rate_typeRepository.saveAndFlush(rate_type);

        // Get all the rate_types
        restRate_typeMockMvc.perform(get("/api/rate-types?sort=id,desc&filtername= "))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(rate_type.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.intValue())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getRate_type() throws Exception {
        // Initialize the database
        rate_typeRepository.saveAndFlush(rate_type);

        // Get the rate_type
        restRate_typeMockMvc.perform(get("/api/rate-types/{id}", rate_type.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(rate_type.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRate_type() throws Exception {
        // Get the rate_type
        restRate_typeMockMvc.perform(get("/api/rate-types/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRate_type() throws Exception {
        // Initialize the database
        rate_typeService.save(rate_type);

        int databaseSizeBeforeUpdate = rate_typeRepository.findAll().size();

        // Update the rate_type
        Rate_type updatedRate_type = new Rate_type();
        updatedRate_type.setId(rate_type.getId());
        updatedRate_type.setName(UPDATED_NAME);
        updatedRate_type.setValue(UPDATED_VALUE);
        updatedRate_type.setDescription(UPDATED_DESCRIPTION);

        restRate_typeMockMvc.perform(put("/api/rate-types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedRate_type)))
                .andExpect(status().isOk());

        // Validate the Rate_type in the database
        List<Rate_type> rate_types = rate_typeRepository.findAll();
        assertThat(rate_types).hasSize(databaseSizeBeforeUpdate);
        Rate_type testRate_type = rate_types.get(rate_types.size() - 1);
        assertThat(testRate_type.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRate_type.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testRate_type.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteRate_type() throws Exception {
        // Initialize the database
        rate_typeService.save(rate_type);

        int databaseSizeBeforeDelete = rate_typeRepository.findAll().size();

        // Get the rate_type
        restRate_typeMockMvc.perform(delete("/api/rate-types/{id}", rate_type.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Rate_type> rate_types = rate_typeRepository.findAll();
        assertThat(rate_types).hasSize(databaseSizeBeforeDelete - 1);
    }
}
