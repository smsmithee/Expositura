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

import com.expositura.model.ccd.Ed;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Parses a ED datatype XML element to the java object or from the object to the xml
 * 
 * @author Sean Smith
 */
public class EdParser {
  
  public static Ed fromXml(final Node node) {
    final Ed ed = new Ed();
    
    // Start by getting the attributes
    final NamedNodeMap attributes = node.getAttributes();
    if (attributes != null && attributes.getLength() > 0) {
      
      // nullFlavor
      final Node nullFlavor = attributes.getNamedItem("nullFlavor");
      if (nullFlavor != null) {
        ed.setNullFlavor(nullFlavor.getNodeValue());
      }
      
      // compression
      final Node compression = attributes.getNamedItem("compression");
      if (compression != null) {
        ed.setCompression(compression.getNodeValue());
      }
      
      // integrityCheck
      final Node integrityCheck = attributes.getNamedItem("integrityCheck");
      if (integrityCheck != null) {
        ed.setIntegrityCheck(integrityCheck.getNodeValue());
      }
      
      // integrityCheckAlgorithm
      final Node integrityCheckAlgorithm = attributes.getNamedItem("integrityCheckAlgorithm");
      if (integrityCheckAlgorithm != null) {
        ed.setIntegrityCheckAlgorithm(integrityCheckAlgorithm.getNodeValue());
      }
      
      // language
      final Node language = attributes.getNamedItem("language");
      if (language != null) {
        ed.setLanguage(language.getNodeValue());
      }
      
      // mediaType
      final Node mediaType = attributes.getNamedItem("mediaType");
      if (mediaType != null) {
        ed.setMediaType(mediaType.getNodeValue());
      }
      
      // representation
      final Node representation = attributes.getNamedItem("representation");
      if (representation != null) {
        ed.setRepresentation(representation.getNodeValue());
      }
      
    }
    
    // Now work thru children
    final NodeList nodeList = node.getChildNodes();
    
    for (int i = 0; i < nodeList.getLength(); i++) {
      final Node child = nodeList.item(i);
      
      // This element may have text content (base64 string)
      if (child.hasAttributes() || child.hasChildNodes()) {
        switch (child.getNamespaceURI() + "|" + child.getLocalName()) {
          case "urn:hl7-org:v3|reference" -> ed.setReference(TelParser.fromXml(child));
          case "urn:hl7-org:v3|thumbnail" -> ed.setThumbnail(EdParser.fromXml(child));
        }
      } else {
        ed.setXmlText(child.getNodeValue());
      }
    }
    
    return Ed.isEmpty(ed) ? null : ed;
  }
          
}
