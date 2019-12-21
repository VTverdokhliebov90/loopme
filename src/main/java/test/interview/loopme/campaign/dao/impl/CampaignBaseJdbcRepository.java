package test.interview.loopme.campaign.dao.impl;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import test.interview.loopme.campaign.dao.CampaignBaseRepository;
import test.interview.loopme.campaign.dao.model.Campaign;

import javax.sql.DataSource;

@Repository
class CampaignBaseJdbcRepository implements CampaignBaseRepository {
    private static final String UPDATE_CAMPAIGN_QUERY =
            "UPDATE campaigns SET " +
                    "name = :name, " +
                    "status = :status, " +
                    "start_date = :start_date, " +
                    "end_date = :end_date " +
                    "WHERE id = :id";
    private static final String DELETE_QUERY = "DELETE FROM campaigns WHERE id = :id";

    private static final String TABLE_NAME = "campaigns";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String STATUS = "status";
    private static final String START_DATE = "start_date";
    private static final String END_DATE = "end_date";

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public CampaignBaseJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName(TABLE_NAME).usingGeneratedKeyColumns(ID);
    }

    @Override
    public int save(Campaign campaign) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue(NAME, campaign.getName())
                .addValue(STATUS, campaign.getStatus())
                .addValue(START_DATE, campaign.getStartDate())
                .addValue(END_DATE, campaign.getEndDate());

        return (int) simpleJdbcInsert.executeAndReturnKey(parameterSource);
    }

    @Override
    public int update(Campaign campaign) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue(ID, campaign.getId())
                .addValue(NAME, campaign.getName())
                .addValue(STATUS, campaign.getStatus())
                .addValue(START_DATE, campaign.getStartDate())
                .addValue(END_DATE, campaign.getEndDate());

        return jdbcTemplate.update(UPDATE_CAMPAIGN_QUERY, parameterSource);
    }

    @Override
    public void delete(int id) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource().addValue(ID, id);
        jdbcTemplate.update(DELETE_QUERY, parameterSource);
    }
}