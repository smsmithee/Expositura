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

import com.expositura.model.ccd.Ad;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Parses a AD datatype XML element to the java object or from the object to the xml
 * 
 * @author Sean Smith
 */
public class AdParser {
  
  public static Ad fromXml(final Node node) {
    final Ad ad = new Ad();
    
    // Start by getting the attributes
    final NamedNodeMap attributes = node.getAttributes();
    if (attributes != null && attributes.getLength() > 0) {
      
      // nullFlavor
      final Node nullFlavor = attributes.getNamedItem("nullFlavor");
      if (nullFlavor != null) {
        ad.setNullFlavor(nullFlavor.getNodeValue());
      }
      
      // isNotOrdered
      final Node isNotOrdered = attributes.getNamedItem("isNotOrdered");
      if (isNotOrdered != null) {
        ad.setIsNotOrdered(Boolean.valueOf(isNotOrdered.getNodeValue()));
      }
      
      // use (special case, use can have multiple values with space seperator
      final Node use = attributes.getNamedItem("use");
      if (use != null) {
        final String useValue = use.getNodeValue();
        if (useValue != null) {
          for (final String singleUse : StringUtils.split(useValue)) {
            ad.addUse(singleUse);
          }
        }
      }
      
    }
    
    // Now work thru children
    final NodeList nodeList = node.getChildNodes();
    
    for (int i = 0; i < nodeList.getLength(); i++) {
      final Node child = nodeList.item(i);
      
      // This datatype can have text which means it's just a string for the address
      if (child.hasAttributes() || child.hasChildNodes()) {
        switch (child.getNamespaceURI() + "|" + child.getLocalName()) {
          case "urn:hl7-org:v3|delimiter" -> ad.setDelimiter(AdxpParser.fromXml(child));
          case "urn:hl7-org:v3|country" -> ad.setCountry(AdxpParser.fromXml(child));
          case "urn:hl7-org:v3|state" -> ad.setState(AdxpParser.fromXml(child));
          case "urn:hl7-org:v3|county" -> ad.setCounty(AdxpParser.fromXml(child));
          case "urn:hl7-org:v3|city" -> ad.setCity(AdxpParser.fromXml(child));
          case "urn:hl7-org:v3|postalCode" -> ad.setPostalCode(AdxpParser.fromXml(child));
          case "urn:hl7-org:v3|streetAddressLine" -> ad.addStreetAddressLine(AdxpParser.fromXml(child));
          case "urn:hl7-org:v3|houseNumber" -> ad.setHouseNumber(AdxpParser.fromXml(child));
          case "urn:hl7-org:v3|houseNumberNumeric" -> ad.setHouseNumberNumeric(AdxpParser.fromXml(child));
          case "urn:hl7-org:v3|direction" -> ad.setDirection(AdxpParser.fromXml(child));
          case "urn:hl7-org:v3|streetName" -> ad.setStreetName(AdxpParser.fromXml(child));
          case "urn:hl7-org:v3|streetNameBase" -> ad.setStreetNameBase(AdxpParser.fromXml(child));
          case "urn:hl7-org:v3|streetNameType" -> ad.setStreetNameType(AdxpParser.fromXml(child));
          case "urn:hl7-org:v3|additionalLocator" -> ad.setAdditionalLocator(AdxpParser.fromXml(child));
          case "urn:hl7-org:v3|unitID" -> ad.setUnitId(AdxpParser.fromXml(child));
          case "urn:hl7-org:v3|unitType" -> ad.setUnitType(AdxpParser.fromXml(child));
          case "urn:hl7-org:v3|careOf" -> ad.setCareOf(AdxpParser.fromXml(child));
          case "urn:hl7-org:v3|censusTract" -> ad.setCensusTract(AdxpParser.fromXml(child));
          case "urn:hl7-org:v3|deliveryAddressLine" -> ad.setDeliveryAddressLine(AdxpParser.fromXml(child));
          case "urn:hl7-org:v3|deliveryInstallationType" -> ad.setDeliveryInstallationType(AdxpParser.fromXml(child));
          case "urn:hl7-org:v3|deliveryInstallationArea" -> ad.setDeliveryInstallationArea(AdxpParser.fromXml(child));
          case "urn:hl7-org:v3|deliveryInstallationQualifier" -> ad.setDeliveryInstallationQualifier(AdxpParser.fromXml(child));
          case "urn:hl7-org:v3|deliveryMode" -> ad.setDeliveryMode(AdxpParser.fromXml(child));
          case "urn:hl7-org:v3|deliveryModeIdentifier" -> ad.setDeliveryModeIdentifier(AdxpParser.fromXml(child));
          case "urn:hl7-org:v3|buildingNumberSuffix" -> ad.setBuildingNumberSuffix(AdxpParser.fromXml(child));
          case "urn:hl7-org:v3|postBox" -> ad.setPostBox(AdxpParser.fromXml(child));
          case "urn:hl7-org:v3|precinct" -> ad.setPrecinct(AdxpParser.fromXml(child));
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
              ad.addUseablePeriodAsEivlTs(EivlTsParser.fromXml(child));
            }
            // Next look for phase, period, alignment, or institution specified which means it's a PIVL_TS
            else if (phase != null 
                    || period != null 
                    || childAttributes.getNamedItem("alignment") != null 
                    || childAttributes.getNamedItem("institution") != null) {
              ad.addUseablePeriodAsPivlTs(PivlTsParser.fromXml(child));
            }
            // Catch all is IVL_TS
            else {
              ad.addUseablePeriodAsIvlTs(IvlTsParser.fromXml(child));
            }
          }
        }
      } else {
        ad.setXmlText(child.getNodeValue());
      } 
    }
    
    return Ad.isEmpty(ad) ? null : ad;
  }
          
}
