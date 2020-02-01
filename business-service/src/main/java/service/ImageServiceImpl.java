package service;

import model.Image;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

public class ImageServiceImpl implements ImageService {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Image> findAllByToyId(Long id) {
        TypedQuery<Image> query = em.createQuery("select i from Image i where i.toy.id = :id", Image.class);
        query.setParameter("id", id);
        return query.getResultList();
    }
}
