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

package io.rdbc.sapi

import java.time._
import java.util.UUID

import scala.reflect.ClassTag

/** Represents a row of a result returned by a database engine.
  *
  * This class defines a set of methods that can be used to get values from the
  * row either by a column name or by a column index. Each method has a version
  * returning an [[scala.Option Option]] to allow null-safe handling of SQL
  * `null` values.
  *
  * @groupname generic Generic getters
  * @groupprio generic 10
  * @groupdesc generic Methods in this group can be used for fetching values
  *            of types not supported by rdbc API out of the box.
  *
  * @groupname unb Unbounded number getters
  * @groupprio unb 20
  *
  * @groupname bool Bool getters
  * @groupprio bool 30
  *
  * @groupname binary Binary getters
  * @groupprio binary 40
  *
  * @groupname char Char getters
  * @groupprio char 50
  *
  * @groupname float Floating point number getters
  * @groupprio float 60
  *
  * @groupname int Integral number getters
  * @groupprio int 70
  *
  * @groupname date Date/time getters
  * @groupprio date 80
  *
  * @groupname string String getters
  * @groupprio string 90
  *
  * @groupname uuid UUID getters
  * @groupprio uuid 100
  *
  * @define exceptionsNamed
  *  Throws:
  *  - [[io.rdbc.sapi.exceptions.ConversionException ConversionException]]
  *  when database value could not be converted to the desired type
  *   - [[io.rdbc.sapi.exceptions.MissingColumnException MissingColumnException]]
  *  when requested column is not present in the row
  *  @define exceptionsIdx
  *  Throws:
  *  - [[io.rdbc.sapi.exceptions.ConversionException ConversionException]]
  *  when database value could not be converted to the desired type
  *   - [[io.rdbc.sapi.exceptions.ColumnIndexOutOfBoundsException ColumnIndexOutOfBoundsException]]
  *  when requested column index is out of range
  * @define nullSafetyNote
  *  For SQL `null` values, [[io.rdbc.sapi.exceptions.ConversionException ConversionException]] is thrown.
  *  For null-safety consider using corresponding `*Opt` method.
  * @define returningNone
  *  For SQL `null` values [[scala.None None]] is returned.
  * @define boolValues
  *  - A single 'T', 'Y' or '1' character values or `1` numeric value are
  *  considered `true`.
  *  - A single 'F', 'N' or '0' character values or `0` numeric value are
  *  considered `false`.
  * @define instantZoneNote
  * The `zoneId` parameter is ignored in case the driver can create an instant
  * from the stored value without an user-provided time zone. This can happen
  * if the database stores TIMESTAMP values as instants or for columns with
  * TIMESTAMP WITH TIME ZONE types.
  */
trait Row {

  /** Returns an object of type `A` from column with a given index.
    *
    * $nullSafetyNote
    *
    * $exceptionsIdx
    *
    * @group generic
    * @usecase def col[A](idx: Int): A
    *  @inheritdoc
    */
  def col[A: ClassTag](idx: Int): A

  /** Returns an object of type `A` from column with a given index.
    *
    * $returningNone
    *
    * $exceptionsIdx
    *
    * @group generic
    * @usecase def colOpt[A](idx: Int): Option[A]
    *  @inheritdoc
    */
  def colOpt[A: ClassTag](idx: Int): Option[A]

  /** Returns an object of type `A` from column with a given name.
    *
    * $nullSafetyNote
    *
    * $exceptionsNamed
    *
    * @group generic
    * @usecase def col[A](name: String): A
    *  @inheritdoc
    */
  def col[A: ClassTag](name: String): A

  /** Returns an object of type `A` from column with a given name.
    *
    * $returningNone
    *
    * $exceptionsNamed
    *
    * @group generic
    * @usecase def colOpt[A](name: String): Option[A]
    *  @inheritdoc
    */
  def colOpt[A: ClassTag](name: String): Option[A]

  /** Returns a `String` from column with a given name.
    *
    * $nullSafetyNote
    *
    * $exceptionsNamed
    *
    * @group string
    */
  def str(name: String): String

  /** Returns a `String` from column with a given name.
    *
    * $returningNone
    *
    * $exceptionsNamed
    *
    * @group string
    */
  def strOpt(name: String): Option[String]

