package test.interview.loopme.campaign;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import test.interview.loopme.campaign.controller.dto.AdPlatform;
import test.interview.loopme.campaign.controller.dto.Status;
import test.interview.loopme.campaign.controller.dto.ad.AdvertisementDTO;
import test.interview.loopme.campaign.controller.dto.ad.NewAdvertisementDTO;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static test.interview.loopme.campaign.utils.TestUtils.jsonToObj;
import static test.interview.loopme.campaign.utils.TestUtils.objToJson;

@SpringBootTest
@AutoConfigureMockMvc
class AdsResourceTest {
    private static final String URI_DELIMITER = "/";
    private static final String AD_RESOURCE_URI = "/ad";

    @Autowired
    private MockMvc mvc;

    @Test
    void testSaveAdvertisement() throws Exception {
        // GIVEN
        String name = "ad_name";
        String assetUrl = "asset_url";
        Status status = Status.PLANNED;

        List<AdPlatform> adPlatforms = Arrays.asList(AdPlatform.WEB, AdPlatform.ANDROID, AdPlatform.IOS);

        NewAdvertisementDTO advertisement = new NewAdvertisementDTO(name, assetUrl, adPlatforms);

        //  WHEN
        MvcResult mvcResult = mvc.perform(post(AD_RESOURCE_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objToJson(advertisement))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        //  THEN
        assertResult(mvcResult, name, status, assetUrl, adPlatforms);
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:dao/ads_get_test.sql")
    void testGetAdvertisement() throws Exception {
        // GIVEN
        int id = 100;
        String name = "ad_name";
        String assetUrl = "asset_url/ad_name";
        Status status = Status.ACTIVE;

        List<AdPlatform> adPlatforms = Arrays.asList(AdPlatform.WEB, AdPlatform.IOS);

        String targetUrl = new StringJoiner(URI_DELIMITER)
                .add(AD_RESOURCE_URI)
                .add(String.valueOf(id))
                .toString();

        //  WHEN
        MvcResult mvcResult = mvc.perform(get(targetUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        //  THEN
        assertResult(mvcResult, name, status, assetUrl, adPlatforms);
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:dao/ads_get_test.sql")
    void testGetNotExistedAdvertisement() throws Exception {
        // GIVEN
        String targetUrl = new StringJoiner(URI_DELIMITER)
                .add(AD_RESOURCE_URI)
                .add(String.valueOf(Integer.MAX_VALUE))
                .toString();

        //  WHEN
        mvc.perform(get(targetUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                //  THEN
                .andExpect(status().isNoContent());

    }

    @Test
    void testUpdateInvalid() throws Exception {
        // GIVEN
        NewAdvertisementDTO advertisement = new NewAdvertisementDTO(null, null, Collections.emptyList());

        //  WHEN
        MvcResult mvcResult = mvc.perform(put(AD_RESOURCE_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objToJson(advertisement))
                .accept(MediaType.APPLICATION_JSON))
                // THEN
                .andExpect(status().isBadRequest())
                .andReturn();

        ErrorResponse errorResponse = jsonToObj(mvcResult.getResponse().getContentAsString(), ErrorResponse.class);

        assertTrue(errorResponse.getErrors().contains("Please provide target id"));
        assertTrue(errorResponse.getErrors().contains("Please provide status"));
        assertTrue(errorResponse.getErrors().contains("Please provide a name"));
        assertTrue(errorResponse.getErrors().contains("Please provide a assetUrl"));
        assertTrue(errorResponse.getErrors().contains("Should be specified at least one platform"));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:dao/ads_update_test.sql")
    void testUpdateAdvertisementPlatformsReduce() throws Exception {
        // GIVEN
        int id = 200;
        String name = "ad_name_updated";
        Status status = Status.ACTIVE;
        String assetUrl = "asset_url_updated";
        List<AdPlatform> adPlatforms = Arrays.asList(AdPlatform.WEB, AdPlatform.IOS);

        AdvertisementDTO advertisement = new AdvertisementDTO(id, name, status, assetUrl, adPlatforms);

        //  WHEN
        MvcResult mvcResult = mvc.perform(put(AD_RESOURCE_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objToJson(advertisement))
                .accept(MediaType.APPLICATION_JSON))
                //  THEN
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        assertResult(mvcResult, name, status, assetUrl, adPlatforms);
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:dao/ads_update_test.sql")
    void testUpdateAdvertisementPlatformsIncrease() throws Exception {
        // GIVEN
        int id = 201;
        String name = "ad_name_updated";
        Status status = Status.ACTIVE;
        String assetUrl = "asset_url_updated";
        List<AdPlatform> adPlatforms = Arrays.asList(AdPlatform.WEB, AdPlatform.ANDROID, AdPlatform.IOS);

        AdvertisementDTO advertisement = new AdvertisementDTO(id, name, status, assetUrl, adPlatforms);

        //  WHEN
        MvcResult mvcResult = mvc.perform(put(AD_RESOURCE_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objToJson(advertisement))
                .accept(MediaType.APPLICATION_JSON))
                //  THEN
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        assertResult(mvcResult, name, status, assetUrl, adPlatforms);
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:dao/ads_delete_test.sql")
    void testDeleteAdvertisement() throws Exception {
        // GIVEN
        int id = 900;

        String targetUrl = new StringJoiner(URI_DELIMITER)
                .add(AD_RESOURCE_URI)
                .add(String.valueOf(id))
                .toString();

        //  WHEN
        mvc.perform(delete(targetUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                //  THEN
                .andExpect(status().isNoContent());
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:dao/ads_delete_test.sql")
    void testDeleteNotExistedAdvertisement() throws Exception {
        // GIVEN
        int id = Integer.MAX_VALUE;

        String targetUrl = new StringJoiner(URI_DELIMITER)
                .add(AD_RESOURCE_URI)
                .add(String.valueOf(id))
                .toString();

        //  WHEN
        mvc.perform(delete(targetUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                //  THEN
                .andExpect(status().isNoContent());
    }

    // TODO: could be done with matchers
    private static void assertResult(MvcResult mvcResult, String name, Status status, String assetUrl,
                                     List<AdPlatform> adPlatformTypes) throws Exception {

        AdvertisementDTO advertisementDTO = jsonToObj(mvcResult.getResponse().getContentAsString(), AdvertisementDTO.class);

        assertNotNull(advertisementDTO);
        assertEquals(name, advertisementDTO.getName());
        assertEquals(status, advertisementDTO.getStatus());
        assertEquals(assetUrl, advertisementDTO.getAssetUrl());
        assertTrue(adPlatformTypes.containsAll(advertisementDTO.getPlatforms()));
    }

}
