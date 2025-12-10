/*
 * Copyright 2025 Sean Smith.
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
package com.expositura.parser.ccd.exeption;

/**
 * Exceptions caused by invalid CCD documents.
 * An example is a file that does not have "ClinicalDocument" as the top element.
 * 
 * @author Sean Smith
 */
public class InvalidCcdException extends Exception {

  public InvalidCcdException() {
  }

  public InvalidCcdException(final String message) {
    super(message);
  }

  public InvalidCcdException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public InvalidCcdException(final Throwable cause) {
    super(cause);
  }

  public InvalidCcdException(
          final String message, 
          final Throwable cause, 
          final boolean enableSuppression, 
          final boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
  
  
}
