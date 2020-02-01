package repository;

import model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface RatingRepository extends JpaRepository<Rating,Long> {
    List<Rating> findAllByToy_Id(Long toyId);

    List<Rating> findAllByParentIdAndToy_IdOrderByPostDateDesc(Long parentId, Long toyId);

    List<Rating> findAllByParentIdInAndToy_IdOrderByPostDate(List<Long> parentIds, Long toyId);

    List<Rating> findAllByAccount_UsernameAndToy_Id(String username, Long toyId);

    @Modifying
    @Transactional
    @Query("update Rating a set a.ratingStar = :ratingStar where a.toy.id = :toyId and a.account.id = :accountId")
    void updateRatingStar(@Param("toyId") Long toyId, @Param("accountId") Long accountId, @Param("ratingStar") Long ratingStar);

}
