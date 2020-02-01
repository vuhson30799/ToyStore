package service;

import model.Brand;
import model.Toy;
import repository.ToyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class ToyServiceImpl implements ToyService {

    @Autowired
    private ToyRepository toyRepository;

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Toy> findRandomToys() {
        return em.createNativeQuery("select * from Toy where display = 'ENABLE' order by rand() limit 8", Toy.class).getResultList();
    }

    @Override
    public List<Toy> findRelatedToys() {
        return em.createNativeQuery("select * from Toy where display = 'ENABLE' order by rand() limit 6", Toy.class).getResultList();
    }

    @Override
    public List<Toy> findBySellerId(Long id) {
        return toyRepository.findAllByAccount_IdAndDisplay(id, "ENABLE");
    }

    @Override
    public Page<Toy> findBrandToys(Long id, Pageable pageable) {
        TypedQuery<Brand> query = em.createQuery("select b from Brand b where b.id = :id", Brand.class);
        query.setParameter("id", id);
        return toyRepository.findAllByBrand_NameContainingAndDisplay(query.getSingleResult().getName(), "ENABLE", pageable);
    }

    @Override
    public Page<Toy> findCategoryToys(List<Long> idArr, Pageable pageable) {
        return toyRepository.findAllByCategory_IdInAndDisplay(idArr, "ENABLE", pageable);
    }

    @Override
    public Page<Toy> findAllByName(String name, String sorted, Pageable pageable) {

        Page<Toy> toys = null;

        switch (sorted) {
            case "none":
                toys = toyRepository.findAllByNameContainingAndDisplay(name, "ENABLE", pageable);
                break;
            case "sale":
                toys = toyRepository.findAllByNameContainingAndDisplayAndOnSaleEquals(name, "ENABLE", true, pageable);
                break;
            case "new":
                toys = toyRepository.findAllByNameContainingAndDisplayOrderByManufacturingDateDesc(name, "ENABLE", pageable);
                break;
            case "high":
                toys = toyRepository.findAllByNameContainingAndDisplayOrderByPriceDesc(name, "ENABLE", pageable);
                break;
            case "low":
                toys = toyRepository.findAllByNameContainingAndDisplayOrderByPriceAsc(name, "ENABLE", pageable);
                break;
        }

        return toys;
    }

    @Override
    public Page<Toy> findAllByPrice(String word, String price1, String price2, Pageable pageable) {

        if (!"".equals(price1) && !"".equals(price2)) {
            return toyRepository.findAllByNameContainingAndDisplayAndPriceGreaterThanEqualAndPriceLessThanEqual(word, "ENABLE", Long.parseLong(price1), Long.parseLong(price2), pageable);
        }

        if (!"".equals(price1) && "".equals(price2)) {
            return toyRepository.findAllByNameContainingAndDisplayAndPriceGreaterThanEqual(word, "ENABLE", Long.parseLong(price1), pageable);
        }

        if ("".equals(price1) && !"".equals(price2)) {
            return toyRepository.findAllByNameContainingAndDisplayAndPriceLessThanEqual(word, "ENABLE", Long.parseLong(price2), pageable);
        }

        return null;
    }

    @Override
    public Toy findById(Long id) {
        return toyRepository.findOne(id);
    }

    @Override
    public void save(Toy toy) {
        toyRepository.save(toy);
    }

    @Override
    public void remove(Long id) {
        toyRepository.deleteToy(id);
    }

    @Override
    public void updateQuantityInStock(Long id, Long qty) {
        toyRepository.updateQuantityInStock(id, qty);
    }
}
