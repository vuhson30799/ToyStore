package service;

import model.District;
import model.District;

import java.util.List;

public interface DistrictService {

    List<District> findAllByParentId(Long parentId);

}
