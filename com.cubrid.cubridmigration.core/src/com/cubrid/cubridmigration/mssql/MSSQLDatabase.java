/*
 * Copyright (C) 2008 Search Solution Corporation.
 * Copyright (C) 2016 CUBRID Corporation.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 * - Redistributions of source code must retain the above copyright notice,
 *   this list of conditions and the following disclaimer.
 *
 * - Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * - Neither the name of the <ORGANIZATION> nor the names of its contributors
 *   may be used to endorse or promote products derived from this software without
 *   specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
 * OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY
 * OF SUCH DAMAGE.
 *
 */
package com.cubrid.cubridmigration.mssql;

import com.cubrid.cubridmigration.core.connection.ConnParameters;
import com.cubrid.cubridmigration.core.connection.IConnHelper;
import com.cubrid.cubridmigration.core.connection.JDBCData;
import com.cubrid.cubridmigration.core.datatype.DBDataTypeHelper;
import com.cubrid.cubridmigration.core.dbtype.DBConstant;
import com.cubrid.cubridmigration.core.dbtype.DatabaseType;
import com.cubrid.cubridmigration.core.sql.SQLHelper;
import com.cubrid.cubridmigration.mssql.export.MSSQLExportHelper;
import com.cubrid.cubridmigration.mssql.meta.MSSQLSchemaFetcher;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.Properties;
import org.apache.commons.lang.StringUtils;

/**
 * MSSQL Database Description
 *
 * @author Kevin Cao
 * @version 1.0 - 2012-2-2 created by Kevin Cao
 */
public class MSSQLDatabase extends DatabaseType {

    public MSSQLDatabase() {
        super(
                DBConstant.DBTYPE_MSSQL,
                DBConstant.DB_NAMES[DBConstant.DBTYPE_MSSQL],
                new String[] {DBConstant.JDBC_CLASS_MSSQL, DBConstant.JDBC_CLASS_MSSQL_JTDS},
                DBConstant.DEF_PORT_MSSQL,
                new MSSQLSchemaFetcher(),
                new MSSQLExportHelper(),
                new ConnHelper(),
                true);
    }

    /**
     * Retrieves the databases SQL helper
     *
     * @param version Database version
     * @return SQLHelper
     */
    public SQLHelper getSQLHelper(String version) {
        return MSSQLSQLHelper.getInstance(version);
    }

    /** @author Administrator */
    private static class ConnHelper implements IConnHelper {
        /**
         * return the jdbc url to connect the database
         *
         * @param conParam ConnParameters
         * @return String
         */
        public String makeUrl(ConnParameters conParam) {
            final JDBCData jdbcData =
                    conParam.getDatabaseType().getJDBCData(conParam.getDriverFileName());
            if (jdbcData == null) {
                return "";
            }
            String cubridJdbcURLPattern;
            if (DBConstant.JDBC_CLASS_MSSQL_JTDS.equals(jdbcData.getDriverClassName())) {
                cubridJdbcURLPattern = "jdbc:jtds:sqlserver://%s:%s/%s";
            } else {
                cubridJdbcURLPattern = "jdbc:sqlserver://%s:%s;databaseName=%s";
            }
            String url =
                    String.format(
                            cubridJdbcURLPattern,
                            conParam.getHost(),
                            conParam.getPort(),
                            conParam.getDbName());
            return url;
        }

        /**
         * get a Connection
         *
         * @param conParam ConnParameters
         * @return Connection
         * @throws SQLException e
         */
        public Connection createConnection(ConnParameters conParam) throws SQLException {
            try {
                Driver driver = conParam.getDriver();
                if (driver == null) {
                    throw new RuntimeException("JDBC driver can't be null.");
                }
                // can't get connection throw DriverManger
                Properties props = new Properties();
                props.put("user", conParam.getConUser());
                props.put("password", conParam.getConPassword());
                Connection conn;
                if (StringUtils.isBlank(conParam.getUserJDBCURL())) {
                    conn = driver.connect(makeUrl(conParam), props);
                } else {
                    conn = driver.connect(conParam.getUserJDBCURL(), props);
                }
                if (conn == null) {
                    throw new SQLException("Can not connect database server.");
                }
                conn.setAutoCommit(false);
                return conn;
            } catch (SQLException e) {
                throw e;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Retrieves the databases data type helper
     *
     * @param version Database version
     * @return DBDataTypeHelper
     */
    public DBDataTypeHelper getDataTypeHelper(String version) {
        return MSSQLDataTypeHelper.getInstance(version);
    };

    /**
     * The database type is supporting multi-schema.
     *
     * @return true if supporting.
     */
    public boolean isSupportMultiSchema() {
        return true;
    }
}
