package service;

import model.District;
import repository.DistrictRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class DistrictServiceImpl implements DistrictService {

    @Autowired
    private DistrictRepository districtRepository;

    @Override
    public List<District> findAllByParentId(Long parentId) {
        return districtRepository.findAllByParentId(parentId);
    }
}
