package org.megapractical.billingplatform.web.rest.dto;

import org.megapractical.billingplatform.domain.Concept;
import org.megapractical.billingplatform.domain.Customs_info;
import org.megapractical.billingplatform.domain.Part_concept;

import java.util.List;

public class ConceptDTO {

    private Concept concept;

    private List<Part_concept> part_concepts;

    private List<Customs_info> customs_infos;

    public Concept getConcept() {
        return concept;
    }

    public void setConcept(Concept concept) {
        this.concept = concept;
    }

    public List<Part_concept> getPart_concepts() {
        return part_concepts;
    }

    public void setPart_concepts(List<Part_concept> part_concepts) {
        this.part_concepts = part_concepts;
    }

    public List<Customs_info> getCustoms_infos() {
        return customs_infos;
    }

    public void setCustoms_infos(List<Customs_info> customs_infos) {
        this.customs_infos = customs_infos;
    }
}
