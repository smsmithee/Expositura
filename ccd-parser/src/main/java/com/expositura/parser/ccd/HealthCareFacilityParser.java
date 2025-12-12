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

import com.expositura.model.ccd.HealthCareFacility;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Parses the HealthCareFacility XML element to the java object or from the object to the xml
 * 
 * @author Sean Smith
 */
public class HealthCareFacilityParser {
  
  public static HealthCareFacility fromXml(final Node node) {
    final HealthCareFacility healthCareFacility = new HealthCareFacility();
    
    // First get the attributes if any
    final NamedNodeMap attributes = node.getAttributes();
    if (attributes != null && attributes.getLength() > 0) {
      
      // nullFlavor
      final Node nullFlavor = attributes.getNamedItem("nullFlavor");
      if (nullFlavor != null) {
        healthCareFacility.setNullFlavor(nullFlavor.getNodeValue());
      }
      
      // classCode
      final Node classCode = attributes.getNamedItem("classCode");
      if (classCode != null) {
        healthCareFacility.setClassCode(classCode.getNodeValue());
      }
      
    }
    
    final NodeList nodeList = node.getChildNodes();
    
    for (int i = 0; i < nodeList.getLength(); i++) {
      final Node child = nodeList.item(i);
      
      // Ignore children with no attributes and no children, they are text such as newlines for formatted XML
      if (child.hasAttributes() || child.hasChildNodes()) {
        switch (child.getNamespaceURI() + "|" + child.getLocalName()) {
          case "urn:hl7-org:v3|realmCode" -> healthCareFacility.addRealmCode(CsParser.fromXml(child));
          case "urn:hl7-org:v3|typeId" -> healthCareFacility.setTypeId(IiParser.fromXml(child));
          case "urn:hl7-org:v3|templateId" -> healthCareFacility.addTemplateId(IiParser.fromXml(child));
          case "urn:hl7-org:v3|id" -> healthCareFacility.addId(IiParser.fromXml(child));
          case "urn:hl7-org:sdtc|identifiedBy" -> healthCareFacility.addIdentifiedBy(IdentifiedByParser.fromXml(child));
          case "urn:hl7-org:v3|code" -> healthCareFacility.setCode(CeParser.fromXml(child));
          case "urn:hl7-org:v3|location" -> healthCareFacility.setLocation(PlaceParser.fromXml(child));
          case "urn:hl7-org:v3|serviceProviderOrganization" -> healthCareFacility.setServiceProviderOrganization(OrganizationParser.fromXml(child));
        }
      }
    }
    
    return HealthCareFacility.isEmpty(healthCareFacility) ? null : healthCareFacility;
  }
          
}
