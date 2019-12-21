package test.interview.loopme.campaign.controller.mapper;

import test.interview.loopme.campaign.controller.dto.Status;
import test.interview.loopme.campaign.controller.dto.campaign.CampaignDTO;
import test.interview.loopme.campaign.controller.dto.campaign.NewCampaignDTO;
import test.interview.loopme.campaign.dao.model.Campaign;

import java.util.Date;

public final class CampaignMapper {

    public static Campaign mapToDomain(NewCampaignDTO campaign) {
        return new Campaign(
                campaign.getName(),
                new Date(campaign.getStartDate()),
                new Date(campaign.getEndDate()),
                campaign.getAdvertisements());
    }

    public static Campaign mapToDomain(CampaignDTO campaign) {
        return new Campaign(
                campaign.getId(),
                campaign.getName(),
                campaign.getStatus().getCode(),
                new Date(campaign.getStartDate()),
                new Date(campaign.getEndDate()),
                campaign.getAdvertisements());
    }

    public static CampaignDTO mapToDto(Campaign campaign) {
        return new CampaignDTO(
                campaign.getId(),
                Status.forCode(campaign.getStatus()),
                campaign.getName(),
                campaign.getStartDate().getTime(),
                campaign.getEndDate().getTime(),
                campaign.getAdvertisements());
    }
}