  /** Returns a `String` from column with a given index.
    *
    * $nullSafetyNote
    *
    * $exceptionsIdx
    *
    * @group string
    */
  def str(idx: Int): String

  /** Returns a `String` from column with a given index.
    *
    * $returningNone
    *
    * $exceptionsIdx
    *
    * @group string
    */
  def strOpt(idx: Int): Option[String]

  /** Returns a boolean value from column with a given name.
    *
    * $boolValues
    *
    * $nullSafetyNote
    *
    * $exceptionsNamed
    *
    * @group bool
    */
  def bool(name: String): Boolean

  /** Returns a boolean value from column with a given name.
    *
    * $boolValues
    *
    * $returningNone
    *
    * $exceptionsNamed
    *
    * @group bool
    */
  def boolOpt(name: String): Option[Boolean]

  /** Returns a boolean value from column with a given index.
    *
    * $boolValues
    *
    * $nullSafetyNote
    *
    * $exceptionsIdx
    *
    * @group bool
    */
  def bool(idx: Int): Boolean

  /** Returns a boolean value from column with a given index.
    *
    * $boolValues
    *
    * $returningNone
    *
    * $exceptionsIdx
    *
    * @group bool
    */
  def boolOpt(idx: Int): Option[Boolean]

  /** Returns a character from column with a given name.
    *
    * Varchar types with a single character are convertible to a `Char`.
    *
    * $nullSafetyNote
    *
    * $exceptionsNamed
    *
    * @group char
    */
  def char(name: String): Char

  /** Returns a character from column with a given name.
    *
    * Varchar types with a single character are convertible to a `Char`.
    *
    * $returningNone
    *
    * $exceptionsNamed
    *
    * @group char
    */
  def charOpt(name: String): Option[Char]

  /** Returns a character from column with a given index.
    *
    * Varchar types with a single character are convertible to a `Char`.
    *
    * $nullSafetyNote
    *
    * $exceptionsIdx
    *
    * @group char
    */
  def char(idx: Int): Char

  /** Returns a character from column with a given index.
    *
    * Varchar types with a single character are convertible to a `Char`.
    *
    * $returningNone
    *
    * $exceptionsIdx
    *
    * @group char
    */
  def charOpt(idx: Int): Option[Char]

  /** Returns a `Short` from column with a given name.
    *
    * All numeric types can be converted to `Short`, but some conversions may
    * involve rounding or truncation.
    *
    * $nullSafetyNote
    *
    * $exceptionsNamed
    *
    * @group int
    */
  def short(name: String): Short

  /** Returns a `Short` from column with a given name.
    *
    * All numeric types can be converted to `Short`, but some conversions may
    * involve rounding or truncation.
    *
    * $returningNone
    *
    * $exceptionsNamed
    *
    * @group int
    */
  def shortOpt(name: String): Option[Short]

  /** Returns a `Short` from column with a given index.
    *
    * All numeric types can be converted to `Short`, but some conversions may
    * involve rounding or truncation.
    *
    * $nullSafetyNote
    *
    * $exceptionsIdx
    *
    * @group int
    */
  def short(idx: Int): Short

  /** Returns a `Short` from column with a given index.
    *
    * All numeric types can be converted to `Short`, but some conversions may
    * involve rounding or truncation.
    *
    * $returningNone
    *
    * $exceptionsIdx
    *
    * @group int
    */
  def shortOpt(idx: Int): Option[Short]

  /** Returns an `Int` from column with a given name.
    *
    * All numeric types can be converted to `Int`, but some conversions may
    * involve rounding or truncation.
    *
    * $nullSafetyNote
    *
    * $exceptionsNamed
    *
    * @group int
    */
  def int(name: String): Int

  /** Returns an `Int` from column with a given name.
    *
    * All numeric types can be converted to `Int`, but some conversions may
    * involve rounding or truncation.
    *
    * $returningNone
    *
    * $exceptionsNamed
    *
    * @group int
    */
  def intOpt(name: String): Option[Int]

  /** Returns an `Int` from column with a given index.
    *
    * All numeric types can be converted to `Int`, but some conversions may
    * involve rounding or truncation.
    *
    * $returningNone
    *
    * $exceptionsIdx
    *
    * @group int
    */
  def int(idx: Int): Int

