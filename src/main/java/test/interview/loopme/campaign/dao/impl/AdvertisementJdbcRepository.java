package test.interview.loopme.campaign.dao.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.simpleflatmapper.jdbc.spring.JdbcTemplateMapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import test.interview.loopme.campaign.dao.AdvertisementRepository;
import test.interview.loopme.campaign.dao.model.Advertisement;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static test.interview.loopme.campaign.dao.utils.DaoUtils.isResultAvailable;

@Repository
// TODO: It may be better to use some DSL lib to build query like JOOQ?
class AdvertisementJdbcRepository implements AdvertisementRepository {
    private static final String SELECT_QUERY =
            "SELECT ads.*, " +
                    "ads_p.platform AS platforms " +
                    "FROM ads " +
                    "LEFT JOIN ads_platforms ads_p ON ads.id = ads_p.ad_id " +
                    "WHERE ads.id = :id";
    private static final String UPDATE_QUERY =
            "UPDATE ads " +
                    "SET name = :name, status = :status, asset_url = :asset_url " +
                    "WHERE id = :id";
    private static final String DELETE_QUERY = "DELETE FROM campaigns WHERE id = :id";

    private static final String INSERT_AD_PLATFORM_QUERY = "INSERT INTO ads_platforms VALUES (:id, :platform, :ad_id)";
    private static final String SELECT_AD_PLATFORM_CODE_QUERY = "SELECT platform FROM ads_platforms WHERE ad_id = :ad_id";
    private static final String DELETE_AD_PLATFORM_QUERY = "DELETE FROM ads_platforms WHERE ad_id = :ad_id AND platform = :platform";

    private static final String TABLE_NAME = "ads";

    private static final String ID = "id";
    private static final String AD_ID = "ad_id";
    private static final String NAME = "name";
    private static final String STATUS = "status";
    private static final String ASSET_URL = "asset_url";

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    public AdvertisementJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName(TABLE_NAME).usingGeneratedKeyColumns(ID);
    }

    @Override
    @Nullable
    @Transactional(readOnly = true)
    public Advertisement find(int id) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource().addValue(ID, id);
        List<Advertisement> advertisements = jdbcTemplate.query(SELECT_QUERY, parameterSource,
                JdbcTemplateMapperFactory.newInstance().addKeys(ID).newResultSetExtractor(Advertisement.class));

        return isResultAvailable(advertisements) ? advertisements.get(0) : null;

    }

    @Override
    @Nullable
    @Transactional
    public Advertisement saveAndGet(Advertisement advertisement) {
        return find(save(advertisement));
    }

    @Override
    @Transactional
    public Advertisement updateAndGet(Advertisement advertisement) {
        int adId = advertisement.getId();
        int updatesCount = update(advertisement);
        if (updatesCount > 0) {
            updateAdPlatforms(adId, advertisement.getPlatforms());
            return find(adId);
        }
        return null;
    }

    @Override
    @Transactional
    public void delete(int id) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource().addValue(ID, id);
        jdbcTemplate.update(DELETE_QUERY, parameterSource);
    }

    private int save(Advertisement advertisement) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue(NAME, advertisement.getName())
                .addValue(STATUS, advertisement.getStatus())
                .addValue(ASSET_URL, advertisement.getAssetUrl());

        int addId = (int) simpleJdbcInsert.executeAndReturnKey(parameterSource);

        List<PlatformExtension> platformExtensions = advertisement.getPlatforms().stream()
                .map(platform -> new PlatformExtension(platform, addId))
                .collect(Collectors.toList());

        insertAdsPlatforms(platformExtensions);

        return addId;
    }

    private int update(Advertisement advertisement) {
        int adId = advertisement.getId();

        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue(ID, adId)
                .addValue(NAME, advertisement.getName())
                .addValue(STATUS, advertisement.getStatus())
                .addValue(ASSET_URL, advertisement.getAssetUrl());

        return jdbcTemplate.update(UPDATE_QUERY, parameterSource);
    }

    private void updateAdPlatforms(int adId, Set<Integer> platforms) {
        List<Integer> currentAdPlatforms = loadAdsPlatforms(adId);
        List<Integer> targetAdPlatforms = new ArrayList<>(platforms);

        if (currentAdPlatforms.size() > targetAdPlatforms.size()) {
            // deleteAll childes
            deleteAdsPlatforms(toPlatformObj(adId, extractUniques(currentAdPlatforms, targetAdPlatforms)));
        } else if (currentAdPlatforms.size() < targetAdPlatforms.size()) {
            // insert childes
            insertAdsPlatforms(toPlatformObj(adId, extractUniques(targetAdPlatforms, currentAdPlatforms)));
        }
    }

    private static List<Integer> extractUniques(List<Integer> source, List<Integer> values) {
        List<Integer> duplicates = new ArrayList<>(source);
        List<Integer> uniques = new ArrayList<>(source);
        duplicates.retainAll(values);
        uniques.removeAll(duplicates);
        return uniques;
    }

    //TODO ads platforms operations could be moved to separate class
    private void insertAdsPlatforms(List<PlatformExtension> platformExtensions) {
        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(platformExtensions);
        jdbcTemplate.batchUpdate(INSERT_AD_PLATFORM_QUERY, batch);
    }

    private List<Integer> loadAdsPlatforms(int adId) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(AD_ID, adId);

        return jdbcTemplate.query(SELECT_AD_PLATFORM_CODE_QUERY, paramMap,
                JdbcTemplateMapperFactory.newInstance().addKeys(ID).newResultSetExtractor(Integer.class));
    }

    private void deleteAdsPlatforms(List<PlatformExtension> platformExtensions) {
        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(platformExtensions.toArray());
        jdbcTemplate.batchUpdate(DELETE_AD_PLATFORM_QUERY, batch);
    }

    private static List<PlatformExtension> toPlatformObj(int addId, List<Integer> platforms) {
        return platforms.stream().map(platform -> new PlatformExtension(platform, addId)).collect(Collectors.toList());
    }

    @Data
    @AllArgsConstructor
    private static final class PlatformExtension {
        private int platform;
        private int ad_id;

        // Required by Batch Operations
        public Integer getId() {
            return null;
        }
    }
}
