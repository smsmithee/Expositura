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

import com.expositura.model.ccd.Precondition2;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Parses the Precondition2 XML element to the java object or from the object to the xml
 * 
 * @author Sean Smith
 */
public class Precondition2Parser {
  
  public static Precondition2 fromXml(final Node node) {
    final Precondition2 precondition2 = new Precondition2();
    
    // First get the attributes if any
    final NamedNodeMap attributes = node.getAttributes();
    if (attributes != null && attributes.getLength() > 0) {
      
      // nullFlavor
      final Node nullFlavor = attributes.getNamedItem("nullFlavor");
      if (nullFlavor != null) {
        precondition2.setNullFlavor(nullFlavor.getNodeValue());
      }
      
      // typeCode
      final Node typeCode = attributes.getNamedItem("typeCode");
      if (typeCode != null) {
        precondition2.setTypeCode(typeCode.getNodeValue());
      }
      
      // negationInd
      final Node negationInd = attributes.getNamedItem("negationInd");
      if (negationInd != null) {
        precondition2.setNegationInd(Boolean.valueOf(negationInd.getNodeValue()));
      }
      
    }
    
    final NodeList nodeList = node.getChildNodes();
    
    for (int i = 0; i < nodeList.getLength(); i++) {
      final Node child = nodeList.item(i);
      
      // Ignore children with no attributes and no children, they are text such as newlines for formatted XML
      if (child.hasAttributes() || child.hasChildNodes()) {
        switch (child.getNamespaceURI() + "|" + child.getLocalName()) {
          case "urn:hl7-org:v3|realmCode" -> precondition2.addRealmCode(CsParser.fromXml(child));
          case "urn:hl7-org:v3|typeId" -> precondition2.setTypeId(IiParser.fromXml(child));
          case "urn:hl7-org:v3|templateId" -> precondition2.addTemplateId(IiParser.fromXml(child));
          case "urn:hl7-org:sdtc|conjunctionCode" -> precondition2.setConjuntionCode(CsParser.fromXml(child));
          case "urn:hl7-org:sdtc|allTrue" -> precondition2.setAllTrue(PreconditionBaseParser.fromXml(child));
          case "urn:hl7-org:sdtc|allFalse" -> precondition2.setAllFalse(PreconditionBaseParser.fromXml(child));
          case "urn:hl7-org:sdtc|atLeastOneTrue" -> precondition2.setAtLeastOneTrue(PreconditionBaseParser.fromXml(child));
          case "urn:hl7-org:sdtc|atLeastOneFalse" -> precondition2.setAtLeastOneFalse(PreconditionBaseParser.fromXml(child));
          case "urn:hl7-org:sdtc|onlyOneTrue" -> precondition2.setOnlyOneTrue(PreconditionBaseParser.fromXml(child));
          case "urn:hl7-org:sdtc|onlyOneFalse" -> precondition2.setOnlyOneFalse(PreconditionBaseParser.fromXml(child));
          case "urn:hl7-org:sdtc|criterion" -> precondition2.setCriterion(CriterionParser.fromXml(child));
        }
      }
    }
    
    return Precondition2.isEmpty(precondition2) ? null : precondition2;
  }
          
}
