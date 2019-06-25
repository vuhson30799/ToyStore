package model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class GiftCardPk implements Serializable {
    @Column(name = "person_id")
    private Long person_id;
    @Column(name = "giftcarddetail_id")
    private Long giftcarddetail_id;
}
