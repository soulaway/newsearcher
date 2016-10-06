package com.crossover.task.articles.service;

import com.crossover.task.articles.model.NameDirectorySolr;
import com.crossover.task.articles.service.NameDirectoryService;

import java.util.ArrayList;
import java.util.List;

/**
 * @since 25/09/2016
 */
// TODO Move to tests
//@Service("NameDirectoryService")
public class NameDirectoryServiceMock implements NameDirectoryService {
    private static List<NameDirectorySolr> rsList = new ArrayList<NameDirectorySolr>();
    private static Long id = 0L;

    @Override
    public List<NameDirectorySolr> getAllRows() {
        return rsList;
    }

    @Override
    public NameDirectorySolr getById(Long id) {
        for (NameDirectorySolr nd : rsList) {
            if (id.equals(nd.getId())) {
                return nd;
            }
        }

        return null;
    }

    @Override
    public Long addNameDirectory(NameDirectorySolr nd) {
        nd.setId(++id);
        rsList.add(nd);
        return id;
    }

    @Override
    public void deleteNameDirectoryById(Long id) {
        NameDirectorySolr found = findRowById(id);
        if (found != null) {
            rsList.remove(found);
        }
    }

    @Override
    public void deleteAll() {
        rsList.clear();
        id = 0L;
    }

    private NameDirectorySolr findRowById(Long id) {
        for (NameDirectorySolr rs : rsList) {
            if (rs.getId().equals(id)) {
                return rs;
            }
        }

        return null;
    }
}
