package restservice.domain;

import javax.sql.DataSource;

import org.springframework.cloud.CloudFactory;
import org.springframework.cloud.Cloud;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("cloud")
public class DataSourceConfig {

  @Bean
  public Cloud cloud() {
    return new CloudFactory().getCloud();
  }

  @Bean
  // @ConfigurationProperties(DataSourceProperties.PREFIX)
  public DataSource dataSource() {
    return cloud().getSingletonServiceConnector(DataSource.class, null);
  }

}
