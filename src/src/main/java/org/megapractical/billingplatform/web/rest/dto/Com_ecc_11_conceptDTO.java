package org.megapractical.billingplatform.web.rest.dto;


import org.megapractical.billingplatform.domain.Com_ecc_11_concept;
import org.megapractical.billingplatform.domain.Com_ecc_11_transfer;

import java.util.List;

public class Com_ecc_11_conceptDTO {

    private Com_ecc_11_concept concept;

    private List<Com_ecc_11_transfer> transfers;

    public Com_ecc_11_concept getConcept() {
        return concept;
    }

    public void setConcept(Com_ecc_11_concept concept) {
        this.concept = concept;
    }

    public List<Com_ecc_11_transfer> getTransfers() {
        return transfers;
    }

    public void setTransfers(List<Com_ecc_11_transfer> transfers) {
        this.transfers = transfers;
    }
}
