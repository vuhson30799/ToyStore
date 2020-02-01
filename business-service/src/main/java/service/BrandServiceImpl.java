package service;

import model.Brand;
import org.springframework.beans.factory.annotation.Autowired;
import repository.BrandRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandRepository brandRepository;

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Brand> findRandomBrands(Long number) {
        String query = "select * from Brand order by rand() limit " + number;
        return em.createNativeQuery(query, Brand.class).getResultList();
    }

    @Override
    public String findBrandName(Long id) {
        return brandRepository.findOne(id).getName();
    }

    @Override
    public List<Brand> findAll() {
        return brandRepository.findAll();
    }

    @Override
    public Brand findBrandByName(String name) {
        return brandRepository.findFirstByName(name);
    }
}
