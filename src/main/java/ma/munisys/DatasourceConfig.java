package ma.munisys;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatasourceConfig {
    @Bean
    public DataSource datasource() {
        return DataSourceBuilder.create()
          .driverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver")
          //.url("jdbc:sqlserver://localhost;databaseName=etatprojet")
          //.username("sa")
          //.password("reallyStrongPwd123")
          .url("jdbc:sqlserver://tripoli;databaseName=MGOUVDEV")
          .username("munitime")
          .password("123456")
          
          .build(); 
    }
}