package com.crossover.task.articles.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

/**
 * @since 25/09/2016
 */
public class UserMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int line) throws SQLException {
    	ResultSetExtractor<User> extractor = new ResultSetExtractor<User>(){
			@Override
			public User extractData(ResultSet resultSet) throws SQLException, DataAccessException {
				User u = new User();
	            u.setUser(resultSet.getString("user"));
	            u.setPass(resultSet.getString("pass"));
	            return u;
			}
    	};
        return extractor.extractData(rs);
    }
}
