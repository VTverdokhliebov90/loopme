package test.interview.loopme.campaign.dao;

import test.interview.loopme.campaign.dao.model.Summaries;
import test.interview.loopme.campaign.dao.model.SummariesSelectOptions;

import java.util.List;

/**
 * Repository provides campaigns summaries info
 */
public interface SummariesRepository {

    /**
     * Finds {@link Summaries} according to select options
     *
     * @param summariesSelectOptions - contains options to clarey search output
     * @return list of {@link Summaries}
     */
    List<Summaries> findSummaries(SummariesSelectOptions summariesSelectOptions);
}
