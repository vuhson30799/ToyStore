package model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "seller")
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String storeName;

    private boolean businessCertificate;

    private Long certificateCode;

    @OneToOne(targetEntity = BusinessField.class)
    @JoinColumn(name = "businessFieldId")
    private BusinessField businessField;

    @OneToOne(targetEntity = Account.class)
    @JoinColumn(name = "accountId")
    private Account account;

    @OneToMany
    private List<Ordered> orderedList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public boolean isBusinessCertificate() {
        return businessCertificate;
    }

    public void setBusinessCertificate(boolean businessCertificate) {
        this.businessCertificate = businessCertificate;
    }

    public Long getCertificateCode() {
        return certificateCode;
    }

    public void setCertificateCode(Long certificateCode) {
        this.certificateCode = certificateCode;
    }

    public BusinessField getBusinessField() {
        return businessField;
    }

    public void setBusinessField(BusinessField businessField) {
        this.businessField = businessField;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public List<Ordered> getOrderedList() {
        return orderedList;
    }

    public void setOrderedList(List<Ordered> orderedList) {
        this.orderedList = orderedList;
    }
}
