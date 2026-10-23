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

import com.expositura.model.ccd.EntryRelationship;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Parses the EntryRelationship XML element to the java object or from the object to the xml
 * 
 * @author Sean Smith
 */
public class EntryRelationshipParser {
  
  public static EntryRelationship fromXml(final Node node) {
    final EntryRelationship entryRelationship = new EntryRelationship();
    
    // First get the attributes if any
    final NamedNodeMap attributes = node.getAttributes();
    if (attributes != null && attributes.getLength() > 0) {
      
      // nullFlavor
      final Node nullFlavor = attributes.getNamedItem("nullFlavor");
      if (nullFlavor != null) {
        entryRelationship.setNullFlavor(nullFlavor.getNodeValue());
      }
      
      // typeCode
      final Node typeCode = attributes.getNamedItem("typeCode");
      if (typeCode != null) {
        entryRelationship.setTypeCode(typeCode.getNodeValue());
      }
      
      // inversionInd
      final Node inversionInd = attributes.getNamedItem("inversionInd");
      if (inversionInd != null) {
        entryRelationship.setInversionInd(Boolean.valueOf(inversionInd.getNodeValue()));
      }
      
      // contextConductionInd
      final Node contextConductionInd = attributes.getNamedItem("contextConductionInd");
      if (contextConductionInd != null) {
        entryRelationship.setContextConductionInd(Boolean.valueOf(contextConductionInd.getNodeValue()));
      }
      
      // negationInd
      final Node negationInd = attributes.getNamedItem("negationInd");
      if (negationInd != null) {
        entryRelationship.setNegationInd(Boolean.valueOf(negationInd.getNodeValue()));
      }
      
    }
    
    final NodeList nodeList = node.getChildNodes();
    
    for (int i = 0; i < nodeList.getLength(); i++) {
      final Node child = nodeList.item(i);
      
      // Ignore children with no attributes and no children, they are text such as newlines for formatted XML
      if (child.hasAttributes() || child.hasChildNodes()) {
        switch (child.getNamespaceURI() + "|" + child.getLocalName()) {
          case "urn:hl7-org:v3|realmCode" -> entryRelationship.addRealmCode(CsParser.fromXml(child));
          case "urn:hl7-org:v3|typeId" -> entryRelationship.setTypeId(IiParser.fromXml(child));
          case "urn:hl7-org:v3|templateId" -> entryRelationship.addTemplateId(IiParser.fromXml(child));
          case "urn:hl7-org:v3|sequenceNumber" -> entryRelationship.setSequenceNumber(IntParser.fromXml(child));
          case "urn:hl7-org:v3|seperatableInd" -> entryRelationship.setSeperatableInd(BlParser.fromXml(child));
          case "urn:hl7-org:v3|act" -> entryRelationship.setAct(ActParser.fromXml(child));
          case "urn:hl7-org:v3|encounter" -> entryRelationship.setEncounter(EncounterParser.fromXml(child));
          case "urn:hl7-org:v3|observation" -> entryRelationship.setObservation(ObservationParser.fromXml(child));
          case "urn:hl7-org:v3|observationMedia" -> entryRelationship.setObservationMedia(ObservationMediaParser.fromXml(child));
          case "urn:hl7-org:v3|organizer" -> entryRelationship.setOrganizer(OrganizerParser.fromXml(child));
          case "urn:hl7-org:v3|procedure" -> entryRelationship.setProcedure(ProcedureParser.fromXml(child));
          case "urn:hl7-org:v3|regionOfInterest" -> entryRelationship.setRegionOfInterest(RegionOfInterestParser.fromXml(child));
          case "urn:hl7-org:v3|substanceAdministration" -> entryRelationship.setSubstanceAdministration(SubstanceAdministrationParser.fromXml(child));
          case "urn:hl7-org:v3|supply" -> entryRelationship.setSupply(SupplyParser.fromXml(child));
        }
      }
    }
    
    return EntryRelationship.isEmpty(entryRelationship) ? null : entryRelationship;
  }
          
}
