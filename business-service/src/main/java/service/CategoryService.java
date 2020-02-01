package service;

import model.Category;

import java.util.List;

public interface CategoryService {

    List<Category> findAll();

    List<Category> findAllByParentIdContaining(String index);

    String findCategoryName(Long id);

    List<Category> findRandomCategories(Long number);

    Category findCategoryByName(String name);
}
