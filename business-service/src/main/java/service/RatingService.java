package service;

import model.Rating;
import model.Rating;

import java.util.List;

public interface RatingService {

    Rating findById(Long id);

    List<Rating> findAllByToyId(Long id);

    List<Rating> findAllByParentIdAndToy_Id(Long parentId, Long toyId);

    List<Rating> findAllByParentIdsAndToy_Id(List<Long> parentIds, Long toyId);

    List<Rating> findAllByAccount_UsernameAndToy_Id(String username, Long toyId);

    void save(Rating rating);

    void updateRatingStar(Long toyId, Long accountId, Long ratingStar);
}
