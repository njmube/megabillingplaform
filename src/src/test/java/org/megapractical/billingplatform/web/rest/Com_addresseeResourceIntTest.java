package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Com_addressee;
import org.megapractical.billingplatform.repository.Com_addresseeRepository;
import org.megapractical.billingplatform.service.Com_addresseeService;

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
 * Test class for the Com_addresseeResource REST controller.
 *
 * @see Com_addresseeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Com_addresseeResourceIntTest {

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
    private Com_addresseeRepository com_addresseeRepository;

    @Inject
    private Com_addresseeService com_addresseeService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCom_addresseeMockMvc;

    private Com_addressee com_addressee;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Com_addresseeResource com_addresseeResource = new Com_addresseeResource();
        ReflectionTestUtils.setField(com_addresseeResource, "com_addresseeService", com_addresseeService);
        this.restCom_addresseeMockMvc = MockMvcBuilders.standaloneSetup(com_addresseeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        com_addressee = new Com_addressee();
        com_addressee.setStreet(DEFAULT_STREET);
        com_addressee.setNo_ext(DEFAULT_NO_EXT);
        com_addressee.setNo_int(DEFAULT_NO_INT);
        com_addressee.setLocate(DEFAULT_LOCATE);
        com_addressee.setReference(DEFAULT_REFERENCE);
        com_addressee.setNumregidtrib(DEFAULT_NUMREGIDTRIB);
        com_addressee.setRfc(DEFAULT_RFC);
        com_addressee.setCurp(DEFAULT_CURP);
        com_addressee.setName(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createCom_addressee() throws Exception {
        int databaseSizeBeforeCreate = com_addresseeRepository.findAll().size();

        // Create the Com_addressee

        restCom_addresseeMockMvc.perform(post("/api/com-addressees")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_addressee)))
                .andExpect(status().isCreated());

        // Validate the Com_addressee in the database
        List<Com_addressee> com_addressees = com_addresseeRepository.findAll();
        assertThat(com_addressees).hasSize(databaseSizeBeforeCreate + 1);
        Com_addressee testCom_addressee = com_addressees.get(com_addressees.size() - 1);
        assertThat(testCom_addressee.getStreet()).isEqualTo(DEFAULT_STREET);
        assertThat(testCom_addressee.getNo_ext()).isEqualTo(DEFAULT_NO_EXT);
        assertThat(testCom_addressee.getNo_int()).isEqualTo(DEFAULT_NO_INT);
        assertThat(testCom_addressee.getLocate()).isEqualTo(DEFAULT_LOCATE);
        assertThat(testCom_addressee.getReference()).isEqualTo(DEFAULT_REFERENCE);
        assertThat(testCom_addressee.getNumregidtrib()).isEqualTo(DEFAULT_NUMREGIDTRIB);
        assertThat(testCom_addressee.getRfc()).isEqualTo(DEFAULT_RFC);
        assertThat(testCom_addressee.getCurp()).isEqualTo(DEFAULT_CURP);
        assertThat(testCom_addressee.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void checkStreetIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_addresseeRepository.findAll().size();
        // set the field null
        com_addressee.setStreet(null);

        // Create the Com_addressee, which fails.

        restCom_addresseeMockMvc.perform(post("/api/com-addressees")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_addressee)))
                .andExpect(status().isBadRequest());

        List<Com_addressee> com_addressees = com_addresseeRepository.findAll();
        assertThat(com_addressees).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCom_addressees() throws Exception {
        // Initialize the database
        com_addresseeRepository.saveAndFlush(com_addressee);

        // Get all the com_addressees
        restCom_addresseeMockMvc.perform(get("/api/com-addressees?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(com_addressee.getId().intValue())))
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
    public void getCom_addressee() throws Exception {
        // Initialize the database
        com_addresseeRepository.saveAndFlush(com_addressee);

        // Get the com_addressee
        restCom_addresseeMockMvc.perform(get("/api/com-addressees/{id}", com_addressee.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(com_addressee.getId().intValue()))
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
    public void getNonExistingCom_addressee() throws Exception {
        // Get the com_addressee
        restCom_addresseeMockMvc.perform(get("/api/com-addressees/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCom_addressee() throws Exception {
        // Initialize the database
        com_addresseeService.save(com_addressee);

        int databaseSizeBeforeUpdate = com_addresseeRepository.findAll().size();

        // Update the com_addressee
        Com_addressee updatedCom_addressee = new Com_addressee();
        updatedCom_addressee.setId(com_addressee.getId());
        updatedCom_addressee.setStreet(UPDATED_STREET);
        updatedCom_addressee.setNo_ext(UPDATED_NO_EXT);
        updatedCom_addressee.setNo_int(UPDATED_NO_INT);
        updatedCom_addressee.setLocate(UPDATED_LOCATE);
        updatedCom_addressee.setReference(UPDATED_REFERENCE);
        updatedCom_addressee.setNumregidtrib(UPDATED_NUMREGIDTRIB);
        updatedCom_addressee.setRfc(UPDATED_RFC);
        updatedCom_addressee.setCurp(UPDATED_CURP);
        updatedCom_addressee.setName(UPDATED_NAME);

        restCom_addresseeMockMvc.perform(put("/api/com-addressees")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCom_addressee)))
                .andExpect(status().isOk());

        // Validate the Com_addressee in the database
        List<Com_addressee> com_addressees = com_addresseeRepository.findAll();
        assertThat(com_addressees).hasSize(databaseSizeBeforeUpdate);
        Com_addressee testCom_addressee = com_addressees.get(com_addressees.size() - 1);
        assertThat(testCom_addressee.getStreet()).isEqualTo(UPDATED_STREET);
        assertThat(testCom_addressee.getNo_ext()).isEqualTo(UPDATED_NO_EXT);
        assertThat(testCom_addressee.getNo_int()).isEqualTo(UPDATED_NO_INT);
        assertThat(testCom_addressee.getLocate()).isEqualTo(UPDATED_LOCATE);
        assertThat(testCom_addressee.getReference()).isEqualTo(UPDATED_REFERENCE);
        assertThat(testCom_addressee.getNumregidtrib()).isEqualTo(UPDATED_NUMREGIDTRIB);
        assertThat(testCom_addressee.getRfc()).isEqualTo(UPDATED_RFC);
        assertThat(testCom_addressee.getCurp()).isEqualTo(UPDATED_CURP);
        assertThat(testCom_addressee.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void deleteCom_addressee() throws Exception {
        // Initialize the database
        com_addresseeService.save(com_addressee);

        int databaseSizeBeforeDelete = com_addresseeRepository.findAll().size();

        // Get the com_addressee
        restCom_addresseeMockMvc.perform(delete("/api/com-addressees/{id}", com_addressee.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Com_addressee> com_addressees = com_addresseeRepository.findAll();
        assertThat(com_addressees).hasSize(databaseSizeBeforeDelete - 1);
    }
}
