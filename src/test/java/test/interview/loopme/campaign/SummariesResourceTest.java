package test.interview.loopme.campaign;

import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import test.interview.loopme.campaign.controller.dto.Status;
import test.interview.loopme.campaign.controller.dto.summaries.SummariesDTO;
import test.interview.loopme.campaign.controller.dto.summaries.SummariesSortType;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static test.interview.loopme.campaign.utils.TestUtils.jsonToObj;

@SpringBootTest
@AutoConfigureMockMvc
class SummariesResourceTest {
    private static final String RESOURCE_URI = "/summaries";

    private static final String LIMIT = "limit";
    private static final String OFFSET = "offset";
    private static final String NAME = "name";
    private static final String STATUS = "status";
    private static final String SORT_BY = "sort_by";

    @Autowired
    private MockMvc mvc;

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:dao/summaries_pagination_test.sql")
    void testFindWithNoParams() throws Exception {
        //  WHEN
        mvc.perform(get(RESOURCE_URI)
                .accept(MediaType.APPLICATION_JSON))
                //  THEN
                .andExpect(status().isNoContent())
                .andReturn();
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:dao/summaries_pagination_test.sql")
    void testFindFirstPage() throws Exception {
        // GIVEN
        int limit = 5;
        List<String> expectedSummariesNames = Arrays.asList(
                "first_page1", "first_page2", "first_page3", "first_page4", "first_page5");
        //  WHEN
        MvcResult mvcResult = mvc.perform(get(RESOURCE_URI)
                .param(LIMIT, Integer.valueOf(limit).toString())
                .accept(MediaType.APPLICATION_JSON))
                //  THEN
                .andExpect(status().isOk())
                .andReturn();

        List<SummariesDTO> summaries = jsonToObj(mvcResult.getResponse().getContentAsString(),
                new TypeReference<List<SummariesDTO>>() {
                });

        assertArrayEquals(expectedSummariesNames.toArray(), summaries.stream().map(SummariesDTO::getName).toArray());
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:dao/summaries_pagination_test.sql")
    void testFindSecondPage() throws Exception {
        // GIVEN
        int limit = 5, offset = 5;
        List<String> expectedSummariesNames = Arrays.asList(
                "second_page1", "second_page2", "second_page3", "second_page4", "second_page5");
        //  WHEN
        MvcResult mvcResult = mvc.perform(get(RESOURCE_URI)
                .param(LIMIT, Integer.valueOf(limit).toString())
                .param(OFFSET, Integer.valueOf(offset).toString())
                .accept(MediaType.APPLICATION_JSON))
                //  THEN
                .andExpect(status().isOk())
                .andReturn();

        List<SummariesDTO> summaries = jsonToObj(mvcResult.getResponse().getContentAsString(),
                new TypeReference<List<SummariesDTO>>() {
                });

        assertArrayEquals(expectedSummariesNames.toArray(), summaries.stream().map(SummariesDTO::getName).toArray());
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:dao/summaries_filter_test.sql")
    void testFindForName() throws Exception {
        // GIVEN
        int limit = Integer.MAX_VALUE, offset = 0;
        String find_name = "find_name";
        List<String> expectedSummariesNames = Collections.singletonList(find_name);
        //  WHEN
        MvcResult mvcResult = mvc.perform(get(RESOURCE_URI)
                .param(LIMIT, Integer.valueOf(limit).toString())
                .param(OFFSET, Integer.valueOf(offset).toString())
                .param(NAME, find_name)
                .accept(MediaType.APPLICATION_JSON))
                //  THEN
                .andExpect(status().isOk())
                .andReturn();

        List<SummariesDTO> summaries = jsonToObj(mvcResult.getResponse().getContentAsString(),
                new TypeReference<List<SummariesDTO>>() {
                });

        Set<String> actualNamesList = summaries.stream().map(SummariesDTO::getName).collect(Collectors.toSet());
        assertArrayEquals(expectedSummariesNames.toArray(), actualNamesList.toArray());
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:dao/summaries_filter_test.sql")
    void testFindForStatus() throws Exception {
        // GIVEN
        int limit = Integer.MAX_VALUE, offset = 0;
        Status status = Status.PLANNED;
        List<Status> expectedSummariesStatuses = Collections.singletonList(status);
        //  WHEN
        MvcResult mvcResult = mvc.perform(get(RESOURCE_URI)
                .param(LIMIT, Integer.valueOf(limit).toString())
                .param(OFFSET, Integer.valueOf(offset).toString())
                .param(STATUS, status.name())
                .accept(MediaType.APPLICATION_JSON))
                //  THEN
                .andExpect(status().isOk())
                .andReturn();

        List<SummariesDTO> summaries = jsonToObj(mvcResult.getResponse().getContentAsString(),
                new TypeReference<List<SummariesDTO>>() {
                });

        Set<Status> actualStatusesList = summaries.stream().map(SummariesDTO::getStatus).collect(Collectors.toSet());
        assertArrayEquals(expectedSummariesStatuses.toArray(), actualStatusesList.toArray());
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:dao/summaries_filter_test.sql")
    void testFindForNameAndStatus() throws Exception {
        // GIVEN
        int limit = Integer.MAX_VALUE, offset = 0;
        String name = "find_name";
        Status status = Status.PLANNED;
        //  WHEN
        MvcResult mvcResult = mvc.perform(get(RESOURCE_URI)
                .param(LIMIT, Integer.valueOf(limit).toString())
                .param(OFFSET, Integer.valueOf(offset).toString())
                .param(NAME, name)
                .param(STATUS, status.name())
                .accept(MediaType.APPLICATION_JSON))
                //  THEN
                .andExpect(status().isOk())
                .andReturn();

        List<SummariesDTO> summaries = jsonToObj(mvcResult.getResponse().getContentAsString(),
                new TypeReference<List<SummariesDTO>>() {
                });

        assertEquals(2, summaries.size());
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:dao/summaries_sort_test.sql")
    void testFindSortingByName() throws Exception {
        // GIVEN
        int limit = 5, offset = 5;
        SummariesSortType summariesSortType = SummariesSortType.NAME;

        List<String> expectedSummariesNames = Arrays.asList("name5", "name5", "name6", "name7", "name8");
        //  WHEN
        MvcResult mvcResult = mvc.perform(get(RESOURCE_URI)
                .param(LIMIT, Integer.valueOf(limit).toString())
                .param(OFFSET, Integer.valueOf(offset).toString())
                .param(SORT_BY, summariesSortType.name())
                .accept(MediaType.APPLICATION_JSON))
                //  THEN
                .andExpect(status().isOk())
                .andReturn();

        List<SummariesDTO> summaries = jsonToObj(mvcResult.getResponse().getContentAsString(),
                new TypeReference<List<SummariesDTO>>() {
                });

        List<String> actualNamesList = summaries.stream().map(SummariesDTO::getName).collect(Collectors.toList());

        assertArrayEquals(expectedSummariesNames.toArray(), actualNamesList.toArray());

    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:dao/summaries_sort_test.sql")
    void testFindSortingByStatus() throws Exception {
        // GIVEN
        int limit = 5, offset = 0;
        SummariesSortType summariesSortType = SummariesSortType.STATUS;

        List<Status> expectedSummariesNames = Arrays.asList(Status.PLANNED, Status.PLANNED, Status.PLANNED,
                Status.PLANNED, Status.ACTIVE);
        //  WHEN
        MvcResult mvcResult = mvc.perform(get(RESOURCE_URI)
                .param(LIMIT, Integer.valueOf(limit).toString())
                .param(OFFSET, Integer.valueOf(offset).toString())
                .param(SORT_BY, summariesSortType.name())
                .accept(MediaType.APPLICATION_JSON))
                //  THEN
                .andExpect(status().isOk())
                .andReturn();

        List<SummariesDTO> summaries = jsonToObj(mvcResult.getResponse().getContentAsString(),
                new TypeReference<List<SummariesDTO>>() {
                });

        List<Status> actualNamesList = summaries.stream().map(SummariesDTO::getStatus).collect(Collectors.toList());

        assertArrayEquals(expectedSummariesNames.toArray(), actualNamesList.toArray());

    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:dao/summaries_sort_ads_count_test.sql")
    void testFindSortingByAdsCount() throws Exception {
        // GIVEN
        int limit = Integer.MAX_VALUE, offset = 0;
        SummariesSortType summariesSortType = SummariesSortType.ADS_COUNT;

        List<Integer> expectedSummariesNames = Arrays.asList(1, 2, 3);
        //  WHEN
        MvcResult mvcResult = mvc.perform(get(RESOURCE_URI)
                .param(LIMIT, Integer.valueOf(limit).toString())
                .param(OFFSET, Integer.valueOf(offset).toString())
                .param(SORT_BY, summariesSortType.name())
                .accept(MediaType.APPLICATION_JSON))
                //  THEN
                .andExpect(status().isOk())
                .andReturn();

        List<SummariesDTO> summaries = jsonToObj(mvcResult.getResponse().getContentAsString(),
                new TypeReference<List<SummariesDTO>>() {
                });

        List<Integer> actualNamesList = summaries.stream().map(SummariesDTO::getAdsCount).collect(Collectors.toList());

        assertArrayEquals(expectedSummariesNames.toArray(), actualNamesList.toArray());

    }
}
