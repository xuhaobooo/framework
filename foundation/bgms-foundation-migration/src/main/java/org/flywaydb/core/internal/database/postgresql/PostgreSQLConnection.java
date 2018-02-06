/*
 * Copyright 2010-2017 Boxfuse GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.flywaydb.core.internal.database.postgresql;

import org.flywaydb.core.api.configuration.FlywayConfiguration;
import org.flywaydb.core.internal.database.Connection;
import org.flywaydb.core.internal.database.Schema;
import org.flywaydb.core.internal.database.Table;
import org.flywaydb.core.internal.exception.FlywaySqlException;
import org.flywaydb.core.internal.util.StringUtils;

import java.sql.SQLException;
import java.util.concurrent.Callable;

/**
 * PostgreSQL connection.
 */
public class PostgreSQLConnection extends Connection<PostgreSQLDatabase> {
    PostgreSQLConnection(FlywayConfiguration configuration, PostgreSQLDatabase database, java.sql.Connection connection, int nullType



    ) {
        super(configuration, database, connection, nullType



        );
    }

    @Override
    public Schema getOriginalSchema() {
        if (originalSchema == null) {
            return null;
        }

        return getSchema(getFirstSchemaFromSearchPath(this.originalSchema));
    }

    static String getFirstSchemaFromSearchPath(String searchPath) {
        String result = searchPath.replace("\"$user\"", "").trim();
        if (result.startsWith(",")) {
            result = result.substring(1);
        }
        if (result.contains(",")) {
            result = result.substring(0, result.indexOf(","));
        }
        result = result.trim();
        // Unquote if necessary
        if (result.startsWith("\"") && result.endsWith("\"") && !result.endsWith("\\\"") && (result.length() > 1)) {
            result = result.substring(1, result.length() - 1);
        }
        return result;
    }

    @Override
    protected String doGetCurrentSchemaName() throws SQLException {
        return jdbcTemplate.queryForString("SHOW search_path");
    }

    @Override
    public void changeCurrentSchemaTo(Schema schema) {
        try {
            // First reset the role in case a migration or callback changed it
            jdbcTemplate.execute("RESET ROLE");

            if (schema.getName().equals(originalSchema) || originalSchema.startsWith(schema.getName() + ",") || !schema.exists()) {
                return;
            }

            if (StringUtils.hasText(originalSchema)) {
                doChangeCurrentSchemaTo(schema.toString() + "," + originalSchema);
            } else {
                doChangeCurrentSchemaTo(schema.toString());
            }
        } catch (SQLException e) {
            throw new FlywaySqlException("Error setting current schema to " + schema, e);
        }
    }

    @Override
    public void doChangeCurrentSchemaTo(String schema) throws SQLException {
        if (!StringUtils.hasLength(schema)) {
            jdbcTemplate.execute("SELECT set_config('search_path', '', false)");
            return;
        }
        jdbcTemplate.execute("SET search_path = " + schema);
    }

    @Override
    public Schema getSchema(String name) {
        return new PostgreSQLSchema(jdbcTemplate, database, name);
    }

    @Override
    public <T> T lock(Table table, Callable<T> callable) {
        return new PostgreSQLAdvisoryLockTemplate(jdbcTemplate, table.toString().hashCode()).execute(callable);
    }
}