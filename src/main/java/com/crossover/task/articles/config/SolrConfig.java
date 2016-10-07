package com.crossover.task.articles.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.server.support.HttpSolrServerFactoryBean;

/**
 * @since 25/09/2016
 */
@Configuration
public class SolrConfig {
	@Value("${solr.host}")
	private String solrHost;

	@Value("${solr.port}")
	private int solrPort;

	@Value("${solr.core.name}")
	private String solrCoreName;

	private static final Logger log = LoggerFactory.getLogger(SolrConfig.class);
	
    @Bean
    public SolrTemplate solrTemplate() throws Exception {
        return new SolrTemplate(solrServerFactoryBean().getObject());
    }
    
	@Bean
	public HttpSolrServerFactoryBean solrServerFactoryBean() {
		HttpSolrServerFactoryBean factory = new HttpSolrServerFactoryBean();
		String url = String.format("http://%s:%d/solr/%s/", solrHost, solrPort, solrCoreName);
		log.info("solrServerFactoryBean created on " + url);
		factory.setUrl(url);
		return factory;
	}
}
