package service;

import model.Village;
import repository.VillageRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class VillageServiceImpl implements VillageService {

    @Autowired
    private VillageRepository villageRepository;

    @Override
    public List<Village> findAllByParentId(Long parentId) {
        return villageRepository.findAllByParentId(parentId);
    }
}
