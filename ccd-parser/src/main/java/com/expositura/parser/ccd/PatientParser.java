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

import com.expositura.model.ccd.Patient;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Parses the Patient XML element to the java object or from the object to the xml
 * 
 * @author Sean Smith
 */
public class PatientParser {
  
  public static Patient fromXml(final Node node) {
    final Patient patient = new Patient();
    
    // First get the attributes if any
    final NamedNodeMap attributes = node.getAttributes();
    if (attributes != null && attributes.getLength() > 0) {
      
      // nullFlavor
      final Node nullFlavor = attributes.getNamedItem("nullFlavor");
      if (nullFlavor != null) {
        patient.setNullFlavor(nullFlavor.getNodeValue());
      }
      
      // classCode
      final Node classCode = attributes.getNamedItem("classCode");
      if (classCode != null) {
        patient.setClassCode(classCode.getNodeValue());
      }
      
      // determinerCode
      final Node determinerCode = attributes.getNamedItem("determinerCode");
      if (determinerCode != null) {
        patient.setDeterminerCode(determinerCode.getNodeValue());
      }
      
    }
    
    final NodeList nodeList = node.getChildNodes();
    
    for (int i = 0; i < nodeList.getLength(); i++) {
      final Node child = nodeList.item(i);
      
      // Ignore children with no attributes and no children, they are text such as newlines for formatted XML
      if (child.hasAttributes() || child.hasChildNodes()) {
        switch (child.getNamespaceURI() + "|" + child.getLocalName()) {
          case "urn:hl7-org:v3|realmCode" -> patient.addRealmCode(CsParser.fromXml(child));
          case "urn:hl7-org:v3|typeId" -> patient.setTypeId(IiParser.fromXml(child));
          case "urn:hl7-org:v3|templateId" -> patient.addTemplateId(IiParser.fromXml(child));
          case "urn:hl7-org:v3|id" -> patient.setId(IiParser.fromXml(child));
          case "urn:hl7-org:v3|name" -> patient.addName(PnParser.fromXml(child));
          case "urn:hl7-org:sdtc|sdtcDesc" -> patient.setDesc(EdParser.fromXml(child));
          case "urn:hl7-org:v3|administrativeGenderCode" -> patient.setAdministrativeGenderCode(CeParser.fromXml(child));
          case "urn:hl7-org:v3|birthTime" -> patient.setBirthTime(TsParser.fromXml(child));
          case "urn:hl7-org:sdtc|sdtcDeceasedInd" -> patient.setDeceasedInd(BlParser.fromXml(child));
          case "urn:hl7-org:sdtc|sdtcDeceasedTime" -> patient.setDeceasedTime(TsParser.fromXml(child));
          case "urn:hl7-org:sdtc|sdtcMultipleBirthInd" -> patient.setMultipleBirthInd(BlParser.fromXml(child));
          case "urn:hl7-org:sdtc|sdtcMultipleBirthOrderNumber" -> patient.setMultipleBirthOrderNumber(IntPosParser.fromXml(child));
          case "urn:hl7-org:v3|maritalStatusCode" -> patient.setMaritalStatusCode(CeParser.fromXml(child));
          case "urn:hl7-org:v3|religiousAffiliationCode" -> patient.setReligiousAffiliationCode(CeParser.fromXml(child));
          case "urn:hl7-org:v3|raceCode" -> patient.addRaceCode(CeParser.fromXml(child));
          case "urn:hl7-org:sdtc|sdtcRaceCode" -> patient.addRaceCode(CeParser.fromXml(child));
          case "urn:hl7-org:v3|ethnicGroupCode" -> patient.addEthnicGroupCode(CeParser.fromXml(child));
          case "urn:hl7-org:sdtc|sdtcEthnicGroupCode" -> patient.addEthnicGroupCode(CeParser.fromXml(child));
          case "urn:hl7-org:v3|guardian" -> patient.addGuardian(GuardianParser.fromXml(child));
          case "urn:hl7-org:v3|birthplace" -> patient.setBirthplace(BirthplaceParser.fromXml(child));
          case "urn:hl7-org:v3|languageCommunication" -> patient.addLanguageCommunication(LanguageCommunicationParser.fromXml(child));
        }
      }
    }
    
    return Patient.isEmpty(patient) ? null : patient;
  }
          
}
