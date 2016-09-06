package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Freecom_local_transfered;
import org.megapractical.billingplatform.repository.Freecom_local_transferedRepository;
import org.megapractical.billingplatform.service.Freecom_local_transferedService;

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
import java.math.BigDecimal;;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the Freecom_local_transferedResource REST controller.
 *
 * @see Freecom_local_transferedResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Freecom_local_transferedResourceIntTest {

    private static final String DEFAULT_IMPLOCTRANSFERED = "AAAAA";
    private static final String UPDATED_IMPLOCTRANSFERED = "BBBBB";

    private static final BigDecimal DEFAULT_TRANSFEREDRATE = new BigDecimal(1);
    private static final BigDecimal UPDATED_TRANSFEREDRATE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_AMOUNTTRANSFERED = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNTTRANSFERED = new BigDecimal(2);

    @Inject
    private Freecom_local_transferedRepository freecom_local_transferedRepository;

    @Inject
    private Freecom_local_transferedService freecom_local_transferedService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFreecom_local_transferedMockMvc;

    private Freecom_local_transfered freecom_local_transfered;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Freecom_local_transferedResource freecom_local_transferedResource = new Freecom_local_transferedResource();
        ReflectionTestUtils.setField(freecom_local_transferedResource, "freecom_local_transferedService", freecom_local_transferedService);
        this.restFreecom_local_transferedMockMvc = MockMvcBuilders.standaloneSetup(freecom_local_transferedResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        freecom_local_transfered = new Freecom_local_transfered();
        freecom_local_transfered.setImploctransfered(DEFAULT_IMPLOCTRANSFERED);
        freecom_local_transfered.setTransferedrate(DEFAULT_TRANSFEREDRATE);
        freecom_local_transfered.setAmounttransfered(DEFAULT_AMOUNTTRANSFERED);
    }

    @Test
    @Transactional
    public void createFreecom_local_transfered() throws Exception {
        int databaseSizeBeforeCreate = freecom_local_transferedRepository.findAll().size();

        // Create the Freecom_local_transfered

        restFreecom_local_transferedMockMvc.perform(post("/api/freecom-local-transfereds")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_local_transfered)))
                .andExpect(status().isCreated());

        // Validate the Freecom_local_transfered in the database
        List<Freecom_local_transfered> freecom_local_transfereds = freecom_local_transferedRepository.findAll();
        assertThat(freecom_local_transfereds).hasSize(databaseSizeBeforeCreate + 1);
        Freecom_local_transfered testFreecom_local_transfered = freecom_local_transfereds.get(freecom_local_transfereds.size() - 1);
        assertThat(testFreecom_local_transfered.getImploctransfered()).isEqualTo(DEFAULT_IMPLOCTRANSFERED);
        assertThat(testFreecom_local_transfered.getTransferedrate()).isEqualTo(DEFAULT_TRANSFEREDRATE);
        assertThat(testFreecom_local_transfered.getAmounttransfered()).isEqualTo(DEFAULT_AMOUNTTRANSFERED);
    }

    @Test
    @Transactional
    public void checkImploctransferedIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_local_transferedRepository.findAll().size();
        // set the field null
        freecom_local_transfered.setImploctransfered(null);

        // Create the Freecom_local_transfered, which fails.

        restFreecom_local_transferedMockMvc.perform(post("/api/freecom-local-transfereds")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_local_transfered)))
                .andExpect(status().isBadRequest());

        List<Freecom_local_transfered> freecom_local_transfereds = freecom_local_transferedRepository.findAll();
        assertThat(freecom_local_transfereds).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTransferedrateIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_local_transferedRepository.findAll().size();
        // set the field null
        freecom_local_transfered.setTransferedrate(null);

        // Create the Freecom_local_transfered, which fails.

        restFreecom_local_transferedMockMvc.perform(post("/api/freecom-local-transfereds")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_local_transfered)))
                .andExpect(status().isBadRequest());

        List<Freecom_local_transfered> freecom_local_transfereds = freecom_local_transferedRepository.findAll();
        assertThat(freecom_local_transfereds).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAmounttransferedIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_local_transferedRepository.findAll().size();
        // set the field null
        freecom_local_transfered.setAmounttransfered(null);

        // Create the Freecom_local_transfered, which fails.

        restFreecom_local_transferedMockMvc.perform(post("/api/freecom-local-transfereds")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_local_transfered)))
                .andExpect(status().isBadRequest());

        List<Freecom_local_transfered> freecom_local_transfereds = freecom_local_transferedRepository.findAll();
        assertThat(freecom_local_transfereds).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFreecom_local_transfereds() throws Exception {
        // Initialize the database
        freecom_local_transferedRepository.saveAndFlush(freecom_local_transfered);

        // Get all the freecom_local_transfereds
        restFreecom_local_transferedMockMvc.perform(get("/api/freecom-local-transfereds?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(freecom_local_transfered.getId().intValue())))
                .andExpect(jsonPath("$.[*].imploctransfered").value(hasItem(DEFAULT_IMPLOCTRANSFERED.toString())))
                .andExpect(jsonPath("$.[*].transferedrate").value(hasItem(DEFAULT_TRANSFEREDRATE.intValue())))
                .andExpect(jsonPath("$.[*].amounttransfered").value(hasItem(DEFAULT_AMOUNTTRANSFERED.intValue())));
    }

    @Test
    @Transactional
    public void getFreecom_local_transfered() throws Exception {
        // Initialize the database
        freecom_local_transferedRepository.saveAndFlush(freecom_local_transfered);

        // Get the freecom_local_transfered
        restFreecom_local_transferedMockMvc.perform(get("/api/freecom-local-transfereds/{id}", freecom_local_transfered.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(freecom_local_transfered.getId().intValue()))
            .andExpect(jsonPath("$.imploctransfered").value(DEFAULT_IMPLOCTRANSFERED.toString()))
            .andExpect(jsonPath("$.transferedrate").value(DEFAULT_TRANSFEREDRATE.intValue()))
            .andExpect(jsonPath("$.amounttransfered").value(DEFAULT_AMOUNTTRANSFERED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingFreecom_local_transfered() throws Exception {
        // Get the freecom_local_transfered
        restFreecom_local_transferedMockMvc.perform(get("/api/freecom-local-transfereds/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFreecom_local_transfered() throws Exception {
        // Initialize the database
        freecom_local_transferedService.save(freecom_local_transfered);

        int databaseSizeBeforeUpdate = freecom_local_transferedRepository.findAll().size();

        // Update the freecom_local_transfered
        Freecom_local_transfered updatedFreecom_local_transfered = new Freecom_local_transfered();
        updatedFreecom_local_transfered.setId(freecom_local_transfered.getId());
        updatedFreecom_local_transfered.setImploctransfered(UPDATED_IMPLOCTRANSFERED);
        updatedFreecom_local_transfered.setTransferedrate(UPDATED_TRANSFEREDRATE);
        updatedFreecom_local_transfered.setAmounttransfered(UPDATED_AMOUNTTRANSFERED);

        restFreecom_local_transferedMockMvc.perform(put("/api/freecom-local-transfereds")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedFreecom_local_transfered)))
                .andExpect(status().isOk());

        // Validate the Freecom_local_transfered in the database
        List<Freecom_local_transfered> freecom_local_transfereds = freecom_local_transferedRepository.findAll();
        assertThat(freecom_local_transfereds).hasSize(databaseSizeBeforeUpdate);
        Freecom_local_transfered testFreecom_local_transfered = freecom_local_transfereds.get(freecom_local_transfereds.size() - 1);
        assertThat(testFreecom_local_transfered.getImploctransfered()).isEqualTo(UPDATED_IMPLOCTRANSFERED);
        assertThat(testFreecom_local_transfered.getTransferedrate()).isEqualTo(UPDATED_TRANSFEREDRATE);
        assertThat(testFreecom_local_transfered.getAmounttransfered()).isEqualTo(UPDATED_AMOUNTTRANSFERED);
    }

    @Test
    @Transactional
    public void deleteFreecom_local_transfered() throws Exception {
        // Initialize the database
        freecom_local_transferedService.save(freecom_local_transfered);

        int databaseSizeBeforeDelete = freecom_local_transferedRepository.findAll().size();

        // Get the freecom_local_transfered
        restFreecom_local_transferedMockMvc.perform(delete("/api/freecom-local-transfereds/{id}", freecom_local_transfered.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Freecom_local_transfered> freecom_local_transfereds = freecom_local_transferedRepository.findAll();
        assertThat(freecom_local_transfereds).hasSize(databaseSizeBeforeDelete - 1);
    }
}
