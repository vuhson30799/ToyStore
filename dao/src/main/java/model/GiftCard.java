package model;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "giftcard")
public class GiftCard {

    @EmbeddedId
    private GiftCardPk id;

    private int quantity;

    public GiftCardPk getId() {
        return id;
    }

    public void setId(GiftCardPk id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
