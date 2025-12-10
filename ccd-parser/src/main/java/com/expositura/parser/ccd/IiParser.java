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

import com.expositura.model.ccd.Ii;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * Parses an II datatype XML element to the java object or from the object to the xml
 * 
 * @author Sean Smith
 */
public class IiParser {
  
  public static Ii fromXml(final Node node) {
    final Ii ii = new Ii();
    
    // All II has is attributes
    final NamedNodeMap attributes = node.getAttributes();
    if (attributes != null && attributes.getLength() > 0) {
      
      // nullFlavor
      final Node nullFlavor = attributes.getNamedItem("nullFlavor");
      if (nullFlavor != null) {
        ii.setNullFlavor(nullFlavor.getNodeValue());
      }
      
      // assigningAuthorityName
      final Node assigningAuthorityName = attributes.getNamedItem("assigningAuthorityName");
      if (assigningAuthorityName != null) {
        ii.setAssigningAuthorityName(assigningAuthorityName.getNodeValue());
      }
      
      // displayable
      final Node displayable = attributes.getNamedItem("displayable");
      if (displayable != null) {
        ii.setDisplayable(Boolean.valueOf(displayable.getNodeValue()));
      }
      
      // root
      final Node root = attributes.getNamedItem("root");
      if (root != null) {
        ii.setRoot(root.getNodeValue());
      }
      
      // extension
      final Node extension = attributes.getNamedItem("extension");
      if (extension != null) {
        ii.setExtension(extension.getNodeValue());
      }
    }
    
    return Ii.isEmpty(ii) ? null : ii;
  }
          
}
