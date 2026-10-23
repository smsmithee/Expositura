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

import com.expositura.model.ccd.Entry;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Parses the Entry XML element to the java object or from the object to the xml
 * 
 * @author Sean Smith
 */
public class EntryParser {
  
  public static Entry fromXml(final Node node) {
    final Entry entry = new Entry();
    
    // First get the attributes if any
    final NamedNodeMap attributes = node.getAttributes();
    if (attributes != null && attributes.getLength() > 0) {
      
      // nullFlavor
      final Node nullFlavor = attributes.getNamedItem("nullFlavor");
      if (nullFlavor != null) {
        entry.setNullFlavor(nullFlavor.getNodeValue());
      }
      
      // typeCode
      final Node typeCode = attributes.getNamedItem("typeCode");
      if (typeCode != null) {
        entry.setTypeCode(typeCode.getNodeValue());
      }
      
      // contextConductionInd
      final Node contextConductionInd = attributes.getNamedItem("contextConductionInd");
      if (contextConductionInd != null) {
        entry.setContextConductionInd(Boolean.valueOf(contextConductionInd.getNodeValue()));
      }
      
    }
    
    final NodeList nodeList = node.getChildNodes();
    
    for (int i = 0; i < nodeList.getLength(); i++) {
      final Node child = nodeList.item(i);
      
      // Ignore children with no attributes and no children, they are text such as newlines for formatted XML
      if (child.hasAttributes() || child.hasChildNodes()) {
        switch (child.getNamespaceURI() + "|" + child.getLocalName()) {
          case "urn:hl7-org:v3|realmCode" -> entry.addRealmCode(CsParser.fromXml(child));
          case "urn:hl7-org:v3|typeId" -> entry.setTypeId(IiParser.fromXml(child));
          case "urn:hl7-org:v3|templateId" -> entry.addTemplateId(IiParser.fromXml(child));
          case "urn:hl7-org:v3|act" -> entry.setAct(ActParser.fromXml(child));
          case "urn:hl7-org:v3|encounter" -> entry.setEncounter(EncounterParser.fromXml(child));
          case "urn:hl7-org:v3|observation" -> entry.setObservation(ObservationParser.fromXml(child));
          case "urn:hl7-org:v3|observationMedia" -> entry.setObservationMedia(ObservationMediaParser.fromXml(child));
          case "urn:hl7-org:v3|organizer" -> entry.setOrganizer(OrganizerParser.fromXml(child));
          case "urn:hl7-org:v3|procedure" -> entry.setProcedure(ProcedureParser.fromXml(child));
          case "urn:hl7-org:v3|regionOfInterest" -> entry.setRegionOfInterest(RegionOfInterestParser.fromXml(child));
          case "urn:hl7-org:v3|substanceAdministration" -> entry.setSubstanceAdministration(SubstanceAdministrationParser.fromXml(child));
          case "urn:hl7-org:v3|supply" -> entry.setSupply(SupplyParser.fromXml(child));
        }
      }
    }
    
    return Entry.isEmpty(entry) ? null : entry;
  }
          
}
