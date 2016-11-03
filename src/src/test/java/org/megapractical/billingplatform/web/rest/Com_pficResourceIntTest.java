package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Com_pfic;
import org.megapractical.billingplatform.repository.Com_pficRepository;
import org.megapractical.billingplatform.service.Com_pficService;

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
 * Test class for the Com_pficResource REST controller.
 *
 * @see Com_pficResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Com_pficResourceIntTest {

    private static final String DEFAULT_VERSION = "AAAAA";
    private static final String UPDATED_VERSION = "BBBBB";
    private static final String DEFAULT_KEY_VEHICLE = "AAAAA";
    private static final String UPDATED_KEY_VEHICLE = "BBBBB";
    private static final String DEFAULT_LICENSE_PLATE = "AAAAA";
    private static final String UPDATED_LICENSE_PLATE = "BBBBB";
    private static final String DEFAULT_RFCPF = "AAAAA";
    private static final String UPDATED_RFCPF = "BBBBB";

    @Inject
    private Com_pficRepository com_pficRepository;

    @Inject
    private Com_pficService com_pficService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCom_pficMockMvc;

    private Com_pfic com_pfic;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Com_pficResource com_pficResource = new Com_pficResource();
        ReflectionTestUtils.setField(com_pficResource, "com_pficService", com_pficService);
        this.restCom_pficMockMvc = MockMvcBuilders.standaloneSetup(com_pficResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        com_pfic = new Com_pfic();
        com_pfic.setVersion(DEFAULT_VERSION);
        com_pfic.setKey_vehicle(DEFAULT_KEY_VEHICLE);
        com_pfic.setLicense_plate(DEFAULT_LICENSE_PLATE);
        com_pfic.setRfcpf(DEFAULT_RFCPF);
    }

    @Test
    @Transactional
    public void createCom_pfic() throws Exception {
        int databaseSizeBeforeCreate = com_pficRepository.findAll().size();

        // Create the Com_pfic

        restCom_pficMockMvc.perform(post("/api/com-pfics")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_pfic)))
                .andExpect(status().isCreated());

        // Validate the Com_pfic in the database
        List<Com_pfic> com_pfics = com_pficRepository.findAll();
        assertThat(com_pfics).hasSize(databaseSizeBeforeCreate + 1);
        Com_pfic testCom_pfic = com_pfics.get(com_pfics.size() - 1);
        assertThat(testCom_pfic.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testCom_pfic.getKey_vehicle()).isEqualTo(DEFAULT_KEY_VEHICLE);
        assertThat(testCom_pfic.getLicense_plate()).isEqualTo(DEFAULT_LICENSE_PLATE);
        assertThat(testCom_pfic.getRfcpf()).isEqualTo(DEFAULT_RFCPF);
    }

    @Test
    @Transactional
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_pficRepository.findAll().size();
        // set the field null
        com_pfic.setVersion(null);

        // Create the Com_pfic, which fails.

        restCom_pficMockMvc.perform(post("/api/com-pfics")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_pfic)))
                .andExpect(status().isBadRequest());

        List<Com_pfic> com_pfics = com_pficRepository.findAll();
        assertThat(com_pfics).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkKey_vehicleIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_pficRepository.findAll().size();
        // set the field null
        com_pfic.setKey_vehicle(null);

        // Create the Com_pfic, which fails.

        restCom_pficMockMvc.perform(post("/api/com-pfics")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_pfic)))
                .andExpect(status().isBadRequest());

        List<Com_pfic> com_pfics = com_pficRepository.findAll();
        assertThat(com_pfics).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLicense_plateIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_pficRepository.findAll().size();
        // set the field null
        com_pfic.setLicense_plate(null);

        // Create the Com_pfic, which fails.

        restCom_pficMockMvc.perform(post("/api/com-pfics")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_pfic)))
                .andExpect(status().isBadRequest());

        List<Com_pfic> com_pfics = com_pficRepository.findAll();
        assertThat(com_pfics).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCom_pfics() throws Exception {
        // Initialize the database
        com_pficRepository.saveAndFlush(com_pfic);

        // Get all the com_pfics
        restCom_pficMockMvc.perform(get("/api/com-pfics?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(com_pfic.getId().intValue())))
                .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.toString())))
                .andExpect(jsonPath("$.[*].key_vehicle").value(hasItem(DEFAULT_KEY_VEHICLE.toString())))
                .andExpect(jsonPath("$.[*].license_plate").value(hasItem(DEFAULT_LICENSE_PLATE.toString())))
                .andExpect(jsonPath("$.[*].rfcpf").value(hasItem(DEFAULT_RFCPF.toString())));
    }

    @Test
    @Transactional
    public void getCom_pfic() throws Exception {
        // Initialize the database
        com_pficRepository.saveAndFlush(com_pfic);

        // Get the com_pfic
        restCom_pficMockMvc.perform(get("/api/com-pfics/{id}", com_pfic.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(com_pfic.getId().intValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.toString()))
            .andExpect(jsonPath("$.key_vehicle").value(DEFAULT_KEY_VEHICLE.toString()))
            .andExpect(jsonPath("$.license_plate").value(DEFAULT_LICENSE_PLATE.toString()))
            .andExpect(jsonPath("$.rfcpf").value(DEFAULT_RFCPF.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCom_pfic() throws Exception {
        // Get the com_pfic
        restCom_pficMockMvc.perform(get("/api/com-pfics/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCom_pfic() throws Exception {
        // Initialize the database
        com_pficService.save(com_pfic);

        int databaseSizeBeforeUpdate = com_pficRepository.findAll().size();

        // Update the com_pfic
        Com_pfic updatedCom_pfic = new Com_pfic();
        updatedCom_pfic.setId(com_pfic.getId());
        updatedCom_pfic.setVersion(UPDATED_VERSION);
        updatedCom_pfic.setKey_vehicle(UPDATED_KEY_VEHICLE);
        updatedCom_pfic.setLicense_plate(UPDATED_LICENSE_PLATE);
        updatedCom_pfic.setRfcpf(UPDATED_RFCPF);

        restCom_pficMockMvc.perform(put("/api/com-pfics")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCom_pfic)))
                .andExpect(status().isOk());

        // Validate the Com_pfic in the database
        List<Com_pfic> com_pfics = com_pficRepository.findAll();
        assertThat(com_pfics).hasSize(databaseSizeBeforeUpdate);
        Com_pfic testCom_pfic = com_pfics.get(com_pfics.size() - 1);
        assertThat(testCom_pfic.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testCom_pfic.getKey_vehicle()).isEqualTo(UPDATED_KEY_VEHICLE);
        assertThat(testCom_pfic.getLicense_plate()).isEqualTo(UPDATED_LICENSE_PLATE);
        assertThat(testCom_pfic.getRfcpf()).isEqualTo(UPDATED_RFCPF);
    }

    @Test
    @Transactional
    public void deleteCom_pfic() throws Exception {
        // Initialize the database
        com_pficService.save(com_pfic);

        int databaseSizeBeforeDelete = com_pficRepository.findAll().size();

        // Get the com_pfic
        restCom_pficMockMvc.perform(delete("/api/com-pfics/{id}", com_pfic.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Com_pfic> com_pfics = com_pficRepository.findAll();
        assertThat(com_pfics).hasSize(databaseSizeBeforeDelete - 1);
    }
}
