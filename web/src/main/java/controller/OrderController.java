package controller;

import model.Account;
import model.Ordered;
import model.Toy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import service.*;
import session.OrderSession;

import java.security.Principal;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

@Controller
@SessionAttributes("cart")
public class OrderController {

    @Autowired
    private ToyService toyService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private OrderedService orderedService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private CategoryService categoryService;

    @ModelAttribute("cart")
    public OrderSession orderSession() {
        return new OrderSession();
    }

    @GetMapping("/cart")
    public ModelAndView cart() {

        ModelAndView modelAndView = new ModelAndView("cart");

        modelAndView.addObject("manufactures", brandService.findRandomBrands(4L));
        modelAndView.addObject("subcats", categoryService.findRandomCategories(6L));

        return modelAndView;
    }

    @GetMapping("/add-to-cart/{id}")
    public ModelAndView addToCart(@PathVariable Long id, @RequestParam("qty") String qty, @ModelAttribute("cart") OrderSession orderSession) {

        ModelAndView modelAndView = new ModelAndView("cart");

        Ordered order = new Ordered();

        Date date = new Date(System.currentTimeMillis());
        order.setOrderDate(date);
        order.setQuantity(Long.parseLong(qty));
        order.setToy(toyService.findById(id));

        if (!orderSession.containOrdered(order)) {
            orderSession.add(order);
        }

        return modelAndView;
    }

    @PostMapping("/remove")
    public String remove(@RequestParam("removes") String[] removes, @ModelAttribute("cart") OrderSession orderSession) {

        for (String i : removes) {
            orderSession.removeOrder(Long.parseLong(i));
        }

        return "redirect: cart";
    }

    @PostMapping("/checkout")
    public ModelAndView checkout(@RequestParam(value = "cQty", required = false) String[] cQty, @RequestParam("paymentType") String paymentType, @ModelAttribute("cart") OrderSession orderSession, Principal principal) {

        ModelAndView modelAndView = new ModelAndView("cart");

        if (cQty == null) {
            modelAndView.addObject("message", "You have no any orders in your cart!");
            return modelAndView;
        }

        int i = 0;

        Account account = accountService.findAccountByUsername(principal.getName());

        for (Ordered order : orderSession.getOrders()) {
            order.setAccount(account);
            order.setQuantity(Long.parseLong(cQty[i]));
            i++;
        }

        ModelAndView payment = new ModelAndView("payment");
        payment.addObject("account", account);

        return payment;
    }

    @PostMapping("/ordered")
    public ModelAndView ordered(@ModelAttribute("cart") OrderSession orderSession) {

        ModelAndView modelAndView = new ModelAndView("ordered");

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Long currentTime = System.currentTimeMillis();
        Long deliveryTime1 = currentTime + 3 * 24 * 60 * 60 * 1000;
        Long deliveryTime2 = currentTime + 5 * 24 * 60 * 60 * 1000;
        String date1 = format.format(new Date(deliveryTime1));
        String date2 = format.format(new Date(deliveryTime2));

        String message = "Thank you for buying toys in ToyStore.com!</br>Your order will be delivery on from " + date1 + " to " + date2;
        modelAndView.addObject("message", message);

        for (Ordered order : orderSession.getOrders()) {

            order.setStatus("DELIVERING");
            Toy toy = toyService.findById(order.getToy().getId());
            toyService.updateQuantityInStock(toy.getId(), toy.getQuantityInStock() - order.getQuantity());

            orderedService.save(order);
        }

        orderSession.setOrders(new ArrayList<Ordered>());

        return modelAndView;
    }

    @GetMapping("/order-history")
    public ModelAndView orderHistory(Principal principal) {

        ModelAndView modelAndView = new ModelAndView("order-history");

        modelAndView.addObject("orders", orderedService.findAllByAccount_Username(principal.getName()));

        return modelAndView;
    }

    @GetMapping("/manage-order")
    public ModelAndView manager() {

        ModelAndView modelAndView = new ModelAndView("manager");

        return modelAndView;

    }

}
