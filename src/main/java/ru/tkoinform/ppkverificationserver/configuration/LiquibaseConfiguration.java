package ru.tkoinform.ppkverificationserver.configuration;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class LiquibaseConfiguration {

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String hibernateDdlAuto;

    @Bean
    public LiquibaseProperties liquibaseProperties() {
        return new LiquibaseProperties();
    }

    @Bean
    public SpringLiquibase liquibase(DataSource dataSource) {
        LiquibaseProperties liquibaseProperties = liquibaseProperties();

        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog("classpath:db/changelog.xml");
        //liquibase.setDataSource(dataSource);
        //liquibase.setChangeLog(liquibaseProperties.getChangeLog());

        liquibase.setContexts(liquibaseProperties.getContexts());
        liquibase.setDataSource(getDataSource(liquibaseProperties, dataSource));
        liquibase.setDefaultSchema(liquibaseProperties.getDefaultSchema());
        liquibase.setDropFirst(liquibaseProperties.isDropFirst());
        liquibase.setShouldRun(!"create".equals(hibernateDdlAuto.toLowerCase()));
        liquibase.setLabels(liquibaseProperties.getLabels());
        liquibase.setChangeLogParameters(liquibaseProperties.getParameters());
        return liquibase;
    }

    private DataSource getDataSource(LiquibaseProperties liquibaseProperties, DataSource dataSource) {
        if (liquibaseProperties.getUrl() == null) {
            return dataSource;
        }
        return DataSourceBuilder.create().url(liquibaseProperties.getUrl())
                .username(liquibaseProperties.getUser())
                .password(liquibaseProperties.getPassword()).build();
    }
}
