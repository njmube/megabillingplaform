package org.megapractical.billingplatform.web.rest.dto;


import org.megapractical.billingplatform.domain.Freecom_ecc11_concept;
import org.megapractical.billingplatform.domain.Freecom_ecc11_transfer;

import java.util.List;

public class Freecom_ecc11_conceptDTO {

    private Freecom_ecc11_concept concept;

    private List<Freecom_ecc11_transfer> transfers;

    public Freecom_ecc11_concept getConcept() {
        return concept;
    }

    public void setConcept(Freecom_ecc11_concept concept) {
        this.concept = concept;
    }

    public List<Freecom_ecc11_transfer> getTransfers() {
        return transfers;
    }

    public void setTransfers(List<Freecom_ecc11_transfer> transfers) {
        this.transfers = transfers;
    }
}
