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
package com.cubrid.cubridmigration.core.engine.executors;

import com.cubrid.cubridmigration.core.engine.ICanDispose;
import java.util.HashMap;
import java.util.Map;

/**
 * Thread safe executor provider by executeor's name.
 *
 * @author Kevin Cao
 */
public class NameExecutorProvider implements ICanDispose {

    private Map<String, IRunnableExecutor> namedExecutors =
            new HashMap<String, IRunnableExecutor>();

    private final Object lockObject = new Object();

    /**
     * @param executorName the executor's name
     * @return the executor who has the name.
     */
    public IRunnableExecutor getExecutorByName(String executorName) {
        synchronized (lockObject) {
            IRunnableExecutor executor = namedExecutors.get(executorName);
            if (executor == null) {
                executor = new SingleQueueExecutor(1, true);
                namedExecutors.put(executorName, executor);
            }
            return executor;
        }
    }

    /** Dispose */
    public void dispose() {
        synchronized (lockObject) {
            for (IRunnableExecutor executor : namedExecutors.values()) {
                executor.dispose();
            }
        }
    }
}
