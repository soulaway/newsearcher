package com.crossover.task.articles.service;

import java.util.List;

import com.crossover.task.articles.model.NameDirectorySolr;

/**
 * @since 25/09/2016
 */
public interface NameDirectoryService {

    List<NameDirectorySolr> getAllRows();

    NameDirectorySolr getById(Long id);

    Long addNameDirectory(NameDirectorySolr nd);

    void deleteNameDirectoryById(Long id);

    void deleteAll();
}
