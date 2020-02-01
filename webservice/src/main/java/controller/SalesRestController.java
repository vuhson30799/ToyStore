package controller;

import JSON.*;
import model.*;
import service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@RestController
public class SalesRestController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private OrderedService orderedService;

    @Autowired
    private ToyService toyService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = "/delivering/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<OrderedJSON>> listDelivering(Principal principal) {

        if (principal != null) {

            Account account = accountService.findAccountByUsername(principal.getName());

            if ("ROLE_SELLER".equals(account.getRole())) {

                List<Ordered> orders = orderedService.findAllByStatusAndAccountId("DELIVERING", account.getId());

                if (!orders.isEmpty()) {

                    List<OrderedJSON> ordersJSON = new ArrayList<>();

                    for (Ordered order : orders) {

                        OrderedJSON ord = new OrderedJSON();

                        ord.setId(order.getId());
                        ord.setOrderDate(order.getOrderDate().toString());
                        ord.setQuantity(order.getQuantity());
                        ord.setTotalPrice(order.getQuantity() * order.getToy().getPrice());
                        ord.setCustomerName(order.getAccount().getName());
                        ord.setCustomerPhone(order.getAccount().getPhone());
                        ord.setCustomerEmail(order.getAccount().getEmail());
                        ord.setCustomerAddress(order.getAccount().getAddress());
                        ord.setCustomerId(order.getAccount().getId());
                        ord.setToyName(order.getToy().getName());
                        ord.setToyId(order.getToy().getId());

                        ordersJSON.add(ord);
                    }

                    return new ResponseEntity<>(ordersJSON, HttpStatus.OK);
                }
            }
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/history/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<OrderedJSON>> historyOrders(Principal principal) {

        if (principal != null) {

            Account account = accountService.findAccountByUsername(principal.getName());

            if ("ROLE_SELLER".equals(account.getRole())) {

                List<Ordered> orders = orderedService.findAllByStatusNotAndAccountId("DELIVERING", account.getId());

                if (!orders.isEmpty()) {

                    List<OrderedJSON> ordersJSON = new ArrayList<>();

                    for (Ordered order : orders) {

                        OrderedJSON ord = new OrderedJSON();

                        ord.setId(order.getId());
                        ord.setOrderDate(order.getOrderDate().toString());
                        ord.setQuantity(order.getQuantity());
                        ord.setTotalPrice(order.getQuantity() * order.getToy().getPrice());
                        ord.setCustomerName(order.getAccount().getName());
                        ord.setCustomerPhone(order.getAccount().getPhone());
                        ord.setCustomerEmail(order.getAccount().getEmail());
                        ord.setCustomerAddress(order.getAccount().getAddress());
                        ord.setCustomerId(order.getAccount().getId());
                        ord.setToyName(order.getToy().getName());
                        ord.setToyId(order.getToy().getId());
                        ord.setDeliveredDate(order.getDeliveredDate().toString());
                        ord.setStatus(order.getStatus());

                        ordersJSON.add(ord);
                    }

                    return new ResponseEntity<>(ordersJSON, HttpStatus.OK);
                }
            }
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/remove/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createComment(@RequestBody StatusJSON status, Principal principal) {

        if (principal != null) {

            Account account = accountService.findAccountByUsername(principal.getName());

            List<Ordered> orders = orderedService.findAllByStatusAndAccountId("DELIVERING", account.getId());

            for (Ordered order : orders) {

                if (order.getId() == status.getOrderId()) {
                    order.setStatus(status.getStatus());
                    order.setDeliveredDate(new Date(System.currentTimeMillis()));
                    orderedService.save(order);
                    return new ResponseEntity<Void>(HttpStatus.CREATED);
                }

            }
        }

        return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);

    }

    @RequestMapping(value = "/inventory/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ToyJSON>> listToys(Principal principal) {

        if (principal != null) {

            Account account = accountService.findAccountByUsername(principal.getName());

            if ("ROLE_SELLER".equals(account.getRole())) {

                List<Toy> toys = toyService.findBySellerId(account.getId());

                if (!toys.isEmpty()) {

                    List<ToyJSON> toysJSON = new ArrayList<>();

                    for (Toy toy : toys) {

                        ToyJSON toyJSON = new ToyJSON();

                        toyJSON.setId(toy.getId());
                        toyJSON.setName(toy.getName());
                        toyJSON.setImage(toy.getImage());
                        toyJSON.setPrice(toy.getPrice());
                        toyJSON.setQuantityInStock(toy.getQuantityInStock());
                        toyJSON.setManufacturingDate(toy.getManufacturingDate().toString());
                        toyJSON.setDescription(toy.getDescription());
                        toyJSON.setInformation(toy.getInformation());
                        toyJSON.setOldPrice(toy.getOldPrice());
                        toyJSON.setBrandId(toy.getBrand().getId());
                        toyJSON.setBrandName(toy.getBrand().getName());
                        toyJSON.setCategoryId(toy.getCategory().getId());
                        toyJSON.setCategoryName(toy.getCategory().getName());

                        toysJSON.add(toyJSON);
                    }

                    return new ResponseEntity<>(toysJSON, HttpStatus.OK);
                }
            }
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/delete/toy/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteCustomer(@PathVariable("id") Long id, Principal principal) {

        if (principal != null) {

            Account account = accountService.findAccountByUsername(principal.getName());

            if ("ROLE_SELLER".equals(account.getRole())) {

                Toy toy = toyService.findById(id);

                if (toy.getAccount().getId() == account.getId()) {
                    toyService.remove(id);
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }
            }
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @RequestMapping(value = "/update-toy", method = RequestMethod.POST)
    public ResponseEntity<String> updateToy(@RequestBody ToyJSON toyJSON, Principal principal) {

        if (principal != null) {

            Account account = accountService.findAccountByUsername(principal.getName());

            if ("ROLE_SELLER".equals(account.getRole())) {

                Toy toy = toyService.findById(toyJSON.getId());

                Category category = new Category();
                category.setId(toy.getCategory().getId());
                category.setName(toyJSON.getCategoryName());
                try{
                    toy.setName(toyJSON.getName());
                    toy.setImage(toyJSON.getImage());
                    toy.setPrice(toyJSON.getPrice());
                    toy.setQuantityInStock(toyJSON.getQuantityInStock());
                    toy.setManufacturingDate(Date.valueOf(toyJSON.getManufacturingDate()));
                    toy.setDescription(toyJSON.getDescription());
                    toy.setInformation(toyJSON.getInformation());
                    toy.setOldPrice(toyJSON.getOldPrice());
                    toy.setCategory(category);
                }catch (Error error){
                    return new ResponseEntity<>(error.getMessage(), HttpStatus.BAD_REQUEST);
                }


                if (toy.getAccount().getId() == account.getId()) {
                    toyService.save(toy);
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }
            }
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/toy/{id}", method = RequestMethod.GET)
    public ResponseEntity<ToyJSON> getToy(@PathVariable("id") Long id, Principal principal) {

        if (principal != null) {

            Account account = accountService.findAccountByUsername(principal.getName());

            if ("ROLE_SELLER".equals(account.getRole())) {

                Toy toy = toyService.findById(id);

                ToyJSON toyJSON = new ToyJSON();

                toyJSON.setId(toy.getId());
                toyJSON.setName(toy.getName());
                toyJSON.setImage(toy.getImage());
                toyJSON.setPrice(toy.getPrice());
                toyJSON.setQuantityInStock(toy.getQuantityInStock());
                toyJSON.setManufacturingDate(toy.getManufacturingDate().toString());
                toyJSON.setDescription(toy.getDescription());
                toyJSON.setInformation(toy.getInformation());
                toyJSON.setOldPrice(toy.getOldPrice());
                toyJSON.setBrandId(toy.getBrand().getId());
                toyJSON.setBrandName(toy.getBrand().getName());
                toyJSON.setCategoryId(toy.getCategory().getId());
                toyJSON.setCategoryName(toy.getCategory().getName());

                if (toy.getAccount().getId() == account.getId()) {
                    return new ResponseEntity<>(toyJSON, HttpStatus.OK);
                }
            }
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/list-category",method = RequestMethod.GET)
    public ResponseEntity<List<CategoryJSON>> getListCategory(){
        List<Category> categories = categoryService.findAll();
        List<CategoryJSON> categoryJSONS = new ArrayList<>();
        for (Category c :
                categories) {
            CategoryJSON categoryJSON = new CategoryJSON();

            categoryJSON.setId(c.getId());
            categoryJSON.setName(c.getName());
            categoryJSON.setParentId(c.getParentId());

            categoryJSONS.add(categoryJSON);
        }
        return new ResponseEntity<>(categoryJSONS, HttpStatus.OK);
    }

    @RequestMapping(value = "/list-brand",method = RequestMethod.GET)
    public ResponseEntity<List<BrandJSON>> getListBrand() {
        List<Brand> brands = brandService.findAll();
        List<BrandJSON> brandJSONS = new ArrayList<>();
        for (Brand b :
                brands) {
            BrandJSON brandJSON = new BrandJSON();

            brandJSON.setId(b.getId());
            brandJSON.setBrandName(b.getName());
            brandJSON.setLogo(b.getLogo());
            brandJSON.setNation(b.getNation());
            brandJSON.setOfflineStore(b.getOfflineStore());

            brandJSONS.add(brandJSON);
        }
        return new ResponseEntity<>(brandJSONS, HttpStatus.OK);
    }

    @RequestMapping(value = "/create-toy",method = RequestMethod.POST)
    public ResponseEntity<Void> createNewToy(@RequestBody ToyJSON toyJSON, Principal principal){
        if (principal != null){
            Account account = accountService.findAccountByUsername(principal.getName());
            if ("ROLE_SELLER".equals(account.getRole())){
                Toy toy = new Toy();
                toy.setName(toyJSON.getName());
                toy.setImage(toyJSON.getImage());
                toy.setPrice(toyJSON.getPrice());
                toy.setQuantityInStock(toyJSON.getQuantityInStock());
                toy.setManufacturingDate(Date.valueOf(toyJSON.getManufacturingDate()));
                toy.setDescription(toyJSON.getDescription());
                toy.setInformation(toyJSON.getInformation());
                toy.setOldPrice(toyJSON.getOldPrice());
                toy.setOnSale(toyJSON.isOnSale());
                toy.setDisplay("ENABLE");

                Brand brand = brandService.findBrandByName(toyJSON.getBrandName());
                toy.setBrand(brand);

                Category category = categoryService.findCategoryByName(toyJSON.getCategoryName());
                toy.setCategory(category);

                toy.setAccount(account);

                toyService.save(toy);
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}
