package com.crossover.task.articles.model;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @since 25/09/2016
 */
public class ArticleMapper implements RowMapper<SolrArticle> {
    @Override
    public SolrArticle mapRow(ResultSet rs, int line) throws SQLException {
    	ResultSetExtractor<SolrArticle> extractor = new ResultSetExtractor<SolrArticle>(){
			@Override
			public SolrArticle extractData(ResultSet resultSet) throws SQLException, DataAccessException {
	            SolrArticle a = new SolrArticle();
	            a.setId(resultSet.getLong("id"));
	            a.setFirstName(resultSet.getString("firstName"));
	            a.setLastName(resultSet.getString("lastName"));
	            a.setCreatedTimestamp(resultSet.getTimestamp("createdTimestamp"));
	            return a;
			}
    	};
        return extractor.extractData(rs);
    }
}
