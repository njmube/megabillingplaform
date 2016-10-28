package org.megapractical.billingplatform.web.rest.dto;


import org.megapractical.billingplatform.domain.Customs_info_part;
import org.megapractical.billingplatform.domain.Part_concept;

import java.util.List;

public class Part_conceptDTO {

   private Part_concept part_concept;

    private List<Customs_info_part> customs_info_parts;

    public Part_concept getPart_concept() {
        return part_concept;
    }

    public void setPart_concept(Part_concept part_concept) {
        this.part_concept = part_concept;
    }

    public List<Customs_info_part> getCustoms_info_parts() {
        return customs_info_parts;
    }

    public void setCustoms_info_parts(List<Customs_info_part> customs_info_parts) {
        this.customs_info_parts = customs_info_parts;
    }
}
