package controller;

import model.Seller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import service.AccountService;
import service.BusinessFieldService;
import service.SellerService;
import validation.SellerValidator;

import javax.validation.Valid;

@Controller
public class SellerController {

    @Autowired
    private SellerService sellerService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private SellerValidator sellerValidator;

    @Autowired
    private BusinessFieldService businessFieldService;

    @GetMapping("/register-selling")
    public ModelAndView registerSellingForm(){
        ModelAndView modelAndView = new ModelAndView("register-selling");
        Seller seller = new Seller();
        seller.setBusinessCertificate(true);
        modelAndView.addObject("seller",seller);
        modelAndView.addObject("businessFields",businessFieldService.findAll());
        return modelAndView;
    }

    @PostMapping("/register-selling")
    public ModelAndView registerSelling(@Valid @ModelAttribute("seller") Seller seller, BindingResult bindingResult) {

        sellerValidator.validate(seller, bindingResult);
        if (bindingResult.hasFieldErrors()) {

            ModelAndView modelAndView = new ModelAndView("register-selling");

            if (seller.getAccount().getProvince() != null) {
                modelAndView.addObject("prv", seller.getAccount().getProvince().getId());
            } else {
                modelAndView.addObject("prv", 0);
            }

            if (seller.getAccount().getDistrict() != null) {
                modelAndView.addObject("dst", seller.getAccount().getDistrict().getId());
            } else {
                modelAndView.addObject("dst", 0);
            }

            if (seller.getAccount().getVillage() != null) {
                modelAndView.addObject("vlg", seller.getAccount().getVillage().getId());
            } else {
                modelAndView.addObject("vlg", 0);
            }

            return modelAndView;
        }

        seller.getAccount().setRole("ROLE_SELLER");
        accountService.save(seller.getAccount());
        sellerService.save(seller);

        return new ModelAndView("redirect: login-form");

    }
}

