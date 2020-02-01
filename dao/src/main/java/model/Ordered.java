package model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Entity
@Table(name = "ordered")
public class Ordered {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Transient
    private Long removeId;

    private Date orderDate;

    private Date deliveredDate;

    private Long quantity;

    private String status;

    @ManyToOne
    @JoinColumn(name = "toyId")
    private Toy toy;

    @ManyToOne
    @JoinColumn(name = "accountId")
    private Account account;

    @ManyToOne(targetEntity = Seller.class)
    @JoinColumn(name = "sellerId")
    private Seller seller;

    public Ordered() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRemoveId() {
        return removeId;
    }

    public void setRemoveId(Long removeId) {
        this.removeId = removeId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getDeliveredDate() {
        return deliveredDate;
    }

    public void setDeliveredDate(Date deliveredDate) {
        this.deliveredDate = deliveredDate;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Toy getToy() {
        return toy;
    }

    public void setToy(Toy toy) {
        this.toy = toy;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }
}
