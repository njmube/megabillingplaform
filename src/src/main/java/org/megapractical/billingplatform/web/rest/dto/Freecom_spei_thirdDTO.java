package org.megapractical.billingplatform.web.rest.dto;


import org.megapractical.billingplatform.domain.Freecom_beneficiary;
import org.megapractical.billingplatform.domain.Freecom_payer;
import org.megapractical.billingplatform.domain.Freecom_spei_third;

public class Freecom_spei_thirdDTO {

    private Freecom_spei_third spei_third;

    private Freecom_payer payer;

    private Freecom_beneficiary beneficiary;

    public Freecom_spei_third getSpei_third() {
        return spei_third;
    }

    public void setSpei_third(Freecom_spei_third spei_third) {
        this.spei_third = spei_third;
    }

    public Freecom_payer getPayer() {
        return payer;
    }

    public void setPayer(Freecom_payer payer) {
        this.payer = payer;
    }

    public Freecom_beneficiary getBeneficiary() {
        return beneficiary;
    }

    public void setBeneficiary(Freecom_beneficiary beneficiary) {
        this.beneficiary = beneficiary;
    }
}
