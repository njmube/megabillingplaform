package org.megapractical.billingplatform.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Free_cfdi.
 */
@javax.persistence.Entity
@Table(name = "free_cfdi")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Free_cfdi implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 3, max = 3)
    @Column(name = "version", length = 3, nullable = false)
    private String version;

    @Size(min = 1, max = 25)
    @Column(name = "serial", length = 25)
    private String serial;

    @Size(min = 1, max = 20)
    @Column(name = "folio", length = 20)
    private String folio;

    @Column(name = "date_expedition", nullable = false)
    private ZonedDateTime date_expedition;

    @Column(name = "payment_conditions")
    private String payment_conditions;

    @Pattern(regexp = "[0-9]{1,14}(.([0-9]{1,6}))?")
    @Column(name = "change_type")
    private String change_type;

    @NotNull
    @Column(name = "place_expedition", nullable = false)
    private String place_expedition;

    @Size(max = 4)
    @Column(name = "account_number", length = 4)
    private String account_number;

    @Column(name = "folio_fiscal_orig")
    private String folio_fiscal_orig;

    @Column(name = "serial_folio_fiscal_orig")
    private String serial_folio_fiscal_orig;

    @Column(name = "date_folio_fiscal_orig")
    private ZonedDateTime date_folio_fiscal_orig;

    @Column(name = "mont_folio_fiscal_orig", precision=10, scale=2)
    private BigDecimal mont_folio_fiscal_orig;

    @Column(name = "total_tax_retention", precision=10, scale=2)
    private BigDecimal total_tax_retention;

    @Column(name = "total_tax_transfered", precision=10, scale=2)
    private BigDecimal total_tax_transfered;

    @Column(name = "discount", precision=10, scale=2)
    private BigDecimal discount;

    @Column(name = "discount_reason")
    private String discount_reason;

    @NotNull
    @Column(name = "subtotal", precision=10, scale=2, nullable = false)
    private BigDecimal subtotal;

    @NotNull
    @Column(name = "total", precision=10, scale=2, nullable = false)
    private BigDecimal total;

    @Column(name = "addenda")
    private String addenda;

    @Column(name = "stamp")
    private String stamp;

    @Size(max = 20)
    @Column(name = "no_certificate", length = 20)
    private String no_certificate;

    @Column(name = "certificate")
    private String certificate;

    @ManyToOne
    private Cfdi_types cfdi_types;

    @ManyToOne
    private Cfdi_states cfdi_states;

    @ManyToOne
    private Free_emitter free_emitter;

    @ManyToOne
    private Payment_method payment_method;

    @ManyToOne
    private Way_payment way_payment;

    @ManyToOne
    private C_money c_money;

    @ManyToOne
    private Cfdi_type_doc cfdi_type_doc;

    @ManyToOne
    private Tax_regime tax_regime;

    @ManyToOne
    private Free_receiver free_receiver;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public ZonedDateTime getDate_expedition() {
        return date_expedition;
    }

    public void setDate_expedition(ZonedDateTime date_expedition) {
        this.date_expedition = date_expedition;
    }

    public String getPayment_conditions() {
        return payment_conditions;
    }

    public void setPayment_conditions(String payment_conditions) {
        this.payment_conditions = payment_conditions;
    }

    public String getChange_type() {
        return change_type;
    }

    public void setChange_type(String change_type) {
        this.change_type = change_type;
    }

    public String getPlace_expedition() {
        return place_expedition;
    }

    public void setPlace_expedition(String place_expedition) {
        this.place_expedition = place_expedition;
    }

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    public String getFolio_fiscal_orig() {
        return folio_fiscal_orig;
    }

    public void setFolio_fiscal_orig(String folio_fiscal_orig) {
        this.folio_fiscal_orig = folio_fiscal_orig;
    }

    public String getSerial_folio_fiscal_orig() {
        return serial_folio_fiscal_orig;
    }

    public void setSerial_folio_fiscal_orig(String serial_folio_fiscal_orig) {
        this.serial_folio_fiscal_orig = serial_folio_fiscal_orig;
    }

    public ZonedDateTime getDate_folio_fiscal_orig() {
        return date_folio_fiscal_orig;
    }

    public void setDate_folio_fiscal_orig(ZonedDateTime date_folio_fiscal_orig) {
        this.date_folio_fiscal_orig = date_folio_fiscal_orig;
    }

    public BigDecimal getMont_folio_fiscal_orig() {
        return mont_folio_fiscal_orig;
    }

    public void setMont_folio_fiscal_orig(BigDecimal mont_folio_fiscal_orig) {
        this.mont_folio_fiscal_orig = mont_folio_fiscal_orig;
    }

    public BigDecimal getTotal_tax_retention() {
        return total_tax_retention;
    }

    public void setTotal_tax_retention(BigDecimal total_tax_retention) {
        this.total_tax_retention = total_tax_retention;
    }

    public BigDecimal getTotal_tax_transfered() {
        return total_tax_transfered;
    }

    public void setTotal_tax_transfered(BigDecimal total_tax_transfered) {
        this.total_tax_transfered = total_tax_transfered;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public String getDiscount_reason() {
        return discount_reason;
    }

    public void setDiscount_reason(String discount_reason) {
        this.discount_reason = discount_reason;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getAddenda() {
        return addenda;
    }

    public void setAddenda(String addenda) {
        this.addenda = addenda;
    }

    public String getStamp() {
        return stamp;
    }

    public void setStamp(String stamp) {
        this.stamp = stamp;
    }

    public String getNo_certificate() {
        return no_certificate;
    }

    public void setNo_certificate(String no_certificate) {
        this.no_certificate = no_certificate;
    }

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public Cfdi_types getCfdi_types() {
        return cfdi_types;
    }

    public void setCfdi_types(Cfdi_types cfdi_types) {
        this.cfdi_types = cfdi_types;
    }

    public Cfdi_states getCfdi_states() {
        return cfdi_states;
    }

    public void setCfdi_states(Cfdi_states cfdi_states) {
        this.cfdi_states = cfdi_states;
    }

    public Free_emitter getFree_emitter() {
        return free_emitter;
    }

    public void setFree_emitter(Free_emitter free_emitter) {
        this.free_emitter = free_emitter;
    }

    public Payment_method getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(Payment_method payment_method) {
        this.payment_method = payment_method;
    }

    public Way_payment getWay_payment() {
        return way_payment;
    }

    public void setWay_payment(Way_payment way_payment) {
        this.way_payment = way_payment;
    }

    public C_money getC_money() {
        return c_money;
    }

    public void setC_money(C_money c_money) {
        this.c_money = c_money;
    }

    public Cfdi_type_doc getCfdi_type_doc() {
        return cfdi_type_doc;
    }

    public void setCfdi_type_doc(Cfdi_type_doc cfdi_type_doc) {
        this.cfdi_type_doc = cfdi_type_doc;
    }

    public Tax_regime getTax_regime() {
        return tax_regime;
    }

    public void setTax_regime(Tax_regime tax_regime) {
        this.tax_regime = tax_regime;
    }

    public Free_receiver getFree_receiver() {
        return free_receiver;
    }

    public void setFree_receiver(Free_receiver free_receiver) {
        this.free_receiver = free_receiver;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Free_cfdi free_cfdi = (Free_cfdi) o;
        if(free_cfdi.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, free_cfdi.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Free_cfdi{" +
            "id=" + id +
            ", version='" + version + "'" +
            ", serial='" + serial + "'" +
            ", folio='" + folio + "'" +
            ", date_expedition='" + date_expedition + "'" +
            ", payment_conditions='" + payment_conditions + "'" +
            ", change_type='" + change_type + "'" +
            ", place_expedition='" + place_expedition + "'" +
            ", account_number='" + account_number + "'" +
            ", folio_fiscal_orig='" + folio_fiscal_orig + "'" +
            ", serial_folio_fiscal_orig='" + serial_folio_fiscal_orig + "'" +
            ", date_folio_fiscal_orig='" + date_folio_fiscal_orig + "'" +
            ", mont_folio_fiscal_orig='" + mont_folio_fiscal_orig + "'" +
            ", total_tax_retention='" + total_tax_retention + "'" +
            ", total_tax_transfered='" + total_tax_transfered + "'" +
            ", discount='" + discount + "'" +
            ", discount_reason='" + discount_reason + "'" +
            ", subtotal='" + subtotal + "'" +
            ", total='" + total + "'" +
            ", addenda='" + addenda + "'" +
            ", stamp='" + stamp + "'" +
            ", no_certificate='" + no_certificate + "'" +
            ", certificate='" + certificate + "'" +
            '}';
    }
}
