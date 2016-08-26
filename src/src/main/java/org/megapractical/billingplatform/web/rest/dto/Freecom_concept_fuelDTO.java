package org.megapractical.billingplatform.web.rest.dto;

import org.megapractical.billingplatform.domain.Freecom_concept_fuel;
import org.megapractical.billingplatform.domain.Freecom_determined;

import java.util.List;

public class Freecom_concept_fuelDTO {

    private Freecom_concept_fuel concept_fuel;

    private List<Freecom_determined> determinates;


    public Freecom_concept_fuel getConcept_fuel() {
        return concept_fuel;
    }

    public void setConcept_fuel(Freecom_concept_fuel concept_fuel) {
        this.concept_fuel = concept_fuel;
    }

    public List<Freecom_determined> getDeterminates() {
        return determinates;
    }

    public void setDeterminates(List<Freecom_determined> determinates) {
        this.determinates = determinates;
    }
}
