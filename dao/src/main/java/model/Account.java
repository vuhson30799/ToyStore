package model;



import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String username;

    private String password;

    @Transient
    private String confirmPassword;

    private String name;

    private String address;

    private String phone;

    private Date dob;

    private String gender;

    private String email;

    private String role;

    @ManyToOne
    @JoinColumn(name = "provinceId")
    private Province province;

    @ManyToOne
    @JoinColumn(name = "districtId")
    private District district;

    @ManyToOne
    @JoinColumn(name = "villageId")
    private Village village;

    @OneToMany(targetEntity = Ordered.class)
    private List<Ordered> orders;

    @OneToMany(targetEntity = Rating.class)
    private List<Rating> ratings;

    @OneToMany(targetEntity = Toy.class)
    private List<Toy> toys;

    @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
    @JoinTable(
            name = "favorite_account_toy",
            joinColumns = { @JoinColumn(name = "account_id") },
            inverseJoinColumns = { @JoinColumn(name = "toy_id") }
    )
    private List<Toy> favoriteToys = new ArrayList<>();

    public Account() {
    }

    public void addToy(Toy toy) {
        favoriteToys.add(toy);
    }

    public void removeToy(Long id) {
        for (Toy toy : favoriteToys) {
            if (toy.getId() == id) {
                favoriteToys.remove(toy);
                break;
            }
        }
    }

    public boolean containToy(Long id) {

        for (Toy toy : favoriteToys) {
            if (toy.getId() == id) {
                return true;
            }
        }

        return false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return village.getName() + " - " + district.getName() + " - " + province.getName();
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public Village getVillage() {
        return village;
    }

    public void setVillage(Village village) {
        this.village = village;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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

    public List<Toy> getToys() {
        return toys;
    }

    public void setToys(List<Toy> toys) {
        this.toys = toys;
    }

    public List<Toy> getFavoriteToys() {
        return favoriteToys;
    }

    public void setFavoriteToys(List<Toy> favoriteToys) {
        this.favoriteToys = favoriteToys;
    }
}

