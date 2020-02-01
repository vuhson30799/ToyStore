package model;

public class Star {

    private Long id;

    private Long quantity;

    private String color;

    public Star(Long id, Long quantity, String color) {
        this.id = id;
        this.quantity = quantity;
        this.color = color;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
