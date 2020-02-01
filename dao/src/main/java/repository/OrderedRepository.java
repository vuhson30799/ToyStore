package repository;

import model.Ordered;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderedRepository extends JpaRepository<Ordered,Long> {
    List<Ordered> findAllByAccount_Username(String username);

    List<Ordered> findAllByStatusAndToy_Account_IdOrderByOrderDateDesc(String status, Long id);

    List<Ordered> findAllByStatusNotAndToy_Account_IdOrderByOrderDateDesc(String status, Long id);
}
