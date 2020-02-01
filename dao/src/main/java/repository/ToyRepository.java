package repository;

import model.Toy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface ToyRepository extends JpaRepository<Toy,Long> {
    List<Toy> findAllByAccount_IdAndDisplay(Long id, String display);

    Page<Toy> findAllByBrand_NameContainingAndDisplay(String name, String display, Pageable pageable);

    Page<Toy> findAllByCategory_IdInAndDisplay(List<Long> names, String display, Pageable pageable);

    Page<Toy> findAllByNameContainingAndDisplay(String name, String display, Pageable pageable);

    Page<Toy> findAllByNameContainingAndDisplayAndOnSaleEquals(String name, String display, Boolean onSale, Pageable pageable);

    Page<Toy> findAllByNameContainingAndDisplayOrderByManufacturingDateDesc(String name, String display, Pageable pageable);

    Page<Toy> findAllByNameContainingAndDisplayOrderByPriceAsc(String name, String display, Pageable pageable);

    Page<Toy> findAllByNameContainingAndDisplayOrderByPriceDesc(String name, String display, Pageable pageable);

    Page<Toy> findAllByNameContainingAndDisplayAndPriceGreaterThanEqualAndPriceLessThanEqual(String word, String display, Long price1, Long price2, Pageable pageable);

    Page<Toy> findAllByNameContainingAndDisplayAndPriceGreaterThanEqual(String word, String display, Long price1, Pageable pageable);

    Page<Toy> findAllByNameContainingAndDisplayAndPriceLessThanEqual(String word, String display, Long price2, Pageable pageable);

    @Modifying
    @Transactional
    @Query("update Toy t set t.quantityInStock = :qty where t.id = :id")
    void updateQuantityInStock(@Param("id") Long id, @Param("qty") Long qty);

    @Modifying
    @Transactional
    @Query("update Toy t set t.display = 'DISABLE' where t.id = :id")
    void deleteToy(@Param("id") Long id);
}
