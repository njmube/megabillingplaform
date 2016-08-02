package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Tracemg;
import org.megapractical.billingplatform.repository.TracemgRepository;
import org.megapractical.billingplatform.service.TracemgService;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the TracemgResource REST controller.
 *
 * @see TracemgResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class TracemgResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("Z"));

    private static final String DEFAULT_PRINCIPAL = "AAAAA";
    private static final String UPDATED_PRINCIPAL = "BBBBB";

    private static final ZonedDateTime DEFAULT_TIMESTAMP = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_TIMESTAMP = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_TIMESTAMP_STR = dateTimeFormatter.format(DEFAULT_TIMESTAMP);
    private static final String DEFAULT_IP = "AAAAA";
    private static final String UPDATED_IP = "BBBBB";

    @Inject
    private TracemgRepository tracemgRepository;

    @Inject
    private TracemgService tracemgService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restTracemgMockMvc;

    private Tracemg tracemg;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TracemgResource tracemgResource = new TracemgResource();
        ReflectionTestUtils.setField(tracemgResource, "tracemgService", tracemgService);
        this.restTracemgMockMvc = MockMvcBuilders.standaloneSetup(tracemgResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        tracemg = new Tracemg();
        tracemg.setPrincipal(DEFAULT_PRINCIPAL);
        tracemg.setTimestamp(DEFAULT_TIMESTAMP);
        tracemg.setIp(DEFAULT_IP);
    }

    @Test
    @Transactional
    public void createTracemg() throws Exception {
        /*
        int databaseSizeBeforeCreate = tracemgRepository.findAll().size();

        // Create the Tracemg

        restTracemgMockMvc.perform(post("/api/tracemgs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(tracemg)))
                .andExpect(status().isCreated());

        // Validate the Tracemg in the database
        List<Tracemg> tracemgs = tracemgRepository.findAll();
        assertThat(tracemgs).hasSize(databaseSizeBeforeCreate + 1);
        Tracemg testTracemg = tracemgs.get(tracemgs.size() - 1);
        assertThat(testTracemg.getPrincipal()).isEqualTo(DEFAULT_PRINCIPAL);
        assertThat(testTracemg.getTimestamp()).isEqualTo(DEFAULT_TIMESTAMP);
        assertThat(testTracemg.getIp()).isEqualTo(DEFAULT_IP);*/
    }

    @Test
    @Transactional
    public void checkPrincipalIsRequired() throws Exception {
        /*
        int databaseSizeBeforeTest = tracemgRepository.findAll().size();
        // set the field null
        tracemg.setPrincipal(null);

        // Create the Tracemg, which fails.

        restTracemgMockMvc.perform(post("/api/tracemgs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(tracemg)))
                .andExpect(status().isBadRequest());

        List<Tracemg> tracemgs = tracemgRepository.findAll();
        assertThat(tracemgs).hasSize(databaseSizeBeforeTest);*/
    }

    @Test
    @Transactional
    public void checkTimestampIsRequired() throws Exception {
        /*
        int databaseSizeBeforeTest = tracemgRepository.findAll().size();
        // set the field null
        tracemg.setTimestamp(null);

        // Create the Tracemg, which fails.

        restTracemgMockMvc.perform(post("/api/tracemgs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(tracemg)))
                .andExpect(status().isBadRequest());

        List<Tracemg> tracemgs = tracemgRepository.findAll();
        assertThat(tracemgs).hasSize(databaseSizeBeforeTest);*/
    }

    @Test
    @Transactional
    public void checkIpIsRequired() throws Exception {
        /*
        int databaseSizeBeforeTest = tracemgRepository.findAll().size();
        // set the field null
        tracemg.setIp(null);

        // Create the Tracemg, which fails.

        restTracemgMockMvc.perform(post("/api/tracemgs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(tracemg)))
                .andExpect(status().isBadRequest());

        List<Tracemg> tracemgs = tracemgRepository.findAll();
        assertThat(tracemgs).hasSize(databaseSizeBeforeTest);*/
    }

    @Test
    @Transactional
    public void getAllTracemgs() throws Exception {
        /*
        // Initialize the database
        tracemgRepository.saveAndFlush(tracemg);

        // Get all the tracemgs
        restTracemgMockMvc.perform(get("/api/tracemgs?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(tracemg.getId().intValue())))
                .andExpect(jsonPath("$.[*].principal").value(hasItem(DEFAULT_PRINCIPAL.toString())))
                .andExpect(jsonPath("$.[*].timestamp").value(hasItem(DEFAULT_TIMESTAMP_STR)))
                .andExpect(jsonPath("$.[*].ip").value(hasItem(DEFAULT_IP.toString())));*/
    }

    @Test
    @Transactional
    public void getTracemg() throws Exception {
        /*
        // Initialize the database
        tracemgRepository.saveAndFlush(tracemg);

        // Get the tracemg
        restTracemgMockMvc.perform(get("/api/tracemgs/{id}", tracemg.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(tracemg.getId().intValue()))
            .andExpect(jsonPath("$.principal").value(DEFAULT_PRINCIPAL.toString()))
            .andExpect(jsonPath("$.timestamp").value(DEFAULT_TIMESTAMP_STR))
            .andExpect(jsonPath("$.ip").value(DEFAULT_IP.toString()));*/
    }

    @Test
    @Transactional
    public void getNonExistingTracemg() throws Exception {
        /*
        // Get the tracemg
        restTracemgMockMvc.perform(get("/api/tracemgs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());*/
    }

    @Test
    @Transactional
    public void updateTracemg() throws Exception {
        /*
        // Initialize the database
        tracemgService.save(tracemg);

        int databaseSizeBeforeUpdate = tracemgRepository.findAll().size();

        // Update the tracemg
        Tracemg updatedTracemg = new Tracemg();
        updatedTracemg.setId(tracemg.getId());
        updatedTracemg.setPrincipal(UPDATED_PRINCIPAL);
        updatedTracemg.setTimestamp(UPDATED_TIMESTAMP);
        updatedTracemg.setIp(UPDATED_IP);

        restTracemgMockMvc.perform(put("/api/tracemgs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedTracemg)))
                .andExpect(status().isOk());

        // Validate the Tracemg in the database
        List<Tracemg> tracemgs = tracemgRepository.findAll();
        assertThat(tracemgs).hasSize(databaseSizeBeforeUpdate);
        Tracemg testTracemg = tracemgs.get(tracemgs.size() - 1);
        assertThat(testTracemg.getPrincipal()).isEqualTo(UPDATED_PRINCIPAL);
        assertThat(testTracemg.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);
        assertThat(testTracemg.getIp()).isEqualTo(UPDATED_IP);*/
    }

    @Test
    @Transactional
    public void deleteTracemg() throws Exception {
        /*
        // Initialize the database
        tracemgService.save(tracemg);

        int databaseSizeBeforeDelete = tracemgRepository.findAll().size();

        // Get the tracemg
        restTracemgMockMvc.perform(delete("/api/tracemgs/{id}", tracemg.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Tracemg> tracemgs = tracemgRepository.findAll();
        assertThat(tracemgs).hasSize(databaseSizeBeforeDelete - 1);*/
    }
}
