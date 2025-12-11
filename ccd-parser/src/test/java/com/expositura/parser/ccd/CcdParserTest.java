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
package com.expositura.parser.ccd;

import com.expositura.model.ccd.ClinicalDocument;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonFactoryBuilder;
import com.fasterxml.jackson.core.StreamReadConstraints;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.InputStream;
import org.junit.jupiter.api.Test;
import org.xml.sax.InputSource;

/**
 * Provides Unit tests for the CCD parser.
 * 
 * @author Sean Smith
 */
public class CcdParserTest {
  
  private static final ObjectMapper mapper = new ObjectMapper(new JsonFactoryBuilder().streamReadConstraints(
      StreamReadConstraints.builder().maxStringLength(Integer.MAX_VALUE).build()).build())
      .registerModule(new JavaTimeModule())
      .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
      .setDefaultPropertyInclusion(JsonInclude.Include.NON_EMPTY)
      .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
  
  /**
   * Test of parseFromCcd method passing the ccd as a string.
   */
  @Test
  public void testParseFromCcd_String() {
    System.out.println("parseFromCcd");
    String ccdAsString = "";
    ClinicalDocument expResult = null;
    ClinicalDocument result = CcdParser.parseFromCcd(ccdAsString);
    // TODO review the generated test code 
    
  }

  /**
   * Test of parseFromCcd method passing the ccd as an input source.
   */
  @Test
  public void testParseFromCcd_InputSource() throws Exception {
    System.out.println("parseFromCcd(InputSource)");
    final InputStream ccdStream = CcdParserTest.class.getResourceAsStream("/ccds/Practice_Fusion/AliceNewmanApi.xml");
    //final InputStream ccdStream = CcdParserTest.class.getResourceAsStream("/ccds/360_Oncology/Alice_Newman_health_summary Delegate.xml");
    final ClinicalDocument result = CcdParser.parseFromCcd(new InputSource(ccdStream));
    System.out.println("result = \n" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(result));
  }
  
}
