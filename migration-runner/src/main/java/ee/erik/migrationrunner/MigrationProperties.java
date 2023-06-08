package ee.erik.migrationrunner;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@Getter
@Setter
@ConfigurationProperties(prefix = "migration")
public class MigrationProperties {
    private Map<String, Datasource> datasources;

    @Getter
    @Setter
    public static class Datasource {
        private String host;
        private String username;
        private String password;
        private String database;
        private String driver;


        public HikariDataSource toDataSource() {
            HikariConfig config = new HikariConfig();
            config.setDriverClassName(this.driver);
            config.setJdbcUrl(this.host);
            config.setUsername(this.username);
            config.setPassword(this.password);
            config.setAutoCommit(false);
            return new HikariDataSource(config);
        }
    }
}
