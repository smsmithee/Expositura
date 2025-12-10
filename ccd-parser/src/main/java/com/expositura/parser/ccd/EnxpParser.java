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

import com.expositura.model.ccd.Adxp;
import com.expositura.model.ccd.Enxp;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Parses a ENXP datatype XML element to the java object or from the object to the xml
 * 
 * @author Sean Smith
 */
public class EnxpParser {
  
  public static Enxp fromXml(final Node node) {
    final Enxp enxp = new Enxp();
    
    // Start by getting the attributes
    final NamedNodeMap attributes = node.getAttributes();
    if (attributes != null && attributes.getLength() > 0) {
      
      // nullFlavor
      final Node nullFlavor = attributes.getNamedItem("nullFlavor");
      if (nullFlavor != null) {
        enxp.setNullFlavor(nullFlavor.getNodeValue());
      }
      
      // representation
      final Node representation = attributes.getNamedItem("representation");
      if (representation != null) {
        enxp.setRepresentation(representation.getNodeValue());
      }
      
      // mediaType
      final Node mediaType = attributes.getNamedItem("mediaType");
      if (mediaType != null) {
        enxp.setMediaType(mediaType.getNodeValue());
      }
      
      // language
      final Node language = attributes.getNamedItem("language");
      if (language != null) {
        enxp.setLanguage(language.getNodeValue());
      }
      
      // partType
      final Node partType = attributes.getNamedItem("partType");
      if (partType != null) {
        enxp.setPartType(partType.getNodeValue());
      }
      
      // qualifier (special case, can have multiple values with space seperator
      final Node qualifier = attributes.getNamedItem("qualifier");
      if (qualifier != null) {
        final String qualifierValue = qualifier.getNodeValue();
        if (qualifierValue != null) {
          for (final String singleQualifier : StringUtils.split(qualifierValue)) {
            enxp.addQualifier(singleQualifier);
          }
        }
      }
    }
    
    // Now work thru children, only expecting a text node
    final NodeList nodeList = node.getChildNodes();
    
    for (int i = 0; i < nodeList.getLength(); i++) {
      final Node child = nodeList.item(i);
      
      // Expecting no children with attributes or sub elements
      if (child.hasAttributes() || child.hasChildNodes()) {
        // Not expecting any of these so ignore
      } else {
        enxp.setXmlText(child.getNodeValue());
      }
    }
    
    return Enxp.isEmpty(enxp) ? null : enxp;
  }
          
}
