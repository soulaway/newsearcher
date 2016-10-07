package com.crossover.task.articles.service;

import com.crossover.task.articles.model.CassArticle;
import com.crossover.task.articles.model.SolrArticle;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @since 25/09/2016
 */
public class SolrArticlesServiceImpl implements SolrArticlesService {
    @Autowired
    private CassandraTemplate cassandraTemplate;

    private AtomicLong nextId = new AtomicLong(1);

    @Override
    public List<SolrArticle> getAll() {
        List<SolrArticle> result = new ArrayList<SolrArticle>();
        result.addAll(cassandraTemplate.selectAll(CassArticle.class));

        for (SolrArticle a : result) {
            while (a.getId() > nextId.longValue()) {
                nextId.compareAndSet(nextId.longValue(), a.getId());
            }
        }

        return result;
    }
    
    private CassArticle getCassArticleById(Long id) {
        Select select = QueryBuilder.select().from(CassArticle.class.getSimpleName());
        select.where(QueryBuilder.eq("id", id));
        CassArticle result = cassandraTemplate.selectOne(select, CassArticle.class);
        return result;
    }
    
    @Override
    public SolrArticle getById(Long id) {
        return new SolrArticle(getCassArticleById(id));
    }

    @Override
    public Long add(SolrArticle nd) {
        this.getAll();

        CassArticle item = new CassArticle(nd);

        Long id = this.getNextSequence();
        Date createdTimestamp = new Date();

        item.setId(id);
        item.setObjectId(UUID.randomUUID().toString());
        item.setCreatedTimestamp(createdTimestamp);

        nd.setId(id);
        nd.setCreatedTimestamp(createdTimestamp);

        cassandraTemplate.insert(item);

        return id;
    }

    @Override
    public void delete(Long id) {
        SolrArticle item = getCassArticleById(id);
        cassandraTemplate.delete(item);
    }

    @Override
    public void deleteAll() {
        cassandraTemplate.deleteAll(CassArticle.class);
    }

    private Long getNextSequence() {
        return nextId.incrementAndGet();
    }
}
