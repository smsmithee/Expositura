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

import com.expositura.model.ccd.SubstanceAdministration;
import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Parses the SubstanceAdministration XML element to the java object or from the object to the xml
 * 
 * @author Sean Smith
 */
public class SubstanceAdministrationParser {
  
  public static SubstanceAdministration fromXml(final Node node) {
    final SubstanceAdministration substanceAdministration = new SubstanceAdministration();
    
    // First get the attributes if any
    final NamedNodeMap attributes = node.getAttributes();
    if (attributes != null && attributes.getLength() > 0) {
      
      // nullFlavor
      final Node nullFlavor = attributes.getNamedItem("nullFlavor");
      if (nullFlavor != null) {
        substanceAdministration.setNullFlavor(nullFlavor.getNodeValue());
      }
      
      // classCode
      final Node classCode = attributes.getNamedItem("classCode");
      if (classCode != null) {
        substanceAdministration.setClassCode(classCode.getNodeValue());
      }
      
      // moodCode
      final Node moodCode = attributes.getNamedItem("moodCode");
      if (moodCode != null) {
        substanceAdministration.setMoodCode(moodCode.getNodeValue());
      }
      
      // negationInd
      final Node negationInd = attributes.getNamedItem("negationInd");
      if (negationInd != null) {
        substanceAdministration.setNegationInd(Boolean.valueOf(negationInd.getNodeValue()));
      }
    }
    
    final NodeList nodeList = node.getChildNodes();
    
    for (int i = 0; i < nodeList.getLength(); i++) {
      final Node child = nodeList.item(i);
      
      // Ignore children with no attributes and no children, they are text such as newlines for formatted XML
      if (child.hasAttributes() || child.hasChildNodes()) {
        switch (child.getNamespaceURI() + "|" + child.getLocalName()) {
          case "urn:hl7-org:v3|realmCode" -> substanceAdministration.addRealmCode(CsParser.fromXml(child));
          case "urn:hl7-org:v3|typeId" -> substanceAdministration.setTypeId(IiParser.fromXml(child));
          case "urn:hl7-org:v3|templateId" -> substanceAdministration.addTemplateId(IiParser.fromXml(child));
          case "urn:hl7-org:v3|id" -> substanceAdministration.addId(IiParser.fromXml(child));
          case "urn:hl7-org:v3|code" -> substanceAdministration.setCode(CdParser.fromXml(child));
          case "urn:hl7-org:v3|text" -> substanceAdministration.setText(EdParser.fromXml(child));
          case "urn:hl7-org:v3|statusCode" -> substanceAdministration.setStatusCode(CsParser.fromXml(child));
          case "urn:hl7-org:v3|effectiveTime" -> {
            // This element may be one of five different datatypes, determine which one
            final NamedNodeMap childAttributes = child.getAttributes();
            
            // These are the possible child nodes for the five different datatypes
            Node low = null;
            Node center = null;
            Node width = null;
            Node high = null;
            Node event = null;
            Node offset = null;
            Node phase = null;
            Node period = null;
            List<Node> comp = new ArrayList<>();
            
            // Figure out which children are present
            if (child.hasChildNodes()) {
              final NodeList subNodes = child.getChildNodes();
              for (int j = 0; j < subNodes.getLength(); j++) {
                if (subNodes.item(j).getLocalName() != null) {
                  switch (subNodes.item(j).getLocalName()) {
                    case "low" -> low = subNodes.item(j);
                    case "center" -> center = subNodes.item(j);
                    case "width" -> width = subNodes.item(j);
                    case "high" -> high = subNodes.item(j);
                    case "event" -> event = subNodes.item(j);
                    case "offset" -> offset = subNodes.item(j);
                    case "phase" -> phase = subNodes.item(j);
                    case "period" -> period = subNodes.item(j);
                    case "comp" -> comp.add(subNodes.item(j));
                  }
                }
              }
            }
            
            // First look for SXPR_TS type which MUST have 'comp' child element(s)
            if (!comp.isEmpty()) {
              substanceAdministration.addEffectiveTimeAsSxprTs(SxprTsParser.fromXml(child));
            }
            // Next look for event or offset as either shows it's a EIVL_TS
            else if (event != null || offset != null) {
              substanceAdministration.addEffectiveTimeAsEivlTs(EivlTsParser.fromXml(child));
            }
            // Next look for phase, period, alignment, or institution specified which means it's a PIVL_TS
            else if (phase != null 
                    || period != null 
                    || childAttributes.getNamedItem("alignment") != null 
                    || childAttributes.getNamedItem("institution") != null) {
              substanceAdministration.addEffectiveTimeAsPivlTs(PivlTsParser.fromXml(child));
            }
            // Next if it has low, center, width, or high then it's IVL_TS
            else if (low != null || center != null || width != null || high != null) {
              substanceAdministration.addEffectiveTimeAsIvlTs(IvlTsParser.fromXml(child));
            }
            // If we get here then last resort is SXCM_TS
            else {
              substanceAdministration.addEffectiveTimeAsSxcmTs(SxcmTsParser.fromXml(child));
            }
          }
          case "urn:hl7-org:v3|priorityCode" -> substanceAdministration.setPriorityCode(CeParser.fromXml(child));
          case "urn:hl7-org:v3|repeatNumber" -> substanceAdministration.setRepeatNumber(IvlIntParser.fromXml(child));
          case "urn:hl7-org:v3|routeCode" -> substanceAdministration.setRouteCode(CeParser.fromXml(child));
          case "urn:hl7-org:v3|approachSiteCode" -> substanceAdministration.addApproachSiteCode(CdParser.fromXml(child));
          case "urn:hl7-org:v3|doseQuantity" -> substanceAdministration.setDoseQuantity(IvlPqParser.fromXml(child));
          case "urn:hl7-org:v3|rateQuantity" -> substanceAdministration.setRateQuantity(IvlPqParser.fromXml(child));
          case "urn:hl7-org:v3|maxDoseQuantity" -> substanceAdministration.setMaxDoseQuantity(RtoPqPqParser.fromXml(child));
          case "urn:hl7-org:v3|administrationUnitCode" -> substanceAdministration.setAdministrationUnitCode(CeParser.fromXml(child));
          case "urn:hl7-org:v3|consumable" -> substanceAdministration.setConsumable(ConsumableParser.fromXml(child));
          case "urn:hl7-org:v3|subject" -> substanceAdministration.setSubject(SubjectParser.fromXml(child));
          case "urn:hl7-org:v3|specimen" -> substanceAdministration.addSpecimen(SpecimenParser.fromXml(child));
          case "urn:hl7-org:v3|performer" -> substanceAdministration.addPerformer(Performer2Parser.fromXml(child));
          case "urn:hl7-org:v3|author" -> substanceAdministration.addAuthor(AuthorParser.fromXml(child));
          case "urn:hl7-org:v3|informant" -> substanceAdministration.addInformant(InformantParser.fromXml(child));
          case "urn:hl7-org:v3|partipant" -> substanceAdministration.addParticipant(Participant2Parser.fromXml(child));
          case "urn:hl7-org:v3|entryRelationship" -> substanceAdministration.addEntryRelationship(EntryRelationshipParser.fromXml(child));
          case "urn:hl7-org:v3|reference" -> substanceAdministration.addReference(ReferenceParser.fromXml(child));
          case "urn:hl7-org:v3|precondition" -> substanceAdministration.addPrecondition(PreconditionParser.fromXml(child));
          case "urn:hl7-org:sdtc|inFulfillmentOf1" -> substanceAdministration.addInFulfillmentOf1(InFulfillmentOf1Parser.fromXml(child));
        }
      }
    }
    
    return SubstanceAdministration.isEmpty(substanceAdministration) ? null : substanceAdministration;
  }
          
}
