package com.pawan.choure.java.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
@Configuration
@MapperScan("com.pawan.choure.java.mapper")
public class PersistenceConfig {

    @Bean
    public DataSource dataSource() {
        return (DataSource) (new EmbeddedDatabaseBuilder()).setType(EmbeddedDatabaseType.H2).addScript("schema.sql").addScript("data.sql").build();
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(this.dataSource());
        return factoryBean.getObject();
    }
}