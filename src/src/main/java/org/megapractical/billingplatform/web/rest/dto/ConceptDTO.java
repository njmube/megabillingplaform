package org.megapractical.billingplatform.web.rest.dto;

import org.megapractical.billingplatform.domain.Concept;
import org.megapractical.billingplatform.domain.Tax_retentions;
import org.megapractical.billingplatform.domain.Tax_transfered;

public class ConceptDTO {

    private Concept concept;

    private Tax_transfered concept_iva;

    private Tax_transfered concept_ieps;

    private Tax_retentions tax_retentions_iva;

    private Tax_retentions tax_retentions_isr;

    public Concept getConcept() {
        return concept;
    }

    public void setConcept(Concept concept) {
        this.concept = concept;
    }

    public Tax_transfered getConcept_iva() {
        return concept_iva;
    }

    public void setConcept_iva(Tax_transfered concept_iva) {
        this.concept_iva = concept_iva;
    }

    public Tax_transfered getConcept_ieps() {
        return concept_ieps;
    }

    public void setConcept_ieps(Tax_transfered concept_ieps) {
        this.concept_ieps = concept_ieps;
    }

    public Tax_retentions getTax_retentions_iva() {
        return tax_retentions_iva;
    }

    public void setTax_retentions_iva(Tax_retentions tax_retentions_iva) {
        this.tax_retentions_iva = tax_retentions_iva;
    }

    public Tax_retentions getTax_retentions_isr() {
        return tax_retentions_isr;
    }

    public void setTax_retentions_isr(Tax_retentions tax_retentions_isr) {
        this.tax_retentions_isr = tax_retentions_isr;
    }
}
