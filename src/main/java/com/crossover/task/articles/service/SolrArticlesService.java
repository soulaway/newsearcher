package com.crossover.task.articles.service;

import java.util.List;

import com.crossover.task.articles.model.SolrArticle;

/**
 * @since 25/09/2016
 */
public interface SolrArticlesService {

	SolrArticle getById(Long id);
    
	List<SolrArticle> getAll();

    Long add(SolrArticle nd);

    void delete(Long id);

    void deleteAll();
}
