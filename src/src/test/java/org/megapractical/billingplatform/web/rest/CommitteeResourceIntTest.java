package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Committee;
import org.megapractical.billingplatform.repository.CommitteeRepository;
import org.megapractical.billingplatform.service.CommitteeService;

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
 * Test class for the CommitteeResource REST controller.
 *
 * @see CommitteeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class CommitteeResourceIntTest {

    private static final String DEFAULT_NAME = "AAA";
    private static final String UPDATED_NAME = "BBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private CommitteeRepository committeeRepository;

    @Inject
    private CommitteeService committeeService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCommitteeMockMvc;

    private Committee committee;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CommitteeResource committeeResource = new CommitteeResource();
        ReflectionTestUtils.setField(committeeResource, "committeeService", committeeService);
        this.restCommitteeMockMvc = MockMvcBuilders.standaloneSetup(committeeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        committee = new Committee();
        committee.setName(DEFAULT_NAME);
        committee.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createCommittee() throws Exception {
        int databaseSizeBeforeCreate = committeeRepository.findAll().size();

        // Create the Committee

        restCommitteeMockMvc.perform(post("/api/committees")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(committee)))
                .andExpect(status().isCreated());

        // Validate the Committee in the database
        List<Committee> committees = committeeRepository.findAll();
        assertThat(committees).hasSize(databaseSizeBeforeCreate + 1);
        Committee testCommittee = committees.get(committees.size() - 1);
        assertThat(testCommittee.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCommittee.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = committeeRepository.findAll().size();
        // set the field null
        committee.setName(null);

        // Create the Committee, which fails.

        restCommitteeMockMvc.perform(post("/api/committees")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(committee)))
                .andExpect(status().isBadRequest());

        List<Committee> committees = committeeRepository.findAll();
        assertThat(committees).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCommittees() throws Exception {
        // Initialize the database
        committeeRepository.saveAndFlush(committee);

        // Get all the committees
        restCommitteeMockMvc.perform(get("/api/committees?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(committee.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getCommittee() throws Exception {
        // Initialize the database
        committeeRepository.saveAndFlush(committee);

        // Get the committee
        restCommitteeMockMvc.perform(get("/api/committees/{id}", committee.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(committee.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCommittee() throws Exception {
        // Get the committee
        restCommitteeMockMvc.perform(get("/api/committees/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCommittee() throws Exception {
        // Initialize the database
        committeeService.save(committee);

        int databaseSizeBeforeUpdate = committeeRepository.findAll().size();

        // Update the committee
        Committee updatedCommittee = new Committee();
        updatedCommittee.setId(committee.getId());
        updatedCommittee.setName(UPDATED_NAME);
        updatedCommittee.setDescription(UPDATED_DESCRIPTION);

        restCommitteeMockMvc.perform(put("/api/committees")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCommittee)))
                .andExpect(status().isOk());

        // Validate the Committee in the database
        List<Committee> committees = committeeRepository.findAll();
        assertThat(committees).hasSize(databaseSizeBeforeUpdate);
        Committee testCommittee = committees.get(committees.size() - 1);
        assertThat(testCommittee.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCommittee.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteCommittee() throws Exception {
        // Initialize the database
        committeeService.save(committee);

        int databaseSizeBeforeDelete = committeeRepository.findAll().size();

        // Get the committee
        restCommitteeMockMvc.perform(delete("/api/committees/{id}", committee.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Committee> committees = committeeRepository.findAll();
        assertThat(committees).hasSize(databaseSizeBeforeDelete - 1);
    }
}
