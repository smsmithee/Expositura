/*
 * Copyright 2025 seansmith.
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
package com.expositura.parser.ccd.util;

import com.expositura.model.ccd.Ce;
import com.expositura.model.ccd.Cs;
import com.expositura.model.ccd.Ii;
import com.expositura.model.ccd.Pn;
import com.expositura.model.ccd.Ts;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Holds various parsing utility methods.
 *
 * @author Sean Smith
 */
public class ParserUtils {

  public static String parseTsToIsoDateString(final Ts ts) {
    // Check for null
    if (ts == null) {
      return null;
    }

    final String value = ts.getValue();

    // Must be at least 4 characters to parse year, less than that and we cannot parse anything
    if (value == null || value.length() < 4) {
      return null;
    }

    final StringBuilder sb = new StringBuilder(value.substring(0, 4)).append("-");

    // Check if original String contains month, if not set to January
    if (value.length() >= 6) {
      sb.append(value.substring(4, 6)).append("-");
    } else {
      sb.append("01").append("-");
    }

    // Check if original String contains day, if not set to the 1st
    if (value.length() >= 8) {
      sb.append(value.substring(6, 8));
    } else {
      sb.append("01");
    }

    // Ignore any time value in original string as only date is desired
    return sb.toString();
  }

  public static Long obtainAgeFromDateOfBirth(final Ts ts) {
    // Check for null
    if (ts == null) {
      return null;
    }

    final String value = ts.getValue();

    // We cannot accurately generate age unless year, month, and day exist for date of birth
    if (value == null || value.length() < 8) {
      return null;
    }

    final int year;
    final int month;
    final int day;

    try {
      year = Integer.parseInt(value.substring(0, 4));
    } catch (final NumberFormatException ex) {
      // If we cannot parse year then we cannot get age
      return null;
    }

    try {
      month = Integer.parseInt(value.substring(4, 6));
    } catch (final NumberFormatException ex) {
      // If we cannot parse month then we cannot get age
      return null;
    }

    try {
      day = Integer.parseInt(value.substring(6, 8));
    } catch (final NumberFormatException ex) {
      // If we cannot parse day then we cannot get age
      return null;
    }

    final LocalDate dob;

    try {
      dob = LocalDate.of(year, month, day);
    } catch (final DateTimeException ex) {
      // We cannot parse the dob so return null for age
      return null;
    }

    return dob.until(LocalDate.now(), ChronoUnit.YEARS);
  }

  /**
   * Parse a CCD CE List/Array datatype into a single string. Uses the first CE only, ignoring the rest. Requires there
   * to at least be a code value. If that's all that is available then it returns the code. If codeSystem is present it
   * will return code@codeSystem. if codeSystem is not present but codeSystemName is then it returns
   * code@codeSystemName.
   *
   * @param ceList The list of CE datatype.
   * @return A String representation of the value of the first CE in the list if present.
   */
  public static String parseCeListToString(final List<Ce> ceList) {
    if (ceList == null) {
      return null;
    }

    final Ce ce = ceList.getFirst();
    if (ce.getCodeSystem() != null) {
      return ce.getCode() + "@" + ce.getCodeSystem();
    } else if (ce.getCodeSystemName() != null) {
      return ce.getCode() + "@" + ce.getCodeSystemName();
    } else {
      return ce.getCode();
    }
  }

  /**
   * Parses a PN (Patient Name) datatype into a single string.
   *
   * @param pn The patient name from a CCD.
   * @return The name in a single string
   */
  public static String parsePnToString(final Pn pn) {
    if (pn == null) {
      return null;
    }

    // First check if there is XML text. If so just return that 
    if (pn.getXmlText() != null) {
      return pn.getXmlText();
    }

    final StringBuilder sb = new StringBuilder();

    // For now we ignore delimiter (have not found a good example to know how to handle it)
    if (pn.getPrefix() != null) {
      pn.getPrefix().forEach(p -> {
        if (p.getXmlText() != null) {
          if (sb.length() > 0) {
            sb.append(" ");
          }
          sb.append(p.getXmlText());
        }
      });
    }

    if (pn.getGiven() != null) {
      pn.getGiven().forEach(g -> {
        if (g.getXmlText() != null) {
          if (sb.length() > 0) {
            sb.append(" ");
          }
          sb.append(g.getXmlText());
        }
      });
    }

    if (pn.getFamily() != null) {
      pn.getFamily().forEach(f -> {
        if (f.getXmlText() != null) {
          if (sb.length() > 0) {
            sb.append(" ");
          }
          sb.append(f.getXmlText());
        }
      });
    }

    if (pn.getSuffix() != null) {
      pn.getSuffix().forEach(s -> {
        if (s.getXmlText() != null) {
          if (sb.length() > 0) {
            sb.append(" ");
          }
          sb.append(s.getXmlText());
        }
      });
    }

    return sb.toString();
  }

  public static String parseIiToString(final Ii ii) {
    if (ii == null) {
      return null;
    }

    if (ii.getExtension() != null && ii.getRoot() != null) {
      return ii.getExtension() + "@" + ii.getRoot();
    } else if (ii.getRoot() != null) {
      return ii.getRoot();
    } else {
      return ii.getExtension();
    }
  }

  public static String parseCsToString(Cs cs) {
    if (cs == null || (cs.getCode() == null)) {
      return null;
    }

    return cs.getCode();
  }

  /**
   * Converts a DOM Node into a map
   * @param node the DOM node
   * @return a map with all attributes and children, grandchildren, etc.
   */
  public static Map<String, Object> nodeToMap(final Node node) {
    final Map<String, Object> map = new HashMap<>();

    // Add attributes
    final NamedNodeMap attributes = node.getAttributes();
    if (attributes != null) {
      for (int i = 0; i < attributes.getLength(); i++) {
        final Node attr = attributes.item(i);
        map.put("@" + attr.getNodeName(), attr.getNodeValue());
      }
    }

    // Add child nodes
    final NodeList children = node.getChildNodes();
    for (int i = 0; i < children.getLength(); i++) {
      final Node child = children.item(i);

      if (child.getNodeType() == Node.TEXT_NODE) {
        final String text = child.getTextContent().trim();
        if (!text.isEmpty()) {
          map.put("_text", text);
        }
      } else if (child.getNodeType() == Node.ELEMENT_NODE) {
        final String name = child.getNodeName();

        // Handle multiple children with same name
        if (map.containsKey(name)) {
          final Object existing = map.get(name);
          if (existing instanceof List) {
            ((List<Object>) existing).add(nodeToMap(child));
          } else {
            List<Object> list = new ArrayList<>();
            list.add(existing);
            list.add(nodeToMap(child));
            map.put(name, list);
          }
        } else {
          map.put(name, nodeToMap(child));
        }
      }
    }

    return map;
  }
}
