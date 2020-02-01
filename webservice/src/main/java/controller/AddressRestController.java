package controller;

import JSON.DistrictJSON;
import JSON.ProvinceJSON;
import JSON.VillageJSON;
import model.District;
import model.Province;
import model.Village;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import service.DistrictService;
import service.ProvinceService;
import service.VillageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
public class AddressRestController {

    @Autowired
    private ProvinceService provinceService;

    @Autowired
    private DistrictService districtService;

    @Autowired
    private VillageService villageService;

    // get provinces
    @RequestMapping(value = "/provinces/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<List<ProvinceJSON>> listProvinces(Principal principal) {

        List<Province> provinces = provinceService.findAll();

        if (provinces.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<ProvinceJSON> provincesJSON = new ArrayList<>();

        for (Province province : provinces) {
            provincesJSON.add(new ProvinceJSON(province.getId(), province.getName()));
        }

        return new ResponseEntity<>(provincesJSON, HttpStatus.OK);

    }

    // get districts
    @RequestMapping(value = "/districts/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<DistrictJSON>> listProvinces(@PathVariable Long id) {

        if (id == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<District> districts = districtService.findAllByParentId(id);

        if (districts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<DistrictJSON> districtsJSON = new ArrayList<>();

        for (District district : districts) {
            districtsJSON.add(new DistrictJSON(district.getId(), district.getName(), district.getParentId()));
        }

        return new ResponseEntity<>(districtsJSON, HttpStatus.OK);

    }

    // get villages
    @RequestMapping(value = "/villages/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<VillageJSON>> listVillages(@PathVariable Long id) {

        if (id == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Long t1 = System.currentTimeMillis();
        List<Village> villages = villageService.findAllByParentId(id);
        System.out.println(System.currentTimeMillis()-t1);

        if (villages.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<VillageJSON> villagesJSON = new ArrayList<>();

        for (Village village : villages) {
            villagesJSON.add(new VillageJSON(village.getId(), village.getName(), village.getParentId()));
        }

        return new ResponseEntity<>(villagesJSON, HttpStatus.OK);

    }

}
