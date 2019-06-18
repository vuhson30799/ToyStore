package repository;

import model.GiftCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GiftCardRepository extends JpaRepository<GiftCard,Long> {

    // primary key is made from 2 foreign key. How to make id pairs management from 2 foreign key?
}
