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
package com.cubrid.cubridmigration.cubrid.trans.converter;

import com.cubrid.cubridmigration.core.common.DBUtils;
import com.cubrid.cubridmigration.core.datatype.DataTypeInstance;
import com.cubrid.cubridmigration.core.engine.config.MigrationConfiguration;
import com.cubrid.cubridmigration.core.trans.AbstractDataConverter;
import com.cubrid.cubridmigration.cubrid.CUBRIDTimeUtil;
import java.sql.Timestamp;
import java.util.Calendar;

/**
 * TimeStampConverter
 *
 * @author Kevin.Wang
 * @version 1.0 - 2011-11-29 created by Kevin.Wang
 */
public class TimeStampConverter extends AbstractDataConverter {
    /**
     * @param obj Object
     * @param dti DataTypeInstance
     * @param config MigrationConfiguration
     * @return value Object
     */
    public Object convert(Object obj, DataTypeInstance dti, MigrationConfiguration config) {
        int nanoTime = 0;
        Long srcTime = null;
        if (obj instanceof Timestamp) {
            Timestamp timestamp = (Timestamp) obj;
            srcTime = timestamp.getTime();
            nanoTime = timestamp.getNanos();
        } else if (obj instanceof java.util.Date) {
            java.util.Date date = (java.util.Date) obj;
            srcTime = date.getTime();
        } else if (obj instanceof Calendar) {
            Calendar calendar = (Calendar) obj;
            srcTime = calendar.getTime().getTime();
        } else {
            try {
                srcTime = DBUtils.getDateFormat().parse(obj.toString()).getTime();
            } catch (Exception e) {
                e.getMessage();
            }
        }
        if (srcTime == null) {
            try {
                srcTime =
                        CUBRIDTimeUtil.parseTimestamp(
                                obj.toString(), config.getSourceDatabaseTimeZone());
            } catch (Exception e1) {
                throw new RuntimeException(
                        "ERROR: could not convert:" + obj + " to CUBRID type DateTime", e1);
            }
        }
        // calculate the new value
        long newTime = srcTime - config.getDetaRawOffset();
        Timestamp timestamp = new Timestamp(newTime);
        timestamp.setNanos(nanoTime);
        return timestamp;
    }
}
