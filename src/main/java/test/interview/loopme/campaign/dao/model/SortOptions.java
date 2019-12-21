package test.interview.loopme.campaign.dao.model;

import lombok.Value;
import test.interview.loopme.campaign.controller.dto.summaries.SummariesSortType;

@Value
public class SortOptions {

    private final SummariesSortType summariesSortType;
}
