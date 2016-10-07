package com.crossover.task.articles.config;

import java.net.InetSocketAddress;
import java.util.Collections;
import java.util.List;

import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.core.CassandraTemplate;

import com.crossover.task.articles.model.CassArticle;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

/**
 * @since 25/09/2016
 */
@Configuration
public class CassandraConfig {
	private static final Logger log = LoggerFactory.getLogger(CassandraConfig.class);

	@Value("${cassandra.host}")
	private String host;

	@Value("${cassandra.port}")
	private int port;

	@Value("${cassandra.name}")
	private String keyspace;

	@Value("${cassandra.query.selectkeyspaces}")
	private String selectKeySpaces;
	
	@Value("${cassandra.query.createkeyspace}")
	private String createKeySpace;
	
	@Value("${cassandra.query.usekeyspace}")
	private String useKeySpace;
	
	@Value("${cassandra.query.createteble}")
	private String createTable;
	
	@Value("${cassandra.query.createindex}")
	private String createIndex;
	
	private Session session;

	@Bean
	public CassandraTemplate cassandraTemplate() {
		log.info("cassandraTemplate:: creating template " + host + " : " + port);
		Cluster cluster = Cluster.builder()
				.addContactPointsWithPorts(Collections.singletonList(new InetSocketAddress(host, port))).build();
		session = cluster.connect();
		this.createScemaIfNotExist();
		CassandraTemplate cassandraTemplate = new CassandraTemplate(session);
		return cassandraTemplate;
	}
	
	protected void createScemaIfNotExist() {
		boolean needToCreate = true;
		List<Row> rows = session.execute(selectKeySpaces).all();
		for (Row row : rows) {
			if (keyspace.equals(row.getString(0))) {
				log.info("createScemaIfNotExist:: using Cassandra schema " + keyspace);
				needToCreate = false;
				session.execute(useKeySpace);
				break;
			}
		}
		if (needToCreate) {
			log.info("createScemaIfNotExist:: creating Cassandra schema " + keyspace);
			session.execute(createKeySpace);
			session.execute(useKeySpace);
			String queryCreateTable = String.format(createTable,
					CassArticle.class.getSimpleName().toLowerCase());
			String queryCreateIndex = String.format(createIndex,
					CassArticle.class.getSimpleName().toLowerCase());
			log.info("createKeySpace " + createKeySpace);
			log.info("useKeySpace " + useKeySpace);			
			log.info("queryCreateTable " + queryCreateTable);
			log.info("queryCreateIndex " + queryCreateIndex);
			session.execute(queryCreateTable);
			session.execute(queryCreateIndex);
		}
	}
	
	@PreDestroy
	protected void destroy() {
		if (session != null) {
			session.close();
			session = null;
		}
	}
}