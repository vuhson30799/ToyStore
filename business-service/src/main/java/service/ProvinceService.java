package service;

import model.Province;

import java.util.List;

public interface ProvinceService {

    List<Province> findAll();

    Province findById(Long id);

}
