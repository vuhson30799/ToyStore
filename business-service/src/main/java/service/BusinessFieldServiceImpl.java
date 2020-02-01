package service;

import model.BusinessField;
import repository.BusinessFieldRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class BusinessFieldServiceImpl implements BusinessFieldService {
    @Autowired
    private BusinessFieldRepository businessFieldRepository;
    @Override
    public List<BusinessField> findAll() {
        return businessFieldRepository.findAll();
    }

    @Override
    public BusinessField findById(Long id) {
        return businessFieldRepository.findOne(id);
    }


}
