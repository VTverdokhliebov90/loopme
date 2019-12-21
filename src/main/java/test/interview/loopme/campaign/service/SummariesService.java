package test.interview.loopme.campaign.service;

import test.interview.loopme.campaign.dao.model.Summaries;
import test.interview.loopme.campaign.dao.model.SummariesSelectOptions;

import java.util.List;

/**
 * The SummariesService. Business logic level
 */
public interface SummariesService {

    List<Summaries> getSummaries(SummariesSelectOptions summariesSelectOptions);
}
