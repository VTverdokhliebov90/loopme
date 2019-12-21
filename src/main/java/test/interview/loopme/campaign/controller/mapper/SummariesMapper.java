package test.interview.loopme.campaign.controller.mapper;

import test.interview.loopme.campaign.controller.dto.Status;
import test.interview.loopme.campaign.controller.dto.summaries.SummariesDTO;
import test.interview.loopme.campaign.dao.model.Summaries;

public final class SummariesMapper {

    public static SummariesDTO mapToDto(Summaries summaries) {
        return new SummariesDTO(
                summaries.getId(),
                summaries.getName(),
                Status.forCode(summaries.getStatus()),
                summaries.getAdsCount());
    }
}
