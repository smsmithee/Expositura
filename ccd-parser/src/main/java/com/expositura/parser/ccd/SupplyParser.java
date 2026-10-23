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

import com.expositura.model.ccd.Supply;
import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Parses the Supply XML element to the java object or from the object to the xml
 * 
 * @author Sean Smith
 */
public class SupplyParser {
  
  public static Supply fromXml(final Node node) {
    final Supply supply = new Supply();
    
    // First get the attributes if any
    final NamedNodeMap attributes = node.getAttributes();
    if (attributes != null && attributes.getLength() > 0) {
      
      // nullFlavor
      final Node nullFlavor = attributes.getNamedItem("nullFlavor");
      if (nullFlavor != null) {
        supply.setNullFlavor(nullFlavor.getNodeValue());
      }
      
      // classCode
      final Node classCode = attributes.getNamedItem("classCode");
      if (classCode != null) {
        supply.setClassCode(classCode.getNodeValue());
      }
      
      // moodCode
      final Node moodCode = attributes.getNamedItem("moodCode");
      if (moodCode != null) {
        supply.setMoodCode(moodCode.getNodeValue());
      }
      
    }
    
    final NodeList nodeList = node.getChildNodes();
    
    for (int i = 0; i < nodeList.getLength(); i++) {
      final Node child = nodeList.item(i);
      
      // Ignore children with no attributes and no children, they are text such as newlines for formatted XML
      if (child.hasAttributes() || child.hasChildNodes()) {
        switch (child.getNamespaceURI() + "|" + child.getLocalName()) {
          case "urn:hl7-org:v3|realmCode" -> supply.addRealmCode(CsParser.fromXml(child));
          case "urn:hl7-org:v3|typeId" -> supply.setTypeId(IiParser.fromXml(child));
          case "urn:hl7-org:v3|templateId" -> supply.addTemplateId(IiParser.fromXml(child));
          case "urn:hl7-org:v3|id" -> supply.addId(IiParser.fromXml(child));
          case "urn:hl7-org:v3|code" -> supply.setCode(CdParser.fromXml(child));
          case "urn:hl7-org:v3|text" -> supply.setText(EdParser.fromXml(child));
          case "urn:hl7-org:v3|statusCode" -> supply.setStatusCode(CsParser.fromXml(child));
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
            
            // First look for SXPR_TS type which MUST have 'comp' child element(s)
            if (!comp.isEmpty()) {
              supply.addEffectiveTimeAsSxprTs(SxprTsParser.fromXml(child));
            }
            // Next look for event or offset as either shows it's a EIVL_TS
            else if (event != null || offset != null) {
              supply.addEffectiveTimeAsEivlTs(EivlTsParser.fromXml(child));
            }
            // Next look for phase, period, alignment, or institution specified which means it's a PIVL_TS
            else if (phase != null 
                    || period != null 
                    || childAttributes.getNamedItem("alignment") != null 
                    || childAttributes.getNamedItem("institution") != null) {
              supply.addEffectiveTimeAsPivlTs(PivlTsParser.fromXml(child));
            }
            // Next if it has low, center, width, or high then it's IVL_TS
            else if (low != null || center != null || width != null || high != null) {
              supply.addEffectiveTimeAsIvlTs(IvlTsParser.fromXml(child));
            }
            // If we get here then last resort is SXCM_TS
            else {
              supply.addEffectiveTimeAsSxcmTs(SxcmTsParser.fromXml(child));
            }
          }
          case "urn:hl7-org:v3|priorityCode" -> supply.addPriorityCode(CeParser.fromXml(child));
          case "urn:hl7-org:v3|repeatNumber" -> supply.setRepeatNumber(IvlIntParser.fromXml(child));
          case "urn:hl7-org:v3|independentInd" -> supply.setIndependentInd(BlParser.fromXml(child));
          case "urn:hl7-org:v3|quantity" -> supply.setQuantity(PqParser.fromXml(child));
          case "urn:hl7-org:v3|expectedUseTime" -> supply.setExpectedUseTime(IvlTsParser.fromXml(child));
          case "urn:hl7-org:v3|product" -> supply.setProduct(ProductParser.fromXml(child));
          case "urn:hl7-org:v3|subject" -> supply.setSubject(SubjectParser.fromXml(child));
          case "urn:hl7-org:v3|specimen" -> supply.addSpecimen(SpecimenParser.fromXml(child));
          case "urn:hl7-org:v3|performer" -> supply.addPerformer(Performer2Parser.fromXml(child));
          case "urn:hl7-org:v3|author" -> supply.addAuthor(AuthorParser.fromXml(child));
          case "urn:hl7-org:v3|informant" -> supply.addInformant(InformantParser.fromXml(child));
          case "urn:hl7-org:v3|partipant" -> supply.addParticipant(Participant2Parser.fromXml(child));
          case "urn:hl7-org:v3|entryRelationship" -> supply.addEntryRelationship(EntryRelationshipParser.fromXml(child));
          case "urn:hl7-org:v3|reference" -> supply.addReference(ReferenceParser.fromXml(child));
          case "urn:hl7-org:v3|precondition" -> supply.addPrecondition(PreconditionParser.fromXml(child));
          case "urn:hl7-org:sdtc|inFulfillmentOf1" -> supply.addInFulfillmentOf1(InFulfillmentOf1Parser.fromXml(child));
        }
      }
    }
    
    return Supply.isEmpty(supply) ? null : supply;
  }
          
}
