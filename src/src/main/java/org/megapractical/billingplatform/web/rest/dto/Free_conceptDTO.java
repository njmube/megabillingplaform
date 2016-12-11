package org.megapractical.billingplatform.web.rest.dto;


import org.megapractical.billingplatform.domain.*;

import java.util.List;

public class Free_conceptDTO {

    private Free_concept free_concept;

    private Free_tax_transfered free_concept_iva;

    private Free_tax_transfered free_concept_ieps;

    private Free_tax_retentions free_tax_retentions_iva;

    private Free_tax_retentions free_tax_retentions_isr;

    private List<Free_customs_info> free_customs_infos;

    private List<Free_part_concept> free_part_concepts;

    public Free_concept getFree_concept() {
        return free_concept;
    }

    public void setFree_concept(Free_concept free_concept) {
        this.free_concept = free_concept;
    }

    public Free_tax_transfered getFree_concept_iva() {
        return free_concept_iva;
    }

    public void setFree_concept_iva(Free_tax_transfered free_concept_iva) {
        this.free_concept_iva = free_concept_iva;
    }

    public Free_tax_transfered getFree_concept_ieps() {
        return free_concept_ieps;
    }

    public void setFree_concept_ieps(Free_tax_transfered free_concept_ieps) {
        this.free_concept_ieps = free_concept_ieps;
    }

    public Free_tax_retentions getFree_tax_retentions_iva() {
        return free_tax_retentions_iva;
    }

    public void setFree_tax_retentions_iva(Free_tax_retentions free_tax_retentions_iva) {
        this.free_tax_retentions_iva = free_tax_retentions_iva;
    }

    public Free_tax_retentions getFree_tax_retentions_isr() {
        return free_tax_retentions_isr;
    }

    public void setFree_tax_retentions_isr(Free_tax_retentions free_tax_retentions_isr) {
        this.free_tax_retentions_isr = free_tax_retentions_isr;
    }

    public List<Free_customs_info> getFree_customs_infos() {
        return free_customs_infos;
    }

    public void setFree_customs_infos(List<Free_customs_info> free_customs_infos) {
        this.free_customs_infos = free_customs_infos;
    }

    public List<Free_part_concept> getFree_part_concepts() {
        return free_part_concepts;
    }

    public void setFree_part_concepts(List<Free_part_concept> free_part_concepts) {
        this.free_part_concepts = free_part_concepts;
    }
}
