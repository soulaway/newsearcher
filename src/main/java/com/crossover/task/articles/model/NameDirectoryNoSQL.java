package com.crossover.task.articles.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.mapping.CassandraType;
import org.springframework.data.cassandra.mapping.Table;

import com.datastax.driver.core.DataType;

/**
 * @since 25/09/2016
 */
@Table
public class NameDirectoryNoSQL extends NameDirectorySolr {
    public NameDirectoryNoSQL() {
    }

    public NameDirectoryNoSQL(NameDirectorySolr from) {
        super(from);
    }

    @Id
    private String objectId;

    @Override
    @CassandraType(type = DataType.Name.INT)
    public Long getId() {
        return super.getId();
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }
}
