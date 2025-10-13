package com.expositura.model.fhir.r5.resources;

import com.expositura.model.datatype.CodeableConcept;
import com.expositura.model.fhir.r5.datatypes.Identifier;
import com.expositura.model.fhir.r5.datatypes.Period;
import com.expositura.model.fhir.r5.datatypes.Reference;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Simplified FHIR R5 Patient resource bean with common elements.
 * See: https://www.hl7.org/fhir/patient.html
 */
public class Patient {

    public enum AdministrativeGender { male, female, other, unknown }

    public static class HumanName {
        public enum NameUse { usual, official, temp, nickname, anonymous, old, maiden }

        private NameUse use;
        private String text;
        private String family;
        private List<String> given = new ArrayList<>();
        private List<String> prefix = new ArrayList<>();
        private List<String> suffix = new ArrayList<>();
        private Period period;

        public NameUse getUse() { return use; }
        public void setUse(NameUse use) { this.use = use; }
        public String getText() { return text; }
        public void setText(String text) { this.text = text; }
        public String getFamily() { return family; }
        public void setFamily(String family) { this.family = family; }
        public List<String> getGiven() { return given; }
        public void setGiven(List<String> given) { this.given = given; }
        public List<String> getPrefix() { return prefix; }
        public void setPrefix(List<String> prefix) { this.prefix = prefix; }
        public List<String> getSuffix() { return suffix; }
        public void setSuffix(List<String> suffix) { this.suffix = suffix; }
        public Period getPeriod() { return period; }
        public void setPeriod(Period period) { this.period = period; }
    }

    public static class ContactPoint {
        public enum ContactPointSystem { phone, fax, email, pager, url, sms, other }
        public enum ContactPointUse { home, work, temp, old, mobile }
        private ContactPointSystem system;
        private String value;
        private ContactPointUse use;
        private Integer rank;
        private Period period;
        public ContactPointSystem getSystem() { return system; }
        public void setSystem(ContactPointSystem system) { this.system = system; }
        public String getValue() { return value; }
        public void setValue(String value) { this.value = value; }
        public ContactPointUse getUse() { return use; }
        public void setUse(ContactPointUse use) { this.use = use; }
        public Integer getRank() { return rank; }
        public void setRank(Integer rank) { this.rank = rank; }
        public Period getPeriod() { return period; }
        public void setPeriod(Period period) { this.period = period; }
    }

    public static class Address {
        public enum AddressUse { home, work, temp, old, billing }
        public enum AddressType { postal, physical, both }
        private AddressUse use;
        private AddressType type;
        private String text;
        private List<String> line = new ArrayList<>();
        private String city;
        private String district;
        private String state;
        private String postalCode;
        private String country;
        private Period period;
        public AddressUse getUse() { return use; }
        public void setUse(AddressUse use) { this.use = use; }
        public AddressType getType() { return type; }
        public void setType(AddressType type) { this.type = type; }
        public String getText() { return text; }
        public void setText(String text) { this.text = text; }
        public List<String> getLine() { return line; }
        public void setLine(List<String> line) { this.line = line; }
        public String getCity() { return city; }
        public void setCity(String city) { this.city = city; }
        public String getDistrict() { return district; }
        public void setDistrict(String district) { this.district = district; }
        public String getState() { return state; }
        public void setState(String state) { this.state = state; }
        public String getPostalCode() { return postalCode; }
        public void setPostalCode(String postalCode) { this.postalCode = postalCode; }
        public String getCountry() { return country; }
        public void setCountry(String country) { this.country = country; }
        public Period getPeriod() { return period; }
        public void setPeriod(Period period) { this.period = period; }
    }

    public static class PatientCommunication {
        private CodeableConcept language;
        private Boolean preferred;
        public CodeableConcept getLanguage() { return language; }
        public void setLanguage(CodeableConcept language) { this.language = language; }
        public Boolean getPreferred() { return preferred; }
        public void setPreferred(Boolean preferred) { this.preferred = preferred; }
    }

    public static class PatientLink {
        public enum LinkType { replace, refer, seealso }
        private Reference other;
        private LinkType type;
        public Reference getOther() { return other; }
        public void setOther(Reference other) { this.other = other; }
        public LinkType getType() { return type; }
        public void setType(LinkType type) { this.type = type; }
    }

    private List<Identifier> identifier = new ArrayList<>();
    private Boolean active;
    private List<HumanName> name = new ArrayList<>();
    private List<ContactPoint> telecom = new ArrayList<>();
    private AdministrativeGender gender;
    private LocalDate birthDate;
    private List<Address> address = new ArrayList<>();
    private Reference managingOrganization;
    private List<Reference> generalPractitioner = new ArrayList<>();
    private List<PatientCommunication> communication = new ArrayList<>();
    private List<PatientLink> link = new ArrayList<>();

    public List<Identifier> getIdentifier() { return identifier; }
    public void setIdentifier(List<Identifier> identifier) { this.identifier = identifier; }
    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
    public List<HumanName> getName() { return name; }
    public void setName(List<HumanName> name) { this.name = name; }
    public List<ContactPoint> getTelecom() { return telecom; }
    public void setTelecom(List<ContactPoint> telecom) { this.telecom = telecom; }
    public AdministrativeGender getGender() { return gender; }
    public void setGender(AdministrativeGender gender) { this.gender = gender; }
    public LocalDate getBirthDate() { return birthDate; }
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }
    public List<Address> getAddress() { return address; }
    public void setAddress(List<Address> address) { this.address = address; }
    public Reference getManagingOrganization() { return managingOrganization; }
    public void setManagingOrganization(Reference managingOrganization) { this.managingOrganization = managingOrganization; }
    public List<Reference> getGeneralPractitioner() { return generalPractitioner; }
    public void setGeneralPractitioner(List<Reference> generalPractitioner) { this.generalPractitioner = generalPractitioner; }
    public List<PatientCommunication> getCommunication() { return communication; }
    public void setCommunication(List<PatientCommunication> communication) { this.communication = communication; }
    public List<PatientLink> getLink() { return link; }
    public void setLink(List<PatientLink> link) { this.link = link; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Patient patient = (Patient) o;
        return Objects.equals(identifier, patient.identifier) && Objects.equals(active, patient.active) && Objects.equals(name, patient.name) && Objects.equals(telecom, patient.telecom) && gender == patient.gender && Objects.equals(birthDate, patient.birthDate) && Objects.equals(address, patient.address) && Objects.equals(managingOrganization, patient.managingOrganization) && Objects.equals(generalPractitioner, patient.generalPractitioner) && Objects.equals(communication, patient.communication) && Objects.equals(link, patient.link);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier, active, name, telecom, gender, birthDate, address, managingOrganization, generalPractitioner, communication, link);
    }
}