  /** Returns an `Int` from column with a given index.
    *
    * All numeric types can be converted to `Int`, but some conversions may
    * involve rounding or truncation.
    *
    * $returningNone
    *
    * $exceptionsIdx
    *
    * @group int
    */
  def intOpt(idx: Int): Option[Int]

  /** Returns a `Long` from column with a given name.
    *
    * All numeric types can be converted to `Long`, but some conversions may
    * involve rounding or truncation.
    *
    * $nullSafetyNote
    *
    * $exceptionsNamed
    *
    * @group int
    */
  def long(name: String): Long

  /** Returns a `Long` from column with a given name.
    *
    * All numeric types can be converted to `Long`, but some conversions may
    * involve rounding or truncation.
    *
    * $returningNone
    *
    * $exceptionsNamed
    *
    * @group int
    */
  def longOpt(name: String): Option[Long]

  /** Returns a `Long` from column with a given index.
    *
    * All numeric types can be converted to `Long`, but some conversions may
    * involve rounding or truncation.
    *
    * $nullSafetyNote
    *
    * $exceptionsIdx
    *
    * @group int
    */
  def long(idx: Int): Long

  /** Returns a `Long` from column with a given index.
    *
    * All numeric types can be converted to `Long`, but some conversions may
    * involve rounding or truncation.
    *
    * $returningNone
    *
    * $exceptionsIdx
    *
    * @group int
    */
  def longOpt(idx: Int): Option[Long]

  /** Returns a [[scala.math.BigDecimal BigDecimal]] from column with a given name.
    *
    * All numeric types can be converted to [[scala.math.BigDecimal BigDecimal]], note however that
    * NaN value is not representable by a [[scala.math.BigDecimal BigDecimal]]. If you expect values
    * to be NaN use `decimal` method instead.
    *
    * $nullSafetyNote
    *
    * $exceptionsNamed
    *
    * @group unb
    */
  def bigDecimal(name: String): BigDecimal

  /** Returns a [[scala.math.BigDecimal BigDecimal]] from column with a given name.
    *
    * All numeric types can be converted to [[scala.math.BigDecimal BigDecimal]], note however that
    * NaN value is not representable by a [[scala.math.BigDecimal BigDecimal]]. If you expect values
    * to be NaN use `decimal` method instead.
    *
    * $returningNone
    *
    * $exceptionsNamed
    *
    * @group unb
    */
  def bigDecimalOpt(name: String): Option[BigDecimal]

  /** Returns a [[scala.math.BigDecimal BigDecimal]] from column with a given index.
    *
    * All numeric types can be converted to [[scala.math.BigDecimal BigDecimal]], note however that
    * NaN value is not representable by a [[scala.math.BigDecimal BigDecimal]]. If you expect values
    * to be NaN use `decimal` method instead.
    *
    * $nullSafetyNote
    *
    * $exceptionsIdx
    *
    * @group unb
    */
  def bigDecimal(idx: Int): BigDecimal

  /** Returns a [[scala.math.BigDecimal BigDecimal]] from column with a given index.
    *
    * All numeric types can be converted to [[scala.math.BigDecimal BigDecimal]], note however that
    * NaN value is not representable by a [[scala.math.BigDecimal BigDecimal]]. If you expect values
    * to be NaN use `decimal` method instead.
    *
    * $returningNone
    *
    * $exceptionsIdx
    *
    * @group unb
    */
  def bigDecimalOpt(idx: Int): Option[BigDecimal]

  /** Returns a [[DecimalNumber]] from column with a given name.
    *
    * $nullSafetyNote
    *
    * $exceptionsNamed
    *
    * @group unb
    */
  def decimal(name: String): DecimalNumber

  /** Returns a [[DecimalNumber]] from column with a given name.
    *
    * $returningNone
    *
    * $exceptionsNamed
    *
    * @group unb
    */
  def decimalOpt(name: String): Option[DecimalNumber]

  /** Returns a [[DecimalNumber]] from column with a given index.
    *
    * $nullSafetyNote
    *
    * $exceptionsIdx
    *
    * @group unb
    */
  def decimal(idx: Int): DecimalNumber

