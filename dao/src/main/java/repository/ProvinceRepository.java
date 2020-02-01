package repository;

import model.Province;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProvinceRepository extends JpaRepository<Province,Long> {
}
