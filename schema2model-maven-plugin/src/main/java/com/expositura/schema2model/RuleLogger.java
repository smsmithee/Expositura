/**
 * Copyright © 2010-2020 Nokia
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.expositura.schema2model;

/**
 * Interface that defines fundamental methods used by all RuleLogger implementations.
 */
public interface RuleLogger {

  /**
   * Log a debug message.
   * @param msg The message.
   */
  void debug(String msg);

  /**
   * Log an error message.
   * @param msg The message.
   */
  void error(String msg);

  /**
   * Log an error message and the exception related to the message.
   * @param s The message.
   * @param e The exception related to the message.
   */
  void error(String s, Throwable e);

  /**
   * Log an info message.
   * @param msg The message.
   */
  void info(String msg);

  boolean isDebugEnabled();

  boolean isErrorEnabled();

  boolean isInfoEnabled();

  boolean isTraceEnabled();

  boolean isWarnEnabled();

  void trace(String msg);

  void warn(String s, Throwable e);

  void warn(String msg);
}
