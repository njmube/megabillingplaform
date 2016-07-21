package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.C_features_work_piece;
import org.megapractical.billingplatform.repository.C_features_work_pieceRepository;
import org.megapractical.billingplatform.service.C_features_work_pieceService;

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
 * Test class for the C_features_work_pieceResource REST controller.
 *
 * @see C_features_work_pieceResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class C_features_work_pieceResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAA";
    private static final String UPDATED_CODE = "BBBBB";
    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    @Inject
    private C_features_work_pieceRepository c_features_work_pieceRepository;

    @Inject
    private C_features_work_pieceService c_features_work_pieceService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restC_features_work_pieceMockMvc;

    private C_features_work_piece c_features_work_piece;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        C_features_work_pieceResource c_features_work_pieceResource = new C_features_work_pieceResource();
        ReflectionTestUtils.setField(c_features_work_pieceResource, "c_features_work_pieceService", c_features_work_pieceService);
        this.restC_features_work_pieceMockMvc = MockMvcBuilders.standaloneSetup(c_features_work_pieceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        c_features_work_piece = new C_features_work_piece();
        c_features_work_piece.setCode(DEFAULT_CODE);
        c_features_work_piece.setName(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createC_features_work_piece() throws Exception {
        int databaseSizeBeforeCreate = c_features_work_pieceRepository.findAll().size();

        // Create the C_features_work_piece

        restC_features_work_pieceMockMvc.perform(post("/api/c-features-work-pieces")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(c_features_work_piece)))
                .andExpect(status().isCreated());

        // Validate the C_features_work_piece in the database
        List<C_features_work_piece> c_features_work_pieces = c_features_work_pieceRepository.findAll();
        assertThat(c_features_work_pieces).hasSize(databaseSizeBeforeCreate + 1);
        C_features_work_piece testC_features_work_piece = c_features_work_pieces.get(c_features_work_pieces.size() - 1);
        assertThat(testC_features_work_piece.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testC_features_work_piece.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = c_features_work_pieceRepository.findAll().size();
        // set the field null
        c_features_work_piece.setCode(null);

        // Create the C_features_work_piece, which fails.

        restC_features_work_pieceMockMvc.perform(post("/api/c-features-work-pieces")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(c_features_work_piece)))
                .andExpect(status().isBadRequest());

        List<C_features_work_piece> c_features_work_pieces = c_features_work_pieceRepository.findAll();
        assertThat(c_features_work_pieces).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = c_features_work_pieceRepository.findAll().size();
        // set the field null
        c_features_work_piece.setName(null);

        // Create the C_features_work_piece, which fails.

        restC_features_work_pieceMockMvc.perform(post("/api/c-features-work-pieces")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(c_features_work_piece)))
                .andExpect(status().isBadRequest());

        List<C_features_work_piece> c_features_work_pieces = c_features_work_pieceRepository.findAll();
        assertThat(c_features_work_pieces).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllC_features_work_pieces() throws Exception {
        // Initialize the database
        c_features_work_pieceRepository.saveAndFlush(c_features_work_piece);

        // Get all the c_features_work_pieces
        restC_features_work_pieceMockMvc.perform(get("/api/c-features-work-pieces?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(c_features_work_piece.getId().intValue())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getC_features_work_piece() throws Exception {
        // Initialize the database
        c_features_work_pieceRepository.saveAndFlush(c_features_work_piece);

        // Get the c_features_work_piece
        restC_features_work_pieceMockMvc.perform(get("/api/c-features-work-pieces/{id}", c_features_work_piece.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(c_features_work_piece.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingC_features_work_piece() throws Exception {
        // Get the c_features_work_piece
        restC_features_work_pieceMockMvc.perform(get("/api/c-features-work-pieces/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateC_features_work_piece() throws Exception {
        // Initialize the database
        c_features_work_pieceService.save(c_features_work_piece);

        int databaseSizeBeforeUpdate = c_features_work_pieceRepository.findAll().size();

        // Update the c_features_work_piece
        C_features_work_piece updatedC_features_work_piece = new C_features_work_piece();
        updatedC_features_work_piece.setId(c_features_work_piece.getId());
        updatedC_features_work_piece.setCode(UPDATED_CODE);
        updatedC_features_work_piece.setName(UPDATED_NAME);

        restC_features_work_pieceMockMvc.perform(put("/api/c-features-work-pieces")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedC_features_work_piece)))
                .andExpect(status().isOk());

        // Validate the C_features_work_piece in the database
        List<C_features_work_piece> c_features_work_pieces = c_features_work_pieceRepository.findAll();
        assertThat(c_features_work_pieces).hasSize(databaseSizeBeforeUpdate);
        C_features_work_piece testC_features_work_piece = c_features_work_pieces.get(c_features_work_pieces.size() - 1);
        assertThat(testC_features_work_piece.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testC_features_work_piece.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void deleteC_features_work_piece() throws Exception {
        // Initialize the database
        c_features_work_pieceService.save(c_features_work_piece);

        int databaseSizeBeforeDelete = c_features_work_pieceRepository.findAll().size();

        // Get the c_features_work_piece
        restC_features_work_pieceMockMvc.perform(delete("/api/c-features-work-pieces/{id}", c_features_work_piece.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<C_features_work_piece> c_features_work_pieces = c_features_work_pieceRepository.findAll();
        assertThat(c_features_work_pieces).hasSize(databaseSizeBeforeDelete - 1);
    }
}
