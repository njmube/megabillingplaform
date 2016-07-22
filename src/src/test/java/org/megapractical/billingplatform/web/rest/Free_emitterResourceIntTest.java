package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Free_emitter;
import org.megapractical.billingplatform.repository.Free_emitterRepository;
import org.megapractical.billingplatform.service.Free_emitterService;

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
import org.springframework.util.Base64Utils;

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
 * Test class for the Free_emitterResource REST controller.
 *
 * @see Free_emitterResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Free_emitterResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("Z"));

    private static final String DEFAULT_REFERENCE = "AAAAA";
    private static final String UPDATED_REFERENCE = "BBBBB";
    private static final String DEFAULT_NUM_INT = "AAAAA";
    private static final String UPDATED_NUM_INT = "BBBBB";
    private static final String DEFAULT_NUM_EXT = "AAAAA";
    private static final String UPDATED_NUM_EXT = "BBBBB";
    private static final String DEFAULT_STREET = "AAAAA";
    private static final String UPDATED_STREET = "BBBBB";

    private static final ZonedDateTime DEFAULT_CREATE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_CREATE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_CREATE_DATE_STR = dateTimeFormatter.format(DEFAULT_CREATE_DATE);

    private static final Boolean DEFAULT_ACTIVATED = false;
    private static final Boolean UPDATED_ACTIVATED = true;
    private static final String DEFAULT_RFC = "AAAAAAAAAAAA";
    private static final String UPDATED_RFC = "BBBBBBBBBBBB";
    private static final String DEFAULT_EMAIL = "AAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBB";
    private static final String DEFAULT_BUSSINES_NAME = "AAA";
    private static final String UPDATED_BUSSINES_NAME = "BBB";
    private static final String DEFAULT_LOCATION = "AAAAA";
    private static final String UPDATED_LOCATION = "BBBBB";
    private static final String DEFAULT_INTERSECTION = "AAAAA";
    private static final String UPDATED_INTERSECTION = "BBBBB";
    private static final String DEFAULT_FAX = "AAAAA";
    private static final String UPDATED_FAX = "BBBBB";
    private static final String DEFAULT_PHONE_1 = "A";
    private static final String UPDATED_PHONE_1 = "B";
    private static final String DEFAULT_PHONE_2 = "AAAAA";
    private static final String UPDATED_PHONE_2 = "BBBBB";
    private static final String DEFAULT_PATH_CERTIFICATE = "AAAAA";
    private static final String UPDATED_PATH_CERTIFICATE = "BBBBB";
    private static final String DEFAULT_PATH_KEY = "AAAAA";
    private static final String UPDATED_PATH_KEY = "BBBBB";
    private static final String DEFAULT_PATH_LOGO = "AAAAA";
    private static final String UPDATED_PATH_LOGO = "BBBBB";

    private static final byte[] DEFAULT_FILECERTIFICATE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FILECERTIFICATE = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_FILECERTIFICATE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FILECERTIFICATE_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_FILEKEY = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FILEKEY = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_FILEKEY_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FILEKEY_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_FILELOGO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FILELOGO = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_FILELOGO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FILELOGO_CONTENT_TYPE = "image/png";

    private static final Integer DEFAULT_ACCURACY = 1;
    private static final Integer UPDATED_ACCURACY = 2;

    private static final Boolean DEFAULT_VALID_CERTIFICATE = false;
    private static final Boolean UPDATED_VALID_CERTIFICATE = true;
    private static final String DEFAULT_INFO_CERTIFICATE = "AAAAA";
    private static final String UPDATED_INFO_CERTIFICATE = "BBBBB";

    private static final LocalDate DEFAULT_DATE_CERTIFICATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CERTIFICATE = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private Free_emitterRepository free_emitterRepository;

    @Inject
    private Free_emitterService free_emitterService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFree_emitterMockMvc;

    private Free_emitter free_emitter;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Free_emitterResource free_emitterResource = new Free_emitterResource();
        ReflectionTestUtils.setField(free_emitterResource, "free_emitterService", free_emitterService);
        this.restFree_emitterMockMvc = MockMvcBuilders.standaloneSetup(free_emitterResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        free_emitter = new Free_emitter();
        free_emitter.setReference(DEFAULT_REFERENCE);
        free_emitter.setNum_int(DEFAULT_NUM_INT);
        free_emitter.setNum_ext(DEFAULT_NUM_EXT);
        free_emitter.setStreet(DEFAULT_STREET);
        free_emitter.setCreate_date(DEFAULT_CREATE_DATE);
        free_emitter.setActivated(DEFAULT_ACTIVATED);
        free_emitter.setRfc(DEFAULT_RFC);
        free_emitter.setEmail(DEFAULT_EMAIL);
        free_emitter.setBussines_name(DEFAULT_BUSSINES_NAME);
        free_emitter.setLocation(DEFAULT_LOCATION);
        free_emitter.setIntersection(DEFAULT_INTERSECTION);
        free_emitter.setFax(DEFAULT_FAX);
        free_emitter.setPhone1(DEFAULT_PHONE_1);
        free_emitter.setPhone2(DEFAULT_PHONE_2);
        free_emitter.setPath_certificate(DEFAULT_PATH_CERTIFICATE);
        free_emitter.setPath_key(DEFAULT_PATH_KEY);
        free_emitter.setPath_logo(DEFAULT_PATH_LOGO);
        free_emitter.setFilecertificate(DEFAULT_FILECERTIFICATE);
        free_emitter.setFilecertificateContentType(DEFAULT_FILECERTIFICATE_CONTENT_TYPE);
        free_emitter.setFilekey(DEFAULT_FILEKEY);
        free_emitter.setFilekeyContentType(DEFAULT_FILEKEY_CONTENT_TYPE);
        free_emitter.setFilelogo(DEFAULT_FILELOGO);
        free_emitter.setFilelogoContentType(DEFAULT_FILELOGO_CONTENT_TYPE);
        free_emitter.setAccuracy(DEFAULT_ACCURACY);
        free_emitter.setValid_certificate(DEFAULT_VALID_CERTIFICATE);
        free_emitter.setInfo_certificate(DEFAULT_INFO_CERTIFICATE);
        free_emitter.setDate_certificate(DEFAULT_DATE_CERTIFICATE);
    }

    @Test
    @Transactional
    public void createFree_emitter() throws Exception {
        /*
        int databaseSizeBeforeCreate = free_emitterRepository.findAll().size();

        // Create the Free_emitter

        restFree_emitterMockMvc.perform(post("/api/free-emitters")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(free_emitter)))
                .andExpect(status().isCreated());

        // Validate the Free_emitter in the database
        List<Free_emitter> free_emitters = free_emitterRepository.findAll();
        assertThat(free_emitters).hasSize(databaseSizeBeforeCreate + 1);
        Free_emitter testFree_emitter = free_emitters.get(free_emitters.size() - 1);
        assertThat(testFree_emitter.getReference()).isEqualTo(DEFAULT_REFERENCE);
        assertThat(testFree_emitter.getNum_int()).isEqualTo(DEFAULT_NUM_INT);
        assertThat(testFree_emitter.getNum_ext()).isEqualTo(DEFAULT_NUM_EXT);
        assertThat(testFree_emitter.getStreet()).isEqualTo(DEFAULT_STREET);
        assertThat(testFree_emitter.getCreate_date()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testFree_emitter.isActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testFree_emitter.getRfc()).isEqualTo(DEFAULT_RFC);
        assertThat(testFree_emitter.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testFree_emitter.getBussines_name()).isEqualTo(DEFAULT_BUSSINES_NAME);
        assertThat(testFree_emitter.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testFree_emitter.getIntersection()).isEqualTo(DEFAULT_INTERSECTION);
        assertThat(testFree_emitter.getFax()).isEqualTo(DEFAULT_FAX);
        assertThat(testFree_emitter.getPhone1()).isEqualTo(DEFAULT_PHONE_1);
        assertThat(testFree_emitter.getPhone2()).isEqualTo(DEFAULT_PHONE_2);
        assertThat(testFree_emitter.getPath_certificate()).isEqualTo(DEFAULT_PATH_CERTIFICATE);
        assertThat(testFree_emitter.getPath_key()).isEqualTo(DEFAULT_PATH_KEY);
        assertThat(testFree_emitter.getPath_logo()).isEqualTo(DEFAULT_PATH_LOGO);
        assertThat(testFree_emitter.getFilecertificate()).isEqualTo(DEFAULT_FILECERTIFICATE);
        assertThat(testFree_emitter.getFilecertificateContentType()).isEqualTo(DEFAULT_FILECERTIFICATE_CONTENT_TYPE);
        assertThat(testFree_emitter.getFilekey()).isEqualTo(DEFAULT_FILEKEY);
        assertThat(testFree_emitter.getFilekeyContentType()).isEqualTo(DEFAULT_FILEKEY_CONTENT_TYPE);
        assertThat(testFree_emitter.getFilelogo()).isEqualTo(DEFAULT_FILELOGO);
        assertThat(testFree_emitter.getFilelogoContentType()).isEqualTo(DEFAULT_FILELOGO_CONTENT_TYPE);
        assertThat(testFree_emitter.getAccuracy()).isEqualTo(DEFAULT_ACCURACY);
        assertThat(testFree_emitter.isValid_certificate()).isEqualTo(DEFAULT_VALID_CERTIFICATE);
        assertThat(testFree_emitter.getInfo_certificate()).isEqualTo(DEFAULT_INFO_CERTIFICATE);
        assertThat(testFree_emitter.getDate_certificate()).isEqualTo(DEFAULT_DATE_CERTIFICATE);*/
    }

    @Test
    @Transactional
    public void checkStreetIsRequired() throws Exception {
        /*
        int databaseSizeBeforeTest = free_emitterRepository.findAll().size();
        // set the field null
        free_emitter.setStreet(null);

        // Create the Free_emitter, which fails.

        restFree_emitterMockMvc.perform(post("/api/free-emitters")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(free_emitter)))
                .andExpect(status().isBadRequest());

        List<Free_emitter> free_emitters = free_emitterRepository.findAll();
        assertThat(free_emitters).hasSize(databaseSizeBeforeTest);*/
    }

    @Test
    @Transactional
    public void checkCreate_dateIsRequired() throws Exception {
        /*
        int databaseSizeBeforeTest = free_emitterRepository.findAll().size();
        // set the field null
        free_emitter.setCreate_date(null);

        // Create the Free_emitter, which fails.

        restFree_emitterMockMvc.perform(post("/api/free-emitters")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(free_emitter)))
                .andExpect(status().isBadRequest());

        List<Free_emitter> free_emitters = free_emitterRepository.findAll();
        assertThat(free_emitters).hasSize(databaseSizeBeforeTest);*/
    }

    @Test
    @Transactional
    public void checkActivatedIsRequired() throws Exception {
        /*
        int databaseSizeBeforeTest = free_emitterRepository.findAll().size();
        // set the field null
        free_emitter.setActivated(null);

        // Create the Free_emitter, which fails.

        restFree_emitterMockMvc.perform(post("/api/free-emitters")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(free_emitter)))
                .andExpect(status().isBadRequest());

        List<Free_emitter> free_emitters = free_emitterRepository.findAll();
        assertThat(free_emitters).hasSize(databaseSizeBeforeTest);*/
    }

    @Test
    @Transactional
    public void checkRfcIsRequired() throws Exception {
        /*
        int databaseSizeBeforeTest = free_emitterRepository.findAll().size();
        // set the field null
        free_emitter.setRfc(null);

        // Create the Free_emitter, which fails.

        restFree_emitterMockMvc.perform(post("/api/free-emitters")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(free_emitter)))
                .andExpect(status().isBadRequest());

        List<Free_emitter> free_emitters = free_emitterRepository.findAll();
        assertThat(free_emitters).hasSize(databaseSizeBeforeTest);*/
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        /*
        int databaseSizeBeforeTest = free_emitterRepository.findAll().size();
        // set the field null
        free_emitter.setEmail(null);

        // Create the Free_emitter, which fails.

        restFree_emitterMockMvc.perform(post("/api/free-emitters")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(free_emitter)))
                .andExpect(status().isBadRequest());

        List<Free_emitter> free_emitters = free_emitterRepository.findAll();
        assertThat(free_emitters).hasSize(databaseSizeBeforeTest);*/
    }

    @Test
    @Transactional
    public void checkBussines_nameIsRequired() throws Exception {
        /*
        int databaseSizeBeforeTest = free_emitterRepository.findAll().size();
        // set the field null
        free_emitter.setBussines_name(null);

        // Create the Free_emitter, which fails.

        restFree_emitterMockMvc.perform(post("/api/free-emitters")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(free_emitter)))
                .andExpect(status().isBadRequest());

        List<Free_emitter> free_emitters = free_emitterRepository.findAll();
        assertThat(free_emitters).hasSize(databaseSizeBeforeTest);*/
    }

    @Test
    @Transactional
    public void checkPhone1IsRequired() throws Exception {
        /*
        int databaseSizeBeforeTest = free_emitterRepository.findAll().size();
        // set the field null
        free_emitter.setPhone1(null);

        // Create the Free_emitter, which fails.

        restFree_emitterMockMvc.perform(post("/api/free-emitters")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(free_emitter)))
                .andExpect(status().isBadRequest());

        List<Free_emitter> free_emitters = free_emitterRepository.findAll();
        assertThat(free_emitters).hasSize(databaseSizeBeforeTest);*/
    }

    @Test
    @Transactional
    public void checkAccuracyIsRequired() throws Exception {
        /*
        int databaseSizeBeforeTest = free_emitterRepository.findAll().size();
        // set the field null
        free_emitter.setAccuracy(null);

        // Create the Free_emitter, which fails.

        restFree_emitterMockMvc.perform(post("/api/free-emitters")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(free_emitter)))
                .andExpect(status().isBadRequest());

        List<Free_emitter> free_emitters = free_emitterRepository.findAll();
        assertThat(free_emitters).hasSize(databaseSizeBeforeTest);*/
    }

    @Test
    @Transactional
    public void getAllFree_emitters() throws Exception {
        /*
        // Initialize the database
        free_emitterRepository.saveAndFlush(free_emitter);

        // Get all the free_emitters
        restFree_emitterMockMvc.perform(get("/api/free-emitters?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(free_emitter.getId().intValue())))
                .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE.toString())))
                .andExpect(jsonPath("$.[*].num_int").value(hasItem(DEFAULT_NUM_INT.toString())))
                .andExpect(jsonPath("$.[*].num_ext").value(hasItem(DEFAULT_NUM_EXT.toString())))
                .andExpect(jsonPath("$.[*].street").value(hasItem(DEFAULT_STREET.toString())))
                .andExpect(jsonPath("$.[*].create_date").value(hasItem(DEFAULT_CREATE_DATE_STR)))
                .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
                .andExpect(jsonPath("$.[*].rfc").value(hasItem(DEFAULT_RFC.toString())))
                .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
                .andExpect(jsonPath("$.[*].bussines_name").value(hasItem(DEFAULT_BUSSINES_NAME.toString())))
                .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION.toString())))
                .andExpect(jsonPath("$.[*].intersection").value(hasItem(DEFAULT_INTERSECTION.toString())))
                .andExpect(jsonPath("$.[*].fax").value(hasItem(DEFAULT_FAX.toString())))
                .andExpect(jsonPath("$.[*].phone1").value(hasItem(DEFAULT_PHONE_1.toString())))
                .andExpect(jsonPath("$.[*].phone2").value(hasItem(DEFAULT_PHONE_2.toString())))
                .andExpect(jsonPath("$.[*].path_certificate").value(hasItem(DEFAULT_PATH_CERTIFICATE.toString())))
                .andExpect(jsonPath("$.[*].path_key").value(hasItem(DEFAULT_PATH_KEY.toString())))
                .andExpect(jsonPath("$.[*].path_logo").value(hasItem(DEFAULT_PATH_LOGO.toString())))
                .andExpect(jsonPath("$.[*].filecertificateContentType").value(hasItem(DEFAULT_FILECERTIFICATE_CONTENT_TYPE)))
                .andExpect(jsonPath("$.[*].filecertificate").value(hasItem(Base64Utils.encodeToString(DEFAULT_FILECERTIFICATE))))
                .andExpect(jsonPath("$.[*].filekeyContentType").value(hasItem(DEFAULT_FILEKEY_CONTENT_TYPE)))
                .andExpect(jsonPath("$.[*].filekey").value(hasItem(Base64Utils.encodeToString(DEFAULT_FILEKEY))))
                .andExpect(jsonPath("$.[*].filelogoContentType").value(hasItem(DEFAULT_FILELOGO_CONTENT_TYPE)))
                .andExpect(jsonPath("$.[*].filelogo").value(hasItem(Base64Utils.encodeToString(DEFAULT_FILELOGO))))
                .andExpect(jsonPath("$.[*].accuracy").value(hasItem(DEFAULT_ACCURACY)))
                .andExpect(jsonPath("$.[*].valid_certificate").value(hasItem(DEFAULT_VALID_CERTIFICATE.booleanValue())))
                .andExpect(jsonPath("$.[*].info_certificate").value(hasItem(DEFAULT_INFO_CERTIFICATE.toString())))
                .andExpect(jsonPath("$.[*].date_certificate").value(hasItem(DEFAULT_DATE_CERTIFICATE.toString())));*/
    }

    @Test
    @Transactional
    public void getFree_emitter() throws Exception {
        /*
        // Initialize the database
        free_emitterRepository.saveAndFlush(free_emitter);

        // Get the free_emitter
        restFree_emitterMockMvc.perform(get("/api/free-emitters/{id}", free_emitter.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(free_emitter.getId().intValue()))
            .andExpect(jsonPath("$.reference").value(DEFAULT_REFERENCE.toString()))
            .andExpect(jsonPath("$.num_int").value(DEFAULT_NUM_INT.toString()))
            .andExpect(jsonPath("$.num_ext").value(DEFAULT_NUM_EXT.toString()))
            .andExpect(jsonPath("$.street").value(DEFAULT_STREET.toString()))
            .andExpect(jsonPath("$.create_date").value(DEFAULT_CREATE_DATE_STR))
            .andExpect(jsonPath("$.activated").value(DEFAULT_ACTIVATED.booleanValue()))
            .andExpect(jsonPath("$.rfc").value(DEFAULT_RFC.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.bussines_name").value(DEFAULT_BUSSINES_NAME.toString()))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION.toString()))
            .andExpect(jsonPath("$.intersection").value(DEFAULT_INTERSECTION.toString()))
            .andExpect(jsonPath("$.fax").value(DEFAULT_FAX.toString()))
            .andExpect(jsonPath("$.phone1").value(DEFAULT_PHONE_1.toString()))
            .andExpect(jsonPath("$.phone2").value(DEFAULT_PHONE_2.toString()))
            .andExpect(jsonPath("$.path_certificate").value(DEFAULT_PATH_CERTIFICATE.toString()))
            .andExpect(jsonPath("$.path_key").value(DEFAULT_PATH_KEY.toString()))
            .andExpect(jsonPath("$.path_logo").value(DEFAULT_PATH_LOGO.toString()))
            .andExpect(jsonPath("$.filecertificateContentType").value(DEFAULT_FILECERTIFICATE_CONTENT_TYPE))
            .andExpect(jsonPath("$.filecertificate").value(Base64Utils.encodeToString(DEFAULT_FILECERTIFICATE)))
            .andExpect(jsonPath("$.filekeyContentType").value(DEFAULT_FILEKEY_CONTENT_TYPE))
            .andExpect(jsonPath("$.filekey").value(Base64Utils.encodeToString(DEFAULT_FILEKEY)))
            .andExpect(jsonPath("$.filelogoContentType").value(DEFAULT_FILELOGO_CONTENT_TYPE))
            .andExpect(jsonPath("$.filelogo").value(Base64Utils.encodeToString(DEFAULT_FILELOGO)))
            .andExpect(jsonPath("$.accuracy").value(DEFAULT_ACCURACY))
            .andExpect(jsonPath("$.valid_certificate").value(DEFAULT_VALID_CERTIFICATE.booleanValue()))
            .andExpect(jsonPath("$.info_certificate").value(DEFAULT_INFO_CERTIFICATE.toString()))
            .andExpect(jsonPath("$.date_certificate").value(DEFAULT_DATE_CERTIFICATE.toString()));*/
    }

    @Test
    @Transactional
    public void getNonExistingFree_emitter() throws Exception {
        /*
        // Get the free_emitter
        restFree_emitterMockMvc.perform(get("/api/free-emitters/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());*/
    }

    @Test
    @Transactional
    public void updateFree_emitter() throws Exception {
        /*
        // Initialize the database
        free_emitterService.save(free_emitter);

        int databaseSizeBeforeUpdate = free_emitterRepository.findAll().size();

        // Update the free_emitter
        Free_emitter updatedFree_emitter = new Free_emitter();
        updatedFree_emitter.setId(free_emitter.getId());
        updatedFree_emitter.setReference(UPDATED_REFERENCE);
        updatedFree_emitter.setNum_int(UPDATED_NUM_INT);
        updatedFree_emitter.setNum_ext(UPDATED_NUM_EXT);
        updatedFree_emitter.setStreet(UPDATED_STREET);
        updatedFree_emitter.setCreate_date(UPDATED_CREATE_DATE);
        updatedFree_emitter.setActivated(UPDATED_ACTIVATED);
        updatedFree_emitter.setRfc(UPDATED_RFC);
        updatedFree_emitter.setEmail(UPDATED_EMAIL);
        updatedFree_emitter.setBussines_name(UPDATED_BUSSINES_NAME);
        updatedFree_emitter.setLocation(UPDATED_LOCATION);
        updatedFree_emitter.setIntersection(UPDATED_INTERSECTION);
        updatedFree_emitter.setFax(UPDATED_FAX);
        updatedFree_emitter.setPhone1(UPDATED_PHONE_1);
        updatedFree_emitter.setPhone2(UPDATED_PHONE_2);
        updatedFree_emitter.setPath_certificate(UPDATED_PATH_CERTIFICATE);
        updatedFree_emitter.setPath_key(UPDATED_PATH_KEY);
        updatedFree_emitter.setPath_logo(UPDATED_PATH_LOGO);
        updatedFree_emitter.setFilecertificate(UPDATED_FILECERTIFICATE);
        updatedFree_emitter.setFilecertificateContentType(UPDATED_FILECERTIFICATE_CONTENT_TYPE);
        updatedFree_emitter.setFilekey(UPDATED_FILEKEY);
        updatedFree_emitter.setFilekeyContentType(UPDATED_FILEKEY_CONTENT_TYPE);
        updatedFree_emitter.setFilelogo(UPDATED_FILELOGO);
        updatedFree_emitter.setFilelogoContentType(UPDATED_FILELOGO_CONTENT_TYPE);
        updatedFree_emitter.setAccuracy(UPDATED_ACCURACY);
        updatedFree_emitter.setValid_certificate(UPDATED_VALID_CERTIFICATE);
        updatedFree_emitter.setInfo_certificate(UPDATED_INFO_CERTIFICATE);
        updatedFree_emitter.setDate_certificate(UPDATED_DATE_CERTIFICATE);

        restFree_emitterMockMvc.perform(put("/api/free-emitters")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedFree_emitter)))
                .andExpect(status().isOk());

        // Validate the Free_emitter in the database
        List<Free_emitter> free_emitters = free_emitterRepository.findAll();
        assertThat(free_emitters).hasSize(databaseSizeBeforeUpdate);
        Free_emitter testFree_emitter = free_emitters.get(free_emitters.size() - 1);
        assertThat(testFree_emitter.getReference()).isEqualTo(UPDATED_REFERENCE);
        assertThat(testFree_emitter.getNum_int()).isEqualTo(UPDATED_NUM_INT);
        assertThat(testFree_emitter.getNum_ext()).isEqualTo(UPDATED_NUM_EXT);
        assertThat(testFree_emitter.getStreet()).isEqualTo(UPDATED_STREET);
        assertThat(testFree_emitter.getCreate_date()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testFree_emitter.isActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testFree_emitter.getRfc()).isEqualTo(UPDATED_RFC);
        assertThat(testFree_emitter.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testFree_emitter.getBussines_name()).isEqualTo(UPDATED_BUSSINES_NAME);
        assertThat(testFree_emitter.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testFree_emitter.getIntersection()).isEqualTo(UPDATED_INTERSECTION);
        assertThat(testFree_emitter.getFax()).isEqualTo(UPDATED_FAX);
        assertThat(testFree_emitter.getPhone1()).isEqualTo(UPDATED_PHONE_1);
        assertThat(testFree_emitter.getPhone2()).isEqualTo(UPDATED_PHONE_2);
        assertThat(testFree_emitter.getPath_certificate()).isEqualTo(UPDATED_PATH_CERTIFICATE);
        assertThat(testFree_emitter.getPath_key()).isEqualTo(UPDATED_PATH_KEY);
        assertThat(testFree_emitter.getPath_logo()).isEqualTo(UPDATED_PATH_LOGO);
        assertThat(testFree_emitter.getFilecertificate()).isEqualTo(UPDATED_FILECERTIFICATE);
        assertThat(testFree_emitter.getFilecertificateContentType()).isEqualTo(UPDATED_FILECERTIFICATE_CONTENT_TYPE);
        assertThat(testFree_emitter.getFilekey()).isEqualTo(UPDATED_FILEKEY);
        assertThat(testFree_emitter.getFilekeyContentType()).isEqualTo(UPDATED_FILEKEY_CONTENT_TYPE);
        assertThat(testFree_emitter.getFilelogo()).isEqualTo(UPDATED_FILELOGO);
        assertThat(testFree_emitter.getFilelogoContentType()).isEqualTo(UPDATED_FILELOGO_CONTENT_TYPE);
        assertThat(testFree_emitter.getAccuracy()).isEqualTo(UPDATED_ACCURACY);
        assertThat(testFree_emitter.isValid_certificate()).isEqualTo(UPDATED_VALID_CERTIFICATE);
        assertThat(testFree_emitter.getInfo_certificate()).isEqualTo(UPDATED_INFO_CERTIFICATE);
        assertThat(testFree_emitter.getDate_certificate()).isEqualTo(UPDATED_DATE_CERTIFICATE);*/
    }

    @Test
    @Transactional
    public void deleteFree_emitter() throws Exception {
        /*
        // Initialize the database
        free_emitterService.save(free_emitter);

        int databaseSizeBeforeDelete = free_emitterRepository.findAll().size();

        // Get the free_emitter
        restFree_emitterMockMvc.perform(delete("/api/free-emitters/{id}", free_emitter.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Free_emitter> free_emitters = free_emitterRepository.findAll();
        assertThat(free_emitters).hasSize(databaseSizeBeforeDelete - 1);*/
    }
}
