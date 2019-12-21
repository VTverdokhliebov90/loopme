package test.interview.loopme.campaign.dao.model;

import lombok.Value;

import java.util.EnumMap;
import java.util.Optional;

@Value
public class SummariesSelectOptions {

    private final EnumMap<SummariesFilterType, Optional<Object>> summariesFilterTypes;
    private final PageOptions pageOptions;
    private final SortOptions sortOptions;

}
