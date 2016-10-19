package org.megapractical.billingplatform.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A Taxpayer_request_confirm.
 */
@Entity
@Table(name = "taxpayer_request_confirm")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Taxpayer_request_confirm implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "taxpayer_request_id")
    private Long taxpayer_request_id;

    @Size(max = 255)
    @Column(name = "account_number_ach", length = 255)
    private String account_number_ach;

    @Size(max = 255)
    @Column(name = "account_type_ach", length = 255)
    private String account_type_ach;

    @Column(name = "additional_value", precision=10, scale=2)
    private BigDecimal additional_value;

    @Column(name = "administrative_fee", precision=10, scale=2)
    private BigDecimal administrative_fee;

    @Column(name = "administrative_fee_base", precision=10, scale=2)
    private BigDecimal administrative_fee_base;

    @Column(name = "administrative_fee_tax", precision=10, scale=2)
    private BigDecimal administrative_fee_tax;

    @Size(max = 255)
    @Column(name = "airline_code", length = 255)
    private String airline_code;

    @Column(name = "attempts")
    private Integer attempts;

    @Size(max = 255)
    @Column(name = "authorization_code", length = 255)
    private String authorization_code;

    @Size(max = 255)
    @Column(name = "bank_id", length = 255)
    private String bank_id;

    @Size(max = 255)
    @Column(name = "billing_address", length = 255)
    private String billing_address;

    @Size(max = 255)
    @Column(name = "billing_city", length = 255)
    private String billing_city;

    @Size(max = 255)
    @Column(name = "billing_country", length = 255)
    private String billing_country;

    @Column(name = "commision_pol", precision=10, scale=2)
    private BigDecimal commision_pol;

    @Size(max = 255)
    @Column(name = "commision_pol_currency", length = 255)
    private String commision_pol_currency;

    @Size(max = 255)
    @Column(name = "currency", length = 255)
    private String currency;

    @Size(max = 255)
    @Column(name = "cus", length = 255)
    private String cus;

    @Column(name = "customer_number")
    private Integer customer_number;

    @Size(max = 255)
    @Column(name = "date", length = 255)
    private String date;

    @Size(max = 255)
    @Column(name = "description", length = 255)
    private String description;

    @Size(max = 255)
    @Column(name = "email_buyer", length = 255)
    private String email_buyer;

    @Size(max = 255)
    @Column(name = "error_code_bank", length = 255)
    private String error_code_bank;

    @Size(max = 255)
    @Column(name = "error_message_bank", length = 255)
    private String error_message_bank;

    @Column(name = "exchange_rate", precision=10, scale=2)
    private BigDecimal exchange_rate;

    @Size(max = 255)
    @Column(name = "extra_1", length = 255)
    private String extra1;

    @Size(max = 255)
    @Column(name = "extra_2", length = 255)
    private String extra2;

    @Column(name = "installments_number")
    private Integer installments_number;

    @Size(max = 255)
    @Column(name = "ip", length = 255)
    private String ip;

    @Column(name = "merchant_id")
    private Integer merchant_id;

    @Size(max = 255)
    @Column(name = "nickname_buyer", length = 255)
    private String nickname_buyer;

    @Size(max = 255)
    @Column(name = "nickname_seller", length = 255)
    private String nickname_seller;

    @Size(max = 255)
    @Column(name = "office_phone", length = 255)
    private String office_phone;

    @Column(name = "payment_method")
    private Integer payment_method;

    @Column(name = "payment_method_id")
    private Integer payment_method_id;

    @Size(max = 255)
    @Column(name = "payment_method_name", length = 255)
    private String payment_method_name;

    @Column(name = "payment_method_type")
    private Integer payment_method_type;

    @Size(max = 255)
    @Column(name = "payment_request_state", length = 255)
    private String payment_request_state;

    @Size(max = 255)
    @Column(name = "phone", length = 255)
    private String phone;

    @Size(max = 255)
    @Column(name = "pse_bank", length = 255)
    private String pse_bank;

    @Size(max = 255)
    @Column(name = "pse_reference_1", length = 255)
    private String pse_reference1;

    @Size(max = 255)
    @Column(name = "pse_reference_2", length = 255)
    private String pse_reference2;

    @Size(max = 255)
    @Column(name = "pse_reference_3", length = 255)
    private String pse_reference3;

    @Size(max = 255)
    @Column(name = "reference_pol", length = 255)
    private String reference_pol;

    @Size(max = 255)
    @Column(name = "reference_sale", length = 255)
    private String reference_sale;

    @Size(max = 255)
    @Column(name = "response_code_pol", length = 255)
    private String response_code_pol;

    @Size(max = 255)
    @Column(name = "response_message_pol", length = 255)
    private String response_message_pol;

    @Column(name = "risk", precision=10, scale=2)
    private BigDecimal risk;

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
    @Column(name = "sign", length = 255)
    private String sign;

    @Size(max = 255)
    @Column(name = "state_pol", length = 255)
    private String state_pol;

    @Column(name = "tax", precision=10, scale=2)
    private BigDecimal tax;

    @Column(name = "test")
    private Boolean test;

    @Size(max = 255)
    @Column(name = "transaction_bank_id", length = 255)
    private String transaction_bank_id;

    @Size(max = 255)
    @Column(name = "transactiondate", length = 255)
    private String transactiondate;

    @Size(max = 255)
    @Column(name = "transaction_id", length = 255)
    private String transaction_id;

    @Column(name = "value", precision=10, scale=2)
    private BigDecimal value;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTaxpayer_request_id() {
        return taxpayer_request_id;
    }

    public void setTaxpayer_request_id(Long taxpayer_request_id) {
        this.taxpayer_request_id = taxpayer_request_id;
    }

    public String getAccount_number_ach() {
        return account_number_ach;
    }

    public void setAccount_number_ach(String account_number_ach) {
        this.account_number_ach = account_number_ach;
    }

    public String getAccount_type_ach() {
        return account_type_ach;
    }

    public void setAccount_type_ach(String account_type_ach) {
        this.account_type_ach = account_type_ach;
    }

    public BigDecimal getAdditional_value() {
        return additional_value;
    }

    public void setAdditional_value(BigDecimal additional_value) {
        this.additional_value = additional_value;
    }

    public BigDecimal getAdministrative_fee() {
        return administrative_fee;
    }

    public void setAdministrative_fee(BigDecimal administrative_fee) {
        this.administrative_fee = administrative_fee;
    }

    public BigDecimal getAdministrative_fee_base() {
        return administrative_fee_base;
    }

    public void setAdministrative_fee_base(BigDecimal administrative_fee_base) {
        this.administrative_fee_base = administrative_fee_base;
    }

    public BigDecimal getAdministrative_fee_tax() {
        return administrative_fee_tax;
    }

    public void setAdministrative_fee_tax(BigDecimal administrative_fee_tax) {
        this.administrative_fee_tax = administrative_fee_tax;
    }

    public String getAirline_code() {
        return airline_code;
    }

    public void setAirline_code(String airline_code) {
        this.airline_code = airline_code;
    }

    public Integer getAttempts() {
        return attempts;
    }

    public void setAttempts(Integer attempts) {
        this.attempts = attempts;
    }

    public String getAuthorization_code() {
        return authorization_code;
    }

    public void setAuthorization_code(String authorization_code) {
        this.authorization_code = authorization_code;
    }

    public String getBank_id() {
        return bank_id;
    }

    public void setBank_id(String bank_id) {
        this.bank_id = bank_id;
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

    public BigDecimal getCommision_pol() {
        return commision_pol;
    }

    public void setCommision_pol(BigDecimal commision_pol) {
        this.commision_pol = commision_pol;
    }

    public String getCommision_pol_currency() {
        return commision_pol_currency;
    }

    public void setCommision_pol_currency(String commision_pol_currency) {
        this.commision_pol_currency = commision_pol_currency;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCus() {
        return cus;
    }

    public void setCus(String cus) {
        this.cus = cus;
    }

    public Integer getCustomer_number() {
        return customer_number;
    }

    public void setCustomer_number(Integer customer_number) {
        this.customer_number = customer_number;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail_buyer() {
        return email_buyer;
    }

    public void setEmail_buyer(String email_buyer) {
        this.email_buyer = email_buyer;
    }

    public String getError_code_bank() {
        return error_code_bank;
    }

    public void setError_code_bank(String error_code_bank) {
        this.error_code_bank = error_code_bank;
    }

    public String getError_message_bank() {
        return error_message_bank;
    }

    public void setError_message_bank(String error_message_bank) {
        this.error_message_bank = error_message_bank;
    }

    public BigDecimal getExchange_rate() {
        return exchange_rate;
    }

    public void setExchange_rate(BigDecimal exchange_rate) {
        this.exchange_rate = exchange_rate;
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

    public Integer getInstallments_number() {
        return installments_number;
    }

    public void setInstallments_number(Integer installments_number) {
        this.installments_number = installments_number;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(Integer merchant_id) {
        this.merchant_id = merchant_id;
    }

    public String getNickname_buyer() {
        return nickname_buyer;
    }

    public void setNickname_buyer(String nickname_buyer) {
        this.nickname_buyer = nickname_buyer;
    }

    public String getNickname_seller() {
        return nickname_seller;
    }

    public void setNickname_seller(String nickname_seller) {
        this.nickname_seller = nickname_seller;
    }

    public String getOffice_phone() {
        return office_phone;
    }

    public void setOffice_phone(String office_phone) {
        this.office_phone = office_phone;
    }

    public Integer getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(Integer payment_method) {
        this.payment_method = payment_method;
    }

    public Integer getPayment_method_id() {
        return payment_method_id;
    }

    public void setPayment_method_id(Integer payment_method_id) {
        this.payment_method_id = payment_method_id;
    }

    public String getPayment_method_name() {
        return payment_method_name;
    }

    public void setPayment_method_name(String payment_method_name) {
        this.payment_method_name = payment_method_name;
    }

    public Integer getPayment_method_type() {
        return payment_method_type;
    }

    public void setPayment_method_type(Integer payment_method_type) {
        this.payment_method_type = payment_method_type;
    }

    public String getPayment_request_state() {
        return payment_request_state;
    }

    public void setPayment_request_state(String payment_request_state) {
        this.payment_request_state = payment_request_state;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPse_bank() {
        return pse_bank;
    }

    public void setPse_bank(String pse_bank) {
        this.pse_bank = pse_bank;
    }

    public String getPse_reference1() {
        return pse_reference1;
    }

    public void setPse_reference1(String pse_reference1) {
        this.pse_reference1 = pse_reference1;
    }

    public String getPse_reference2() {
        return pse_reference2;
    }

    public void setPse_reference2(String pse_reference2) {
        this.pse_reference2 = pse_reference2;
    }

    public String getPse_reference3() {
        return pse_reference3;
    }

    public void setPse_reference3(String pse_reference3) {
        this.pse_reference3 = pse_reference3;
    }

    public String getReference_pol() {
        return reference_pol;
    }

    public void setReference_pol(String reference_pol) {
        this.reference_pol = reference_pol;
    }

    public String getReference_sale() {
        return reference_sale;
    }

    public void setReference_sale(String reference_sale) {
        this.reference_sale = reference_sale;
    }

    public String getResponse_code_pol() {
        return response_code_pol;
    }

    public void setResponse_code_pol(String response_code_pol) {
        this.response_code_pol = response_code_pol;
    }

    public String getResponse_message_pol() {
        return response_message_pol;
    }

    public void setResponse_message_pol(String response_message_pol) {
        this.response_message_pol = response_message_pol;
    }

    public BigDecimal getRisk() {
        return risk;
    }

    public void setRisk(BigDecimal risk) {
        this.risk = risk;
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

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getState_pol() {
        return state_pol;
    }

    public void setState_pol(String state_pol) {
        this.state_pol = state_pol;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public Boolean isTest() {
        return test;
    }

    public void setTest(Boolean test) {
        this.test = test;
    }

    public String getTransaction_bank_id() {
        return transaction_bank_id;
    }

    public void setTransaction_bank_id(String transaction_bank_id) {
        this.transaction_bank_id = transaction_bank_id;
    }

    public String getTransactiondate() {
        return transactiondate;
    }

    public void setTransactiondate(String transactiondate) {
        this.transactiondate = transactiondate;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Taxpayer_request_confirm taxpayer_request_confirm = (Taxpayer_request_confirm) o;
        if(taxpayer_request_confirm.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, taxpayer_request_confirm.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Taxpayer_request_confirm{" +
            "id=" + id +
            ", taxpayer_request_id='" + taxpayer_request_id + "'" +
            ", account_number_ach='" + account_number_ach + "'" +
            ", account_type_ach='" + account_type_ach + "'" +
            ", additional_value='" + additional_value + "'" +
            ", administrative_fee='" + administrative_fee + "'" +
            ", administrative_fee_base='" + administrative_fee_base + "'" +
            ", administrative_fee_tax='" + administrative_fee_tax + "'" +
            ", airline_code='" + airline_code + "'" +
            ", attempts='" + attempts + "'" +
            ", authorization_code='" + authorization_code + "'" +
            ", bank_id='" + bank_id + "'" +
            ", billing_address='" + billing_address + "'" +
            ", billing_city='" + billing_city + "'" +
            ", billing_country='" + billing_country + "'" +
            ", commision_pol='" + commision_pol + "'" +
            ", commision_pol_currency='" + commision_pol_currency + "'" +
            ", currency='" + currency + "'" +
            ", cus='" + cus + "'" +
            ", customer_number='" + customer_number + "'" +
            ", date='" + date + "'" +
            ", description='" + description + "'" +
            ", email_buyer='" + email_buyer + "'" +
            ", error_code_bank='" + error_code_bank + "'" +
            ", error_message_bank='" + error_message_bank + "'" +
            ", exchange_rate='" + exchange_rate + "'" +
            ", extra1='" + extra1 + "'" +
            ", extra2='" + extra2 + "'" +
            ", installments_number='" + installments_number + "'" +
            ", ip='" + ip + "'" +
            ", merchant_id='" + merchant_id + "'" +
            ", nickname_buyer='" + nickname_buyer + "'" +
            ", nickname_seller='" + nickname_seller + "'" +
            ", office_phone='" + office_phone + "'" +
            ", payment_method='" + payment_method + "'" +
            ", payment_method_id='" + payment_method_id + "'" +
            ", payment_method_name='" + payment_method_name + "'" +
            ", payment_method_type='" + payment_method_type + "'" +
            ", payment_request_state='" + payment_request_state + "'" +
            ", phone='" + phone + "'" +
            ", pse_bank='" + pse_bank + "'" +
            ", pse_reference1='" + pse_reference1 + "'" +
            ", pse_reference2='" + pse_reference2 + "'" +
            ", pse_reference3='" + pse_reference3 + "'" +
            ", reference_pol='" + reference_pol + "'" +
            ", reference_sale='" + reference_sale + "'" +
            ", response_code_pol='" + response_code_pol + "'" +
            ", response_message_pol='" + response_message_pol + "'" +
            ", risk='" + risk + "'" +
            ", shipping_address='" + shipping_address + "'" +
            ", shipping_city='" + shipping_city + "'" +
            ", shipping_country='" + shipping_country + "'" +
            ", sign='" + sign + "'" +
            ", state_pol='" + state_pol + "'" +
            ", tax='" + tax + "'" +
            ", test='" + test + "'" +
            ", transaction_bank_id='" + transaction_bank_id + "'" +
            ", transactiondate='" + transactiondate + "'" +
            ", transaction_id='" + transaction_id + "'" +
            ", value='" + value + "'" +
            '}';
    }
}
