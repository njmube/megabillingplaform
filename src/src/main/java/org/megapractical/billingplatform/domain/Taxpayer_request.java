package org.megapractical.billingplatform.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A Taxpayer_request.
 */
@Entity
@Table(name = "taxpayer_request")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Taxpayer_request implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "account_id")
    private Long account_id;

    @Column(name = "ring_pack_id")
    private Long ring_pack_id;

    @Size(max = 255)
    @Column(name = "algorithm_signature", length = 255)
    private String algorithm_signature;

    @Column(name = "amount", precision=10, scale=2)
    private BigDecimal amount;

    @Size(max = 255)
    @Column(name = "billing_address", length = 255)
    private String billing_address;

    @Size(max = 255)
    @Column(name = "billing_city", length = 255)
    private String billing_city;

    @Size(max = 255)
    @Column(name = "billing_country", length = 255)
    private String billing_country;

    @Size(max = 255)
    @Column(name = "buyer_email", length = 255)
    private String buyer_email;

    @Size(max = 255)
    @Column(name = "buyer_full_name", length = 255)
    private String buyer_full_name;

    @Size(max = 255)
    @Column(name = "confirmation_url", length = 255)
    private String confirmation_url;

    @Size(max = 255)
    @Column(name = "currency", length = 255)
    private String currency;

    @Size(max = 255)
    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "discount", precision=10, scale=2)
    private BigDecimal discount;

    @Size(max = 255)
    @Column(name = "extra_1", length = 255)
    private String extra1;

    @Size(max = 255)
    @Column(name = "extra_2", length = 255)
    private String extra2;

    @Size(max = 255)
    @Column(name = "extra_3", length = 255)
    private String extra3;

    @Size(max = 255)
    @Column(name = "lng", length = 255)
    private String lng;

    @Column(name = "merchant_id")
    private Integer merchant_id;

    @Size(max = 255)
    @Column(name = "mobile_phone", length = 255)
    private String mobile_phone;

    @Size(max = 255)
    @Column(name = "office_telephone", length = 255)
    private String office_telephone;

    @Size(max = 255)
    @Column(name = "payer_document", length = 255)
    private String payer_document;

    @Size(max = 255)
    @Column(name = "payer_email", length = 255)
    private String payer_email;

    @Size(max = 255)
    @Column(name = "payer_full_name", length = 255)
    private String payer_full_name;

    @Size(max = 255)
    @Column(name = "payer_mobile_phone", length = 255)
    private String payer_mobile_phone;

    @Size(max = 255)
    @Column(name = "payer_office_phone", length = 255)
    private String payer_office_phone;

    @Size(max = 255)
    @Column(name = "payer_phone", length = 255)
    private String payer_phone;

    @Size(max = 255)
    @Column(name = "reference_code", length = 255)
    private String reference_code;

    @Size(max = 255)
    @Column(name = "response_url", length = 255)
    private String response_url;

    @Size(max = 255)
    @Column(name = "shipping_address", length = 255)
    private String shipping_address;

    @Size(max = 255)
    @Column(name = "shipping_city", length = 255)
    private String shipping_city;

    @Size(max = 255)
    @Column(name = "shipping_country", length = 255)
    private String shipping_country;

    @Size(max = 255)
    @Column(name = "signature", length = 255)
    private String signature;

    @Column(name = "tax", precision=10, scale=2)
    private BigDecimal tax;

    @Column(name = "tax_return_base", precision=10, scale=2)
    private BigDecimal tax_return_base;

    @Size(max = 255)
    @Column(name = "telephone", length = 255)
    private String telephone;

    @Column(name = "test")
    private Integer test;

    @Size(max = 255)
    @Column(name = "zipcode", length = 255)
    private String zipcode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAccount_id() {
        return account_id;
    }

    public void setAccount_id(Long account_id) {
        this.account_id = account_id;
    }

    public Long getRing_pack_id() {
        return ring_pack_id;
    }

    public void setRing_pack_id(Long ring_pack_id) {
        this.ring_pack_id = ring_pack_id;
    }

    public String getAlgorithm_signature() {
        return algorithm_signature;
    }

    public void setAlgorithm_signature(String algorithm_signature) {
        this.algorithm_signature = algorithm_signature;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getBilling_address() {
        return billing_address;
    }

    public void setBilling_address(String billing_address) {
        this.billing_address = billing_address;
    }

    public String getBilling_city() {
        return billing_city;
    }

    public void setBilling_city(String billing_city) {
        this.billing_city = billing_city;
    }

    public String getBilling_country() {
        return billing_country;
    }

    public void setBilling_country(String billing_country) {
        this.billing_country = billing_country;
    }

    public String getBuyer_email() {
        return buyer_email;
    }

    public void setBuyer_email(String buyer_email) {
        this.buyer_email = buyer_email;
    }

    public String getBuyer_full_name() {
        return buyer_full_name;
    }

    public void setBuyer_full_name(String buyer_full_name) {
        this.buyer_full_name = buyer_full_name;
    }

    public String getConfirmation_url() {
        return confirmation_url;
    }

    public void setConfirmation_url(String confirmation_url) {
        this.confirmation_url = confirmation_url;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public String getExtra1() {
        return extra1;
    }

    public void setExtra1(String extra1) {
        this.extra1 = extra1;
    }

    public String getExtra2() {
        return extra2;
    }

    public void setExtra2(String extra2) {
        this.extra2 = extra2;
    }

    public String getExtra3() {
        return extra3;
    }

    public void setExtra3(String extra3) {
        this.extra3 = extra3;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public Integer getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(Integer merchant_id) {
        this.merchant_id = merchant_id;
    }

    public String getMobile_phone() {
        return mobile_phone;
    }

    public void setMobile_phone(String mobile_phone) {
        this.mobile_phone = mobile_phone;
    }

    public String getOffice_telephone() {
        return office_telephone;
    }

    public void setOffice_telephone(String office_telephone) {
        this.office_telephone = office_telephone;
    }

    public String getPayer_document() {
        return payer_document;
    }

    public void setPayer_document(String payer_document) {
        this.payer_document = payer_document;
    }

    public String getPayer_email() {
        return payer_email;
    }

    public void setPayer_email(String payer_email) {
        this.payer_email = payer_email;
    }

    public String getPayer_full_name() {
        return payer_full_name;
    }

    public void setPayer_full_name(String payer_full_name) {
        this.payer_full_name = payer_full_name;
    }

    public String getPayer_mobile_phone() {
        return payer_mobile_phone;
    }

    public void setPayer_mobile_phone(String payer_mobile_phone) {
        this.payer_mobile_phone = payer_mobile_phone;
    }

    public String getPayer_office_phone() {
        return payer_office_phone;
    }

    public void setPayer_office_phone(String payer_office_phone) {
        this.payer_office_phone = payer_office_phone;
    }

    public String getPayer_phone() {
        return payer_phone;
    }

    public void setPayer_phone(String payer_phone) {
        this.payer_phone = payer_phone;
    }

    public String getReference_code() {
        return reference_code;
    }

    public void setReference_code(String reference_code) {
        this.reference_code = reference_code;
    }

    public String getResponse_url() {
        return response_url;
    }

    public void setResponse_url(String response_url) {
        this.response_url = response_url;
    }

    public String getShipping_address() {
        return shipping_address;
    }

    public void setShipping_address(String shipping_address) {
        this.shipping_address = shipping_address;
    }

    public String getShipping_city() {
        return shipping_city;
    }

    public void setShipping_city(String shipping_city) {
        this.shipping_city = shipping_city;
    }

    public String getShipping_country() {
        return shipping_country;
    }

    public void setShipping_country(String shipping_country) {
        this.shipping_country = shipping_country;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public BigDecimal getTax_return_base() {
        return tax_return_base;
    }

    public void setTax_return_base(BigDecimal tax_return_base) {
        this.tax_return_base = tax_return_base;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Integer getTest() {
        return test;
    }

    public void setTest(Integer test) {
        this.test = test;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Taxpayer_request taxpayer_request = (Taxpayer_request) o;
        if(taxpayer_request.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, taxpayer_request.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Taxpayer_request{" +
            "id=" + id +
            ", account_id='" + account_id + "'" +
            ", ring_pack_id='" + ring_pack_id + "'" +
            ", algorithm_signature='" + algorithm_signature + "'" +
            ", amount='" + amount + "'" +
            ", billing_address='" + billing_address + "'" +
            ", billing_city='" + billing_city + "'" +
            ", billing_country='" + billing_country + "'" +
            ", buyer_email='" + buyer_email + "'" +
            ", buyer_full_name='" + buyer_full_name + "'" +
            ", confirmation_url='" + confirmation_url + "'" +
            ", currency='" + currency + "'" +
            ", description='" + description + "'" +
            ", discount='" + discount + "'" +
            ", extra1='" + extra1 + "'" +
            ", extra2='" + extra2 + "'" +
            ", extra3='" + extra3 + "'" +
            ", lng='" + lng + "'" +
            ", merchant_id='" + merchant_id + "'" +
            ", mobile_phone='" + mobile_phone + "'" +
            ", office_telephone='" + office_telephone + "'" +
            ", payer_document='" + payer_document + "'" +
            ", payer_email='" + payer_email + "'" +
            ", payer_full_name='" + payer_full_name + "'" +
            ", payer_mobile_phone='" + payer_mobile_phone + "'" +
            ", payer_office_phone='" + payer_office_phone + "'" +
            ", payer_phone='" + payer_phone + "'" +
            ", reference_code='" + reference_code + "'" +
            ", response_url='" + response_url + "'" +
            ", shipping_address='" + shipping_address + "'" +
            ", shipping_city='" + shipping_city + "'" +
            ", shipping_country='" + shipping_country + "'" +
            ", signature='" + signature + "'" +
            ", tax='" + tax + "'" +
            ", tax_return_base='" + tax_return_base + "'" +
            ", telephone='" + telephone + "'" +
            ", test='" + test + "'" +
            ", zipcode='" + zipcode + "'" +
            '}';
    }
}
