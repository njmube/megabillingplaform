package org.megapractical.billingplatform.web.rest.dto;


import org.megapractical.billingplatform.domain.Com_concept_fuel;
import org.megapractical.billingplatform.domain.Com_determined;

import java.util.List;

public class Com_concept_fuelDTO {

    private Com_concept_fuel concept_fuel;

    private List<Com_determined> determinates;

    public Com_concept_fuel getConcept_fuel() {
        return concept_fuel;
    }

    public void setConcept_fuel(Com_concept_fuel concept_fuel) {
        this.concept_fuel = concept_fuel;
    }

    public List<Com_determined> getDeterminates() {
        return determinates;
    }

    public void setDeterminates(List<Com_determined> determinates) {
        this.determinates = determinates;
    }
}
