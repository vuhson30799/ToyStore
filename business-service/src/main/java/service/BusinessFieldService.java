package service;

import model.BusinessField;

import java.util.List;

public interface BusinessFieldService {
    List<BusinessField> findAll();
    BusinessField findById(Long id);
}
