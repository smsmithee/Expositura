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

import com.expositura.model.ccd.Tel;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Parses a TEL datatype XML element to the java object or from the object to the xml
 * 
 * @author Sean Smith
 */
public class TelParser {
  
  public static Tel fromXml(final Node node) {
    final Tel tel = new Tel();
    
    // Start by getting the attributes
    final NamedNodeMap attributes = node.getAttributes();
    if (attributes != null && attributes.getLength() > 0) {
      
      // nullFlavor
      final Node nullFlavor = attributes.getNamedItem("nullFlavor");
      if (nullFlavor != null) {
        tel.setNullFlavor(nullFlavor.getNodeValue());
      }
      
      // value
      final Node value = attributes.getNamedItem("value");
      if (value != null) {
        tel.setValue(value.getNodeValue());
      }
      
      // use (special case, use can have multiple values with space seperator
      final Node use = attributes.getNamedItem("use");
      if (use != null) {
        final String useValue = use.getNodeValue();
        if (useValue != null) {
          for (final String singleUse : StringUtils.split(useValue)) {
            tel.addUse(singleUse);
          }
        }
      }
      
    }
    
    // Now work thru children, ignore text only nodes (probably white space)
    final NodeList nodeList = node.getChildNodes();
    
    for (int i = 0; i < nodeList.getLength(); i++) {
      final Node child = nodeList.item(i);
      
      // Ignore children with no attributes or elements, means it is a text only child, probably whitespace
      if (child.hasAttributes() || child.hasChildNodes()) {
        switch (child.getNamespaceURI() + "|" + child.getLocalName()) {
          case "urn:hl7-org:v3|useablePeriod" -> {
            // This element may be one of four different datatypes, determine which one
            final NamedNodeMap childAttributes = child.getAttributes();
            
            // These are the possible child nodes for the five different datatypes
            Node event = null;
            Node offset = null;
            Node phase = null;
            Node period = null;
            List<Node> comp = new ArrayList<>();          
            
            // Figure out which children are present
            if (child.hasChildNodes()) {
              final NodeList subNodes = child.getChildNodes();
              for (int j = 0; j < subNodes.getLength(); j++) {
                switch (subNodes.item(j).getLocalName()) {
                  case "event" -> event = subNodes.item(j);
                  case "offset" -> offset = subNodes.item(j);
                  case "phase" -> phase = subNodes.item(j);
                  case "period" -> period = subNodes.item(j);
                  case "comp" -> comp.add(subNodes.item(j));
                }
              }
            }
            
            // First look for SXPR_TS type which MUST have 'comp' child element(s)
            if (!comp.isEmpty()) {
              SxprTsParser.fromXml(child);
            }
            // Next look for event or offset as either shows it's a EIVL_TS
            else if (event != null || offset != null) {
              tel.addUseablePeriodAsEivlTs(EivlTsParser.fromXml(child));
            }
            // Next look for phase, period, alignment, or institution specified which means it's a PIVL_TS
            else if (phase != null 
                    || period != null 
                    || childAttributes.getNamedItem("alignment") != null 
                    || childAttributes.getNamedItem("institution") != null) {
              tel.addUseablePeriodAsPivlTs(PivlTsParser.fromXml(child));
            }
            // Catch all is IVL_TS
            else {
              tel.addUseablePeriodAsIvlTs(IvlTsParser.fromXml(child));
            }
          }
        }
      } 
    }
    
    return Tel.isEmpty(tel) ? null : tel;
  }
          
}
