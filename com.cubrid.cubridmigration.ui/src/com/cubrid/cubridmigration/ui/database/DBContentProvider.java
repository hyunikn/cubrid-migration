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
package com.cubrid.cubridmigration.ui.database;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * DBContentProvider
 *
 * @author JessieHuang
 * @version 1.0 - 2009-12-24 created by JessieHuang
 */
public class DBContentProvider implements IStructuredContentProvider {
    Set<Integer> supportedDatabases;

    public DBContentProvider(Set<Integer> supportedDatabases) {
        super();
        this.supportedDatabases = supportedDatabases;
    }

    /**
     * getElements
     *
     * @param inputElement Object
     * @return Object[]
     */
    @SuppressWarnings("unchecked")
    public Object[] getElements(Object inputElement) {
        if (inputElement instanceof ArrayList) {
            List<DatabaseConnectionInfo> list = (ArrayList<DatabaseConnectionInfo>) inputElement;
            List<DatabaseConnectionInfo> list2 = new ArrayList<DatabaseConnectionInfo>(list.size());
            for (DatabaseConnectionInfo info : list) {
                if (supportedDatabases.contains(info.getDatabaseTypeID())) {
                    list2.add(info);
                }
            }

            return list2.toArray();
        } else {
            return new Object[0];
        }
    }

    /** dispose */
    public void dispose() {
        // do nothing
    }

    /**
     * inputChanged
     *
     * @param viewer Viewer
     * @param oldInput Object
     * @param newInput Object
     */
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        // do nothing
    }
}
