package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Freecom_pfic;
import org.megapractical.billingplatform.repository.Freecom_pficRepository;
import org.megapractical.billingplatform.service.Freecom_pficService;

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
 * Test class for the Freecom_pficResource REST controller.
 *
 * @see Freecom_pficResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Freecom_pficResourceIntTest {

    private static final String DEFAULT_VERSION = "AAAAA";
    private static final String UPDATED_VERSION = "BBBBB";
    private static final String DEFAULT_KEY_VEHICLE = "AAAAA";
    private static final String UPDATED_KEY_VEHICLE = "BBBBB";
    private static final String DEFAULT_LICENSE_PLATE = "AAAAA";
    private static final String UPDATED_LICENSE_PLATE = "BBBBB";
    private static final String DEFAULT_RFCPF = "AAAAA";
    private static final String UPDATED_RFCPF = "BBBBB";

    @Inject
    private Freecom_pficRepository freecom_pficRepository;

    @Inject
    private Freecom_pficService freecom_pficService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFreecom_pficMockMvc;

    private Freecom_pfic freecom_pfic;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Freecom_pficResource freecom_pficResource = new Freecom_pficResource();
        ReflectionTestUtils.setField(freecom_pficResource, "freecom_pficService", freecom_pficService);
        this.restFreecom_pficMockMvc = MockMvcBuilders.standaloneSetup(freecom_pficResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        freecom_pfic = new Freecom_pfic();
        freecom_pfic.setVersion(DEFAULT_VERSION);
        freecom_pfic.setKey_vehicle(DEFAULT_KEY_VEHICLE);
        freecom_pfic.setLicense_plate(DEFAULT_LICENSE_PLATE);
        freecom_pfic.setRfcpf(DEFAULT_RFCPF);
    }

    @Test
    @Transactional
    public void createFreecom_pfic() throws Exception {
        int databaseSizeBeforeCreate = freecom_pficRepository.findAll().size();

        // Create the Freecom_pfic

        restFreecom_pficMockMvc.perform(post("/api/freecom-pfics")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_pfic)))
                .andExpect(status().isCreated());

        // Validate the Freecom_pfic in the database
        List<Freecom_pfic> freecom_pfics = freecom_pficRepository.findAll();
        assertThat(freecom_pfics).hasSize(databaseSizeBeforeCreate + 1);
        Freecom_pfic testFreecom_pfic = freecom_pfics.get(freecom_pfics.size() - 1);
        assertThat(testFreecom_pfic.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testFreecom_pfic.getKey_vehicle()).isEqualTo(DEFAULT_KEY_VEHICLE);
        assertThat(testFreecom_pfic.getLicense_plate()).isEqualTo(DEFAULT_LICENSE_PLATE);
        assertThat(testFreecom_pfic.getRfcpf()).isEqualTo(DEFAULT_RFCPF);
    }

    @Test
    @Transactional
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_pficRepository.findAll().size();
        // set the field null
        freecom_pfic.setVersion(null);

        // Create the Freecom_pfic, which fails.

        restFreecom_pficMockMvc.perform(post("/api/freecom-pfics")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_pfic)))
                .andExpect(status().isBadRequest());

        List<Freecom_pfic> freecom_pfics = freecom_pficRepository.findAll();
        assertThat(freecom_pfics).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkKey_vehicleIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_pficRepository.findAll().size();
        // set the field null
        freecom_pfic.setKey_vehicle(null);

        // Create the Freecom_pfic, which fails.

        restFreecom_pficMockMvc.perform(post("/api/freecom-pfics")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_pfic)))
                .andExpect(status().isBadRequest());

        List<Freecom_pfic> freecom_pfics = freecom_pficRepository.findAll();
        assertThat(freecom_pfics).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLicense_plateIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_pficRepository.findAll().size();
        // set the field null
        freecom_pfic.setLicense_plate(null);

        // Create the Freecom_pfic, which fails.

        restFreecom_pficMockMvc.perform(post("/api/freecom-pfics")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_pfic)))
                .andExpect(status().isBadRequest());

        List<Freecom_pfic> freecom_pfics = freecom_pficRepository.findAll();
        assertThat(freecom_pfics).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFreecom_pfics() throws Exception {
        // Initialize the database
        freecom_pficRepository.saveAndFlush(freecom_pfic);

        // Get all the freecom_pfics
        restFreecom_pficMockMvc.perform(get("/api/freecom-pfics?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(freecom_pfic.getId().intValue())))
                .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.toString())))
                .andExpect(jsonPath("$.[*].key_vehicle").value(hasItem(DEFAULT_KEY_VEHICLE.toString())))
                .andExpect(jsonPath("$.[*].license_plate").value(hasItem(DEFAULT_LICENSE_PLATE.toString())))
                .andExpect(jsonPath("$.[*].rfcpf").value(hasItem(DEFAULT_RFCPF.toString())));
    }

    @Test
    @Transactional
    public void getFreecom_pfic() throws Exception {
        // Initialize the database
        freecom_pficRepository.saveAndFlush(freecom_pfic);

        // Get the freecom_pfic
        restFreecom_pficMockMvc.perform(get("/api/freecom-pfics/{id}", freecom_pfic.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(freecom_pfic.getId().intValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.toString()))
            .andExpect(jsonPath("$.key_vehicle").value(DEFAULT_KEY_VEHICLE.toString()))
            .andExpect(jsonPath("$.license_plate").value(DEFAULT_LICENSE_PLATE.toString()))
            .andExpect(jsonPath("$.rfcpf").value(DEFAULT_RFCPF.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFreecom_pfic() throws Exception {
        // Get the freecom_pfic
        restFreecom_pficMockMvc.perform(get("/api/freecom-pfics/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFreecom_pfic() throws Exception {
        // Initialize the database
        freecom_pficService.save(freecom_pfic);

        int databaseSizeBeforeUpdate = freecom_pficRepository.findAll().size();

        // Update the freecom_pfic
        Freecom_pfic updatedFreecom_pfic = new Freecom_pfic();
        updatedFreecom_pfic.setId(freecom_pfic.getId());
        updatedFreecom_pfic.setVersion(UPDATED_VERSION);
        updatedFreecom_pfic.setKey_vehicle(UPDATED_KEY_VEHICLE);
        updatedFreecom_pfic.setLicense_plate(UPDATED_LICENSE_PLATE);
        updatedFreecom_pfic.setRfcpf(UPDATED_RFCPF);

        restFreecom_pficMockMvc.perform(put("/api/freecom-pfics")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedFreecom_pfic)))
                .andExpect(status().isOk());

        // Validate the Freecom_pfic in the database
        List<Freecom_pfic> freecom_pfics = freecom_pficRepository.findAll();
        assertThat(freecom_pfics).hasSize(databaseSizeBeforeUpdate);
        Freecom_pfic testFreecom_pfic = freecom_pfics.get(freecom_pfics.size() - 1);
        assertThat(testFreecom_pfic.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testFreecom_pfic.getKey_vehicle()).isEqualTo(UPDATED_KEY_VEHICLE);
        assertThat(testFreecom_pfic.getLicense_plate()).isEqualTo(UPDATED_LICENSE_PLATE);
        assertThat(testFreecom_pfic.getRfcpf()).isEqualTo(UPDATED_RFCPF);
    }

    @Test
    @Transactional
    public void deleteFreecom_pfic() throws Exception {
        // Initialize the database
        freecom_pficService.save(freecom_pfic);

        int databaseSizeBeforeDelete = freecom_pficRepository.findAll().size();

        // Get the freecom_pfic
        restFreecom_pficMockMvc.perform(delete("/api/freecom-pfics/{id}", freecom_pfic.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Freecom_pfic> freecom_pfics = freecom_pficRepository.findAll();
        assertThat(freecom_pfics).hasSize(databaseSizeBeforeDelete - 1);
    }
}
