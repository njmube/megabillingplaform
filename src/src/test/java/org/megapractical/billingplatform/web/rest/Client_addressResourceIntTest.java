package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Client_address;
import org.megapractical.billingplatform.repository.Client_addressRepository;
import org.megapractical.billingplatform.service.Client_addressService;

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
 * Test class for the Client_addressResource REST controller.
 *
 * @see Client_addressResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Client_addressResourceIntTest {

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
    private Client_addressRepository client_addressRepository;

    @Inject
    private Client_addressService client_addressService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restClient_addressMockMvc;

    private Client_address client_address;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Client_addressResource client_addressResource = new Client_addressResource();
        ReflectionTestUtils.setField(client_addressResource, "client_addressService", client_addressService);
        this.restClient_addressMockMvc = MockMvcBuilders.standaloneSetup(client_addressResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        client_address = new Client_address();
        client_address.setStreet(DEFAULT_STREET);
        client_address.setNo_int(DEFAULT_NO_INT);
        client_address.setNo_ext(DEFAULT_NO_EXT);
        client_address.setLocation(DEFAULT_LOCATION);
        client_address.setIntersection(DEFAULT_INTERSECTION);
        client_address.setReference(DEFAULT_REFERENCE);
    }

    @Test
    @Transactional
    public void createClient_address() throws Exception {
        int databaseSizeBeforeCreate = client_addressRepository.findAll().size();

        // Create the Client_address

        restClient_addressMockMvc.perform(post("/api/client-addresses")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(client_address)))
                .andExpect(status().isCreated());

        // Validate the Client_address in the database
        List<Client_address> client_addresses = client_addressRepository.findAll();
        assertThat(client_addresses).hasSize(databaseSizeBeforeCreate + 1);
        Client_address testClient_address = client_addresses.get(client_addresses.size() - 1);
        assertThat(testClient_address.getStreet()).isEqualTo(DEFAULT_STREET);
        assertThat(testClient_address.getNo_int()).isEqualTo(DEFAULT_NO_INT);
        assertThat(testClient_address.getNo_ext()).isEqualTo(DEFAULT_NO_EXT);
        assertThat(testClient_address.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testClient_address.getIntersection()).isEqualTo(DEFAULT_INTERSECTION);
        assertThat(testClient_address.getReference()).isEqualTo(DEFAULT_REFERENCE);
    }

    @Test
    @Transactional
    public void checkStreetIsRequired() throws Exception {
        int databaseSizeBeforeTest = client_addressRepository.findAll().size();
        // set the field null
        client_address.setStreet(null);

        // Create the Client_address, which fails.

        restClient_addressMockMvc.perform(post("/api/client-addresses")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(client_address)))
                .andExpect(status().isBadRequest());

        List<Client_address> client_addresses = client_addressRepository.findAll();
        assertThat(client_addresses).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllClient_addresses() throws Exception {
        // Initialize the database
        client_addressRepository.saveAndFlush(client_address);

        // Get all the client_addresses
        restClient_addressMockMvc.perform(get("/api/client-addresses?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(client_address.getId().intValue())))
                .andExpect(jsonPath("$.[*].street").value(hasItem(DEFAULT_STREET.toString())))
                .andExpect(jsonPath("$.[*].no_int").value(hasItem(DEFAULT_NO_INT.toString())))
                .andExpect(jsonPath("$.[*].no_ext").value(hasItem(DEFAULT_NO_EXT.toString())))
                .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION.toString())))
                .andExpect(jsonPath("$.[*].intersection").value(hasItem(DEFAULT_INTERSECTION.toString())))
                .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE.toString())));
    }

    @Test
    @Transactional
    public void getClient_address() throws Exception {
        // Initialize the database
        client_addressRepository.saveAndFlush(client_address);

        // Get the client_address
        restClient_addressMockMvc.perform(get("/api/client-addresses/{id}", client_address.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(client_address.getId().intValue()))
            .andExpect(jsonPath("$.street").value(DEFAULT_STREET.toString()))
            .andExpect(jsonPath("$.no_int").value(DEFAULT_NO_INT.toString()))
            .andExpect(jsonPath("$.no_ext").value(DEFAULT_NO_EXT.toString()))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION.toString()))
            .andExpect(jsonPath("$.intersection").value(DEFAULT_INTERSECTION.toString()))
            .andExpect(jsonPath("$.reference").value(DEFAULT_REFERENCE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingClient_address() throws Exception {
        // Get the client_address
        restClient_addressMockMvc.perform(get("/api/client-addresses/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClient_address() throws Exception {
        // Initialize the database
        client_addressService.save(client_address);

        int databaseSizeBeforeUpdate = client_addressRepository.findAll().size();

        // Update the client_address
        Client_address updatedClient_address = new Client_address();
        updatedClient_address.setId(client_address.getId());
        updatedClient_address.setStreet(UPDATED_STREET);
        updatedClient_address.setNo_int(UPDATED_NO_INT);
        updatedClient_address.setNo_ext(UPDATED_NO_EXT);
        updatedClient_address.setLocation(UPDATED_LOCATION);
        updatedClient_address.setIntersection(UPDATED_INTERSECTION);
        updatedClient_address.setReference(UPDATED_REFERENCE);

        restClient_addressMockMvc.perform(put("/api/client-addresses")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedClient_address)))
                .andExpect(status().isOk());

        // Validate the Client_address in the database
        List<Client_address> client_addresses = client_addressRepository.findAll();
        assertThat(client_addresses).hasSize(databaseSizeBeforeUpdate);
        Client_address testClient_address = client_addresses.get(client_addresses.size() - 1);
        assertThat(testClient_address.getStreet()).isEqualTo(UPDATED_STREET);
        assertThat(testClient_address.getNo_int()).isEqualTo(UPDATED_NO_INT);
        assertThat(testClient_address.getNo_ext()).isEqualTo(UPDATED_NO_EXT);
        assertThat(testClient_address.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testClient_address.getIntersection()).isEqualTo(UPDATED_INTERSECTION);
        assertThat(testClient_address.getReference()).isEqualTo(UPDATED_REFERENCE);
    }

    @Test
    @Transactional
    public void deleteClient_address() throws Exception {
        // Initialize the database
        client_addressService.save(client_address);

        int databaseSizeBeforeDelete = client_addressRepository.findAll().size();

        // Get the client_address
        restClient_addressMockMvc.perform(delete("/api/client-addresses/{id}", client_address.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Client_address> client_addresses = client_addressRepository.findAll();
        assertThat(client_addresses).hasSize(databaseSizeBeforeDelete - 1);
    }
}