  /** Returns a [[DecimalNumber]] from column with a given index.
    *
    * $returningNone
    *
    * $exceptionsIdx
    *
    * @group unb
    */
  def decimalOpt(idx: Int): Option[DecimalNumber]

  /** Returns a `Double` from column with a given name.
    *
    * All numeric types can be converted to `Double`, but some conversions
    * may involve rounding or truncation.
    *
    * $nullSafetyNote
    *
    * $exceptionsNamed
    *
    * @group float
    */
  def double(name: String): Double

  /** Returns a `Double` from column with a given name.
    *
    * All numeric types can be converted to `Double`, but some conversions may
    * involve rounding or truncation.
    *
    * $returningNone
    *
    * $exceptionsNamed
    *
    * @group float
    */
  def doubleOpt(name: String): Option[Double]

  /** Returns a `Double` from column with a given index.
    *
    * All numeric types can be converted to `Double`, but some conversions
    * may involve rounding or truncation.
    *
    * $nullSafetyNote
    *
    * $exceptionsIdx
    *
    * @group float
    */
  def double(idx: Int): Double

  /** Returns a `Double` from column with a given index.
    *
    * All numeric types can be converted to `Double`, but some conversions may
    * involve rounding or truncation.
    *
    * $returningNone
    *
    * $exceptionsIdx
    *
    * @group float
    */
  def doubleOpt(idx: Int): Option[Double]

  /** Returns a `Float` from column with a given name.
    *
    * All numeric types can be converted to `Float`, but some conversions may
    * involve rounding or truncation.
    *
    * $nullSafetyNote
    *
    * $exceptionsNamed
    *
    * @group float
    */
  def float(name: String): Float

  /** Returns a `Float` from column with a given name.
    *
    * All numeric types can be converted to `Float`, but some conversions may
    * involve rounding or truncation.
    *
    * $returningNone
    *
    * $exceptionsNamed
    *
    * @group float
    */
  def floatOpt(name: String): Option[Float]

  /** Returns a `Float` from column with a given index.
    *
    * All numeric types can be converted to `Float`, but some conversions may
    * involve rounding or truncation.
    *
    * $nullSafetyNote
    *
    * $exceptionsIdx
    *
    * @group float
    */
  def float(idx: Int): Float

  /** Returns a `Float` from column with a given index.
    *
    * All numeric types can be converted to `Float`, but some conversions may
    * involve rounding or truncation.
    *
    * $returningNone
    *
    * $exceptionsIdx
    *
    * @group float
    */
  def floatOpt(idx: Int): Option[Float]

  /** Returns an `Instant` from column with a given name.
    *
    * Note that regular timestamp values are not convertible to an `Instant`
    * because timestamp values do not hold a time zone information.
    *
    * $nullSafetyNote
    *
    * $exceptionsNamed
    *
    * @group date
    */
  def instant(name: String): Instant

  /** Returns an `Instant` from column with a given name.
    *
    * Note that standard SQL TIMESTAMP values are not convertible to an `Instant`
    * because they do not hold a time zone information.
    *
    * $returningNone
    *
    * $exceptionsNamed
    *
    * @group date
    */
  def instantOpt(name: String): Option[Instant]

  /** Returns an `Instant` from column with a given index.
    *
    * Note that standard SQL TIMESTAMP values are not convertible to an `Instant`
    * because they do not hold a time zone information.
    *
    * $nullSafetyNote
    *
    * $exceptionsIdx
    *
    * @group date
    */
  def instant(idx: Int): Instant

  /** Returns an `Instant` from column with a given index.
    *
    * Note that regular timestamp values are not convertible to an `Instant`
    * because timestamp values do not hold a time zone information.
    *
    * $returningNone
    *
    * $exceptionsIdx
    *
    * @group date
    */
  def instantOpt(idx: Int): Option[Instant]

  /** Returns an `Instant` from column with a given name, assuming
    * that the TIMESTAMP value stored in the database is in the provided time
    * zone.
    *
    * $instantZoneNote
    *
    * $nullSafetyNote
    *
    * $exceptionsNamed
    *
    * @group date
    */
  def instant(name: String, zoneId: ZoneId): Instant

