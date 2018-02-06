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
package org.flywaydb.core.internal.database.sybasease;

import org.flywaydb.core.api.configuration.FlywayConfiguration;
import org.flywaydb.core.api.logging.Log;
import org.flywaydb.core.api.logging.LogFactory;
import org.flywaydb.core.internal.database.Connection;
import org.flywaydb.core.internal.database.Schema;

import java.sql.SQLException;

/**
 * Sybase ASE Connection.
 */
public class SybaseASEConnection extends Connection<SybaseASEDatabase> {
    private static final Log LOG = LogFactory.getLog(SybaseASEConnection.class);

    /**
     * Whether the warning message has already been printed.
     */
    private static boolean schemaMessagePrinted;

    SybaseASEConnection(FlywayConfiguration configuration, SybaseASEDatabase database, java.sql.Connection connection, int nullType



    ) {
        super(configuration, database, connection, nullType



        );
    }


    @Override
    public Schema getSchema(String name) {
        //Sybase does not support schemas, nor changing users on the fly. Always return the same dummy schema.
        return new SybaseASESchema(jdbcTemplate, database, "dbo");
    }

    @Override
    protected String doGetCurrentSchemaName() throws SQLException {
        return "dbo";
    }

    @Override
    public void doChangeCurrentSchemaTo(String schema) throws SQLException {
        if (!schemaMessagePrinted) {
            LOG.info("Sybase ASE does not support setting the schema for the current session. Default schema NOT changed to " + schema);
            schemaMessagePrinted = true;
        }
    }
}