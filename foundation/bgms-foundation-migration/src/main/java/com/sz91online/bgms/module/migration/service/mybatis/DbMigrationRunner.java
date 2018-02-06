/**
 * This file is part of mycollab-migration.
 *
 * mycollab-migration is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * mycollab-migration is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with mycollab-migration.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.sz91online.bgms.module.migration.service.mybatis;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("dbMigration")
public class DbMigrationRunner {
    private static final Logger LOG = LoggerFactory.getLogger(DbMigrationRunner.class);

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    public void migrate() {
        try {
            Flyway flyway = new Flyway();
            flyway.setBaselineOnMigrate(true);
            flyway.setDataSource(dataSource);
            flyway.setValidateOnMigrate(false);
            flyway.setLocations("db/migration");
            boolean doMigrateLoop = true;
            while (doMigrateLoop) {
                try {
                    flyway.migrate();
                    doMigrateLoop = false;
                } catch (Exception e) {
                	e.printStackTrace();
                    LOG.info("Error: {}", e.getMessage());
                    doMigrateLoop = false;
                }
            }
        } catch (Exception e) {
            LOG.error("Error while migrate database", e);
            System.exit(-1);
        }
    }
}
