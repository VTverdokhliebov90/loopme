package test.interview.loopme.campaign.dao.impl;

import org.simpleflatmapper.jdbc.spring.JdbcTemplateMapperFactory;
import org.simpleflatmapper.jdbc.spring.ResultSetExtractorImpl;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import test.interview.loopme.campaign.dao.SummariesRepository;
import test.interview.loopme.campaign.dao.dsl.WhereClauseQueryBuilder;
import test.interview.loopme.campaign.dao.model.Summaries;
import test.interview.loopme.campaign.dao.model.SummariesFilterType;
import test.interview.loopme.campaign.dao.model.SummariesSelectOptions;
import test.interview.loopme.campaign.controller.dto.summaries.SummariesSortType;

import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Optional;

@Repository
public class SummariesJdbcRepository implements SummariesRepository {
    private static final String SELECT_QUERY =
            "SELECT id, name, status, temp.ads_count " +
                    "FROM campaigns " +
                    "LEFT JOIN " +
                    " (SELECT campaign_id, COUNT(ad_id) AS ads_count FROM campaigns_ads GROUP BY campaign_id) " +
                    "temp ON temp.campaign_id = id " +
                    "[where_clause_holder] " +
                    "ORDER BY [order_field] " +
                    "LIMIT :limit " +
                    "OFFSET :offset";

    private static final String ID = "id";
    private static final String LIMIT = "limit";
    private static final String OFFSET = "offset";
    private static final String ORDER_FIELD_PLACEHOLDER = "[order_field]";
    private static final String WHERE_CLAUSE_PLACEHOLDER = "[where_clause_holder]";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public SummariesJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Summaries> findSummaries(SummariesSelectOptions summariesSelectOptions) {

        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue(LIMIT, summariesSelectOptions.getPageOptions().getLimit())
                .addValue(OFFSET, summariesSelectOptions.getPageOptions().getOffset());

        String selectQuery = SELECT_QUERY
                .replace(WHERE_CLAUSE_PLACEHOLDER,
                        buildWhereClause(summariesSelectOptions.getSummariesFilterTypes(), parameterSource))
                .replace(ORDER_FIELD_PLACEHOLDER,
                        Optional.ofNullable(summariesSelectOptions.getSortOptions().getSummariesSortType())
                                .orElse(SummariesSortType.ID).name());

        ResultSetExtractorImpl<Summaries> summariesResultSetExtractor = JdbcTemplateMapperFactory.newInstance()
                .addKeys(ID)
                .newResultSetExtractor(Summaries.class);

        List<Summaries> summaries = jdbcTemplate.query(selectQuery, parameterSource, summariesResultSetExtractor);

        return Optional.ofNullable(summaries).orElse(Collections.emptyList());
    }

    private String buildWhereClause(EnumMap<SummariesFilterType, Optional<Object>> summariesFilterTypes,
                                    MapSqlParameterSource parameterSource) {
        WhereClauseQueryBuilder whereClauseQueryBuilder = new WhereClauseQueryBuilder();
        summariesFilterTypes.forEach((filterType, value) -> value.ifPresent(presentValue -> {
                    whereClauseQueryBuilder.eq(filterType.name());
                    parameterSource.addValue(filterType.name(), presentValue);
                })
        );
        return whereClauseQueryBuilder.build();
    }
}
