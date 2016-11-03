package org.megapractical.billingplatform.web.rest.dto;


import org.megapractical.billingplatform.domain.Com_beneficiary;
import org.megapractical.billingplatform.domain.Com_payer;
import org.megapractical.billingplatform.domain.Com_spei_third;

public class Com_spei_thirdDTO {

    private Com_spei_third spei_third;

    private Com_payer payer;

    private Com_beneficiary beneficiary;

    public Com_spei_third getSpei_third() {
        return spei_third;
    }

    public void setSpei_third(Com_spei_third spei_third) {
        this.spei_third = spei_third;
    }

    public Com_payer getPayer() {
        return payer;
    }

    public void setPayer(Com_payer payer) {
        this.payer = payer;
    }

    public Com_beneficiary getBeneficiary() {
        return beneficiary;
    }

    public void setBeneficiary(Com_beneficiary beneficiary) {
        this.beneficiary = beneficiary;
    }
}
