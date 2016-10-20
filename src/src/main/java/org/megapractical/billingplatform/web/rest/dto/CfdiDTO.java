package org.megapractical.billingplatform.web.rest.dto;

import org.megapractical.billingplatform.domain.Cfdi;
import org.megapractical.billingplatform.domain.Concept;
import org.megapractical.billingplatform.domain.Tax_retentions;
import org.megapractical.billingplatform.domain.Tax_transfered;

import java.util.List;

public class CfdiDTO {

    private Cfdi cfdi;

    private List<ConceptDTO> conceptDTOs;

    private List<Concept> concepts;

    private List<Tax_transfered> taxTransfereds;

    private List<Tax_retentions> taxRetentions;

    public Cfdi getCfdi() {
        return cfdi;
    }

    public void setCfdi(Cfdi cfdi) {
        this.cfdi = cfdi;
    }

    public List<ConceptDTO> getConceptDTOs() {
        return conceptDTOs;
    }

    public void setConceptDTOs(List<ConceptDTO> conceptDTOs) {
        this.conceptDTOs = conceptDTOs;
    }

    public List<Concept> getConcepts() {
        return concepts;
    }

    public void setConcepts(List<Concept> concepts) {
        this.concepts = concepts;
    }

    public List<Tax_transfered> getTaxTransfereds() {
        return taxTransfereds;
    }

    public void setTaxTransfereds(List<Tax_transfered> taxTransfereds) {
        this.taxTransfereds = taxTransfereds;
    }

    public List<Tax_retentions> getTaxRetentions() {
        return taxRetentions;
    }

    public void setTaxRetentions(List<Tax_retentions> taxRetentions) {
        this.taxRetentions = taxRetentions;
    }
}
