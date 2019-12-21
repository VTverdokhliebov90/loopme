package test.interview.loopme.campaign.dao.dsl;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class WhereClauseQueryBuilder {
    private static final String WHERE = "WHERE";
    private static final String _AND = " AND ";
    private static final String EMPTY = "";
    private static final String WHITE_SPACE = " ";
    private static final String EQUALS = " = :";

    private List<String> whereConditions = new ArrayList<>();

    public String build() {
        if (whereConditions.isEmpty()) {
            return EMPTY;
        }

        StringJoiner stringJoiner = new StringJoiner(_AND, WHERE, EMPTY);
        whereConditions.forEach(stringJoiner::add);
        return stringJoiner.toString();

    }

    public WhereClauseQueryBuilder eq(String columnName) {
        whereConditions.add(WHITE_SPACE + columnName + EQUALS + columnName);
        return this;
    }
}
