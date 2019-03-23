package com.ledc.ims.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.persistence.EntityManagerFactory;
import java.beans.PropertyVetoException;

@Configuration
@EnableJpaRepositories(basePackages ="com.ledc.ims.Repository")
public class JpaConfig {

    @Bean
    public ComboPooledDataSource dataSource() {
        ComboPooledDataSource ds = new ComboPooledDataSource();
        try {
            ds.setDriverClass("com.mysql.cj.jdbc.Driver");
        } catch (PropertyVetoException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        ds.setJdbcUrl("jdbc:mysql://localhost:3306/test?serverTimezone=UTC&useSSL=false&useUnicode=true&characterEncoding=utf8");
        ds.setUser("root");
        ds.setPassword("00000000");
        ds.setInitialPoolSize(10);
        ds.setMinPoolSize(10);
        ds.setMaxPoolSize(100);
        ds.setCheckoutTimeout(30000);
        ds.setMaxIdleTime(30);
        ds.setMaxStatements(200);
        ds.setAcquireIncrement(100);
        ds.setIdleConnectionTestPeriod(30);
        ds.setMaxStatementsPerConnection(100);
        //ds.setTestConnectionOnCheckin(true);
        //ds.setAcquireRetryDelay(1000);
        return ds;
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setDatabasePlatform("org.hibernate.dialect.MySQL5InnoDBDialect");
        //adapter.setDatabase("MYSQL");
        adapter.setGenerateDdl(true);
        adapter.setShowSql(true);
        return adapter;
    }

    @Bean
    //@Autowired
    public EntityManagerFactory entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(jpaVendorAdapter());
        factory.setPackagesToScan("com.ledc.ims.Entity");
        factory.setDataSource(dataSource());
        factory.afterPropertiesSet();
        return factory.getObject();
    }

}
