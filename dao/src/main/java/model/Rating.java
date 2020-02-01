package model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "rating")
public class Rating {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private Long ratingStar;

    private String comment;

    private Long parentId;

    private Timestamp postDate;

    @Transient
    private String timeAgo;

    @ManyToOne
    @JoinColumn(name = "toyId")
    private Toy toy;

    @ManyToOne
    @JoinColumn(name = "accountId")
    private Account account;

    public Rating() {
    }

    public Rating(Long ratingStar, String comment, Long parentId, Timestamp postDate, Toy toy, Account account) {
        this.ratingStar = ratingStar;
        this.comment = comment;
        this.parentId = parentId;
        this.postDate = postDate;
        this.toy = toy;
        this.account = account;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRatingStar() {
        return ratingStar;
    }

    public void setRatingStar(Long ratingStar) {
        this.ratingStar = ratingStar;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Timestamp getPostDate() {
        return postDate;
    }

    public void setPostDate(Timestamp postDate) {
        this.postDate = postDate;
    }

    public String getTimeAgo() {
        return timeAgo;
    }

    public void setTimeAgo(String timeAgo) {
        this.timeAgo = timeAgo;
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
}