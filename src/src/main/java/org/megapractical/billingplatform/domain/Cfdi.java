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
 * A Cfdi.
 */
@Entity
@Table(name = "cfdi")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Cfdi implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 3)
    @Column(name = "version", length = 3, nullable = false)
    private String version;

    @Size(max = 25)
    @Column(name = "serial", length = 25)
    private String serial;

    @Size(max = 20)
    @Column(name = "folio", length = 20)
    private String folio;

    @NotNull
    @Column(name = "date_expedition", nullable = false)
    private ZonedDateTime date_expedition;

    @Column(name = "payment_conditions")
    private String payment_conditions;

    @Column(name = "change_type")
    private String change_type;

    @NotNull
    @Column(name = "place_expedition", nullable = false)
    private String place_expedition;

    @Column(name = "account_number")
    private String account_number;

    @Column(name = "folio_fiscal_orig")
    private String folio_fiscal_orig;

    @Column(name = "serial_folio_fiscal_orig")
    private String serial_folio_fiscal_orig;

    @Column(name = "date_folio_fiscal_orig")
    private ZonedDateTime date_folio_fiscal_orig;

    @Column(name = "mont_folio_fiscal_orig", precision=32, scale=6)
    private BigDecimal mont_folio_fiscal_orig;

    @Column(name = "total_tax_retention", precision=32, scale=6)
    private BigDecimal total_tax_retention;

    @Column(name = "total_tax_transfered", precision=32, scale=6)
    private BigDecimal total_tax_transfered;

    @Column(name = "discount", precision=32, scale=6)
    private BigDecimal discount;

    @Column(name = "discount_reason")
    private String discount_reason;

    @NotNull
    @Column(name = "subtotal", precision=32, scale=6, nullable = false)
    private BigDecimal subtotal;

    @NotNull
    @Column(name = "total", precision=32, scale=6, nullable = false)
    private BigDecimal total;

    @Column(name = "addenda")
    private String addenda;

    @NotNull
    @Size(max = 20)
    @Column(name = "number_certificate", length = 20, nullable = false)
    private String number_certificate;

    @NotNull
    @Column(name = "certificate", nullable = false)
    private String certificate;

    @Column(name = "way_payment")
    private String way_payment;

    @Column(name = "path_cfdi")
    private String path_cfdi;

    @Lob
    @Column(name = "filepdf")
    private byte[] filepdf;

    @Column(name = "filepdf_content_type")
    private String filepdfContentType;

    @Lob
    @Column(name = "filexml")
    private byte[] filexml;

    @Column(name = "filexml_content_type")
    private String filexmlContentType;

    @ManyToOne
    @NotNull
    private Cfdi_states cfdi_states;

    @ManyToOne
    @NotNull
    private Payment_method payment_method;

    @ManyToOne
    @NotNull
    private Cfdi_types cfdi_types;

    @ManyToOne
    @NotNull
    private Cfdi_type_doc cfdi_type_doc;

    @ManyToOne
    private C_money c_money;

    @ManyToOne
    private Com_tfd com_tfd;

    @ManyToOne
    private Taxpayer_account taxpayer_account;

    @ManyToOne
    @NotNull
    private Tax_regime tax_regime;

    @OneToOne
    @JoinColumn(unique = true)
    private Client client;

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

    public String getNumber_certificate() {
        return number_certificate;
    }

    public void setNumber_certificate(String number_certificate) {
        this.number_certificate = number_certificate;
    }

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public String getWay_payment() {
        return way_payment;
    }

    public void setWay_payment(String way_payment) {
        this.way_payment = way_payment;
    }

    public String getPath_cfdi() {
        return path_cfdi;
    }

    public void setPath_cfdi(String path_cfdi) {
        this.path_cfdi = path_cfdi;
    }

    public byte[] getFilepdf() {
        return filepdf;
    }

    public void setFilepdf(byte[] filepdf) {
        this.filepdf = filepdf;
    }

    public String getFilepdfContentType() {
        return filepdfContentType;
    }

    public void setFilepdfContentType(String filepdfContentType) {
        this.filepdfContentType = filepdfContentType;
    }

    public byte[] getFilexml() {
        return filexml;
    }

    public void setFilexml(byte[] filexml) {
        this.filexml = filexml;
    }

    public String getFilexmlContentType() {
        return filexmlContentType;
    }

    public void setFilexmlContentType(String filexmlContentType) {
        this.filexmlContentType = filexmlContentType;
    }

    public Cfdi_states getCfdi_states() {
        return cfdi_states;
    }

    public void setCfdi_states(Cfdi_states cfdi_states) {
        this.cfdi_states = cfdi_states;
    }

    public Payment_method getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(Payment_method payment_method) {
        this.payment_method = payment_method;
    }

    public Cfdi_types getCfdi_types() {
        return cfdi_types;
    }

    public void setCfdi_types(Cfdi_types cfdi_types) {
        this.cfdi_types = cfdi_types;
    }

    public Cfdi_type_doc getCfdi_type_doc() {
        return cfdi_type_doc;
    }

    public void setCfdi_type_doc(Cfdi_type_doc cfdi_type_doc) {
        this.cfdi_type_doc = cfdi_type_doc;
    }

    public C_money getC_money() {
        return c_money;
    }

    public void setC_money(C_money c_money) {
        this.c_money = c_money;
    }

    public Com_tfd getCom_tfd() {
        return com_tfd;
    }

    public void setCom_tfd(Com_tfd com_tfd) {
        this.com_tfd = com_tfd;
    }

    public Taxpayer_account getTaxpayer_account() {
        return taxpayer_account;
    }

    public void setTaxpayer_account(Taxpayer_account taxpayer_account) {
        this.taxpayer_account = taxpayer_account;
    }

    public Tax_regime getTax_regime() {
        return tax_regime;
    }

    public void setTax_regime(Tax_regime tax_regime) {
        this.tax_regime = tax_regime;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Cfdi cfdi = (Cfdi) o;
        if(cfdi.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, cfdi.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Cfdi{" +
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
            ", number_certificate='" + number_certificate + "'" +
            ", certificate='" + certificate + "'" +
            ", way_payment='" + way_payment + "'" +
            ", path_cfdi='" + path_cfdi + "'" +
            ", filepdf='" + filepdf + "'" +
            ", filepdfContentType='" + filepdfContentType + "'" +
            ", filexml='" + filexml + "'" +
            ", filexmlContentType='" + filexmlContentType + "'" +
            '}';
    }
}
