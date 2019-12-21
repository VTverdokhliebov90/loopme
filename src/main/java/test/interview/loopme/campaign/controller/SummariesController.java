package test.interview.loopme.campaign.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import test.interview.loopme.campaign.controller.dto.Status;
import test.interview.loopme.campaign.controller.dto.summaries.SummariesDTO;
import test.interview.loopme.campaign.controller.dto.summaries.SummariesSortType;
import test.interview.loopme.campaign.controller.mapper.SummariesMapper;
import test.interview.loopme.campaign.dao.model.PageOptions;
import test.interview.loopme.campaign.dao.model.SortOptions;
import test.interview.loopme.campaign.dao.model.SummariesFilterType;
import test.interview.loopme.campaign.dao.model.SummariesSelectOptions;
import test.interview.loopme.campaign.service.SummariesService;

import java.util.EnumMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("summaries")
public class SummariesController {
    private static final String DEFAULT_NUMBER_VALUE = "0";

    private final SummariesService summariesService;

    @Autowired
    public SummariesController(SummariesService summariesService) {
        this.summariesService = summariesService;
    }

    @GetMapping
    public ResponseEntity<List<SummariesDTO>> getSummaries(@RequestParam(required = false) String name,
                                                           @RequestParam(required = false) Status status,
                                                           @RequestParam(required = false, defaultValue = DEFAULT_NUMBER_VALUE) int limit,
                                                           @RequestParam(required = false, defaultValue = DEFAULT_NUMBER_VALUE) int offset,
                                                           @RequestParam(name = "sort_by", required = false) SummariesSortType summariesSortType) {

        EnumMap<SummariesFilterType, Optional<Object>> summariesFilterOptions = new EnumMap<>(SummariesFilterType.class);
        summariesFilterOptions.put(SummariesFilterType.NAME, Optional.ofNullable(name));
        summariesFilterOptions.put(SummariesFilterType.STATUS, Optional.ofNullable(status).map(Status::getCode));
        PageOptions pageOptions = new PageOptions(limit, offset);
        SortOptions sortOptions = new SortOptions(summariesSortType);

        SummariesSelectOptions summariesSelectOptions =
                new SummariesSelectOptions(summariesFilterOptions, pageOptions, sortOptions);

        List<SummariesDTO> summaries = summariesService.getSummaries(summariesSelectOptions)
                .stream()
                .map(SummariesMapper::mapToDto)
                .collect(Collectors.toList());

        return summaries.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(summaries, HttpStatus.OK);
    }

}
