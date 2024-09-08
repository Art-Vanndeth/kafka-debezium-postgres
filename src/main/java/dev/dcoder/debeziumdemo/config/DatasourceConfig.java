package dev.dcoder.debeziumdemo.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaRepositories(
        basePackages = "dev.dcoder.debeziumdemo.repository",  // Repository package
        entityManagerFactoryRef = "primaryEntityManagerFactory",
        transactionManagerRef = "primaryTransactionManager"
)
@EntityScan(basePackages = "dev.dcoder.debeziumdemo.model")  // Entity package
public class DatasourceConfig {

    // Define primary data source and JPA configuration for 'db1'
    @Bean
    public LocalContainerEntityManagerFactoryBean primaryEntityManagerFactory(DataSource primaryDataSource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(primaryDataSource);
        em.setPackagesToScan("dev.dcoder.debeziumdemo.model");  // Entity package
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "update");  // Adjust as necessary
        em.setJpaPropertyMap(properties);

        return em;
    }

    @Bean
    public JpaTransactionManager primaryTransactionManager(LocalContainerEntityManagerFactoryBean primaryEntityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(primaryEntityManagerFactory.getObject());
        return transactionManager;
    }

    // Define other beans and configurations if necessary
}
