package service;

import model.Rating;
import repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class RatingServiceImpl implements RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    @Override
    public Rating findById(Long id) {
        return ratingRepository.findOne(id);
    }

    @Override
    public List<Rating> findAllByToyId(Long id) {
        return ratingRepository.findAllByToy_Id(id);
    }

    @Override
    public List<Rating> findAllByParentIdAndToy_Id(Long parentId, Long toyId) {
        return ratingRepository.findAllByParentIdAndToy_IdOrderByPostDateDesc(parentId, toyId);
    }

    @Override
    public List<Rating> findAllByParentIdsAndToy_Id(List<Long> parentIds, Long toyId) {
        return ratingRepository.findAllByParentIdInAndToy_IdOrderByPostDate(parentIds, toyId);
    }

    @Override
    public List<Rating> findAllByAccount_UsernameAndToy_Id(String username, Long toyId) {
        return ratingRepository.findAllByAccount_UsernameAndToy_Id(username, toyId);
    }

    @Override
    public void save(Rating rating) {
        ratingRepository.save(rating);
    }

    @Override
    public void updateRatingStar(Long toyId, Long accountId, Long ratingStar) {
        ratingRepository.updateRatingStar(toyId, accountId, ratingStar);
    }


}
