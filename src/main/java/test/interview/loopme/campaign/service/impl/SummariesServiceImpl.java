package test.interview.loopme.campaign.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import test.interview.loopme.campaign.dao.SummariesRepository;
import test.interview.loopme.campaign.dao.model.Summaries;
import test.interview.loopme.campaign.dao.model.SummariesSelectOptions;
import test.interview.loopme.campaign.service.SummariesService;

import java.util.List;

@Component
public class SummariesServiceImpl implements SummariesService {
    private final SummariesRepository summariesRepository;

    @Autowired
    public SummariesServiceImpl(SummariesRepository summariesRepository) {
        this.summariesRepository = summariesRepository;
    }

    @Override
    public List<Summaries> getSummaries(SummariesSelectOptions summariesSelectOptions) {
        return summariesRepository.findSummaries(summariesSelectOptions);
    }
}
