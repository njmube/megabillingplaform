package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Taxpayer_client;
import org.megapractical.billingplatform.repository.Taxpayer_clientRepository;
import org.megapractical.billingplatform.service.Taxpayer_clientService;

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
 * Test class for the Taxpayer_clientResource REST controller.
 *
 * @see Taxpayer_clientResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Taxpayer_clientResourceIntTest {

    private static final String DEFAULT_RFC = "AAAAAAAAAAAA";
    private static final String UPDATED_RFC = "BBBBBBBBBBBB";
    private static final String DEFAULT_BUSSINESNAME = "AAAAA";
    private static final String UPDATED_BUSSINESNAME = "BBBBB";
    private static final String DEFAULT_EMAIL = "AAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBB";
    private static final String DEFAULT_EMAIL_2 = "AAAAA";
    private static final String UPDATED_EMAIL_2 = "BBBBB";
    private static final String DEFAULT_PHONE = "AAAAA";
    private static final String UPDATED_PHONE = "BBBBB";

    @Inject
    private Taxpayer_clientRepository taxpayer_clientRepository;

    @Inject
    private Taxpayer_clientService taxpayer_clientService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restTaxpayer_clientMockMvc;

    private Taxpayer_client taxpayer_client;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Taxpayer_clientResource taxpayer_clientResource = new Taxpayer_clientResource();
        ReflectionTestUtils.setField(taxpayer_clientResource, "taxpayer_clientService", taxpayer_clientService);
        this.restTaxpayer_clientMockMvc = MockMvcBuilders.standaloneSetup(taxpayer_clientResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        taxpayer_client = new Taxpayer_client();
        taxpayer_client.setRfc(DEFAULT_RFC);
        taxpayer_client.setBussinesname(DEFAULT_BUSSINESNAME);
        taxpayer_client.setEmail(DEFAULT_EMAIL);
        taxpayer_client.setEmail2(DEFAULT_EMAIL_2);
        taxpayer_client.setPhone(DEFAULT_PHONE);
    }

    @Test
    @Transactional
    public void createTaxpayer_client() throws Exception {
        int databaseSizeBeforeCreate = taxpayer_clientRepository.findAll().size();

        // Create the Taxpayer_client

        restTaxpayer_clientMockMvc.perform(post("/api/taxpayer-clients")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(taxpayer_client)))
                .andExpect(status().isCreated());

        // Validate the Taxpayer_client in the database
        List<Taxpayer_client> taxpayer_clients = taxpayer_clientRepository.findAll();
        assertThat(taxpayer_clients).hasSize(databaseSizeBeforeCreate + 1);
        Taxpayer_client testTaxpayer_client = taxpayer_clients.get(taxpayer_clients.size() - 1);
        assertThat(testTaxpayer_client.getRfc()).isEqualTo(DEFAULT_RFC);
        assertThat(testTaxpayer_client.getBussinesname()).isEqualTo(DEFAULT_BUSSINESNAME);
        assertThat(testTaxpayer_client.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testTaxpayer_client.getEmail2()).isEqualTo(DEFAULT_EMAIL_2);
        assertThat(testTaxpayer_client.getPhone()).isEqualTo(DEFAULT_PHONE);
    }

    @Test
    @Transactional
    public void checkRfcIsRequired() throws Exception {
        int databaseSizeBeforeTest = taxpayer_clientRepository.findAll().size();
        // set the field null
        taxpayer_client.setRfc(null);

        // Create the Taxpayer_client, which fails.

        restTaxpayer_clientMockMvc.perform(post("/api/taxpayer-clients")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(taxpayer_client)))
                .andExpect(status().isBadRequest());

        List<Taxpayer_client> taxpayer_clients = taxpayer_clientRepository.findAll();
        assertThat(taxpayer_clients).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBussinesnameIsRequired() throws Exception {
        int databaseSizeBeforeTest = taxpayer_clientRepository.findAll().size();
        // set the field null
        taxpayer_client.setBussinesname(null);

        // Create the Taxpayer_client, which fails.

        restTaxpayer_clientMockMvc.perform(post("/api/taxpayer-clients")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(taxpayer_client)))
                .andExpect(status().isBadRequest());

        List<Taxpayer_client> taxpayer_clients = taxpayer_clientRepository.findAll();
        assertThat(taxpayer_clients).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = taxpayer_clientRepository.findAll().size();
        // set the field null
        taxpayer_client.setEmail(null);

        // Create the Taxpayer_client, which fails.

        restTaxpayer_clientMockMvc.perform(post("/api/taxpayer-clients")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(taxpayer_client)))
                .andExpect(status().isBadRequest());

        List<Taxpayer_client> taxpayer_clients = taxpayer_clientRepository.findAll();
        assertThat(taxpayer_clients).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTaxpayer_clients() throws Exception {
        // Initialize the database
        taxpayer_clientRepository.saveAndFlush(taxpayer_client);

        // Get all the taxpayer_clients
        restTaxpayer_clientMockMvc.perform(get("/api/taxpayer-clients?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(taxpayer_client.getId().intValue())))
                .andExpect(jsonPath("$.[*].rfc").value(hasItem(DEFAULT_RFC.toString())))
                .andExpect(jsonPath("$.[*].bussinesname").value(hasItem(DEFAULT_BUSSINESNAME.toString())))
                .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
                .andExpect(jsonPath("$.[*].email2").value(hasItem(DEFAULT_EMAIL_2.toString())))
                .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())));
    }

    @Test
    @Transactional
    public void getTaxpayer_client() throws Exception {
        // Initialize the database
        taxpayer_clientRepository.saveAndFlush(taxpayer_client);

        // Get the taxpayer_client
        restTaxpayer_clientMockMvc.perform(get("/api/taxpayer-clients/{id}", taxpayer_client.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(taxpayer_client.getId().intValue()))
            .andExpect(jsonPath("$.rfc").value(DEFAULT_RFC.toString()))
            .andExpect(jsonPath("$.bussinesname").value(DEFAULT_BUSSINESNAME.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.email2").value(DEFAULT_EMAIL_2.toString()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTaxpayer_client() throws Exception {
        // Get the taxpayer_client
        restTaxpayer_clientMockMvc.perform(get("/api/taxpayer-clients/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTaxpayer_client() throws Exception {
        // Initialize the database
        taxpayer_clientService.save(taxpayer_client);

        int databaseSizeBeforeUpdate = taxpayer_clientRepository.findAll().size();

        // Update the taxpayer_client
        Taxpayer_client updatedTaxpayer_client = new Taxpayer_client();
        updatedTaxpayer_client.setId(taxpayer_client.getId());
        updatedTaxpayer_client.setRfc(UPDATED_RFC);
        updatedTaxpayer_client.setBussinesname(UPDATED_BUSSINESNAME);
        updatedTaxpayer_client.setEmail(UPDATED_EMAIL);
        updatedTaxpayer_client.setEmail2(UPDATED_EMAIL_2);
        updatedTaxpayer_client.setPhone(UPDATED_PHONE);

        restTaxpayer_clientMockMvc.perform(put("/api/taxpayer-clients")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedTaxpayer_client)))
                .andExpect(status().isOk());

        // Validate the Taxpayer_client in the database
        List<Taxpayer_client> taxpayer_clients = taxpayer_clientRepository.findAll();
        assertThat(taxpayer_clients).hasSize(databaseSizeBeforeUpdate);
        Taxpayer_client testTaxpayer_client = taxpayer_clients.get(taxpayer_clients.size() - 1);
        assertThat(testTaxpayer_client.getRfc()).isEqualTo(UPDATED_RFC);
        assertThat(testTaxpayer_client.getBussinesname()).isEqualTo(UPDATED_BUSSINESNAME);
        assertThat(testTaxpayer_client.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testTaxpayer_client.getEmail2()).isEqualTo(UPDATED_EMAIL_2);
        assertThat(testTaxpayer_client.getPhone()).isEqualTo(UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void deleteTaxpayer_client() throws Exception {
        // Initialize the database
        taxpayer_clientService.save(taxpayer_client);

        int databaseSizeBeforeDelete = taxpayer_clientRepository.findAll().size();

        // Get the taxpayer_client
        restTaxpayer_clientMockMvc.perform(delete("/api/taxpayer-clients/{id}", taxpayer_client.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Taxpayer_client> taxpayer_clients = taxpayer_clientRepository.findAll();
        assertThat(taxpayer_clients).hasSize(databaseSizeBeforeDelete - 1);
    }
}
