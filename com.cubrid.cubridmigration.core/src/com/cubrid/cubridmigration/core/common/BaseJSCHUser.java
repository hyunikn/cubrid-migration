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
package com.cubrid.cubridmigration.core.common;

import com.jcraft.jsch.UserInfo;

/** @author Kevin Cao */
public class BaseJSCHUser implements UserInfo {

    /**
     * @see com.jcraft.jsch.UserInfo#getPassphrase()
     * @return null;
     */
    public String getPassphrase() {
        return null;
    }

    /**
     * @see com.jcraft.jsch.UserInfo#getPassword()
     * @return null;
     */
    public String getPassword() {
        return null;
    }

    /**
     * @see com.jcraft.jsch.UserInfo#promptPassphrase(java.lang.String)
     * @param arg0 String
     * @return false
     */
    public boolean promptPassphrase(String arg0) {
        return false;
    }

    /**
     * @see com.jcraft.jsch.UserInfo#promptPassword(java.lang.String)
     * @param arg0 String
     * @return false
     */
    public boolean promptPassword(String arg0) {
        return false;
    }

    /**
     * @see com.jcraft.jsch.UserInfo#promptYesNo(java.lang.String)
     * @param arg0 String
     * @return false
     */
    public boolean promptYesNo(String arg0) {
        return false;
    }

    /**
     * @see com.jcraft.jsch.UserInfo#showMessage(java.lang.String)
     * @param arg0 String
     */
    public void showMessage(String arg0) {}
}
