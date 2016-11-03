package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Com_local_transfered;
import org.megapractical.billingplatform.repository.Com_local_transferedRepository;
import org.megapractical.billingplatform.service.Com_local_transferedService;

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
 * Test class for the Com_local_transferedResource REST controller.
 *
 * @see Com_local_transferedResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Com_local_transferedResourceIntTest {

    private static final String DEFAULT_IMPLOCTRANSFERED = "AAAAA";
    private static final String UPDATED_IMPLOCTRANSFERED = "BBBBB";

    private static final BigDecimal DEFAULT_TRANSFEREDRATE = new BigDecimal(1);
    private static final BigDecimal UPDATED_TRANSFEREDRATE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_AMOUNTTRANSFERED = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNTTRANSFERED = new BigDecimal(2);

    @Inject
    private Com_local_transferedRepository com_local_transferedRepository;

    @Inject
    private Com_local_transferedService com_local_transferedService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCom_local_transferedMockMvc;

    private Com_local_transfered com_local_transfered;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Com_local_transferedResource com_local_transferedResource = new Com_local_transferedResource();
        ReflectionTestUtils.setField(com_local_transferedResource, "com_local_transferedService", com_local_transferedService);
        this.restCom_local_transferedMockMvc = MockMvcBuilders.standaloneSetup(com_local_transferedResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        com_local_transfered = new Com_local_transfered();
        com_local_transfered.setImploctransfered(DEFAULT_IMPLOCTRANSFERED);
        com_local_transfered.setTransferedrate(DEFAULT_TRANSFEREDRATE);
        com_local_transfered.setAmounttransfered(DEFAULT_AMOUNTTRANSFERED);
    }

    @Test
    @Transactional
    public void createCom_local_transfered() throws Exception {
        int databaseSizeBeforeCreate = com_local_transferedRepository.findAll().size();

        // Create the Com_local_transfered

        restCom_local_transferedMockMvc.perform(post("/api/com-local-transfereds")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_local_transfered)))
                .andExpect(status().isCreated());

        // Validate the Com_local_transfered in the database
        List<Com_local_transfered> com_local_transfereds = com_local_transferedRepository.findAll();
        assertThat(com_local_transfereds).hasSize(databaseSizeBeforeCreate + 1);
        Com_local_transfered testCom_local_transfered = com_local_transfereds.get(com_local_transfereds.size() - 1);
        assertThat(testCom_local_transfered.getImploctransfered()).isEqualTo(DEFAULT_IMPLOCTRANSFERED);
        assertThat(testCom_local_transfered.getTransferedrate()).isEqualTo(DEFAULT_TRANSFEREDRATE);
        assertThat(testCom_local_transfered.getAmounttransfered()).isEqualTo(DEFAULT_AMOUNTTRANSFERED);
    }

    @Test
    @Transactional
    public void checkImploctransferedIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_local_transferedRepository.findAll().size();
        // set the field null
        com_local_transfered.setImploctransfered(null);

        // Create the Com_local_transfered, which fails.

        restCom_local_transferedMockMvc.perform(post("/api/com-local-transfereds")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_local_transfered)))
                .andExpect(status().isBadRequest());

        List<Com_local_transfered> com_local_transfereds = com_local_transferedRepository.findAll();
        assertThat(com_local_transfereds).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTransferedrateIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_local_transferedRepository.findAll().size();
        // set the field null
        com_local_transfered.setTransferedrate(null);

        // Create the Com_local_transfered, which fails.

        restCom_local_transferedMockMvc.perform(post("/api/com-local-transfereds")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_local_transfered)))
                .andExpect(status().isBadRequest());

        List<Com_local_transfered> com_local_transfereds = com_local_transferedRepository.findAll();
        assertThat(com_local_transfereds).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAmounttransferedIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_local_transferedRepository.findAll().size();
        // set the field null
        com_local_transfered.setAmounttransfered(null);

        // Create the Com_local_transfered, which fails.

        restCom_local_transferedMockMvc.perform(post("/api/com-local-transfereds")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_local_transfered)))
                .andExpect(status().isBadRequest());

        List<Com_local_transfered> com_local_transfereds = com_local_transferedRepository.findAll();
        assertThat(com_local_transfereds).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCom_local_transfereds() throws Exception {
        // Initialize the database
        com_local_transferedRepository.saveAndFlush(com_local_transfered);

        // Get all the com_local_transfereds
        restCom_local_transferedMockMvc.perform(get("/api/com-local-transfereds?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(com_local_transfered.getId().intValue())))
                .andExpect(jsonPath("$.[*].imploctransfered").value(hasItem(DEFAULT_IMPLOCTRANSFERED.toString())))
                .andExpect(jsonPath("$.[*].transferedrate").value(hasItem(DEFAULT_TRANSFEREDRATE.intValue())))
                .andExpect(jsonPath("$.[*].amounttransfered").value(hasItem(DEFAULT_AMOUNTTRANSFERED.intValue())));
    }

    @Test
    @Transactional
    public void getCom_local_transfered() throws Exception {
        // Initialize the database
        com_local_transferedRepository.saveAndFlush(com_local_transfered);

        // Get the com_local_transfered
        restCom_local_transferedMockMvc.perform(get("/api/com-local-transfereds/{id}", com_local_transfered.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(com_local_transfered.getId().intValue()))
            .andExpect(jsonPath("$.imploctransfered").value(DEFAULT_IMPLOCTRANSFERED.toString()))
            .andExpect(jsonPath("$.transferedrate").value(DEFAULT_TRANSFEREDRATE.intValue()))
            .andExpect(jsonPath("$.amounttransfered").value(DEFAULT_AMOUNTTRANSFERED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCom_local_transfered() throws Exception {
        // Get the com_local_transfered
        restCom_local_transferedMockMvc.perform(get("/api/com-local-transfereds/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCom_local_transfered() throws Exception {
        // Initialize the database
        com_local_transferedService.save(com_local_transfered);

        int databaseSizeBeforeUpdate = com_local_transferedRepository.findAll().size();

        // Update the com_local_transfered
        Com_local_transfered updatedCom_local_transfered = new Com_local_transfered();
        updatedCom_local_transfered.setId(com_local_transfered.getId());
        updatedCom_local_transfered.setImploctransfered(UPDATED_IMPLOCTRANSFERED);
        updatedCom_local_transfered.setTransferedrate(UPDATED_TRANSFEREDRATE);
        updatedCom_local_transfered.setAmounttransfered(UPDATED_AMOUNTTRANSFERED);

        restCom_local_transferedMockMvc.perform(put("/api/com-local-transfereds")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCom_local_transfered)))
                .andExpect(status().isOk());

        // Validate the Com_local_transfered in the database
        List<Com_local_transfered> com_local_transfereds = com_local_transferedRepository.findAll();
        assertThat(com_local_transfereds).hasSize(databaseSizeBeforeUpdate);
        Com_local_transfered testCom_local_transfered = com_local_transfereds.get(com_local_transfereds.size() - 1);
        assertThat(testCom_local_transfered.getImploctransfered()).isEqualTo(UPDATED_IMPLOCTRANSFERED);
        assertThat(testCom_local_transfered.getTransferedrate()).isEqualTo(UPDATED_TRANSFEREDRATE);
        assertThat(testCom_local_transfered.getAmounttransfered()).isEqualTo(UPDATED_AMOUNTTRANSFERED);
    }

    @Test
    @Transactional
    public void deleteCom_local_transfered() throws Exception {
        // Initialize the database
        com_local_transferedService.save(com_local_transfered);

        int databaseSizeBeforeDelete = com_local_transferedRepository.findAll().size();

        // Get the com_local_transfered
        restCom_local_transferedMockMvc.perform(delete("/api/com-local-transfereds/{id}", com_local_transfered.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Com_local_transfered> com_local_transfereds = com_local_transferedRepository.findAll();
        assertThat(com_local_transfereds).hasSize(databaseSizeBeforeDelete - 1);
    }
}
