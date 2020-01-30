package com.laplas.orm.base;

import org.hibernate.jpa.boot.internal.EntityManagerFactoryBuilderImpl;
import org.hibernate.jpa.boot.internal.PersistenceUnitInfoDescriptor;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.spi.PersistenceUnitInfo;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractHibernateTest {

    private EntityManagerFactory entityManagerFactory;

    @Test
    void setUp() {
        entityManagerFactory = createEntityManagerFactory();
    }

    protected abstract Class<?>[] getEntitiesClasses();

    private List<String> getEntitiesClassesNames() {
        return Arrays.stream(getEntitiesClasses())
                     .map(Class::getName)
                     .collect(Collectors.toList());
    }

    private String getUnitInfoName() {
        return getClass().getSimpleName();
    }

    private EntityManagerFactory createEntityManagerFactory() {
        PersistenceUnitInfo unitInfo = createPersistenceUnitInfo();
        PersistenceUnitInfoDescriptor unitInfoDescriptor = new PersistenceUnitInfoDescriptor(unitInfo);
        return new EntityManagerFactoryBuilderImpl(unitInfoDescriptor, Collections.emptyMap()).build();
    }

    private PersistenceUnitInfo createPersistenceUnitInfo() {
        HandyPersistenceUnitInfo unitInfo = new HandyPersistenceUnitInfo();
        unitInfo.setPersistenceUnitName(getUnitInfoName());
        unitInfo.addMappingFileNames(getEntitiesClassesNames());
        return unitInfo;
    }
}
