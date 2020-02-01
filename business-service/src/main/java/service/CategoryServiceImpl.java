package service;

import model.Category;
import repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public List<Category> findAllByParentIdContaining(String index) {
        return categoryRepository.findAllByParentIdContaining(index);
    }

    @Override
    public String findCategoryName(Long id) {
        return categoryRepository.findOne(id).getName();
    }

    @Override
    public List<Category> findRandomCategories(Long number) {
        String query = "select * from Category order by rand() limit " + number;
        return em.createNativeQuery(query, Category.class).getResultList();
    }

    @Override
    public Category findCategoryByName(String name) {
        return categoryRepository.findFirstByName(name);
    }

}
