package com.laplas.orm.base.session;

import java.util.Properties;

public abstract class AbstractSecondLevelCacheTest extends AbstractHibernateTest {

    @Override
    protected Properties getDatabaseProperties() {
        Properties properties = super.getDatabaseProperties();
        properties.put("hibernate.cache.use_second_level_cache", "true");
        properties.put("hibernate.cache.region.factory_class", "hazelcast");
        return properties;
    }
}
