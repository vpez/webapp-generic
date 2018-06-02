package org.vap.webapp.data.mysql;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

/**
 * @author Vahe Pezeshkian
 * June 02, 2018
 */
@Configuration
@PropertySource(value = {"application.properties"})
public class SqlConfig {

    @Value("${spring.data.jdbc.url}")
    private String url;

    @Value("${spring.data.jdbc.username}")
    private String username;

    @Value("${spring.data.jdbc.password}")
    private String password;

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        return dataSource;
    }


}
