package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Payment_method;
import org.megapractical.billingplatform.repository.Payment_methodRepository;
import org.megapractical.billingplatform.service.Payment_methodService;

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
 * Test class for the Payment_methodResource REST controller.
 *
 * @see Payment_methodResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Payment_methodResourceIntTest {

    private static final String DEFAULT_NAME = "AAA";
    private static final String UPDATED_NAME = "BBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";
    private static final String DEFAULT_CODE = "AAA";
    private static final String UPDATED_CODE = "BBB";

    @Inject
    private Payment_methodRepository payment_methodRepository;

    @Inject
    private Payment_methodService payment_methodService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPayment_methodMockMvc;

    private Payment_method payment_method;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Payment_methodResource payment_methodResource = new Payment_methodResource();
        ReflectionTestUtils.setField(payment_methodResource, "payment_methodService", payment_methodService);
        this.restPayment_methodMockMvc = MockMvcBuilders.standaloneSetup(payment_methodResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        payment_method = new Payment_method();
        payment_method.setName(DEFAULT_NAME);
        payment_method.setDescription(DEFAULT_DESCRIPTION);
        payment_method.setCode(DEFAULT_CODE);
    }

    @Test
    @Transactional
    public void createPayment_method() throws Exception {
        int databaseSizeBeforeCreate = payment_methodRepository.findAll().size();

        // Create the Payment_method

        restPayment_methodMockMvc.perform(post("/api/payment-methods")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(payment_method)))
                .andExpect(status().isCreated());

        // Validate the Payment_method in the database
        List<Payment_method> payment_methods = payment_methodRepository.findAll();
        assertThat(payment_methods).hasSize(databaseSizeBeforeCreate + 1);
        Payment_method testPayment_method = payment_methods.get(payment_methods.size() - 1);
        assertThat(testPayment_method.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPayment_method.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPayment_method.getCode()).isEqualTo(DEFAULT_CODE);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = payment_methodRepository.findAll().size();
        // set the field null
        payment_method.setName(null);

        // Create the Payment_method, which fails.

        restPayment_methodMockMvc.perform(post("/api/payment-methods")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(payment_method)))
                .andExpect(status().isBadRequest());

        List<Payment_method> payment_methods = payment_methodRepository.findAll();
        assertThat(payment_methods).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = payment_methodRepository.findAll().size();
        // set the field null
        payment_method.setCode(null);

        // Create the Payment_method, which fails.

        restPayment_methodMockMvc.perform(post("/api/payment-methods")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(payment_method)))
                .andExpect(status().isBadRequest());

        List<Payment_method> payment_methods = payment_methodRepository.findAll();
        assertThat(payment_methods).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPayment_methods() throws Exception {
        // Initialize the database
        payment_methodRepository.saveAndFlush(payment_method);

        // Get all the payment_methods
        restPayment_methodMockMvc.perform(get("/api/payment-methods?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(payment_method.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())));
    }

    @Test
    @Transactional
    public void getPayment_method() throws Exception {
        // Initialize the database
        payment_methodRepository.saveAndFlush(payment_method);

        // Get the payment_method
        restPayment_methodMockMvc.perform(get("/api/payment-methods/{id}", payment_method.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(payment_method.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPayment_method() throws Exception {
        // Get the payment_method
        restPayment_methodMockMvc.perform(get("/api/payment-methods/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePayment_method() throws Exception {
        // Initialize the database
        payment_methodService.save(payment_method);

        int databaseSizeBeforeUpdate = payment_methodRepository.findAll().size();

        // Update the payment_method
        Payment_method updatedPayment_method = new Payment_method();
        updatedPayment_method.setId(payment_method.getId());
        updatedPayment_method.setName(UPDATED_NAME);
        updatedPayment_method.setDescription(UPDATED_DESCRIPTION);
        updatedPayment_method.setCode(UPDATED_CODE);

        restPayment_methodMockMvc.perform(put("/api/payment-methods")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedPayment_method)))
                .andExpect(status().isOk());

        // Validate the Payment_method in the database
        List<Payment_method> payment_methods = payment_methodRepository.findAll();
        assertThat(payment_methods).hasSize(databaseSizeBeforeUpdate);
        Payment_method testPayment_method = payment_methods.get(payment_methods.size() - 1);
        assertThat(testPayment_method.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPayment_method.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPayment_method.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    public void deletePayment_method() throws Exception {
        // Initialize the database
        payment_methodService.save(payment_method);

        int databaseSizeBeforeDelete = payment_methodRepository.findAll().size();

        // Get the payment_method
        restPayment_methodMockMvc.perform(delete("/api/payment-methods/{id}", payment_method.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Payment_method> payment_methods = payment_methodRepository.findAll();
        assertThat(payment_methods).hasSize(databaseSizeBeforeDelete - 1);
    }
}
