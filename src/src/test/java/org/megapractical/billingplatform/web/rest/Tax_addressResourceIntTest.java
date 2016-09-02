package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Tax_address;
import org.megapractical.billingplatform.repository.Tax_addressRepository;
import org.megapractical.billingplatform.service.Tax_addressService;

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
 * Test class for the Tax_addressResource REST controller.
 *
 * @see Tax_addressResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Tax_addressResourceIntTest {

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
    private Tax_addressRepository tax_addressRepository;

    @Inject
    private Tax_addressService tax_addressService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restTax_addressMockMvc;

    private Tax_address tax_address;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Tax_addressResource tax_addressResource = new Tax_addressResource();
        ReflectionTestUtils.setField(tax_addressResource, "tax_addressService", tax_addressService);
        this.restTax_addressMockMvc = MockMvcBuilders.standaloneSetup(tax_addressResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        tax_address = new Tax_address();
        tax_address.setStreet(DEFAULT_STREET);
        tax_address.setNo_int(DEFAULT_NO_INT);
        tax_address.setNo_ext(DEFAULT_NO_EXT);
        tax_address.setLocation(DEFAULT_LOCATION);
        tax_address.setIntersection(DEFAULT_INTERSECTION);
        tax_address.setReference(DEFAULT_REFERENCE);
    }

    @Test
    @Transactional
    public void createTax_address() throws Exception {
        int databaseSizeBeforeCreate = tax_addressRepository.findAll().size();

        // Create the Tax_address

        restTax_addressMockMvc.perform(post("/api/tax-addresses")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(tax_address)))
                .andExpect(status().isCreated());

        // Validate the Tax_address in the database
        List<Tax_address> tax_addresses = tax_addressRepository.findAll();
        assertThat(tax_addresses).hasSize(databaseSizeBeforeCreate + 1);
        Tax_address testTax_address = tax_addresses.get(tax_addresses.size() - 1);
        assertThat(testTax_address.getStreet()).isEqualTo(DEFAULT_STREET);
        assertThat(testTax_address.getNo_int()).isEqualTo(DEFAULT_NO_INT);
        assertThat(testTax_address.getNo_ext()).isEqualTo(DEFAULT_NO_EXT);
        assertThat(testTax_address.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testTax_address.getIntersection()).isEqualTo(DEFAULT_INTERSECTION);
        assertThat(testTax_address.getReference()).isEqualTo(DEFAULT_REFERENCE);
    }

    @Test
    @Transactional
    public void checkStreetIsRequired() throws Exception {
        int databaseSizeBeforeTest = tax_addressRepository.findAll().size();
        // set the field null
        tax_address.setStreet(null);

        // Create the Tax_address, which fails.

        restTax_addressMockMvc.perform(post("/api/tax-addresses")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(tax_address)))
                .andExpect(status().isBadRequest());

        List<Tax_address> tax_addresses = tax_addressRepository.findAll();
        assertThat(tax_addresses).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTax_addresses() throws Exception {
        // Initialize the database
        tax_addressRepository.saveAndFlush(tax_address);

        // Get all the tax_addresses
        restTax_addressMockMvc.perform(get("/api/tax-addresses?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(tax_address.getId().intValue())))
                .andExpect(jsonPath("$.[*].street").value(hasItem(DEFAULT_STREET.toString())))
                .andExpect(jsonPath("$.[*].no_int").value(hasItem(DEFAULT_NO_INT.toString())))
                .andExpect(jsonPath("$.[*].no_ext").value(hasItem(DEFAULT_NO_EXT.toString())))
                .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION.toString())))
                .andExpect(jsonPath("$.[*].intersection").value(hasItem(DEFAULT_INTERSECTION.toString())))
                .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE.toString())));
    }

    @Test
    @Transactional
    public void getTax_address() throws Exception {
        // Initialize the database
        tax_addressRepository.saveAndFlush(tax_address);

        // Get the tax_address
        restTax_addressMockMvc.perform(get("/api/tax-addresses/{id}", tax_address.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(tax_address.getId().intValue()))
            .andExpect(jsonPath("$.street").value(DEFAULT_STREET.toString()))
            .andExpect(jsonPath("$.no_int").value(DEFAULT_NO_INT.toString()))
            .andExpect(jsonPath("$.no_ext").value(DEFAULT_NO_EXT.toString()))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION.toString()))
            .andExpect(jsonPath("$.intersection").value(DEFAULT_INTERSECTION.toString()))
            .andExpect(jsonPath("$.reference").value(DEFAULT_REFERENCE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTax_address() throws Exception {
        // Get the tax_address
        restTax_addressMockMvc.perform(get("/api/tax-addresses/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTax_address() throws Exception {
        // Initialize the database
        tax_addressService.save(tax_address);

        int databaseSizeBeforeUpdate = tax_addressRepository.findAll().size();

        // Update the tax_address
        Tax_address updatedTax_address = new Tax_address();
        updatedTax_address.setId(tax_address.getId());
        updatedTax_address.setStreet(UPDATED_STREET);
        updatedTax_address.setNo_int(UPDATED_NO_INT);
        updatedTax_address.setNo_ext(UPDATED_NO_EXT);
        updatedTax_address.setLocation(UPDATED_LOCATION);
        updatedTax_address.setIntersection(UPDATED_INTERSECTION);
        updatedTax_address.setReference(UPDATED_REFERENCE);

        restTax_addressMockMvc.perform(put("/api/tax-addresses")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedTax_address)))
                .andExpect(status().isOk());

        // Validate the Tax_address in the database
        List<Tax_address> tax_addresses = tax_addressRepository.findAll();
        assertThat(tax_addresses).hasSize(databaseSizeBeforeUpdate);
        Tax_address testTax_address = tax_addresses.get(tax_addresses.size() - 1);
        assertThat(testTax_address.getStreet()).isEqualTo(UPDATED_STREET);
        assertThat(testTax_address.getNo_int()).isEqualTo(UPDATED_NO_INT);
        assertThat(testTax_address.getNo_ext()).isEqualTo(UPDATED_NO_EXT);
        assertThat(testTax_address.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testTax_address.getIntersection()).isEqualTo(UPDATED_INTERSECTION);
        assertThat(testTax_address.getReference()).isEqualTo(UPDATED_REFERENCE);
    }

    @Test
    @Transactional
    public void deleteTax_address() throws Exception {
        // Initialize the database
        tax_addressService.save(tax_address);

        int databaseSizeBeforeDelete = tax_addressRepository.findAll().size();

        // Get the tax_address
        restTax_addressMockMvc.perform(delete("/api/tax-addresses/{id}", tax_address.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Tax_address> tax_addresses = tax_addressRepository.findAll();
        assertThat(tax_addresses).hasSize(databaseSizeBeforeDelete - 1);
    }
}
