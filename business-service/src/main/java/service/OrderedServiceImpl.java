package service;

import model.Ordered;
import repository.OrderedRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class OrderedServiceImpl implements OrderedService {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private OrderedRepository orderedRepository;

    @Override
    public void save(Ordered ordered) {
        orderedRepository.save(ordered);
    }

    @Override
    public List<Ordered> findBestSeller() {
        return em.createNativeQuery("select * from Ordered group by toyId order by count(toyId) desc limit 3", Ordered.class).getResultList();
    }

    @Override
    public List<Ordered> findAllByAccount_Username(String username) {
        return orderedRepository.findAllByAccount_Username(username);
    }

    @Override
    public List<Ordered> findAllByStatusAndAccountId(String status, Long id) {
        return orderedRepository.findAllByStatusAndToy_Account_IdOrderByOrderDateDesc(status, id);
    }

    @Override
    public List<Ordered> findAllByStatusNotAndAccountId(String status, Long id) {
        return orderedRepository.findAllByStatusNotAndToy_Account_IdOrderByOrderDateDesc(status, id);
    }
}
