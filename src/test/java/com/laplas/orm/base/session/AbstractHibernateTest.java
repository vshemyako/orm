package com.laplas.orm.base.session;

import com.laplas.orm.base.datasource.InMemoryDatabase;
import org.hibernate.jpa.boot.internal.EntityManagerFactoryBuilderImpl;
import org.hibernate.jpa.boot.internal.PersistenceUnitInfoDescriptor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.spi.PersistenceUnitInfo;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class AbstractHibernateTest {

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    @BeforeEach
    void setUp() {
        entityManagerFactory = createEntityManagerFactory();
        entityManager = createEntityManager();
    }

    @AfterEach
    void tearDown() {
        if (entityManager != null) {
            entityManager.close();
        }
        if (entityManagerFactory != null) {
            entityManagerFactory.close();
        }
    }

    protected abstract Class<?>[] getEntitiesClasses();

    protected <T> T execute(Function<EntityManager, T> function) {
        T result;
        EntityTransaction txn = null;
        try {
            txn = entityManager.getTransaction();
            txn.begin();
            result = function.apply(entityManager);
            txn.commit();
        } catch (Throwable t) {
            if (txn != null && txn.isActive()) {
                txn.rollback();
            }
            throw t;
        }
        return result;
    }

    protected Properties getDatabaseProperties() {
        return new InMemoryDatabase().getDatabaseProperties();
    }

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
        unitInfo.setProperties(getDatabaseProperties());
        return unitInfo;
    }

    private EntityManager createEntityManager() {
        return this.entityManagerFactory.createEntityManager();
    }

    protected EntityManager getEntityManager() {
        return this.entityManager;
    }
}
