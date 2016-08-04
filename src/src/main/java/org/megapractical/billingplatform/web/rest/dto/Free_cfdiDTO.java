package org.megapractical.billingplatform.web.rest.dto;

import org.megapractical.billingplatform.domain.Free_cfdi;
import org.megapractical.billingplatform.domain.Free_concept;
import org.megapractical.billingplatform.domain.Free_tax_retentions;
import org.megapractical.billingplatform.domain.Free_tax_transfered;

import java.util.List;

public class Free_cfdiDTO {

    private Free_cfdi freeCFDI;

    private List<Free_concept> concepts;

    private List<Free_tax_transfered> freeTaxTransfereds;

    private List<Free_tax_retentions> freeTaxRetentions;

    public Free_cfdiDTO(){
    }

    public Free_cfdi getFreeCFDI() {
        return freeCFDI;
    }

    public void setFreeCFDI(Free_cfdi freeCFDI) {
        this.freeCFDI = freeCFDI;
    }

    public List<Free_concept> getConcepts() {
        return concepts;
    }

    public void setConcepts(List<Free_concept> concepts) {
        this.concepts = concepts;
    }

    public List<Free_tax_transfered> getFreeTaxTransfereds() {
        return freeTaxTransfereds;
    }

    public void setFreeTaxTransfereds(List<Free_tax_transfered> freeTaxTransfereds) {
        this.freeTaxTransfereds = freeTaxTransfereds;
    }

    public List<Free_tax_retentions> getFreeTaxRetentions() {
        return freeTaxRetentions;
    }

    public void setFreeTaxRetentions(List<Free_tax_retentions> freeTaxRetentions) {
        this.freeTaxRetentions = freeTaxRetentions;
    }
}
