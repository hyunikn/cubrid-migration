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
package com.cubrid.cubridmigration.core.engine.task.exp;

import com.cubrid.cubridmigration.core.engine.IMigrationEventHandler;
import com.cubrid.cubridmigration.core.engine.MigrationContext;
import com.cubrid.cubridmigration.core.engine.config.MigrationConfiguration;
import com.cubrid.cubridmigration.core.engine.event.MigrationEvent;
import com.cubrid.cubridmigration.core.engine.exception.NormalMigrationException;
import com.cubrid.cubridmigration.core.engine.template.TemplateParserTest;
import com.cubrid.cubridmigration.core.io.SQLParser;
import java.io.IOException;
import java.util.List;
import org.junit.Test;

/**
 * SQLExportTaskTest Description
 *
 * @author Kevin Cao
 * @version 1.0 - 2013-2-4 created by Kevin Cao
 */
public class SQLExportTaskTest {

    private static class SQLExportTaskMock extends SQLExportTask {

        public SQLExportTaskMock(MigrationContext mrManager, String sqlFile) {
            super(mrManager, sqlFile);
        }

        /** Execute export operation, parsing SQL file and call importing SQLs */
        protected void executeExportTask() {
            try {
                final MigrationConfiguration config = mrManager.getConfig();
                SQLParser.ISQLParsingCallback callBack =
                        new SQLParser.ISQLParsingCallback() {

                            public void executeSQLs(List<String> sqlList, long size) {
                                sqlList.clear();
                            }

                            public boolean isCommitNow(int sqlsSize) {
                                return config.getCommitCount() <= sqlsSize;
                            }
                        };
                SQLParser.executeSQLFile(
                        sqlFile, config.getSourceFileEncoding(), config.getCommitCount(), callBack);

            } catch (IOException e) {
                throw new NormalMigrationException(e);
            }
        }
    }

    @Test
    public void testSQLExportTask() throws Exception {
        MigrationConfiguration config = TemplateParserTest.getCubridConfig();
        config.setSourceFileEncoding("utf-8");
        config.setCommitCount(1000);
        config.setSourceType(MigrationConfiguration.SQL);
        // config.addSQLFile("testdb_xe_member_message.sql");
        MigrationContext mrManager =
                MigrationContext.buildContext(
                        config,
                        new IMigrationEventHandler() {

                            public void handleEvent(MigrationEvent event) {}

                            public void dispose() {}
                        });
        SQLExportTaskMock task = new SQLExportTaskMock(mrManager, "createdb/all_cubrid.sql");
        task.executeExportTask();
        mrManager.dispose(false);
    }
}
