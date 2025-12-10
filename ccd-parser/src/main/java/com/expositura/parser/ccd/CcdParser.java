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
import com.expositura.parser.ccd.exeption.InvalidCcdException;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Provides static method(s) to parse to/from a CCD and Expositura's java CCD model.
 *
 * @author Sean Smith
 */
public class CcdParser {

  private static final DocumentBuilderFactory FACTORY;
  private static final ThreadLocal<DocumentBuilder> BUILDER;
  private static final String HL7_NAMESPACE = "urn:hl7-org:v3";
  private static final String SDTC_NAMESPACE = "urn:hl7-org:sdtc";
  
  static {
    FACTORY = DocumentBuilderFactory.newInstance();
    FACTORY.setNamespaceAware(true);
    FACTORY.setIgnoringComments(true);
    BUILDER = ThreadLocal.withInitial(() -> {
            try {
                return FACTORY.newDocumentBuilder();
            } catch (ParserConfigurationException e) {
                throw new RuntimeException(e);
            }
        });
  }
  
  /**
   * Parses a CCD (as a String) into a ClinicalDocument java bean. If null is provided or there is no data in the CCD a
   * null value is returned.
   *
   * @param ccdAsString The CCD as a String.
   * @return The ClinicalDocument java bean with data parsed from the CCD. Returns null if null is passed in or no data
   * can be parsed.
   */
  public static ClinicalDocument parseFromCcd(String ccdAsString) {
    if (ccdAsString == null) {
      return null;
    }

    final ClinicalDocument ccd = new ClinicalDocument();

    // Return null if the ccd has no data
    return ClinicalDocument.isEmpty(ccd) ? null : ccd;
  }

  public static ClinicalDocument parseFromCcd(final InputSource ccdAsInputSource)
          throws IOException, InvalidCcdException, SAXException {
    final DocumentBuilder builder = BUILDER.get();
    final Document doc = builder.parse(ccdAsInputSource);

    validateCcdDocument(doc);
    
    final ClinicalDocument ccd = ClinicalDocumentParser.fromXml(doc.getFirstChild());
    
    return ClinicalDocument.isEmpty(ccd) ? null : ccd;
  }

  /**
   * Validate that this is a CCD Document. There should be one child node with the correct namespace and local name
   */
  private static void validateCcdDocument(final Document ccd) throws InvalidCcdException {
    
    // Validate that there are any XML nodes in the document
    if (!ccd.hasChildNodes()) {
      throw new InvalidCcdException("CCD does not have any XML elements to parse");
    }
    
    if (ccd.getChildNodes().getLength() != 1) {
      throw new InvalidCcdException("CCD must have a single top level XML element");
    }
    
    final Node clinicalDocNode = ccd.getFirstChild();
    
    if (!HL7_NAMESPACE.equals(clinicalDocNode.getNamespaceURI())) {
      throw new InvalidCcdException("Top level element must have namespace of '" + HL7_NAMESPACE + "'. Instead found '" 
              + clinicalDocNode.getNamespaceURI() + "'");
    }
    
    if (!"ClinicalDocument".equals(clinicalDocNode.getLocalName())) {
      throw new InvalidCcdException("Top level element must be 'ClinicalDocument'. Instead found '" 
              + clinicalDocNode.getLocalName() + "'");
    }
  }
}
