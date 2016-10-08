package com.crossover.task.articles.controller;

import com.crossover.task.articles.model.SolrArticle;
import com.crossover.task.articles.service.SolrArticlesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Query;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.SimpleStringCriteria;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @since 25/09/2016
 */
@Controller
@RequestMapping("/articles")
public class ArticlesController {
    @Autowired
    private SolrArticlesService solrArticlesService;

    @Autowired
    private SolrTemplate solrTemplate;

    @RequestMapping("/getList.json")
    @ResponseBody
    public List<SolrArticle> getList() {
        return solrArticlesService.getAll();
    }

    @RequestMapping(value = "/search.json", method = RequestMethod.POST)
    @ResponseBody
    public List<SolrArticle> doSearch(@RequestBody String text) {
        if (StringUtils.isEmpty(text) || solrTemplate == null) {
            return this.getList();
        }

        String queryString = String.format("title:*%s* OR body:*%s*", text, text);
        Query query = new SimpleQuery(new SimpleStringCriteria(queryString));
        return solrTemplate.queryForPage(query, SolrArticle.class).getContent();
    }

    @RequestMapping(value = "/autocomplete.json", method = RequestMethod.POST)
    @ResponseBody
    public List<String> doTypeahead(@RequestBody String text) {
        if (StringUtils.isEmpty(text) || solrTemplate == null) {
            return Collections.emptyList();
        }

        String queryString = String.format("title:*%s*", text);
        Query query = new SimpleQuery(new SimpleStringCriteria(queryString));
        List<SolrArticle> content = solrTemplate.queryForPage(query, SolrArticle.class).getContent();
        List<String> result = new ArrayList<String>();

        for (SolrArticle nd : content) {
            result.add(nd.getTitle());
        }
        return result;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public void add(@RequestBody SolrArticle nd) {
        Long id = solrArticlesService.add(nd);
        SolrArticle saved = solrArticlesService.getById(id);

        if (this.isUseSolr()) {
            solrTemplate.saveBean(saved);
            solrTemplate.commit();
        }
    }

    @RequestMapping(value = "/remove/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void remove(@PathVariable("id") Long id) {
        solrArticlesService.delete(id);

        if (this.isUseSolr()) {
            solrTemplate.deleteById(String.valueOf(id));
            solrTemplate.commit();
        }
    }

    @RequestMapping(value = "/removeAll", method = RequestMethod.DELETE)
    @ResponseBody
    public void removeAll() {
        solrArticlesService.deleteAll();

        if (this.isUseSolr()) {
            solrTemplate.delete(new SimpleQuery(new SimpleStringCriteria("*:*")));
            solrTemplate.commit();
        }
    }
    
    // Layouts may separate UI for company and user
    
    @RequestMapping("/layout")
    public String getPartialPage() {
        if (this.isUseSolr()) {
            return "articles/layout_search";
        }
        return "articles/layout";
    }

    protected boolean isUseSolr() {
        return solrTemplate != null;
    }
}
