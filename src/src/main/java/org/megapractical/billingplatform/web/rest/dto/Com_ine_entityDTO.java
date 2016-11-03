package org.megapractical.billingplatform.web.rest.dto;


import org.megapractical.billingplatform.domain.Accounting;
import org.megapractical.billingplatform.domain.Com_accounting;
import org.megapractical.billingplatform.domain.Com_ine_entity;

import java.util.List;

public class Com_ine_entityDTO {

    private Com_ine_entity com_ine_entity;

    private List<Com_accounting> accountings;

    public Com_ine_entity getCom_ine_entity() {
        return com_ine_entity;
    }

    public void setCom_ine_entity(Com_ine_entity com_ine_entity) {
        this.com_ine_entity = com_ine_entity;
    }

    public List<Com_accounting> getAccountings() {
        return accountings;
    }

    public void setAccountings(List<Com_accounting> accountings) {
        this.accountings = accountings;
    }
}
