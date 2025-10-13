package com.expositura.model.fhir.r5.resources;

import com.expositura.model.datatype.CodeableConcept;
import com.expositura.model.fhir.r5.datatypes.Identifier;
import com.expositura.model.fhir.r5.datatypes.Reference;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Simplified FHIR R5 AllergyIntolerance resource bean with common elements.
 * See: https://www.hl7.org/fhir/allergyintolerance.html
 */
public class AllergyIntolerance {

    public enum AllergyIntoleranceType { allergy, intolerance }
    public enum AllergyIntoleranceCategory { food, medication, environment, biologic }
    public enum AllergyIntoleranceCriticality { low, high, unable_to_assess }

    public static class Annotation {
        private Reference authorReference;
        private String authorString;
        private LocalDateTime time;
        private String text;
        public Reference getAuthorReference() { return authorReference; }
        public void setAuthorReference(Reference authorReference) { this.authorReference = authorReference; }
        public String getAuthorString() { return authorString; }
        public void setAuthorString(String authorString) { this.authorString = authorString; }
        public LocalDateTime getTime() { return time; }
        public void setTime(LocalDateTime time) { this.time = time; }
        public String getText() { return text; }
        public void setText(String text) { this.text = text; }
    }

    public static class Reaction {
        private List<CodeableConcept> substance = new ArrayList<>();
        private List<CodeableConcept> manifestation = new ArrayList<>();
        private CodeableConcept description;
        private LocalDateTime onset;
        private AllergyIntoleranceCriticality severity;
        private List<CodeableConcept> exposureRoute = new ArrayList<>();
        private List<Annotation> note = new ArrayList<>();

        public List<CodeableConcept> getSubstance() { return substance; }
        public void setSubstance(List<CodeableConcept> substance) { this.substance = substance; }
        public List<CodeableConcept> getManifestation() { return manifestation; }
        public void setManifestation(List<CodeableConcept> manifestation) { this.manifestation = manifestation; }
        public CodeableConcept getDescription() { return description; }
        public void setDescription(CodeableConcept description) { this.description = description; }
        public LocalDateTime getOnset() { return onset; }
        public void setOnset(LocalDateTime onset) { this.onset = onset; }
        public AllergyIntoleranceCriticality getSeverity() { return severity; }
        public void setSeverity(AllergyIntoleranceCriticality severity) { this.severity = severity; }
        public List<CodeableConcept> getExposureRoute() { return exposureRoute; }
        public void setExposureRoute(List<CodeableConcept> exposureRoute) { this.exposureRoute = exposureRoute; }
        public List<Annotation> getNote() { return note; }
        public void setNote(List<Annotation> note) { this.note = note; }
    }

    private List<Identifier> identifier = new ArrayList<>();
    private CodeableConcept clinicalStatus;
    private CodeableConcept verificationStatus;
    private AllergyIntoleranceType type;
    private List<AllergyIntoleranceCategory> category = new ArrayList<>();
    private AllergyIntoleranceCriticality criticality;
    private CodeableConcept code;
    private Reference patient;
    private Reference encounter;
    private LocalDateTime onsetDateTime; // simplified onset choice
    private LocalDateTime recordedDate;
    private Reference recorder;
    private Reference asserter;
    private LocalDateTime lastOccurrence;
    private List<Annotation> note = new ArrayList<>();
    private List<Reaction> reaction = new ArrayList<>();

    public List<Identifier> getIdentifier() { return identifier; }
    public void setIdentifier(List<Identifier> identifier) { this.identifier = identifier; }
    public CodeableConcept getClinicalStatus() { return clinicalStatus; }
    public void setClinicalStatus(CodeableConcept clinicalStatus) { this.clinicalStatus = clinicalStatus; }
    public CodeableConcept getVerificationStatus() { return verificationStatus; }
    public void setVerificationStatus(CodeableConcept verificationStatus) { this.verificationStatus = verificationStatus; }
    public AllergyIntoleranceType getType() { return type; }
    public void setType(AllergyIntoleranceType type) { this.type = type; }
    public List<AllergyIntoleranceCategory> getCategory() { return category; }
    public void setCategory(List<AllergyIntoleranceCategory> category) { this.category = category; }
    public AllergyIntoleranceCriticality getCriticality() { return criticality; }
    public void setCriticality(AllergyIntoleranceCriticality criticality) { this.criticality = criticality; }
    public CodeableConcept getCode() { return code; }
    public void setCode(CodeableConcept code) { this.code = code; }
    public Reference getPatient() { return patient; }
    public void setPatient(Reference patient) { this.patient = patient; }
    public Reference getEncounter() { return encounter; }
    public void setEncounter(Reference encounter) { this.encounter = encounter; }
    public LocalDateTime getOnsetDateTime() { return onsetDateTime; }
    public void setOnsetDateTime(LocalDateTime onsetDateTime) { this.onsetDateTime = onsetDateTime; }
    public LocalDateTime getRecordedDate() { return recordedDate; }
    public void setRecordedDate(LocalDateTime recordedDate) { this.recordedDate = recordedDate; }
    public Reference getRecorder() { return recorder; }
    public void setRecorder(Reference recorder) { this.recorder = recorder; }
    public Reference getAsserter() { return asserter; }
    public void setAsserter(Reference asserter) { this.asserter = asserter; }
    public LocalDateTime getLastOccurrence() { return lastOccurrence; }
    public void setLastOccurrence(LocalDateTime lastOccurrence) { this.lastOccurrence = lastOccurrence; }
    public List<Annotation> getNote() { return note; }
    public void setNote(List<Annotation> note) { this.note = note; }
    public List<Reaction> getReaction() { return reaction; }
    public void setReaction(List<Reaction> reaction) { this.reaction = reaction; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AllergyIntolerance that = (AllergyIntolerance) o;
        return Objects.equals(identifier, that.identifier) && Objects.equals(clinicalStatus, that.clinicalStatus) && Objects.equals(verificationStatus, that.verificationStatus) && type == that.type && Objects.equals(category, that.category) && criticality == that.criticality && Objects.equals(code, that.code) && Objects.equals(patient, that.patient) && Objects.equals(encounter, that.encounter) && Objects.equals(onsetDateTime, that.onsetDateTime) && Objects.equals(recordedDate, that.recordedDate) && Objects.equals(recorder, that.recorder) && Objects.equals(asserter, that.asserter) && Objects.equals(lastOccurrence, that.lastOccurrence) && Objects.equals(note, that.note) && Objects.equals(reaction, that.reaction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier, clinicalStatus, verificationStatus, type, category, criticality, code, patient, encounter, onsetDateTime, recordedDate, recorder, asserter, lastOccurrence, note, reaction);
    }
}


