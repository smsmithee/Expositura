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

import com.expositura.model.ccd.Sc;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Parses a ED datatype XML element to the java object or from the object to the xml
 * 
 * @author Sean Smith
 */
public class ScParser {
  
  public static Sc fromXml(final Node node) {
    final Sc sc = new Sc();
    
    // Start by getting the attributes
    final NamedNodeMap attributes = node.getAttributes();
    if (attributes != null && attributes.getLength() > 0) {
      
      // nullFlavor
      final Node nullFlavor = attributes.getNamedItem("nullFlavor");
      if (nullFlavor != null) {
        sc.setNullFlavor(nullFlavor.getNodeValue());
      }
      
      // representation
      final Node representation = attributes.getNamedItem("representation");
      if (representation != null) {
        sc.setRepresentation(representation.getNodeValue());
      }
      
      // mediaType
      final Node mediaType = attributes.getNamedItem("mediaType");
      if (mediaType != null) {
        sc.setMediaType(mediaType.getNodeValue());
      }

      // language
      final Node language = attributes.getNamedItem("language");
      if (language != null) {
        sc.setLanguage(language.getNodeValue());
      }
      
      // code
      final Node code = attributes.getNamedItem("code");
      if (code != null) {
        sc.setCode(code.getNodeValue());
      }
      
      // codeSystem
      final Node codeSystem = attributes.getNamedItem("codeSystem");
      if (codeSystem != null) {
        sc.setCodeSystem(codeSystem.getNodeValue());
      }
      
      // codeSystemName
      final Node codeSystemName = attributes.getNamedItem("codeSystemName");
      if (codeSystemName != null) {
        sc.setCodeSystemName(codeSystemName.getNodeValue());
      }
      
      // codeSystemVersion
      final Node codeSystemVersion = attributes.getNamedItem("codeSystemVersion");
      if (codeSystemVersion != null) {
        sc.setCodeSystemVersion(codeSystemVersion.getNodeValue());
      }
      
      // displayName
      final Node displayName = attributes.getNamedItem("displayName");
      if (displayName != null) {
        sc.setDisplayName(displayName.getNodeValue());
      }
    }
    
    // Now work thru children
    final NodeList nodeList = node.getChildNodes();
    
    for (int i = 0; i < nodeList.getLength(); i++) {
      final Node child = nodeList.item(i);
      
      // This element may have text content (base64 string)
      if (child.hasAttributes() || child.hasChildNodes()) {
        // Not expecting any regular children
      } else {
        sc.setXmlText(child.getNodeValue());
      }
    }
    
    return Sc.isEmpty(sc) ? null : sc;
  }
          
}
