package com.crossover.task.articles.service;

import com.crossover.task.articles.model.NameDirectoryNoSQL;
import com.crossover.task.articles.model.NameDirectorySolr;
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
public class NameDirectoryServiceImpl implements NameDirectoryService {
    @Autowired
    private CassandraTemplate cassandraTemplate;

    private AtomicLong nextId = new AtomicLong(1);

    @Override
    public List<NameDirectorySolr> getAllRows() {
        List<NameDirectorySolr> result = new ArrayList<NameDirectorySolr>();
        result.addAll(cassandraTemplate.selectAll(NameDirectoryNoSQL.class));

        for (NameDirectorySolr nameDirectory : result) {
            while (nameDirectory.getId() > nextId.longValue()) {
                nextId.compareAndSet(nextId.longValue(), nameDirectory.getId());
            }
        }

        return result;
    }
    private NameDirectoryNoSQL getNoSqlById(Long id) {
        Select select = QueryBuilder.select().from(NameDirectoryNoSQL.class.getSimpleName());
        select.where(QueryBuilder.eq("id", id));
        NameDirectoryNoSQL result = cassandraTemplate.selectOne(select, NameDirectoryNoSQL.class);
        return result;
    }
    
    @Override
    public NameDirectorySolr getById(Long id) {
        return new NameDirectorySolr(getNoSqlById(id));
    }

    @Override
    public Long addNameDirectory(NameDirectorySolr nd) {
        this.getAllRows();

        NameDirectoryNoSQL item = new NameDirectoryNoSQL(nd);

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
    public void deleteNameDirectoryById(Long id) {
        NameDirectorySolr item = getNoSqlById(id);
        cassandraTemplate.delete(item);
    }

    @Override
    public void deleteAll() {
        cassandraTemplate.deleteAll(NameDirectoryNoSQL.class);
    }

    private Long getNextSequence() {
        return nextId.incrementAndGet();
    }
}
