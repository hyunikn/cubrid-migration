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
package com.cubrid.cubridmigration.cubrid.exception;

/**
 * Thrown to indicate that error occurs in formating CUBRID data type.
 *
 * @author moulinwang
 */
public class FormatCUBRIDDataTypeException extends RuntimeException {
    /** Constructs an UnSupportCUBRIDDataTypeException with no detail message. */
    public FormatCUBRIDDataTypeException() {
        super();
    }

    /**
     * Constructs an UnSupportCUBRIDDataTypeException with the specified detail message.
     *
     * @param message the detail message
     */
    public FormatCUBRIDDataTypeException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception with the specified detail message and cause.
     *
     * <p>Note that the detail message associated with <code>cause</code> is <i>not</i>
     * automatically incorporated in this exception's detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the {@link
     *     Throwable#getMessage()} method).
     * @param cause the cause (which is saved for later retrieval by the {@link
     *     Throwable#getCause()} method). (A <tt>null</tt> value is permitted, and indicates that
     *     the cause is nonexistent or unknown.)
     * @since 1.5
     */
    public FormatCUBRIDDataTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new exception with the specified cause and a detail message of <tt>(cause==null
     * ? null : cause.toString())</tt> (which typically contains the class and detail message of
     * <tt>cause</tt>). This constructor is useful for exceptions that are little more than wrappers
     * for other throwables (for example, {@link java.security.PrivilegedActionException}).
     *
     * @param cause the cause (which is saved for later retrieval by the {@link
     *     Throwable#getCause()} method). (A <tt>null</tt> value is permitted, and indicates that
     *     the cause is nonexistent or unknown.)
     * @since 1.5
     */
    public FormatCUBRIDDataTypeException(Throwable cause) {
        super(cause);
    }

    static final long serialVersionUID = -1242599979055084573L;
}
