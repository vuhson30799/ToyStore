package model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "toy")
public class Toy {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String name;

    private String image;

    private Long price;

    private Long quantityInStock;

    private Date manufacturingDate;

    private String description;

    private String information;

    private Long oldPrice;

    private Boolean onSale;

    private String display;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "brandId")
    private Brand brand;

    @ManyToOne
    @JoinColumn(name = "accountId")
    private Account account;

    @OneToMany(targetEntity = Image.class)
    private List<Image> images;

    @OneToMany(targetEntity = Ordered.class)
    private List<Ordered> orders;

    @OneToMany(targetEntity = Rating.class)
    private List<Rating> ratings;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "favoriteToys")
    private List<Account> favoriteAccounts = new ArrayList<>();

    @ManyToOne(targetEntity = Seller.class)
    @JoinColumn(name = "sellerId")
    private Seller seller;

    public Toy() {}

    public void addAccount(Account account) {
        favoriteAccounts.add(account);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(Long quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    public Date getManufacturingDate() {
        return manufacturingDate;
    }

    public void setManufacturingDate(Date manufacturingDate) {
        this.manufacturingDate = manufacturingDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public Long getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(Long oldPrice) {
        this.oldPrice = oldPrice;
    }

    public Boolean getOnSale() {
        return onSale;
    }

    public void setOnSale(Boolean onSale) {
        this.onSale = onSale;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public List<Ordered> getOrders() {
        return orders;
    }

    public void setOrders(List<Ordered> orders) {
        this.orders = orders;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public List<Account> getFavoriteAccounts() {
        return favoriteAccounts;
    }

    public void setFavoriteAccounts(List<Account> favoriteAccounts) {
        this.favoriteAccounts = favoriteAccounts;
    }
}
