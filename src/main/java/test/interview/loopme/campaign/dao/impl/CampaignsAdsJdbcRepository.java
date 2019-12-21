package test.interview.loopme.campaign.dao.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.simpleflatmapper.jdbc.spring.JdbcTemplateMapperFactory;
import org.simpleflatmapper.jdbc.spring.ResultSetExtractorImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CampaignsAdsJdbcRepository {
    private static final String INSERT_CAMPAIGNS_ADS_BINDING =
            "INSERT INTO campaigns_ads (campaign_id, ad_id) VALUES (:campaign_id, :ad_id)";
    private static final String DELETE_CAMPAIGNS_ADS_BINDING =
            "DELETE FROM campaigns_ads WHERE campaign_id = :campaign_id AND ad_id = :ad_id";
    private static final String SELECT_CAMPAIGNS_ADS_BINDING =
            "SELECT ad_id FROM campaigns_ads WHERE campaign_id = :id";

    private static final String ID = "id";
    private static final String ADVERTISEMENTS_ID = "advertisements_id";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public CampaignsAdsJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    void insertCampaignAdsBindings(int campaignId, List<Integer> adsIds) {
        executeButchQuery(INSERT_CAMPAIGNS_ADS_BINDING, campaignId, adsIds);
    }

    void deleteCampaignAdsBindings(int campaignId, List<Integer> adsIds) {
        executeButchQuery(DELETE_CAMPAIGNS_ADS_BINDING, campaignId, adsIds);
    }

    private void executeButchQuery(String query, int campaignId, List<Integer> adsIds) {
        List<CampaignAdId> campaignAdIds = adsIds.stream()
                .map(adId -> new CampaignAdId(campaignId, adId))
                .collect(Collectors.toList());

        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(campaignAdIds);

        jdbcTemplate.batchUpdate(query, batch);
    }

    List<Integer> selectCampaignAdsBindings(int campaignId) {
        ResultSetExtractorImpl<Integer> adIdsResultSetExtractor = JdbcTemplateMapperFactory
                .newInstance()
                .addKeys(ID, ADVERTISEMENTS_ID)
                .newResultSetExtractor(Integer.class);

        MapSqlParameterSource parameterSource = new MapSqlParameterSource().addValue(ID, campaignId);
        return jdbcTemplate.query(SELECT_CAMPAIGNS_ADS_BINDING, parameterSource, adIdsResultSetExtractor);
    }

    @AllArgsConstructor
    @Getter
    private static class CampaignAdId {
        private int campaign_id;
        private int ad_id;
    }
}
