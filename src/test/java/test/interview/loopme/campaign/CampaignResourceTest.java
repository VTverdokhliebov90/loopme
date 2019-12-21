package test.interview.loopme.campaign;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import test.interview.loopme.campaign.controller.dto.Status;
import test.interview.loopme.campaign.controller.dto.campaign.CampaignDTO;
import test.interview.loopme.campaign.controller.dto.campaign.NewCampaignDTO;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
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
// TODO: some tests could be grouped to parametrized to reduce code amount
class CampaignResourceTest {
    private static final String URI_DELIMITER = "/";
    private static final String CAMPAIGN_RESOURCE_URI = "/campaign";

    @Autowired
    private MockMvc mvc;

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:dao/campaign_save_test.sql")
    void testSave() throws Exception {
        // GIVEN
        String name = "campaign_name_created";
        Status status = Status.PLANNED;
        long startDate = System.currentTimeMillis();
        long endDate = System.currentTimeMillis();
        List<Integer> advertisements = Arrays.asList(100, 101);

        NewCampaignDTO newCampaignDTO = new NewCampaignDTO(name, startDate, endDate, advertisements);

        //  WHEN
        MvcResult mvcResult = mvc.perform(post(CAMPAIGN_RESOURCE_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objToJson(newCampaignDTO))
                .accept(MediaType.APPLICATION_JSON))
                //  THEN
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        //  THEN
        assertResult(mvcResult, name, status, startDate, endDate, advertisements);
    }

    @Test
    void testUpdateInvalid() throws Exception {
        // GIVEN
        long startDate = System.currentTimeMillis();
        long endDate = System.currentTimeMillis();

        NewCampaignDTO newCampaignDTO = new NewCampaignDTO(null, startDate, endDate, Collections.emptyList());

        //  WHEN
        MvcResult mvcResult = mvc.perform(put(CAMPAIGN_RESOURCE_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objToJson(newCampaignDTO))
                .accept(MediaType.APPLICATION_JSON))
                //  THEN
                .andExpect(status().isBadRequest())
                .andReturn();

        ErrorResponse errorResponse = jsonToObj(mvcResult.getResponse().getContentAsString(), ErrorResponse.class);

        assertTrue(errorResponse.getErrors().contains("Please provide target id"));
        assertTrue(errorResponse.getErrors().contains("Please provide status"));
        assertTrue(errorResponse.getErrors().contains("Please provide a name"));
        assertTrue(errorResponse.getErrors().contains("Should be specified at least one ad id"));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:dao/campaign_get_test.sql")
    void testGetById() throws Exception {
        // GIVEN
        int id = 10;
        String name = "campaign_name1";
        Status status = Status.ACTIVE;
        List<Integer> advertisements = Collections.singletonList(100);

        String targetUrl = new StringJoiner(URI_DELIMITER)
                .add(CAMPAIGN_RESOURCE_URI)
                .add(String.valueOf(id))
                .toString();

        //  WHEN
        MvcResult mvcResult = mvc.perform(get(targetUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                //  THEN
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        //  THEN
        assertResult(mvcResult, name, status, advertisements);
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:dao/campaign_get_test.sql")
    void testGetNotExisted() throws Exception {
        // GIVEN
        Integer id = Integer.MAX_VALUE;
        String targetUrl = new StringJoiner(URI_DELIMITER)
                .add(CAMPAIGN_RESOURCE_URI)
                .add(String.valueOf(id))
                .toString();

        //  WHEN
        mvc.perform(get(targetUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                //  THEN
                .andExpect(status().isNoContent());
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:dao/campaign_update_test.sql")
    void testUpdate() throws Exception {
        // GIVEN
        String name = "campaign_name_updated";
        Integer id = 10;
        Status status = Status.ACTIVE;
        long startDate = System.currentTimeMillis();
        long endDate = System.currentTimeMillis();
        List<Integer> advertisements = Arrays.asList(101, 102);

        CampaignDTO updatedCampaignDTO = new CampaignDTO(id, status, name, startDate, endDate, advertisements);

        //  WHEN
        MvcResult mvcResult = mvc.perform(put(CAMPAIGN_RESOURCE_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objToJson(updatedCampaignDTO))
                .accept(MediaType.APPLICATION_JSON))
                //  THEN
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        assertResult(mvcResult, name, status, startDate, endDate, advertisements);
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:dao/campaign_delete_test.sql")
    void testDelete() throws Exception {
        // GIVEN
        Integer id = 10;
        String targetUrl = new StringJoiner(URI_DELIMITER)
                .add(CAMPAIGN_RESOURCE_URI)
                .add(String.valueOf(id))
                .toString();

        //  WHEN
        mvc.perform(get(targetUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mvc.perform(delete(targetUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        //  THEN
        mvc.perform(get(targetUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:dao/campaign_delete_test.sql")
    void testDeleteNotExisted() throws Exception {
        // GIVEN
        Integer id = Integer.MAX_VALUE;
        String targetUrl = new StringJoiner(URI_DELIMITER)
                .add(CAMPAIGN_RESOURCE_URI)
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
    private static void assertResult(MvcResult mvcResult, String name, Status status, long startDate, long endDate,
                                     List<Integer> advertisements) throws Exception {

        CampaignDTO campaignDTO = jsonToObj(mvcResult.getResponse().getContentAsString(), CampaignDTO.class);

        assertNotNull(campaignDTO);
        assertEquals(name, campaignDTO.getName());
        assertEquals(status, campaignDTO.getStatus());
        assertEquals(startDate, campaignDTO.getStartDate());
        assertEquals(endDate, campaignDTO.getEndDate());
        assertArrayEquals(advertisements.toArray(), campaignDTO.getAdvertisements().toArray());
    }

    private static void assertResult(MvcResult mvcResult, String name, Status status,
                                     List<Integer> advertisements) throws Exception {

        CampaignDTO campaignDTO = jsonToObj(mvcResult.getResponse().getContentAsString(), CampaignDTO.class);

        assertNotNull(campaignDTO);
        assertEquals(name, campaignDTO.getName());
        assertEquals(status, campaignDTO.getStatus());
        assertArrayEquals(advertisements.toArray(), campaignDTO.getAdvertisements().toArray());
    }

}
