package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.C_country;
import org.megapractical.billingplatform.repository.C_countryRepository;
import org.megapractical.billingplatform.service.C_countryService;

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
 * Test class for the C_countryResource REST controller.
 *
 * @see C_countryResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class C_countryResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_ABREV = "AAAAA";
    private static final String UPDATED_ABREV = "BBBBB";

    @Inject
    private C_countryRepository c_countryRepository;

    @Inject
    private C_countryService c_countryService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restC_countryMockMvc;

    private C_country c_country;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        C_countryResource c_countryResource = new C_countryResource();
        ReflectionTestUtils.setField(c_countryResource, "c_countryService", c_countryService);
        this.restC_countryMockMvc = MockMvcBuilders.standaloneSetup(c_countryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        c_country = new C_country();
        c_country.setName(DEFAULT_NAME);
        c_country.setAbrev(DEFAULT_ABREV);
    }

    @Test
    @Transactional
    public void createC_country() throws Exception {
        int databaseSizeBeforeCreate = c_countryRepository.findAll().size();

        // Create the C_country

        restC_countryMockMvc.perform(post("/api/c-countries")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(c_country)))
                .andExpect(status().isCreated());

        // Validate the C_country in the database
        List<C_country> c_countries = c_countryRepository.findAll();
        assertThat(c_countries).hasSize(databaseSizeBeforeCreate + 1);
        C_country testC_country = c_countries.get(c_countries.size() - 1);
        assertThat(testC_country.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testC_country.getAbrev()).isEqualTo(DEFAULT_ABREV);
    }

    @Test
    @Transactional
    public void getAllC_countries() throws Exception {
        // Initialize the database
        c_countryRepository.saveAndFlush(c_country);

        // Get all the c_countries
        restC_countryMockMvc.perform(get("/api/c-countries?sort=id,desc&pg=1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(c_country.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].abrev").value(hasItem(DEFAULT_ABREV.toString())));
    }

    @Test
    @Transactional
    public void getC_country() throws Exception {
        // Initialize the database
        c_countryRepository.saveAndFlush(c_country);

        // Get the c_country
        restC_countryMockMvc.perform(get("/api/c-countries/{id}", c_country.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(c_country.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.abrev").value(DEFAULT_ABREV.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingC_country() throws Exception {
        // Get the c_country
        restC_countryMockMvc.perform(get("/api/c-countries/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateC_country() throws Exception {
        // Initialize the database
        c_countryService.save(c_country);

        int databaseSizeBeforeUpdate = c_countryRepository.findAll().size();

        // Update the c_country
        C_country updatedC_country = new C_country();
        updatedC_country.setId(c_country.getId());
        updatedC_country.setName(UPDATED_NAME);
        updatedC_country.setAbrev(UPDATED_ABREV);

        restC_countryMockMvc.perform(put("/api/c-countries")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedC_country)))
                .andExpect(status().isOk());

        // Validate the C_country in the database
        List<C_country> c_countries = c_countryRepository.findAll();
        assertThat(c_countries).hasSize(databaseSizeBeforeUpdate);
        C_country testC_country = c_countries.get(c_countries.size() - 1);
        assertThat(testC_country.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testC_country.getAbrev()).isEqualTo(UPDATED_ABREV);
    }

    @Test
    @Transactional
    public void deleteC_country() throws Exception {
        // Initialize the database
        c_countryService.save(c_country);

        int databaseSizeBeforeDelete = c_countryRepository.findAll().size();

        // Get the c_country
        restC_countryMockMvc.perform(delete("/api/c-countries/{id}", c_country.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<C_country> c_countries = c_countryRepository.findAll();
        assertThat(c_countries).hasSize(databaseSizeBeforeDelete - 1);
    }
}
