package com.expositura.model.fhir.r5.datatypes;

import java.util.Objects;

/**
 * FHIR R5 Coding datatype.
 */
public class Coding {
    private String system;
    private String version;
    private String code;
    private String display;
    private Boolean userSelected;

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public Boolean getUserSelected() {
        return userSelected;
    }

    public void setUserSelected(Boolean userSelected) {
        this.userSelected = userSelected;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coding coding = (Coding) o;
        return Objects.equals(system, coding.system) && Objects.equals(version, coding.version) && Objects.equals(code, coding.code) && Objects.equals(display, coding.display) && Objects.equals(userSelected, coding.userSelected);
    }

    @Override
    public int hashCode() {
        return Objects.hash(system, version, code, display, userSelected);
    }
}


