package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Com_dataunacquiring;
import org.megapractical.billingplatform.repository.Com_dataunacquiringRepository;
import org.megapractical.billingplatform.service.Com_dataunacquiringService;

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
 * Test class for the Com_dataunacquiringResource REST controller.
 *
 * @see Com_dataunacquiringResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Com_dataunacquiringResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_LAST_NAME = "AAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBB";
    private static final String DEFAULT_MOTHER_LAST_NAME = "AAAAA";
    private static final String UPDATED_MOTHER_LAST_NAME = "BBBBB";
    private static final String DEFAULT_RFC = "AAAAA";
    private static final String UPDATED_RFC = "BBBBB";
    private static final String DEFAULT_CURP = "AAAAA";
    private static final String UPDATED_CURP = "BBBBB";

    @Inject
    private Com_dataunacquiringRepository com_dataunacquiringRepository;

    @Inject
    private Com_dataunacquiringService com_dataunacquiringService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCom_dataunacquiringMockMvc;

    private Com_dataunacquiring com_dataunacquiring;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Com_dataunacquiringResource com_dataunacquiringResource = new Com_dataunacquiringResource();
        ReflectionTestUtils.setField(com_dataunacquiringResource, "com_dataunacquiringService", com_dataunacquiringService);
        this.restCom_dataunacquiringMockMvc = MockMvcBuilders.standaloneSetup(com_dataunacquiringResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        com_dataunacquiring = new Com_dataunacquiring();
        com_dataunacquiring.setName(DEFAULT_NAME);
        com_dataunacquiring.setLast_name(DEFAULT_LAST_NAME);
        com_dataunacquiring.setMother_last_name(DEFAULT_MOTHER_LAST_NAME);
        com_dataunacquiring.setRfc(DEFAULT_RFC);
        com_dataunacquiring.setCurp(DEFAULT_CURP);
    }

    @Test
    @Transactional
    public void createCom_dataunacquiring() throws Exception {
        int databaseSizeBeforeCreate = com_dataunacquiringRepository.findAll().size();

        // Create the Com_dataunacquiring

        restCom_dataunacquiringMockMvc.perform(post("/api/com-dataunacquirings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_dataunacquiring)))
                .andExpect(status().isCreated());

        // Validate the Com_dataunacquiring in the database
        List<Com_dataunacquiring> com_dataunacquirings = com_dataunacquiringRepository.findAll();
        assertThat(com_dataunacquirings).hasSize(databaseSizeBeforeCreate + 1);
        Com_dataunacquiring testCom_dataunacquiring = com_dataunacquirings.get(com_dataunacquirings.size() - 1);
        assertThat(testCom_dataunacquiring.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCom_dataunacquiring.getLast_name()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testCom_dataunacquiring.getMother_last_name()).isEqualTo(DEFAULT_MOTHER_LAST_NAME);
        assertThat(testCom_dataunacquiring.getRfc()).isEqualTo(DEFAULT_RFC);
        assertThat(testCom_dataunacquiring.getCurp()).isEqualTo(DEFAULT_CURP);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_dataunacquiringRepository.findAll().size();
        // set the field null
        com_dataunacquiring.setName(null);

        // Create the Com_dataunacquiring, which fails.

        restCom_dataunacquiringMockMvc.perform(post("/api/com-dataunacquirings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_dataunacquiring)))
                .andExpect(status().isBadRequest());

        List<Com_dataunacquiring> com_dataunacquirings = com_dataunacquiringRepository.findAll();
        assertThat(com_dataunacquirings).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRfcIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_dataunacquiringRepository.findAll().size();
        // set the field null
        com_dataunacquiring.setRfc(null);

        // Create the Com_dataunacquiring, which fails.

        restCom_dataunacquiringMockMvc.perform(post("/api/com-dataunacquirings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_dataunacquiring)))
                .andExpect(status().isBadRequest());

        List<Com_dataunacquiring> com_dataunacquirings = com_dataunacquiringRepository.findAll();
        assertThat(com_dataunacquirings).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCom_dataunacquirings() throws Exception {
        // Initialize the database
        com_dataunacquiringRepository.saveAndFlush(com_dataunacquiring);

        // Get all the com_dataunacquirings
        restCom_dataunacquiringMockMvc.perform(get("/api/com-dataunacquirings?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(com_dataunacquiring.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].last_name").value(hasItem(DEFAULT_LAST_NAME.toString())))
                .andExpect(jsonPath("$.[*].mother_last_name").value(hasItem(DEFAULT_MOTHER_LAST_NAME.toString())))
                .andExpect(jsonPath("$.[*].rfc").value(hasItem(DEFAULT_RFC.toString())))
                .andExpect(jsonPath("$.[*].curp").value(hasItem(DEFAULT_CURP.toString())));
    }

    @Test
    @Transactional
    public void getCom_dataunacquiring() throws Exception {
        // Initialize the database
        com_dataunacquiringRepository.saveAndFlush(com_dataunacquiring);

        // Get the com_dataunacquiring
        restCom_dataunacquiringMockMvc.perform(get("/api/com-dataunacquirings/{id}", com_dataunacquiring.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(com_dataunacquiring.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.last_name").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.mother_last_name").value(DEFAULT_MOTHER_LAST_NAME.toString()))
            .andExpect(jsonPath("$.rfc").value(DEFAULT_RFC.toString()))
            .andExpect(jsonPath("$.curp").value(DEFAULT_CURP.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCom_dataunacquiring() throws Exception {
        // Get the com_dataunacquiring
        restCom_dataunacquiringMockMvc.perform(get("/api/com-dataunacquirings/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCom_dataunacquiring() throws Exception {
        // Initialize the database
        com_dataunacquiringService.save(com_dataunacquiring);

        int databaseSizeBeforeUpdate = com_dataunacquiringRepository.findAll().size();

        // Update the com_dataunacquiring
        Com_dataunacquiring updatedCom_dataunacquiring = new Com_dataunacquiring();
        updatedCom_dataunacquiring.setId(com_dataunacquiring.getId());
        updatedCom_dataunacquiring.setName(UPDATED_NAME);
        updatedCom_dataunacquiring.setLast_name(UPDATED_LAST_NAME);
        updatedCom_dataunacquiring.setMother_last_name(UPDATED_MOTHER_LAST_NAME);
        updatedCom_dataunacquiring.setRfc(UPDATED_RFC);
        updatedCom_dataunacquiring.setCurp(UPDATED_CURP);

        restCom_dataunacquiringMockMvc.perform(put("/api/com-dataunacquirings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCom_dataunacquiring)))
                .andExpect(status().isOk());

        // Validate the Com_dataunacquiring in the database
        List<Com_dataunacquiring> com_dataunacquirings = com_dataunacquiringRepository.findAll();
        assertThat(com_dataunacquirings).hasSize(databaseSizeBeforeUpdate);
        Com_dataunacquiring testCom_dataunacquiring = com_dataunacquirings.get(com_dataunacquirings.size() - 1);
        assertThat(testCom_dataunacquiring.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCom_dataunacquiring.getLast_name()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testCom_dataunacquiring.getMother_last_name()).isEqualTo(UPDATED_MOTHER_LAST_NAME);
        assertThat(testCom_dataunacquiring.getRfc()).isEqualTo(UPDATED_RFC);
        assertThat(testCom_dataunacquiring.getCurp()).isEqualTo(UPDATED_CURP);
    }

    @Test
    @Transactional
    public void deleteCom_dataunacquiring() throws Exception {
        // Initialize the database
        com_dataunacquiringService.save(com_dataunacquiring);

        int databaseSizeBeforeDelete = com_dataunacquiringRepository.findAll().size();

        // Get the com_dataunacquiring
        restCom_dataunacquiringMockMvc.perform(delete("/api/com-dataunacquirings/{id}", com_dataunacquiring.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Com_dataunacquiring> com_dataunacquirings = com_dataunacquiringRepository.findAll();
        assertThat(com_dataunacquirings).hasSize(databaseSizeBeforeDelete - 1);
    }
}
