package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Branch_office;
import org.megapractical.billingplatform.repository.Branch_officeRepository;
import org.megapractical.billingplatform.service.Branch_officeService;

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
 * Test class for the Branch_officeResource REST controller.
 *
 * @see Branch_officeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Branch_officeResourceIntTest {

    private static final String DEFAULT_BUSSINES_NAME = "AAAAA";
    private static final String UPDATED_BUSSINES_NAME = "BBBBB";

    @Inject
    private Branch_officeRepository branch_officeRepository;

    @Inject
    private Branch_officeService branch_officeService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restBranch_officeMockMvc;

    private Branch_office branch_office;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Branch_officeResource branch_officeResource = new Branch_officeResource();
        ReflectionTestUtils.setField(branch_officeResource, "branch_officeService", branch_officeService);
        this.restBranch_officeMockMvc = MockMvcBuilders.standaloneSetup(branch_officeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        branch_office = new Branch_office();
        branch_office.setBussines_name(DEFAULT_BUSSINES_NAME);
    }

    @Test
    @Transactional
    public void createBranch_office() throws Exception {
        int databaseSizeBeforeCreate = branch_officeRepository.findAll().size();

        // Create the Branch_office

        restBranch_officeMockMvc.perform(post("/api/branch-offices")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(branch_office)))
                .andExpect(status().isCreated());

        // Validate the Branch_office in the database
        List<Branch_office> branch_offices = branch_officeRepository.findAll();
        assertThat(branch_offices).hasSize(databaseSizeBeforeCreate + 1);
        Branch_office testBranch_office = branch_offices.get(branch_offices.size() - 1);
        assertThat(testBranch_office.getBussines_name()).isEqualTo(DEFAULT_BUSSINES_NAME);
    }

    @Test
    @Transactional
    public void checkBussines_nameIsRequired() throws Exception {
        int databaseSizeBeforeTest = branch_officeRepository.findAll().size();
        // set the field null
        branch_office.setBussines_name(null);

        // Create the Branch_office, which fails.

        restBranch_officeMockMvc.perform(post("/api/branch-offices")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(branch_office)))
                .andExpect(status().isBadRequest());

        List<Branch_office> branch_offices = branch_officeRepository.findAll();
        assertThat(branch_offices).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBranch_offices() throws Exception {
        // Initialize the database
        branch_officeRepository.saveAndFlush(branch_office);

        // Get all the branch_offices
        restBranch_officeMockMvc.perform(get("/api/branch-offices?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(branch_office.getId().intValue())))
                .andExpect(jsonPath("$.[*].bussines_name").value(hasItem(DEFAULT_BUSSINES_NAME.toString())));
    }

    @Test
    @Transactional
    public void getBranch_office() throws Exception {
        // Initialize the database
        branch_officeRepository.saveAndFlush(branch_office);

        // Get the branch_office
        restBranch_officeMockMvc.perform(get("/api/branch-offices/{id}", branch_office.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(branch_office.getId().intValue()))
            .andExpect(jsonPath("$.bussines_name").value(DEFAULT_BUSSINES_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBranch_office() throws Exception {
        // Get the branch_office
        restBranch_officeMockMvc.perform(get("/api/branch-offices/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBranch_office() throws Exception {
        // Initialize the database
        branch_officeService.save(branch_office);

        int databaseSizeBeforeUpdate = branch_officeRepository.findAll().size();

        // Update the branch_office
        Branch_office updatedBranch_office = new Branch_office();
        updatedBranch_office.setId(branch_office.getId());
        updatedBranch_office.setBussines_name(UPDATED_BUSSINES_NAME);

        restBranch_officeMockMvc.perform(put("/api/branch-offices")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedBranch_office)))
                .andExpect(status().isOk());

        // Validate the Branch_office in the database
        List<Branch_office> branch_offices = branch_officeRepository.findAll();
        assertThat(branch_offices).hasSize(databaseSizeBeforeUpdate);
        Branch_office testBranch_office = branch_offices.get(branch_offices.size() - 1);
        assertThat(testBranch_office.getBussines_name()).isEqualTo(UPDATED_BUSSINES_NAME);
    }

    @Test
    @Transactional
    public void deleteBranch_office() throws Exception {
        // Initialize the database
        branch_officeService.save(branch_office);

        int databaseSizeBeforeDelete = branch_officeRepository.findAll().size();

        // Get the branch_office
        restBranch_officeMockMvc.perform(delete("/api/branch-offices/{id}", branch_office.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Branch_office> branch_offices = branch_officeRepository.findAll();
        assertThat(branch_offices).hasSize(databaseSizeBeforeDelete - 1);
    }
}
