package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Freecom_addressee;
import org.megapractical.billingplatform.repository.Freecom_addresseeRepository;
import org.megapractical.billingplatform.service.Freecom_addresseeService;

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
 * Test class for the Freecom_addresseeResource REST controller.
 *
 * @see Freecom_addresseeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Freecom_addresseeResourceIntTest {

    private static final String DEFAULT_STREET = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_STREET = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";
    private static final String DEFAULT_NO_EXT = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_NO_EXT = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";
    private static final String DEFAULT_NO_INT = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_NO_INT = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";
    private static final String DEFAULT_LOCATE = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_LOCATE = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";
    private static final String DEFAULT_REFERENCE = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_REFERENCE = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";
    private static final String DEFAULT_NUMREGIDTRIB = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_NUMREGIDTRIB = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";
    private static final String DEFAULT_RFC = "AAAAA";
    private static final String UPDATED_RFC = "BBBBB";
    private static final String DEFAULT_CURP = "AAAAA";
    private static final String UPDATED_CURP = "BBBBB";
    private static final String DEFAULT_NAME = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";

    @Inject
    private Freecom_addresseeRepository freecom_addresseeRepository;

    @Inject
    private Freecom_addresseeService freecom_addresseeService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFreecom_addresseeMockMvc;

    private Freecom_addressee freecom_addressee;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Freecom_addresseeResource freecom_addresseeResource = new Freecom_addresseeResource();
        ReflectionTestUtils.setField(freecom_addresseeResource, "freecom_addresseeService", freecom_addresseeService);
        this.restFreecom_addresseeMockMvc = MockMvcBuilders.standaloneSetup(freecom_addresseeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        freecom_addressee = new Freecom_addressee();
        freecom_addressee.setStreet(DEFAULT_STREET);
        freecom_addressee.setNo_ext(DEFAULT_NO_EXT);
        freecom_addressee.setNo_int(DEFAULT_NO_INT);
        freecom_addressee.setLocate(DEFAULT_LOCATE);
        freecom_addressee.setReference(DEFAULT_REFERENCE);
        freecom_addressee.setNumregidtrib(DEFAULT_NUMREGIDTRIB);
        freecom_addressee.setRfc(DEFAULT_RFC);
        freecom_addressee.setCurp(DEFAULT_CURP);
        freecom_addressee.setName(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createFreecom_addressee() throws Exception {
        int databaseSizeBeforeCreate = freecom_addresseeRepository.findAll().size();

        // Create the Freecom_addressee

        restFreecom_addresseeMockMvc.perform(post("/api/freecom-addressees")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_addressee)))
                .andExpect(status().isCreated());

        // Validate the Freecom_addressee in the database
        List<Freecom_addressee> freecom_addressees = freecom_addresseeRepository.findAll();
        assertThat(freecom_addressees).hasSize(databaseSizeBeforeCreate + 1);
        Freecom_addressee testFreecom_addressee = freecom_addressees.get(freecom_addressees.size() - 1);
        assertThat(testFreecom_addressee.getStreet()).isEqualTo(DEFAULT_STREET);
        assertThat(testFreecom_addressee.getNo_ext()).isEqualTo(DEFAULT_NO_EXT);
        assertThat(testFreecom_addressee.getNo_int()).isEqualTo(DEFAULT_NO_INT);
        assertThat(testFreecom_addressee.getLocate()).isEqualTo(DEFAULT_LOCATE);
        assertThat(testFreecom_addressee.getReference()).isEqualTo(DEFAULT_REFERENCE);
        assertThat(testFreecom_addressee.getNumregidtrib()).isEqualTo(DEFAULT_NUMREGIDTRIB);
        assertThat(testFreecom_addressee.getRfc()).isEqualTo(DEFAULT_RFC);
        assertThat(testFreecom_addressee.getCurp()).isEqualTo(DEFAULT_CURP);
        assertThat(testFreecom_addressee.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void checkStreetIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_addresseeRepository.findAll().size();
        // set the field null
        freecom_addressee.setStreet(null);

        // Create the Freecom_addressee, which fails.

        restFreecom_addresseeMockMvc.perform(post("/api/freecom-addressees")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_addressee)))
                .andExpect(status().isBadRequest());

        List<Freecom_addressee> freecom_addressees = freecom_addresseeRepository.findAll();
        assertThat(freecom_addressees).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFreecom_addressees() throws Exception {
        // Initialize the database
        freecom_addresseeRepository.saveAndFlush(freecom_addressee);

        // Get all the freecom_addressees
        restFreecom_addresseeMockMvc.perform(get("/api/freecom-addressees?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(freecom_addressee.getId().intValue())))
                .andExpect(jsonPath("$.[*].street").value(hasItem(DEFAULT_STREET.toString())))
                .andExpect(jsonPath("$.[*].no_ext").value(hasItem(DEFAULT_NO_EXT.toString())))
                .andExpect(jsonPath("$.[*].no_int").value(hasItem(DEFAULT_NO_INT.toString())))
                .andExpect(jsonPath("$.[*].locate").value(hasItem(DEFAULT_LOCATE.toString())))
                .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE.toString())))
                .andExpect(jsonPath("$.[*].numregidtrib").value(hasItem(DEFAULT_NUMREGIDTRIB.toString())))
                .andExpect(jsonPath("$.[*].rfc").value(hasItem(DEFAULT_RFC.toString())))
                .andExpect(jsonPath("$.[*].curp").value(hasItem(DEFAULT_CURP.toString())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getFreecom_addressee() throws Exception {
        // Initialize the database
        freecom_addresseeRepository.saveAndFlush(freecom_addressee);

        // Get the freecom_addressee
        restFreecom_addresseeMockMvc.perform(get("/api/freecom-addressees/{id}", freecom_addressee.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(freecom_addressee.getId().intValue()))
            .andExpect(jsonPath("$.street").value(DEFAULT_STREET.toString()))
            .andExpect(jsonPath("$.no_ext").value(DEFAULT_NO_EXT.toString()))
            .andExpect(jsonPath("$.no_int").value(DEFAULT_NO_INT.toString()))
            .andExpect(jsonPath("$.locate").value(DEFAULT_LOCATE.toString()))
            .andExpect(jsonPath("$.reference").value(DEFAULT_REFERENCE.toString()))
            .andExpect(jsonPath("$.numregidtrib").value(DEFAULT_NUMREGIDTRIB.toString()))
            .andExpect(jsonPath("$.rfc").value(DEFAULT_RFC.toString()))
            .andExpect(jsonPath("$.curp").value(DEFAULT_CURP.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFreecom_addressee() throws Exception {
        // Get the freecom_addressee
        restFreecom_addresseeMockMvc.perform(get("/api/freecom-addressees/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFreecom_addressee() throws Exception {
        // Initialize the database
        freecom_addresseeService.save(freecom_addressee);

        int databaseSizeBeforeUpdate = freecom_addresseeRepository.findAll().size();

        // Update the freecom_addressee
        Freecom_addressee updatedFreecom_addressee = new Freecom_addressee();
        updatedFreecom_addressee.setId(freecom_addressee.getId());
        updatedFreecom_addressee.setStreet(UPDATED_STREET);
        updatedFreecom_addressee.setNo_ext(UPDATED_NO_EXT);
        updatedFreecom_addressee.setNo_int(UPDATED_NO_INT);
        updatedFreecom_addressee.setLocate(UPDATED_LOCATE);
        updatedFreecom_addressee.setReference(UPDATED_REFERENCE);
        updatedFreecom_addressee.setNumregidtrib(UPDATED_NUMREGIDTRIB);
        updatedFreecom_addressee.setRfc(UPDATED_RFC);
        updatedFreecom_addressee.setCurp(UPDATED_CURP);
        updatedFreecom_addressee.setName(UPDATED_NAME);

        restFreecom_addresseeMockMvc.perform(put("/api/freecom-addressees")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedFreecom_addressee)))
                .andExpect(status().isOk());

        // Validate the Freecom_addressee in the database
        List<Freecom_addressee> freecom_addressees = freecom_addresseeRepository.findAll();
        assertThat(freecom_addressees).hasSize(databaseSizeBeforeUpdate);
        Freecom_addressee testFreecom_addressee = freecom_addressees.get(freecom_addressees.size() - 1);
        assertThat(testFreecom_addressee.getStreet()).isEqualTo(UPDATED_STREET);
        assertThat(testFreecom_addressee.getNo_ext()).isEqualTo(UPDATED_NO_EXT);
        assertThat(testFreecom_addressee.getNo_int()).isEqualTo(UPDATED_NO_INT);
        assertThat(testFreecom_addressee.getLocate()).isEqualTo(UPDATED_LOCATE);
        assertThat(testFreecom_addressee.getReference()).isEqualTo(UPDATED_REFERENCE);
        assertThat(testFreecom_addressee.getNumregidtrib()).isEqualTo(UPDATED_NUMREGIDTRIB);
        assertThat(testFreecom_addressee.getRfc()).isEqualTo(UPDATED_RFC);
        assertThat(testFreecom_addressee.getCurp()).isEqualTo(UPDATED_CURP);
        assertThat(testFreecom_addressee.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void deleteFreecom_addressee() throws Exception {
        // Initialize the database
        freecom_addresseeService.save(freecom_addressee);

        int databaseSizeBeforeDelete = freecom_addresseeRepository.findAll().size();

        // Get the freecom_addressee
        restFreecom_addresseeMockMvc.perform(delete("/api/freecom-addressees/{id}", freecom_addressee.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Freecom_addressee> freecom_addressees = freecom_addresseeRepository.findAll();
        assertThat(freecom_addressees).hasSize(databaseSizeBeforeDelete - 1);
    }
}
