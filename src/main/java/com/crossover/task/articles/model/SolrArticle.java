package com.crossover.task.articles.model;

import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;

import java.util.Date;

/**
 * @since 25/09/2016
 */
@SolrDocument(solrCoreName = "articles")
public class SolrArticle {
    public SolrArticle() {
    }

    public SolrArticle(SolrArticle from) {
        this.id = from.id;
        this.title = from.title;
        this.body = from.body;
        this.createdTimestamp = from.createdTimestamp;
    }

    @Indexed
    private Long id;

    @Indexed
    private String title;

    @Indexed
    private String body;

    private Date createdTimestamp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Date getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(Date createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }
}
