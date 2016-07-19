package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Way_payment;
import org.megapractical.billingplatform.repository.Way_paymentRepository;
import org.megapractical.billingplatform.service.Way_paymentService;

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
 * Test class for the Way_paymentResource REST controller.
 *
 * @see Way_paymentResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Way_paymentResourceIntTest {

    private static final String DEFAULT_NAME = "AAA";
    private static final String UPDATED_NAME = "BBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private Way_paymentRepository way_paymentRepository;

    @Inject
    private Way_paymentService way_paymentService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restWay_paymentMockMvc;

    private Way_payment way_payment;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Way_paymentResource way_paymentResource = new Way_paymentResource();
        ReflectionTestUtils.setField(way_paymentResource, "way_paymentService", way_paymentService);
        this.restWay_paymentMockMvc = MockMvcBuilders.standaloneSetup(way_paymentResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        way_payment = new Way_payment();
        way_payment.setName(DEFAULT_NAME);
        way_payment.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createWay_payment() throws Exception {
        int databaseSizeBeforeCreate = way_paymentRepository.findAll().size();

        // Create the Way_payment

        restWay_paymentMockMvc.perform(post("/api/way-payments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(way_payment)))
                .andExpect(status().isCreated());

        // Validate the Way_payment in the database
        List<Way_payment> way_payments = way_paymentRepository.findAll();
        assertThat(way_payments).hasSize(databaseSizeBeforeCreate + 1);
        Way_payment testWay_payment = way_payments.get(way_payments.size() - 1);
        assertThat(testWay_payment.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testWay_payment.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = way_paymentRepository.findAll().size();
        // set the field null
        way_payment.setName(null);

        // Create the Way_payment, which fails.

        restWay_paymentMockMvc.perform(post("/api/way-payments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(way_payment)))
                .andExpect(status().isBadRequest());

        List<Way_payment> way_payments = way_paymentRepository.findAll();
        assertThat(way_payments).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllWay_payments() throws Exception {
        /*
        // Initialize the database
        way_paymentRepository.saveAndFlush(way_payment);

        // Get all the way_payments
        restWay_paymentMockMvc.perform(get("/api/way-payments?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(way_payment.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));*/
    }

    @Test
    @Transactional
    public void getWay_payment() throws Exception {
        // Initialize the database
        way_paymentRepository.saveAndFlush(way_payment);

        // Get the way_payment
        restWay_paymentMockMvc.perform(get("/api/way-payments/{id}", way_payment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(way_payment.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingWay_payment() throws Exception {
        // Get the way_payment
        restWay_paymentMockMvc.perform(get("/api/way-payments/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWay_payment() throws Exception {
        // Initialize the database
        way_paymentService.save(way_payment);

        int databaseSizeBeforeUpdate = way_paymentRepository.findAll().size();

        // Update the way_payment
        Way_payment updatedWay_payment = new Way_payment();
        updatedWay_payment.setId(way_payment.getId());
        updatedWay_payment.setName(UPDATED_NAME);
        updatedWay_payment.setDescription(UPDATED_DESCRIPTION);

        restWay_paymentMockMvc.perform(put("/api/way-payments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedWay_payment)))
                .andExpect(status().isOk());

        // Validate the Way_payment in the database
        List<Way_payment> way_payments = way_paymentRepository.findAll();
        assertThat(way_payments).hasSize(databaseSizeBeforeUpdate);
        Way_payment testWay_payment = way_payments.get(way_payments.size() - 1);
        assertThat(testWay_payment.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testWay_payment.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteWay_payment() throws Exception {
        // Initialize the database
        way_paymentService.save(way_payment);

        int databaseSizeBeforeDelete = way_paymentRepository.findAll().size();

        // Get the way_payment
        restWay_paymentMockMvc.perform(delete("/api/way-payments/{id}", way_payment.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Way_payment> way_payments = way_paymentRepository.findAll();
        assertThat(way_payments).hasSize(databaseSizeBeforeDelete - 1);
    }
}
