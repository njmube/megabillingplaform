package org.megapractical.billingplatform.web.rest.dto;


import org.megapractical.billingplatform.domain.Freecom_commodity;
import org.megapractical.billingplatform.domain.Freecom_specific_descriptions;

import java.util.List;

public class Freecom_CommodityDTO {

    private Freecom_commodity freecom_commodity;

    private List<Freecom_specific_descriptions> specific_descriptions;

    public Freecom_commodity getFreecom_commodity() {
        return freecom_commodity;
    }

    public void setFreecom_commodity(Freecom_commodity freecom_commodity) {
        this.freecom_commodity = freecom_commodity;
    }

    public List<Freecom_specific_descriptions> getSpecific_descriptions() {
        return specific_descriptions;
    }

    public void setSpecific_descriptions(List<Freecom_specific_descriptions> specific_descriptions) {
        this.specific_descriptions = specific_descriptions;
    }
}
