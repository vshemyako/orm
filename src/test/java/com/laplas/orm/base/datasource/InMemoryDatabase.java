package com.laplas.orm.base.datasource;

import org.hibernate.dialect.HSQLDialect;
import org.hsqldb.jdbc.JDBCDataSource;

import javax.sql.DataSource;
import java.util.Properties;

public class InMemoryDatabase implements AbstractDatabase {

    @Override
    public Properties getDatabaseProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", getDialect());
        properties.put("hibernate.connection.datasource", getDataSource());
        return properties;
    }

    private String getDialect() {
        return HSQLDialect.class.getName();
    }

    private DataSource getDataSource() {
        JDBCDataSource dataSource = new JDBCDataSource();
        dataSource.setUrl(url());
        dataSource.setUser(username());
        dataSource.setPassword(password());
        return dataSource;
    }

    private String url() {
        return "jdbc:hsqldb:mem:test";
    }

    private String username() {
        return "sa";
    }

    private String password() {
        return "";
    }
}
