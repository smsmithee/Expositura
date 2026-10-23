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

import com.expositura.model.ccd.Criterion;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Parses the Criterion XML element to the java object or from the object to the xml
 * 
 * @author Sean Smith
 */
public class CriterionParser {
  
  public static Criterion fromXml(final Node node) {
    final Criterion criterion = new Criterion();
    
    // First get the attributes if any
    final NamedNodeMap attributes = node.getAttributes();
    if (attributes != null && attributes.getLength() > 0) {
      
      // nullFlavor
      final Node nullFlavor = attributes.getNamedItem("nullFlavor");
      if (nullFlavor != null) {
        criterion.setNullFlavor(nullFlavor.getNodeValue());
      }
      
      // classCode
      final Node classCode = attributes.getNamedItem("classCode");
      if (classCode != null) {
        criterion.setClassCode(classCode.getNodeValue());
      }
      
      // moodCode
      final Node moodCode = attributes.getNamedItem("moodCode");
      if (moodCode != null) {
        criterion.setMoodCode(moodCode.getNodeValue());
      }
      
    }
    
    final NodeList nodeList = node.getChildNodes();
    
    for (int i = 0; i < nodeList.getLength(); i++) {
      final Node child = nodeList.item(i);
      
      // Ignore children with no attributes and no children, they are text such as newlines for formatted XML
      if (child.hasAttributes() || child.hasChildNodes()) {
        switch (child.getNamespaceURI() + "|" + child.getLocalName()) {
          case "urn:hl7-org:v3|realmCode" -> criterion.addRealmCode(CsParser.fromXml(child));
          case "urn:hl7-org:v3|typeId" -> criterion.setTypeId(IiParser.fromXml(child));
          case "urn:hl7-org:v3|templateId" -> criterion.addTemplateId(IiParser.fromXml(child));
          case "urn:hl7-org:v3|code" -> criterion.setCode(CdParser.fromXml(child));
          case "urn:hl7-org:v3|text" -> criterion.setText(EdParser.fromXml(child));
          case "urn:hl7-org:v3|value" -> {
            
            // If there is no type attribute then we cannot parse the value (won't know how to parse it)
            if (child.getAttributes() != null) {
              final Node type = child.getAttributes().getNamedItem("type");
              if (type != null) {
                switch (type.getNodeValue().toUpperCase()) {
                  case "BL" -> criterion.setValueBl(BlParser.fromXml(child));
                  case "ED" -> criterion.setValueEd(EdParser.fromXml(child));
                  case "ST" -> criterion.setValueSt(StParser.fromXml(child));
                  case "CD" -> criterion.setValueCd(CdParser.fromXml(child));
                  case "CV" -> criterion.setValueCv(CvParser.fromXml(child));
                  case "CE" -> criterion.setValueCe(CeParser.fromXml(child));
                  case "SC" -> criterion.setValueSc(ScParser.fromXml(child));
                  case "II" -> criterion.setValueIi(IiParser.fromXml(child));
                  case "TEL" -> criterion.setValueTel(TelParser.fromXml(child));
                  case "AD" -> criterion.setValueAd(AdParser.fromXml(child));
                  case "EN" -> criterion.setValueEn(EnParser.fromXml(child));
                  case "INT" -> criterion.setValueInt(IntParser.fromXml(child));
                  case "REAL" -> criterion.setValueReal(RealParser.fromXml(child));
                  case "PQ" -> criterion.setValuePq(PqParser.fromXml(child));
                  case "MO" -> criterion.setValueMo(MoParser.fromXml(child));
                  case "TS" -> criterion.setValueTs(TsParser.fromXml(child));
                  case "IVL_PQ" -> criterion.setValueIvlPq(IvlPqParser.fromXml(child));
                  case "IVL_TS" -> criterion.setValueIvlTs(IvlTsParser.fromXml(child));
                  case "PIVL_TS" -> criterion.setValuePivlTs(PivlTsParser.fromXml(child));
                  case "EIVL_TS" -> criterion.setValueEivlTs(EivlTsParser.fromXml(child));
                  case "SXPR_TS" -> criterion.setValueSxprTs(SxprTsParser.fromXml(child));
                }
              }
            }
          }
          
        }
      }
    }
    
    return Criterion.isEmpty(criterion) ? null : criterion;
  }
          
}
