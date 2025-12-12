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
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Parses the ClinicalDocument XML element to the java object or from the object to the xml
 * 
 * @author Sean Smith
 */
public class ClinicalDocumentParser {
  
  public static ClinicalDocument fromXml(final Node node) {
    final ClinicalDocument ccd = new ClinicalDocument();
    
    // First get the attributes if any
    final NamedNodeMap attributes = node.getAttributes();
    if (attributes != null && attributes.getLength() > 0) {
      
      // nullFlavor
      final Node nullFlavor = attributes.getNamedItem("nullFlavor");
      if (nullFlavor != null) {
        ccd.setNullFlavor(nullFlavor.getNodeValue());
      }
      
      // classCode
      final Node classCode = attributes.getNamedItem("classCode");
      if (classCode != null) {
        ccd.setClassCode(classCode.getNodeValue());
      }
      
      // moodCode
      final Node moodCode = attributes.getNamedItem("moodCode");
      if (moodCode != null) {
        ccd.setMoodCode(moodCode.getNodeValue());
      }
    }
    
    final NodeList nodeList = node.getChildNodes();
    
    for (int i = 0; i < nodeList.getLength(); i++) {
      final Node child = nodeList.item(i);
      
      // Ignore children with no attributes and no children, they are text such as newlines for formatted XML
      if (child.hasAttributes() || child.hasChildNodes()) {
        switch (child.getNamespaceURI() + "|" + child.getLocalName()) {
          case "urn:hl7-org:v3|realmCode" -> ccd.addRealmCode(CsParser.fromXml(child));
          case "urn:hl7-org:v3|typeId" -> ccd.setTypeId(IiParser.fromXml(child));
          case "urn:hl7-org:v3|templateId" -> ccd.addTemplateId(IiParser.fromXml(child));
          case "urn:hl7-org:v3|id" -> ccd.setId(IiParser.fromXml(child));
          case "urn:hl7-org:sdtc|category" -> ccd.addCategory(CdParser.fromXml(child));
          case "urn:hl7-org:v3|code" -> ccd.setCode(CeParser.fromXml(child));
          case "urn:hl7-org:v3|title" -> ccd.setTitle(StParser.fromXml(child));
          case "urn:hl7-org:sdtc|statusCode" -> ccd.setStatusCode(CsParser.fromXml(child));
          case "urn:hl7-org:v3|effectiveTime" -> ccd.setEffectiveTime(TsParser.fromXml(child));
          case "urn:hl7-org:v3|confidentialityCode" -> ccd.setConfidentialityCode(CeParser.fromXml(child));
          case "urn:hl7-org:v3|languageCode" -> ccd.setLanguageCode(CsParser.fromXml(child));
          case "urn:hl7-org:v3|setId" -> ccd.setSettId(IiParser.fromXml(child));
          case "urn:hl7-org:v3|versionNumber" -> ccd.setVersionNumber(IntParser.fromXml(child));
          case "urn:hl7-org:v3|copyTime" -> ccd.setCopyTime(TsParser.fromXml(child));
          case "urn:hl7-org:v3|recordTarget" -> ccd.addRecordTarget(RecordTargetParser.fromXml(child));
          case "urn:hl7-org:v3|author" -> ccd.addAuthor(AuthorParser.fromXml(child));
          case "urn:hl7-org:v3|dataEnterer" -> ccd.setDataEnterer(DataEntererParser.fromXml(child));
          case "urn:hl7-org:v3|informant" -> ccd.addInformant(InformantParser.fromXml(child));
          case "urn:hl7-org:v3|custodian" -> ccd.setCustodian(CustodianParser.fromXml(child));
          case "urn:hl7-org:v3|informationRecipient" -> ccd.addInformationRecipient(InformationRecipientParser.fromXml(child));
        }
      }
    }
    
    return ClinicalDocument.isEmpty(ccd) ? null : ccd;
  }
          
}
