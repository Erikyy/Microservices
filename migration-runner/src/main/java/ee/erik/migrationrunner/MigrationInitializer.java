package ee.erik.migrationrunner;

import lombok.AllArgsConstructor;
import org.flywaydb.core.Flyway;

@AllArgsConstructor
public class MigrationInitializer {
    private MigrationProperties properties;

    public void migrate() {
        String baseMigrationDir = "db/migration";

        for (var db : properties.getDatasources().entrySet()) {
            var name = db.getKey();
            var config = db.getValue();

            Flyway flyway = Flyway.configure()
                    .locations(baseMigrationDir + "/" + name)
                    .baselineOnMigrate(false)
                    .dataSource(config.toDataSource())
                    //.schemas(config.getDatabase())
                    .load();

            flyway.migrate();
        }
    }
}