  /** Returns an `Instant` from column with a given name, assuming
    * that the TIMESTAMP value stored in the database is in the provided time
    * zone.
    *
    * $instantZoneNote
    *
    * $returningNone
    *
    * $exceptionsNamed
    *
    * @group date
    */
  def instantOpt(name: String, zoneId: ZoneId): Option[Instant]

  /** Returns an `Instant` from column with a given index, assuming
    * that the TIMESTAMP value stored in the database is in the provided time
    * zone.
    *
    * $instantZoneNote
    *
    * $nullSafetyNote
    *
    * $exceptionsIdx
    *
    * @group date
    */
  def instant(idx: Int, zoneId: ZoneId): Instant

  /** Returns an `Instant` from column with a given index, assuming
    * that the TIMESTAMP value stored in the database is in the provided time
    * zone.
    *
    * $instantZoneNote
    *
    * $returningNone
    *
    * $exceptionsIdx
    *
    * @group date
    */
  def instantOpt(idx: Int, zoneId: ZoneId): Option[Instant]

  /** Returns a `ZonedDateTime` from column with a given name.
    *
    * $nullSafetyNote
    *
    * $exceptionsNamed
    *
    * @group date
    */
  def zonedDateTime(name: String): ZonedDateTime

  /** Returns a `ZonedDateTime` from column with a given name.
    *
    * $returningNone
    *
    * $exceptionsNamed
    *
    * @group date
    */
  def zonedDateTimeOpt(name: String): Option[ZonedDateTime]

  /** Returns a `ZonedDateTime` from column with a given index.
    *
    * $nullSafetyNote
    *
    * $exceptionsIdx
    *
    * @group date
    */
  def zonedDateTime(idx: Int): ZonedDateTime

  /** Returns a `ZonedDateTime` from column with a given index.
    *
    * $returningNone
    *
    * $exceptionsIdx
    *
    * @group date
    */
  def zonedDateTimeOpt(idx: Int): Option[ZonedDateTime]

  /** Returns a `LocalDateTime` from column with a given name.
    *
    * For SQL date type that does not hold a time, `LocalDateTime` at start
    * of day is returned.
    *
    * $nullSafetyNote
    *
    * $exceptionsNamed
    *
    * @group date
    */
  def localDateTime(name: String): LocalDateTime

  /** Returns a `LocalDateTime` from column with a given name.
    *
    * For SQL date type that does not hold a time, `LocalDateTime` at start
    * of day is returned.
    *
    * $returningNone
    *
    * $exceptionsNamed
    *
    * @group date
    */
  def localDateTimeOpt(name: String): Option[LocalDateTime]

  /** Returns a `LocalDateTime` from column with a given index.
    *
    * For SQL date type that does not hold a time, `LocalDateTime` at start
    * of day is returned.
    *
    * $nullSafetyNote
    *
    * $exceptionsIdx
    *
    * @group date
    */
  def localDateTime(idx: Int): LocalDateTime

  /** Returns a `LocalDateTime` from column with a given index.
    *
    * For SQL date type that does not hold a time, `LocalDateTime` at start
    * of day is returned.
    *
    * $returningNone
    *
    * $exceptionsIdx
    *
    * @group date
    */
  def localDateTimeOpt(idx: Int): Option[LocalDateTime]

  /** Returns a `LocalDate` from column with a given name.
    *
    * SQL types that represent a date with a time are convertible to
    * `LocalDate` - a time part is truncated.
    *
    * $nullSafetyNote
    *
    * $exceptionsNamed
    *
    * @group date
    */
  def localDate(name: String): LocalDate

  /** Returns a `LocalDate` from column with a given name.
    *
    * SQL types that represent a date with a time are convertible to
    * `LocalDate` - a time part is truncated.
    *
    * $returningNone
    *
    * $exceptionsNamed
    *
    * @group date
    */
  def localDateOpt(name: String): Option[LocalDate]

  /** Returns a `LocalDate` from column with a given index.
    *
    * SQL types that represent a date with a time are convertible to
    * `LocalDate` - a time part is truncated.
    *
    * $nullSafetyNote
    *
    * $exceptionsIdx
    *
    * @group date
    */
  def localDate(idx: Int): LocalDate

