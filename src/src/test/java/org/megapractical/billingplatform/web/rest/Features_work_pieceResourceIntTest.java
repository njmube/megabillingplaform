package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Features_work_piece;
import org.megapractical.billingplatform.repository.Features_work_pieceRepository;
import org.megapractical.billingplatform.service.Features_work_pieceService;

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
 * Test class for the Features_work_pieceResource REST controller.
 *
 * @see Features_work_pieceResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Features_work_pieceResourceIntTest {

    private static final String DEFAULT_CODE = "AAA";
    private static final String UPDATED_CODE = "BBB";

    @Inject
    private Features_work_pieceRepository features_work_pieceRepository;

    @Inject
    private Features_work_pieceService features_work_pieceService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFeatures_work_pieceMockMvc;

    private Features_work_piece features_work_piece;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Features_work_pieceResource features_work_pieceResource = new Features_work_pieceResource();
        ReflectionTestUtils.setField(features_work_pieceResource, "features_work_pieceService", features_work_pieceService);
        this.restFeatures_work_pieceMockMvc = MockMvcBuilders.standaloneSetup(features_work_pieceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        features_work_piece = new Features_work_piece();
        features_work_piece.setCode(DEFAULT_CODE);
    }

    @Test
    @Transactional
    public void createFeatures_work_piece() throws Exception {
        int databaseSizeBeforeCreate = features_work_pieceRepository.findAll().size();

        // Create the Features_work_piece

        restFeatures_work_pieceMockMvc.perform(post("/api/features-work-pieces")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(features_work_piece)))
                .andExpect(status().isCreated());

        // Validate the Features_work_piece in the database
        List<Features_work_piece> features_work_pieces = features_work_pieceRepository.findAll();
        assertThat(features_work_pieces).hasSize(databaseSizeBeforeCreate + 1);
        Features_work_piece testFeatures_work_piece = features_work_pieces.get(features_work_pieces.size() - 1);
        assertThat(testFeatures_work_piece.getCode()).isEqualTo(DEFAULT_CODE);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = features_work_pieceRepository.findAll().size();
        // set the field null
        features_work_piece.setCode(null);

        // Create the Features_work_piece, which fails.

        restFeatures_work_pieceMockMvc.perform(post("/api/features-work-pieces")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(features_work_piece)))
                .andExpect(status().isBadRequest());

        List<Features_work_piece> features_work_pieces = features_work_pieceRepository.findAll();
        assertThat(features_work_pieces).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFeatures_work_pieces() throws Exception {
        // Initialize the database
        features_work_pieceRepository.saveAndFlush(features_work_piece);

        // Get all the features_work_pieces
        restFeatures_work_pieceMockMvc.perform(get("/api/features-work-pieces?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(features_work_piece.getId().intValue())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())));
    }

    @Test
    @Transactional
    public void getFeatures_work_piece() throws Exception {
        // Initialize the database
        features_work_pieceRepository.saveAndFlush(features_work_piece);

        // Get the features_work_piece
        restFeatures_work_pieceMockMvc.perform(get("/api/features-work-pieces/{id}", features_work_piece.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(features_work_piece.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFeatures_work_piece() throws Exception {
        // Get the features_work_piece
        restFeatures_work_pieceMockMvc.perform(get("/api/features-work-pieces/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFeatures_work_piece() throws Exception {
        // Initialize the database
        features_work_pieceService.save(features_work_piece);

        int databaseSizeBeforeUpdate = features_work_pieceRepository.findAll().size();

        // Update the features_work_piece
        Features_work_piece updatedFeatures_work_piece = new Features_work_piece();
        updatedFeatures_work_piece.setId(features_work_piece.getId());
        updatedFeatures_work_piece.setCode(UPDATED_CODE);

        restFeatures_work_pieceMockMvc.perform(put("/api/features-work-pieces")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedFeatures_work_piece)))
                .andExpect(status().isOk());

        // Validate the Features_work_piece in the database
        List<Features_work_piece> features_work_pieces = features_work_pieceRepository.findAll();
        assertThat(features_work_pieces).hasSize(databaseSizeBeforeUpdate);
        Features_work_piece testFeatures_work_piece = features_work_pieces.get(features_work_pieces.size() - 1);
        assertThat(testFeatures_work_piece.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    public void deleteFeatures_work_piece() throws Exception {
        // Initialize the database
        features_work_pieceService.save(features_work_piece);

        int databaseSizeBeforeDelete = features_work_pieceRepository.findAll().size();

        // Get the features_work_piece
        restFeatures_work_pieceMockMvc.perform(delete("/api/features-work-pieces/{id}", features_work_piece.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Features_work_piece> features_work_pieces = features_work_pieceRepository.findAll();
        assertThat(features_work_pieces).hasSize(databaseSizeBeforeDelete - 1);
    }
}
