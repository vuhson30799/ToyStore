package JSON;

public class BrandJSON {

    private Long id;

    private String brandName;

    private String logo;

    private String nation;

    private String offlineStore;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String name) {
        this.brandName = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getOfflineStore() {
        return offlineStore;
    }

    public void setOfflineStore(String offlineStore) {
        this.offlineStore = offlineStore;
    }
}
