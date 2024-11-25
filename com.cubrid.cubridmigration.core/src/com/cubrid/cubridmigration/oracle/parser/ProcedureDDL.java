package com.cubrid.cubridmigration.oracle.parser;

import java.util.Objects;

public class ProcedureDDL {

    private final String header;
    private final String body;
    private final boolean hasUnsupportedType;

    public ProcedureDDL(String header, String body, boolean hasUnsupportedType) {
        this.header = header;
        this.body = body;
        this.hasUnsupportedType = hasUnsupportedType;
    }

    public String getHeader() {
        return header;
    }

    public String getBody() {
        return body;
    }

    public boolean hasUnsupportedType() {
        return hasUnsupportedType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProcedureDDL that = (ProcedureDDL) o;
        return Objects.equals(header, that.header) &&
            Objects.equals(body, that.body) &&
            hasUnsupportedType == that.hasUnsupportedType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(header, body, hasUnsupportedType);
    }
}
