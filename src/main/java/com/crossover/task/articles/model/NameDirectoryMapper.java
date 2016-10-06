package com.crossover.task.articles.model;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @since 25/09/2016
 */
public class NameDirectoryMapper implements RowMapper<NameDirectorySolr> {
    @Override
    public NameDirectorySolr mapRow(ResultSet rs, int line) throws SQLException {
        NameDirectoryResultSetExtractor extractor = new NameDirectoryResultSetExtractor();
        return extractor.extractData(rs);
    }

    class NameDirectoryResultSetExtractor implements ResultSetExtractor<NameDirectorySolr> {
        @Override
        public NameDirectorySolr extractData(ResultSet resultSet) throws SQLException, DataAccessException {
            NameDirectorySolr nd = new NameDirectorySolr();
            nd.setId(resultSet.getLong("id"));
            nd.setFirstName(resultSet.getString("firstName"));
            nd.setLastName(resultSet.getString("lastName"));
            nd.setCreatedTimestamp(resultSet.getTimestamp("createdTimestamp"));
            return nd;
        }
    }
}
