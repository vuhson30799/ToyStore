package service;

import model.Ordered;
import model.Ordered;

import java.util.List;

public interface OrderedService {

    void save(Ordered ordered);

    List<Ordered> findBestSeller();

    List<Ordered> findAllByAccount_Username(String username);

    List<Ordered> findAllByStatusAndAccountId(String status, Long id);

    List<Ordered> findAllByStatusNotAndAccountId(String status, Long id);
}
