package controller;

import model.*;
import service.*;
import session.OrderSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@SessionAttributes("cart")
public class ToyController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private ToyService toyService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private RatingService ratingService;

    @Autowired
    private AdSlideService adSlideService;

    @Autowired
    private OrderedService orderedService;

    private String str;
    private List<Long> cat;

    private void getMenu(List<Category> categories, Long parentId) {

        if (!categories.isEmpty()) {
            str += "<ul>";

            for (Category c : categories) {
                if (c.getParentId() == parentId) {
                    str += ("<li><a href='/products-category/" + c.getId() + "'>" + c.getName() + "</a>");
                    getMenu(categories, c.getId());
                    str += "</li>";
                }
            }

            str += "</ul>";
        }
    }

    private void getCategory(List<Category> categories, Long parentId) {

        if (!categories.isEmpty()) {
            for (Category c : categories) {
                if (c.getParentId() == parentId) {
                    cat.add(c.getId());
                    getCategory(categories, c.getId());
                }
            }
        }
    }

    @ModelAttribute("cart")
    public OrderSession orderSession() {
        return new OrderSession();
    }

    @ModelAttribute("categoryHtml")
    public String categoryHtml() {

        str = "";
        getMenu(categoryService.findAll(), 0L);

        return str;
    }

    @ModelAttribute("adImages")
    public Iterable<AdSlide> adImages() {
        return adSlideService.findAll();
    }

    @GetMapping({"/home","/"})
    public ModelAndView homepage(Pageable pageable) {

        ModelAndView modelAndView = new ModelAndView("index");

        modelAndView.addObject("brands", brandService.findRandomBrands(6L));

        List<Toy> toys = toyService.findRandomToys();
        if (toys.size() <= 4) {
            modelAndView.addObject("toys1", toys);
        } else {
            modelAndView.addObject("toys1", toys.subList(0, 4));
            modelAndView.addObject("toys2", toys.subList(4, toys.size()));
        }

        return modelAndView;
    }

    @GetMapping("/products-brand/{id}")
    public ModelAndView productsBrand(@PathVariable Long id, Pageable pageable) {

        ModelAndView modelAndView = new ModelAndView("products-brand");

        modelAndView.addObject("title", brandService.findBrandName(id));

        modelAndView.addObject("id", id);
        pageable = new PageRequest(pageable.getPageNumber(), 9);
        Page<Toy> toys = toyService.findBrandToys(id, pageable);
        modelAndView.addObject("toys", toys);

        if (toys.getTotalElements() == 0) {
            modelAndView.addObject("message", "No product has been found");
        }

        modelAndView.addObject("manufactures", brandService.findRandomBrands(4L));
        modelAndView.addObject("subcats", categoryService.findRandomCategories(6L));
        modelAndView.addObject("bestsellers", orderedService.findBestSeller());

        return modelAndView;
    }

    @GetMapping("/products-category/{id}")
    public ModelAndView productsCategory(@PathVariable Long id, Pageable pageable) {

        ModelAndView modelAndView = new ModelAndView("products-category");

        modelAndView.addObject("title", categoryService.findCategoryName(id));

        modelAndView.addObject("id", id);
        pageable = new PageRequest(pageable.getPageNumber(), 9);
        cat = new ArrayList<>();
        cat.add(id);
        getCategory(categoryService.findAll(), id);
        Page<Toy> toys = toyService.findCategoryToys(cat, pageable);
        modelAndView.addObject("toys", toys);

        if (toys.getTotalElements() == 0) {
            modelAndView.addObject("message", "No product has been found");
        }

        modelAndView.addObject("manufactures", brandService.findRandomBrands(4L));
        modelAndView.addObject("subcats", categoryService.findRandomCategories(6L));
        modelAndView.addObject("bestsellers", orderedService.findBestSeller());

        return modelAndView;
    }

    @GetMapping("/detail/{id}")
    public ModelAndView detail(@PathVariable Long id, Principal principal) {

        ModelAndView modelAndView = new ModelAndView("detail");

        Toy toy = toyService.findById(id);
        modelAndView.addObject("toy", toy);

        modelAndView.addObject("images", imageService.findAllByToyId(id));

        List<Information> informations = new ArrayList<>();
        for (String s : toy.getInformation().split("[|]")) {
            String[] info = s.split(":");
            informations.add(new Information(info[0], info[1]));
        }

        modelAndView.addObject("informations", informations);

        modelAndView.addObject("manufactures", brandService.findRandomBrands(4L));
        modelAndView.addObject("subcats", categoryService.findRandomCategories(6L));
        modelAndView.addObject("bestsellers", orderedService.findBestSeller());

        List<Toy> relatedToys = toyService.findRelatedToys();
        modelAndView.addObject("relatedToys1", relatedToys.subList(0, 3));
        modelAndView.addObject("relatedToys2", relatedToys.subList(3, relatedToys.size()));

        if (principal != null) {
            if (ratingService.findAllByAccount_UsernameAndToy_Id(principal.getName(), id).size() != 0) {
                modelAndView.addObject("star", ratingService.findAllByAccount_UsernameAndToy_Id(principal.getName(), id).get(0).getRatingStar());
            }

            modelAndView.addObject("username", principal.getName());
        }

        return modelAndView;
    }

    @GetMapping("/search-sort/{sort}/{word}")
    public ModelAndView searchSort(@PathVariable String sort, @PathVariable String word, Pageable pageable) {

        ModelAndView modelAndView = new ModelAndView("search");

        pageable = new PageRequest(pageable.getPageNumber(), 9);
        Page<Toy> toys = toyService.findAllByName(word, sort, pageable);

        String message = "";
        if (toys.getTotalElements() == 0) {
            message = "No results for '" + word + "'";
        } else {
            message = "Search result for '" + word + "': " + toys.getTotalElements() + " results";
        }

        modelAndView.addObject("message", message);

        modelAndView.addObject("toys", toys);
        modelAndView.addObject("word", word);
        modelAndView.addObject("sorted", sort);
        modelAndView.addObject("manufactures", brandService.findRandomBrands(4L));
        modelAndView.addObject("bestsellers", orderedService.findBestSeller());

        return modelAndView;
    }

    @PostMapping("/search")
    public ModelAndView search(@RequestParam("word") String word, Pageable pageable) {

        ModelAndView modelAndView = new ModelAndView("search");

        pageable = new PageRequest(pageable.getPageNumber(), 9);
        Page<Toy> toys = toyService.findAllByName(word, "none", pageable);

        String message = "";
        if (toys.getTotalElements() == 0) {
            message = "No results for '" + word + "'";
        } else {
            message = "Search result for '" + word + "': " + toys.getTotalElements() + " results";
        }

        modelAndView.addObject("message", message);

        modelAndView.addObject("toys", toys);
        modelAndView.addObject("word", word);
        modelAndView.addObject("sorted", "none");
        modelAndView.addObject("manufactures", brandService.findRandomBrands(4L));
        modelAndView.addObject("bestsellers", orderedService.findBestSeller());

        return modelAndView;
    }

    @PostMapping("/price-range/{word}")
    public ModelAndView priceRange(@PathVariable String word, @RequestParam("price1") String price1, @RequestParam("price2") String price2, Pageable pageable) {

        ModelAndView modelAndView = new ModelAndView("search");

        pageable = new PageRequest(pageable.getPageNumber(), 9);
        Page<Toy> toys = toyService.findAllByPrice(word, price1, price2, pageable);

        String message = "";
        if (toys.getTotalElements() == 0) {
            if (!"".equals(price1) && !"".equals(price2)) {
                message = "No results for price range from $" + price1 + " to " + price2;
            }
            if (!"".equals(price1) && "".equals(price2)) {
                message = "No results for price greater than $" + price1;
            }
            if ("".equals(price1) && !"".equals(price2)) {
                message = "No results for price less than $" + price2;
            }
        } else {
            if (!"".equals(price1) && !"".equals(price2)) {
                message = "Search result for price range from $" + price1 + " to $" + price2 + ": " + toys.getTotalElements() + " results";
            }
            if (!"".equals(price1) && "".equals(price2)) {
                message = "Search result for price greater than $" + price1 + ": " + toys.getTotalElements() + " results";
            }
            if ("".equals(price1) && !"".equals(price2)) {
                message = "Search result for price less than $" + price2 + ": " + toys.getTotalElements() + " results";
            }
        }

        modelAndView.addObject("message", message);

        modelAndView.addObject("toys", toys);
        modelAndView.addObject("word", word);
        modelAndView.addObject("sorted", "none");
        modelAndView.addObject("manufactures", brandService.findRandomBrands(4L));

        return modelAndView;
    }

}