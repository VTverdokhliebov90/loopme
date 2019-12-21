package test.interview.loopme.campaign.dao.impl;

import org.simpleflatmapper.jdbc.spring.JdbcTemplateMapperFactory;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import test.interview.loopme.campaign.dao.CampaignBaseRepository;
import test.interview.loopme.campaign.dao.CampaignRepository;
import test.interview.loopme.campaign.dao.model.Campaign;

import java.util.ArrayList;
import java.util.List;

import static test.interview.loopme.campaign.dao.utils.DaoUtils.isResultAvailable;

@Repository
class CampaignJdbcRepository implements CampaignRepository {
    private static final String SELECT_CASCADE_QUERY =
            "SELECT campaigns.*, " +
                    "campaigns_ads.ad_id AS advertisements_id " +
                    "FROM campaigns " +
                    "LEFT JOIN campaigns_ads ON campaigns.id = campaigns_ads.campaign_id " +
                    "WHERE campaigns_ads.campaign_id = :id";

    private static final String ID = "id";
    private static final String ADVERTISEMENTS_ID = "advertisements_id";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final CampaignBaseRepository campaignBaseRepository;
    private final CampaignsAdsJdbcRepository campaignsAdsJdbcRepository;
    private final ResultSetExtractor<List<Campaign>> resultSetExtractor;


    public CampaignJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate,
                                  CampaignBaseRepository campaignBaseRepository,
                                  CampaignsAdsJdbcRepository campaignsAdsJdbcRepository) {

        this.jdbcTemplate = jdbcTemplate;
        this.campaignBaseRepository = campaignBaseRepository;
        this.campaignsAdsJdbcRepository = campaignsAdsJdbcRepository;
        this.resultSetExtractor = JdbcTemplateMapperFactory
                .newInstance()
                .addKeys(ID, ADVERTISEMENTS_ID)
                .newResultSetExtractor(Campaign.class);
    }

    @Override
    @Nullable
    @Transactional(readOnly = true)
    public Campaign find(int id) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource().addValue(ID, id);
        List<Campaign> campaigns = jdbcTemplate.query(SELECT_CASCADE_QUERY, parameterSource, resultSetExtractor);

        return isResultAvailable(campaigns) ? campaigns.get(0) : null;
    }

    @Override
    @Transactional
    public Campaign saveAndGet(Campaign campaign) {
        int campaignId = campaignBaseRepository.save(campaign);
        List<Integer> adsIds = campaign.getAdvertisements();
        campaignsAdsJdbcRepository.insertCampaignAdsBindings(campaignId, adsIds);
        return find(campaignId);
    }

    @Override
    @Nullable
    @Transactional
    public Campaign updateAndGet(Campaign campaign) {
        int updatesCount = campaignBaseRepository.update(campaign);
        if (updatesCount > 0) {

            List<Integer> currentAds = campaignsAdsJdbcRepository.selectCampaignAdsBindings(campaign.getId());
            List<Integer> newAds = campaign.getAdvertisements();

            List<Integer> duplicates = new ArrayList<>(currentAds);
            duplicates.retainAll(newAds);
            List<Integer> adsToAttach = new ArrayList<>(newAds);
            adsToAttach.removeAll(duplicates);
            List<Integer> adsToDetach = new ArrayList<>(currentAds);
            adsToDetach.removeAll(duplicates);

            campaignsAdsJdbcRepository.insertCampaignAdsBindings(campaign.getId(), adsToAttach);
            campaignsAdsJdbcRepository.deleteCampaignAdsBindings(campaign.getId(), adsToDetach);
            return find(campaign.getId());
        }
        return null;

    }

    @Override
    @Transactional
    public void delete(int id) {
        campaignBaseRepository.delete(id);
    }

}