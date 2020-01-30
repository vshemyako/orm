package com.laplas.orm.base;

import org.springframework.orm.jpa.persistenceunit.MutablePersistenceUnitInfo;

import java.util.List;

public class HandyPersistenceUnitInfo extends MutablePersistenceUnitInfo {

    public void addMappingFileNames(List<String> mappingFileNames) {
        getMappingFileNames().addAll(mappingFileNames);
    }
}
