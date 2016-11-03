package org.megapractical.billingplatform.web.rest.dto;


import org.megapractical.billingplatform.domain.Com_commodity;
import org.megapractical.billingplatform.domain.Com_specific_descriptions;

import java.util.List;

public class Com_commodityDTO {

    private Com_commodity com_commodity;

    private List<Com_specific_descriptions> specific_descriptions;

    public Com_commodity getCom_commodity() {
        return com_commodity;
    }

    public void setCom_commodity(Com_commodity com_commodity) {
        this.com_commodity = com_commodity;
    }

    public List<Com_specific_descriptions> getSpecific_descriptions() {
        return specific_descriptions;
    }

    public void setSpecific_descriptions(List<Com_specific_descriptions> specific_descriptions) {
        this.specific_descriptions = specific_descriptions;
    }
}
