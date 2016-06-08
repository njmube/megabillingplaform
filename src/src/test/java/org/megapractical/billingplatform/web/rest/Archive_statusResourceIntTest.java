package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Archive_status;
import org.megapractical.billingplatform.repository.Archive_statusRepository;
import org.megapractical.billingplatform.service.Archive_statusService;

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
import java.time.LocalDate;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the Archive_statusResource REST controller.
 *
 * @see Archive_statusResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Archive_statusResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("Z"));

    private static final String DEFAULT_NAME = "AAA";
    private static final String UPDATED_NAME = "BBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final ZonedDateTime DEFAULT_DATE_1 = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_DATE_1 = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_DATE_1_STR = dateTimeFormatter.format(DEFAULT_DATE_1);

    @Inject
    private Archive_statusRepository archive_statusRepository;

    @Inject
    private Archive_statusService archive_statusService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restArchive_statusMockMvc;

    private Archive_status archive_status;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Archive_statusResource archive_statusResource = new Archive_statusResource();
        ReflectionTestUtils.setField(archive_statusResource, "archive_statusService", archive_statusService);
        this.restArchive_statusMockMvc = MockMvcBuilders.standaloneSetup(archive_statusResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        archive_status = new Archive_status();
        archive_status.setName(DEFAULT_NAME);
        archive_status.setDescription(DEFAULT_DESCRIPTION);
        archive_status.setDate(DEFAULT_DATE);
        archive_status.setDate1(DEFAULT_DATE_1);
    }

    @Test
    @Transactional
    public void createArchive_status() throws Exception {
        int databaseSizeBeforeCreate = archive_statusRepository.findAll().size();

        // Create the Archive_status

        restArchive_statusMockMvc.perform(post("/api/archive-statuses")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(archive_status)))
                .andExpect(status().isCreated());

        // Validate the Archive_status in the database
        List<Archive_status> archive_statuses = archive_statusRepository.findAll();
        assertThat(archive_statuses).hasSize(databaseSizeBeforeCreate + 1);
        Archive_status testArchive_status = archive_statuses.get(archive_statuses.size() - 1);
        assertThat(testArchive_status.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testArchive_status.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testArchive_status.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testArchive_status.getDate1()).isEqualTo(DEFAULT_DATE_1);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = archive_statusRepository.findAll().size();
        // set the field null
        archive_status.setName(null);

        // Create the Archive_status, which fails.

        restArchive_statusMockMvc.perform(post("/api/archive-statuses")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(archive_status)))
                .andExpect(status().isBadRequest());

        List<Archive_status> archive_statuses = archive_statusRepository.findAll();
        assertThat(archive_statuses).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = archive_statusRepository.findAll().size();
        // set the field null
        archive_status.setDate(null);

        // Create the Archive_status, which fails.

        restArchive_statusMockMvc.perform(post("/api/archive-statuses")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(archive_status)))
                .andExpect(status().isBadRequest());

        List<Archive_status> archive_statuses = archive_statusRepository.findAll();
        assertThat(archive_statuses).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDate1IsRequired() throws Exception {
        int databaseSizeBeforeTest = archive_statusRepository.findAll().size();
        // set the field null
        archive_status.setDate1(null);

        // Create the Archive_status, which fails.

        restArchive_statusMockMvc.perform(post("/api/archive-statuses")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(archive_status)))
                .andExpect(status().isBadRequest());

        List<Archive_status> archive_statuses = archive_statusRepository.findAll();
        assertThat(archive_statuses).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllArchive_statuses() throws Exception {
        // Initialize the database
        archive_statusRepository.saveAndFlush(archive_status);

        // Get all the archive_statuses
        restArchive_statusMockMvc.perform(get("/api/archive-statuses?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(archive_status.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
                .andExpect(jsonPath("$.[*].date1").value(hasItem(DEFAULT_DATE_1_STR)));
    }

    @Test
    @Transactional
    public void getArchive_status() throws Exception {
        // Initialize the database
        archive_statusRepository.saveAndFlush(archive_status);

        // Get the archive_status
        restArchive_statusMockMvc.perform(get("/api/archive-statuses/{id}", archive_status.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(archive_status.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.date1").value(DEFAULT_DATE_1_STR));
    }

    @Test
    @Transactional
    public void getNonExistingArchive_status() throws Exception {
        // Get the archive_status
        restArchive_statusMockMvc.perform(get("/api/archive-statuses/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateArchive_status() throws Exception {
        // Initialize the database
        archive_statusService.save(archive_status);

        int databaseSizeBeforeUpdate = archive_statusRepository.findAll().size();

        // Update the archive_status
        Archive_status updatedArchive_status = new Archive_status();
        updatedArchive_status.setId(archive_status.getId());
        updatedArchive_status.setName(UPDATED_NAME);
        updatedArchive_status.setDescription(UPDATED_DESCRIPTION);
        updatedArchive_status.setDate(UPDATED_DATE);
        updatedArchive_status.setDate1(UPDATED_DATE_1);

        restArchive_statusMockMvc.perform(put("/api/archive-statuses")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedArchive_status)))
                .andExpect(status().isOk());

        // Validate the Archive_status in the database
        List<Archive_status> archive_statuses = archive_statusRepository.findAll();
        assertThat(archive_statuses).hasSize(databaseSizeBeforeUpdate);
        Archive_status testArchive_status = archive_statuses.get(archive_statuses.size() - 1);
        assertThat(testArchive_status.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testArchive_status.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testArchive_status.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testArchive_status.getDate1()).isEqualTo(UPDATED_DATE_1);
    }

    @Test
    @Transactional
    public void deleteArchive_status() throws Exception {
        // Initialize the database
        archive_statusService.save(archive_status);

        int databaseSizeBeforeDelete = archive_statusRepository.findAll().size();

        // Get the archive_status
        restArchive_statusMockMvc.perform(delete("/api/archive-statuses/{id}", archive_status.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Archive_status> archive_statuses = archive_statusRepository.findAll();
        assertThat(archive_statuses).hasSize(databaseSizeBeforeDelete - 1);
    }
}
