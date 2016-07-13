package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.C_money;
import org.megapractical.billingplatform.repository.C_moneyRepository;
import org.megapractical.billingplatform.service.C_moneyService;

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
 * Test class for the C_moneyResource REST controller.
 *
 * @see C_moneyResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class C_moneyResourceIntTest {

    private static final String DEFAULT_NAME = "AAA";
    private static final String UPDATED_NAME = "BBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private C_moneyRepository c_moneyRepository;

    @Inject
    private C_moneyService c_moneyService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restC_moneyMockMvc;

    private C_money c_money;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        C_moneyResource c_moneyResource = new C_moneyResource();
        ReflectionTestUtils.setField(c_moneyResource, "c_moneyService", c_moneyService);
        this.restC_moneyMockMvc = MockMvcBuilders.standaloneSetup(c_moneyResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        c_money = new C_money();
        c_money.setName(DEFAULT_NAME);
        c_money.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createC_money() throws Exception {
        int databaseSizeBeforeCreate = c_moneyRepository.findAll().size();

        // Create the C_money

        restC_moneyMockMvc.perform(post("/api/c-monies")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(c_money)))
                .andExpect(status().isCreated());

        // Validate the C_money in the database
        List<C_money> c_monies = c_moneyRepository.findAll();
        assertThat(c_monies).hasSize(databaseSizeBeforeCreate + 1);
        C_money testC_money = c_monies.get(c_monies.size() - 1);
        assertThat(testC_money.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testC_money.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = c_moneyRepository.findAll().size();
        // set the field null
        c_money.setName(null);

        // Create the C_money, which fails.

        restC_moneyMockMvc.perform(post("/api/c-monies")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(c_money)))
                .andExpect(status().isBadRequest());

        List<C_money> c_monies = c_moneyRepository.findAll();
        assertThat(c_monies).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllC_monies() throws Exception {
        // Initialize the database
        /*
        c_moneyRepository.saveAndFlush(c_money);

        // Get all the c_monies
        restC_moneyMockMvc.perform(get("/api/c-monies?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(c_money.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));*/
    }

    @Test
    @Transactional
    public void getC_money() throws Exception {
        // Initialize the database
        c_moneyRepository.saveAndFlush(c_money);

        // Get the c_money
        restC_moneyMockMvc.perform(get("/api/c-monies/{id}", c_money.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(c_money.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingC_money() throws Exception {
        // Get the c_money
        restC_moneyMockMvc.perform(get("/api/c-monies/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateC_money() throws Exception {
        // Initialize the database
        c_moneyService.save(c_money);

        int databaseSizeBeforeUpdate = c_moneyRepository.findAll().size();

        // Update the c_money
        C_money updatedC_money = new C_money();
        updatedC_money.setId(c_money.getId());
        updatedC_money.setName(UPDATED_NAME);
        updatedC_money.setDescription(UPDATED_DESCRIPTION);

        restC_moneyMockMvc.perform(put("/api/c-monies")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedC_money)))
                .andExpect(status().isOk());

        // Validate the C_money in the database
        List<C_money> c_monies = c_moneyRepository.findAll();
        assertThat(c_monies).hasSize(databaseSizeBeforeUpdate);
        C_money testC_money = c_monies.get(c_monies.size() - 1);
        assertThat(testC_money.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testC_money.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteC_money() throws Exception {
        // Initialize the database
        c_moneyService.save(c_money);

        int databaseSizeBeforeDelete = c_moneyRepository.findAll().size();

        // Get the c_money
        restC_moneyMockMvc.perform(delete("/api/c-monies/{id}", c_money.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<C_money> c_monies = c_moneyRepository.findAll();
        assertThat(c_monies).hasSize(databaseSizeBeforeDelete - 1);
    }
}
