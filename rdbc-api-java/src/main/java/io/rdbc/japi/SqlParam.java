/*
 * Copyright 2016 rdbc contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.rdbc.japi;

/**
 * Represents an SQL-typed statement parameter.
 * <p>
 * This wrapper can be used to force a given SQL type for a passed statement
 * parameter. For example, `new SqlParam("example", StandardSqlTypes.CLOB)` will
 * result in a parameter being passed as a CLOB.
 */
public final class SqlParam {

    private final Object value;
    private final SqlType sqlType;

    public SqlParam(Object value, SqlType sqlType) {
        this.value = value;
        this.sqlType = sqlType;
    }

    public Object getValue() {
        return value;
    }

    public SqlType getSqlType() {
        return sqlType;
    }

    @Override
    public String toString() {
        return "SqlParam{" +
                "type=" + sqlType +
                ", value=" + value +
                '}';
    }
}
