package org.megapractical.billingplatform.web.rest.dto;

import org.megapractical.billingplatform.domain.Accounting;
import org.megapractical.billingplatform.domain.Freecom_ine_entity;

import java.util.List;


public class Freecom_ine_entityDTO {

    private Freecom_ine_entity freecom_ine_entity;

    private List<Accounting> accountings;

    public Freecom_ine_entity getFreecom_ine_entity() {
        return freecom_ine_entity;
    }

    public void setFreecom_ine_entity(Freecom_ine_entity freecom_ine_entity) {
        this.freecom_ine_entity = freecom_ine_entity;
    }

    public List<Accounting> getAccountings() {
        return accountings;
    }

    public void setAccountings(List<Accounting> accountings) {
        this.accountings = accountings;
    }
}
