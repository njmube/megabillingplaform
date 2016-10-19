package org.megapractical.billingplatform.web.rest.dto;

import org.megapractical.billingplatform.domain.Discount_concept;
import org.megapractical.billingplatform.domain.Measure_unit_concept;
import org.megapractical.billingplatform.domain.Price_concept;
import org.megapractical.billingplatform.domain.Taxpayer_concept;

import java.util.List;

public class TaxpayerConceptDTO {

    private Taxpayer_concept taxpayer_concept;

    private List<Measure_unit_concept> measure_units;

    private List<Price_concept> prices;

    private List<Discount_concept> discounts;

    public Taxpayer_concept getTaxpayer_concept() {
        return taxpayer_concept;
    }

    public void setTaxpayer_concept(Taxpayer_concept taxpayer_concept) {
        this.taxpayer_concept = taxpayer_concept;
    }

    public List<Measure_unit_concept> getMeasure_units() {
        return measure_units;
    }

    public void setMeasure_units(List<Measure_unit_concept> measure_units) {
        this.measure_units = measure_units;
    }

    public List<Price_concept> getPrices() {
        return prices;
    }

    public void setPrices(List<Price_concept> prices) {
        this.prices = prices;
    }

    public List<Discount_concept> getDiscounts() {
        return discounts;
    }

    public void setDiscounts(List<Discount_concept> discounts) {
        this.discounts = discounts;
    }
}
