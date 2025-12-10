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

import com.expositura.model.ccd.Intt;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * Parses a INT datatype XML element to the java object or from the object to the xml
 * 
 * @author Sean Smith
 */
public class IntParser {
  
  public static Intt fromXml(final Node node) {
    final Intt intt = new Intt();
    
    // Start by getting the attributes
    final NamedNodeMap attributes = node.getAttributes();
    if (attributes != null && attributes.getLength() > 0) {
      
      // nullFlavor
      final Node nullFlavor = attributes.getNamedItem("nullFlavor");
      if (nullFlavor != null) {
        intt.setNullFlavor(nullFlavor.getNodeValue());
      }
      
      // value
      final Node value = attributes.getNamedItem("value");
      if (value != null) {
        intt.setValue(Long.valueOf(value.getNodeValue()));
      }
      
    }
    
    return Intt.isEmpty(intt) ? null : intt;
  }
          
}
