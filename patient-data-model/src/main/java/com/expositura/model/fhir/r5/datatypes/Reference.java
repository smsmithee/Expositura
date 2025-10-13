package com.expositura.model.fhir.r5.datatypes;

import java.util.Objects;

/**
 * FHIR R5 Reference datatype.
 */
public class Reference {
    private String reference;
    private String type;
    private Identifier identifier;
    private String display;

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    public void setIdentifier(Identifier identifier) {
        this.identifier = identifier;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reference reference1 = (Reference) o;
        return Objects.equals(reference, reference1.reference) && Objects.equals(type, reference1.type) && Objects.equals(identifier, reference1.identifier) && Objects.equals(display, reference1.display);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reference, type, identifier, display);
    }
}


