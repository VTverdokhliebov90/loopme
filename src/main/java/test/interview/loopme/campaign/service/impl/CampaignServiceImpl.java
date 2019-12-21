package test.interview.loopme.campaign.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import test.interview.loopme.campaign.dao.CampaignRepository;
import test.interview.loopme.campaign.dao.model.Campaign;
import test.interview.loopme.campaign.service.CampaignService;

import java.util.Optional;

@Component
@Transactional
public class CampaignServiceImpl implements CampaignService {
    private final CampaignRepository campaignRepository;

    @Autowired
    public CampaignServiceImpl(CampaignRepository campaignRepository) {
        this.campaignRepository = campaignRepository;
    }

    @Override
    public Optional<Campaign> find(int id) {
        return Optional.ofNullable(campaignRepository.find(id));
    }

    @Override
    public Optional<Campaign> add(Campaign campaign) {
        return Optional.ofNullable(campaignRepository.saveAndGet(campaign));
    }

    @Override
    public Optional<Campaign> update(Campaign campaign) {
        return Optional.ofNullable(campaignRepository.updateAndGet(campaign));
    }

    @Override
    public void delete(int id) {
        campaignRepository.delete(id);
    }
}
