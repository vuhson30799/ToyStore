package service;

import model.Brand;

import java.util.List;

public interface BrandService {

    List<Brand> findRandomBrands(Long number);

    String findBrandName(Long id);

    List<Brand> findAll();

    Brand findBrandByName(String name);
}
