package com.crossover.task.articles.service;

import com.crossover.task.articles.model.SolrArticle;
import com.crossover.task.articles.service.SolrArticlesService;

import java.util.ArrayList;
import java.util.List;

/**
 * @since 25/09/2016
 */
// TODO Move to tests
//@Service("SolrArticlesService")
public class SolrArticlesServiceMock implements SolrArticlesService {
    private static List<SolrArticle> rsList = new ArrayList<SolrArticle>();
    private static Long id = 0L;

    @Override
    public List<SolrArticle> getAll() {
        return rsList;
    }

    @Override
    public SolrArticle getById(Long id) {
        for (SolrArticle nd : rsList) {
            if (id.equals(nd.getId())) {
                return nd;
            }
        }

        return null;
    }

    @Override
    public Long add(SolrArticle nd) {
        nd.setId(++id);
        rsList.add(nd);
        return id;
    }

    @Override
    public void delete(Long id) {
        SolrArticle found = findRowById(id);
        if (found != null) {
            rsList.remove(found);
        }
    }

    @Override
    public void deleteAll() {
        rsList.clear();
        id = 0L;
    }

    private SolrArticle findRowById(Long id) {
        for (SolrArticle rs : rsList) {
            if (rs.getId().equals(id)) {
                return rs;
            }
        }

        return null;
    }
}
