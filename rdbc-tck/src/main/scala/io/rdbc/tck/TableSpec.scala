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

package io.rdbc.tck

import java.util.concurrent.atomic.AtomicInteger

import io.rdbc.sapi.Connection

trait TableSpec {
  this: RdbcSpec =>

  private val counter: AtomicInteger = new AtomicInteger(0)

  protected def withTable[A](c: Connection, columnsDefinition: String)(body: String => A): A = {
    val tableName = s"tbl${counter.incrementAndGet()}"
    c.statement(s"create table $tableName ($columnsDefinition)")
      .noArgs.execute().get
    body(tableName)
  }
}
