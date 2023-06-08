package ee.erik.migrationrunner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties
@ConfigurationPropertiesScan
public class MigrationRunnerApplication {

    private MigrationProperties properties;

    @Autowired
    public void setProperties(MigrationProperties properties) {
        this.properties = properties;
    }

    @Bean(initMethod = "migrate")
    public MigrationInitializer migrationInitializer() {
        return new MigrationInitializer(properties);
    }

    public static void main(String[] args) {
        SpringApplication.run(MigrationRunnerApplication.class, args);
    }

}