  /** Returns a `LocalDate` from column with a given index.
    *
    * SQL types that represent a date with a time are convertible to
    * `LocalDate` - a time part is truncated.
    *
    * $returningNone
    *
    * $exceptionsIdx
    *
    * @group date
    */
  def localDateOpt(idx: Int): Option[LocalDate]

  /** Returns a `LocalDate` from column with a given name.
    *
    * SQL types that represent a date with a time are convertible to
    * `LocalTime` - a date part is truncated.
    *
    * $nullSafetyNote
    *
    * $exceptionsNamed
    *
    * @group date
    */
  def localTime(name: String): LocalTime

  /** Returns a `LocalDate` from column with a given name.
    *
    * SQL types that represent a date with a time are convertible to
    * `LocalTime` - a date part is truncated.
    *
    * $returningNone
    *
    * $exceptionsNamed
    *
    * @group date
    */
  def localTimeOpt(name: String): Option[LocalTime]

  /** Returns a `LocalDate` from column with a given index.
    *
    * SQL types that represent a date with a time are convertible to
    * `LocalTime` - a date part is truncated.
    *
    * $nullSafetyNote
    *
    * $exceptionsIdx
    *
    * @group date
    */
  def localTime(idx: Int): LocalTime

  /** Returns a `LocalDate` from column with a given index.
    *
    * SQL types that represent a date with a time are convertible to
    * `LocalTime` - a date part is truncated.
    *
    * $returningNone
    *
    * $exceptionsIdx
    *
    * @group date
    */
  def localTimeOpt(idx: Int): Option[LocalTime]

  /** Returns a byte array from column with a given name.
    *
    * Note that this method cannot be used to fetch raw value of any type from
    * the database, it should be used only to fetch binary data.
    *
    * $nullSafetyNote
    *
    * $exceptionsNamed
    *
    * @group binary
    */
  def bytes(name: String): Array[Byte]

  /** Returns a byte array from column with a given name.
    *
    * Note that this method cannot be used to fetch raw value of any type from
    * the database, it should be used
    * only to fetch binary data.
    *
    * $returningNone
    *
    * $exceptionsNamed
    *
    * @group binary
    */
  def bytesOpt(name: String): Option[Array[Byte]]

  /** Returns a byte array from column with a given index.
    *
    * Note that this method cannot be used to fetch raw value of any type from
    * the database, it should be used only to fetch binary data.
    *
    * $nullSafetyNote
    *
    * $exceptionsIdx
    *
    * @group binary
    */
  def bytes(idx: Int): Array[Byte]

  /** Returns a byte array from column with a given index.
    *
    * Note that this method cannot be used to fetch raw value of any type from
    * the database, it should be used
    * only to fetch binary data.
    *
    * $returningNone
    *
    * $exceptionsIdx
    *
    * @group binary
    */
  def bytesOpt(idx: Int): Option[Array[Byte]]

  /** Returns an `UUID` from column with a given name.
    *
    * A string type with a standard UUID representation as defined by
    * `UUID.fromString` is convertible to UUID.
    *
    * $nullSafetyNote
    *
    * $exceptionsNamed
    *
    * @group uuid
    */
  def uuid(name: String): UUID

  /** Returns an `UUID` from column with a given name.
    *
    * A string type with a standard UUID representation as defined by
    * `UUID.fromString` is convertible to UUID.
    *
    * $returningNone
    *
    * $exceptionsNamed
    *
    * @group uuid
    */
  def uuidOpt(name: String): Option[UUID]

  /** Returns an `UUID` from column with a given index.
    *
    * A string type with a standard UUID representation as defined by
    * `UUID.fromString` is convertible to UUID.
    *
    * $nullSafetyNote
    *
    * $exceptionsIdx
    *
    * @group uuid
    */
  def uuid(idx: Int): UUID

  /** Returns an `UUID` from column with a given index.
    *
    * A string type with a standard UUID representation as defined by
    * `UUID.fromString` is convertible to UUID.
    *
    * $returningNone
    *
    * $exceptionsIdx
    *
    * @group uuid
    */
  def uuidOpt(idx: Int): Option[UUID]
}
