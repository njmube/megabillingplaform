package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Tax_address_request;
import org.megapractical.billingplatform.repository.Tax_address_requestRepository;
import org.megapractical.billingplatform.service.Tax_address_requestService;

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
 * Test class for the Tax_address_requestResource REST controller.
 *
 * @see Tax_address_requestResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Tax_address_requestResourceIntTest {

    private static final String DEFAULT_STREET = "AAAAA";
    private static final String UPDATED_STREET = "BBBBB";
    private static final String DEFAULT_NO_INT = "AAAAA";
    private static final String UPDATED_NO_INT = "BBBBB";
    private static final String DEFAULT_NO_EXT = "AAAAAAAAAA";
    private static final String UPDATED_NO_EXT = "BBBBBBBBBB";
    private static final String DEFAULT_LOCATION = "AAAAA";
    private static final String UPDATED_LOCATION = "BBBBB";
    private static final String DEFAULT_INTERSECTION = "AAAAA";
    private static final String UPDATED_INTERSECTION = "BBBBB";
    private static final String DEFAULT_REFERENCE = "AAAAA";
    private static final String UPDATED_REFERENCE = "BBBBB";

    @Inject
    private Tax_address_requestRepository tax_address_requestRepository;

    @Inject
    private Tax_address_requestService tax_address_requestService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restTax_address_requestMockMvc;

    private Tax_address_request tax_address_request;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Tax_address_requestResource tax_address_requestResource = new Tax_address_requestResource();
        ReflectionTestUtils.setField(tax_address_requestResource, "tax_address_requestService", tax_address_requestService);
        this.restTax_address_requestMockMvc = MockMvcBuilders.standaloneSetup(tax_address_requestResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        tax_address_request = new Tax_address_request();
        tax_address_request.setStreet(DEFAULT_STREET);
        tax_address_request.setNo_int(DEFAULT_NO_INT);
        tax_address_request.setNo_ext(DEFAULT_NO_EXT);
        tax_address_request.setLocation(DEFAULT_LOCATION);
        tax_address_request.setIntersection(DEFAULT_INTERSECTION);
        tax_address_request.setReference(DEFAULT_REFERENCE);
    }

    @Test
    @Transactional
    public void createTax_address_request() throws Exception {
        int databaseSizeBeforeCreate = tax_address_requestRepository.findAll().size();

        // Create the Tax_address_request

        restTax_address_requestMockMvc.perform(post("/api/tax-address-requests")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(tax_address_request)))
                .andExpect(status().isCreated());

        // Validate the Tax_address_request in the database
        List<Tax_address_request> tax_address_requests = tax_address_requestRepository.findAll();
        assertThat(tax_address_requests).hasSize(databaseSizeBeforeCreate + 1);
        Tax_address_request testTax_address_request = tax_address_requests.get(tax_address_requests.size() - 1);
        assertThat(testTax_address_request.getStreet()).isEqualTo(DEFAULT_STREET);
        assertThat(testTax_address_request.getNo_int()).isEqualTo(DEFAULT_NO_INT);
        assertThat(testTax_address_request.getNo_ext()).isEqualTo(DEFAULT_NO_EXT);
        assertThat(testTax_address_request.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testTax_address_request.getIntersection()).isEqualTo(DEFAULT_INTERSECTION);
        assertThat(testTax_address_request.getReference()).isEqualTo(DEFAULT_REFERENCE);
    }

    @Test
    @Transactional
    public void checkStreetIsRequired() throws Exception {
        int databaseSizeBeforeTest = tax_address_requestRepository.findAll().size();
        // set the field null
        tax_address_request.setStreet(null);

        // Create the Tax_address_request, which fails.

        restTax_address_requestMockMvc.perform(post("/api/tax-address-requests")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(tax_address_request)))
                .andExpect(status().isBadRequest());

        List<Tax_address_request> tax_address_requests = tax_address_requestRepository.findAll();
        assertThat(tax_address_requests).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTax_address_requests() throws Exception {
        // Initialize the database
        tax_address_requestRepository.saveAndFlush(tax_address_request);

        // Get all the tax_address_requests
        restTax_address_requestMockMvc.perform(get("/api/tax-address-requests?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(tax_address_request.getId().intValue())))
                .andExpect(jsonPath("$.[*].street").value(hasItem(DEFAULT_STREET.toString())))
                .andExpect(jsonPath("$.[*].no_int").value(hasItem(DEFAULT_NO_INT.toString())))
                .andExpect(jsonPath("$.[*].no_ext").value(hasItem(DEFAULT_NO_EXT.toString())))
                .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION.toString())))
                .andExpect(jsonPath("$.[*].intersection").value(hasItem(DEFAULT_INTERSECTION.toString())))
                .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE.toString())));
    }

    @Test
    @Transactional
    public void getTax_address_request() throws Exception {
        // Initialize the database
        tax_address_requestRepository.saveAndFlush(tax_address_request);

        // Get the tax_address_request
        restTax_address_requestMockMvc.perform(get("/api/tax-address-requests/{id}", tax_address_request.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(tax_address_request.getId().intValue()))
            .andExpect(jsonPath("$.street").value(DEFAULT_STREET.toString()))
            .andExpect(jsonPath("$.no_int").value(DEFAULT_NO_INT.toString()))
            .andExpect(jsonPath("$.no_ext").value(DEFAULT_NO_EXT.toString()))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION.toString()))
            .andExpect(jsonPath("$.intersection").value(DEFAULT_INTERSECTION.toString()))
            .andExpect(jsonPath("$.reference").value(DEFAULT_REFERENCE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTax_address_request() throws Exception {
        // Get the tax_address_request
        restTax_address_requestMockMvc.perform(get("/api/tax-address-requests/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTax_address_request() throws Exception {
        // Initialize the database
        tax_address_requestService.save(tax_address_request);

        int databaseSizeBeforeUpdate = tax_address_requestRepository.findAll().size();

        // Update the tax_address_request
        Tax_address_request updatedTax_address_request = new Tax_address_request();
        updatedTax_address_request.setId(tax_address_request.getId());
        updatedTax_address_request.setStreet(UPDATED_STREET);
        updatedTax_address_request.setNo_int(UPDATED_NO_INT);
        updatedTax_address_request.setNo_ext(UPDATED_NO_EXT);
        updatedTax_address_request.setLocation(UPDATED_LOCATION);
        updatedTax_address_request.setIntersection(UPDATED_INTERSECTION);
        updatedTax_address_request.setReference(UPDATED_REFERENCE);

        restTax_address_requestMockMvc.perform(put("/api/tax-address-requests")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedTax_address_request)))
                .andExpect(status().isOk());

        // Validate the Tax_address_request in the database
        List<Tax_address_request> tax_address_requests = tax_address_requestRepository.findAll();
        assertThat(tax_address_requests).hasSize(databaseSizeBeforeUpdate);
        Tax_address_request testTax_address_request = tax_address_requests.get(tax_address_requests.size() - 1);
        assertThat(testTax_address_request.getStreet()).isEqualTo(UPDATED_STREET);
        assertThat(testTax_address_request.getNo_int()).isEqualTo(UPDATED_NO_INT);
        assertThat(testTax_address_request.getNo_ext()).isEqualTo(UPDATED_NO_EXT);
        assertThat(testTax_address_request.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testTax_address_request.getIntersection()).isEqualTo(UPDATED_INTERSECTION);
        assertThat(testTax_address_request.getReference()).isEqualTo(UPDATED_REFERENCE);
    }

    @Test
    @Transactional
    public void deleteTax_address_request() throws Exception {
        // Initialize the database
        tax_address_requestService.save(tax_address_request);

        int databaseSizeBeforeDelete = tax_address_requestRepository.findAll().size();

        // Get the tax_address_request
        restTax_address_requestMockMvc.perform(delete("/api/tax-address-requests/{id}", tax_address_request.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Tax_address_request> tax_address_requests = tax_address_requestRepository.findAll();
        assertThat(tax_address_requests).hasSize(databaseSizeBeforeDelete - 1);
    }
}
