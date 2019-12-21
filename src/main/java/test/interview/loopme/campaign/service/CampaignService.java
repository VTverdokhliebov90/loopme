package test.interview.loopme.campaign.service;

import test.interview.loopme.campaign.dao.model.Campaign;

import java.util.Optional;

/**
 * The CampaignService. Business logic level
 */
public interface CampaignService {

    Optional<Campaign> find(int id);

    Optional<Campaign> add(Campaign campaign);

    Optional<Campaign> update(Campaign campaign);

    void delete(int id);

}
