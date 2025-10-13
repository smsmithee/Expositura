package com.expositura.model.fhir.r5.datatypes;

import com.expositura.model.datatype.CodeableConcept;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * FHIR R5 Identifier datatype.
 */
public class Identifier {
    public enum IdentifierUse { usual, official, temp, secondary, old }

    private IdentifierUse use;
    private CodeableConcept type;
    private String system;
    private String value;
    private Period period;
    private Reference assigner;
    private LocalDateTime issued; // convenience extension

    public IdentifierUse getUse() {
        return use;
    }

    public void setUse(IdentifierUse use) {
        this.use = use;
    }

    public CodeableConcept getType() {
        return type;
    }

    public void setType(CodeableConcept type) {
        this.type = type;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }

    public Reference getAssigner() {
        return assigner;
    }

    public void setAssigner(Reference assigner) {
        this.assigner = assigner;
    }

    public LocalDateTime getIssued() {
        return issued;
    }

    public void setIssued(LocalDateTime issued) {
        this.issued = issued;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Identifier that = (Identifier) o;
        return use == that.use && Objects.equals(type, that.type) && Objects.equals(system, that.system) && Objects.equals(value, that.value) && Objects.equals(period, that.period) && Objects.equals(assigner, that.assigner) && Objects.equals(issued, that.issued);
    }

    @Override
    public int hashCode() {
        return Objects.hash(use, type, system, value, period, assigner, issued);
    }
}


