package model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String content;

    @DateTimeFormat(pattern = "dd/MM/yy")
    private Date availableDate;

    @DateTimeFormat(pattern = "dd/MM/yy")
    private Date expirationDate;

    @ManyToOne(targetEntity = Account.class)
    @JoinColumn(name = "accountId")
    private Account account;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getAvailableDate() {
        return availableDate;
    }

    public void setAvailableDate(Date availableDate) {
        this.availableDate = availableDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
