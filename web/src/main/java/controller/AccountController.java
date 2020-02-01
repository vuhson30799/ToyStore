package controller;

import model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import service.AccountService;
import service.RatingService;
import session.OrderSession;
import validation.AccountValidator;
import validation.PasswordValidator;
import validation.UpdateAccountValidator;

import javax.validation.Valid;
import java.security.Principal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Controller
@SessionAttributes("cart")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private RatingService ratingService;

    @Autowired
    private AccountValidator accountValidator;

    @Autowired
    private UpdateAccountValidator updateAccountValidator;

    @Autowired
    private PasswordValidator passwordValidator;

    @Autowired
    private JavaMailSender javaMailSender;

    @ModelAttribute("cart")
    public OrderSession orderSession() {
        return new OrderSession();
    }

    @ModelAttribute("login-account")
    public Account loginAccount() {
        return new Account();
    }

    @GetMapping("/login-form")
    public ModelAndView login(@RequestParam(value = "error", required = false) String error) {

        ModelAndView modelAndView = new ModelAndView("login");
        modelAndView.addObject("account", new Account());

        if (error != null) {
            modelAndView.addObject("message", "Username or password is incorrect");
        }

        return modelAndView;
    }

    @GetMapping("/profile")
    public ModelAndView profile(Principal principal) {

        ModelAndView modelAndView = new ModelAndView("/profile");

        Account account = accountService.findAccountByUsername(principal.getName());
        modelAndView.addObject("account", account);

        modelAndView.addObject("prv", account.getProvince().getId());
        modelAndView.addObject("dst", account.getDistrict().getId());
        modelAndView.addObject("vlg", account.getVillage().getId());

        Password password = new Password();
        password.setOldPassword(account.getPassword());
        modelAndView.addObject("password", password);

        return modelAndView;
    }

    @PostMapping("/update")
    public ModelAndView updateProfile(@Valid @ModelAttribute("account") Account account, BindingResult bindingResult) {

        ModelAndView modelAndView = new ModelAndView("/profile");

        if (account.getProvince() != null) {
            modelAndView.addObject("prv", account.getProvince().getId());
        } else {
            modelAndView.addObject("prv", 0);
        }

        if (account.getDistrict() != null) {
            modelAndView.addObject("dst", account.getDistrict().getId());
        } else {
            modelAndView.addObject("dst", 0);
        }

        if (account.getVillage() != null) {
            modelAndView.addObject("vlg", account.getVillage().getId());
        } else {
            modelAndView.addObject("vlg", 0);
        }

        updateAccountValidator.validate(account, bindingResult);
        if (bindingResult.hasFieldErrors()) {
            return modelAndView;
        }

        accountService.update(account);

        modelAndView.addObject("account", account);
        modelAndView.addObject("message", "Update profile information successful!");

        return modelAndView;
    }

    @GetMapping("/change-password/{id}")
    public ModelAndView changePasswordForm(@PathVariable Long id) {

        ModelAndView modelAndView = new ModelAndView("/change-password");

        Password password = new Password();
        password.setId(id);
        modelAndView.addObject("password", password);

        return modelAndView;
    }

    @PostMapping("/change-password")
    public ModelAndView changePassword(@Valid @ModelAttribute("password") Password password, BindingResult bindingResult) {

        ModelAndView modelAndView = new ModelAndView("/change-password");

        password.setOldPassword(accountService.getPasswordById(password.getId()));
        passwordValidator.validate(password, bindingResult);
        if (bindingResult.hasFieldErrors()) {
            return modelAndView;
        }

        accountService.updatePassword(password.getId(), password.getNewPassword());
        modelAndView.addObject("message", "Changed password successful!");
        modelAndView.addObject("password", password);

        return modelAndView;
    }

    @GetMapping("/register")
    public ModelAndView registerForm() {

        ModelAndView modelAndView = new ModelAndView("register");
        modelAndView.addObject("account", new Account());

        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView register(@Valid @ModelAttribute("account") Account account, BindingResult bindingResult) {

        accountValidator.validate(account, bindingResult);
        if (bindingResult.hasFieldErrors()) {

            ModelAndView modelAndView = new ModelAndView("register");

            if (account.getProvince() != null) {
                modelAndView.addObject("prv", account.getProvince().getId());
            } else {
                modelAndView.addObject("prv", 0);
            }

            if (account.getDistrict() != null) {
                modelAndView.addObject("dst", account.getDistrict().getId());
            } else {
                modelAndView.addObject("dst", 0);
            }

            if (account.getVillage() != null) {
                modelAndView.addObject("vlg", account.getVillage().getId());
            } else {
                modelAndView.addObject("vlg", 0);
            }

            return modelAndView;
        }

        account.setRole("ROLE_USER");
        accountService.save(account);

        return new ModelAndView("redirect: login-form");

    }

    @GetMapping("/rating/{id}")
    public ModelAndView rating(@PathVariable Long id) {

        ModelAndView modelAndView = new ModelAndView("iframe/rating");
        List<Rating> ratings = new ArrayList<>();

        // remove duplicate rating
        List<Long> accountIds = new ArrayList<>();
        for (Rating rating : ratingService.findAllByToyId(id)) {
            Boolean add = true;
            for (Long i : accountIds) {
                if (rating.getAccount().getId() == i) {
                    add = false;
                    break;
                }
            }
            if (add) {
                accountIds.add(rating.getAccount().getId());
                ratings.add(rating);
            }
        }

        // remove comment not rating
        Long ratingSize = 0L;
        for (Rating rating : ratings) {
            if (rating.getRatingStar() != 0) {
                ratingSize++;
            }
        }

        List<Star> stars = new ArrayList<>();

        if (!ratings.isEmpty()) {
            Long average = 0L;
            String color;
            Long avgStar;

            for (Long i = 5L; i > 0; i --) {
                Long temp = 0L;
                for (Rating rating : ratings) {
                    if (rating.getRatingStar() == i) {
                        temp++;
                    }
                }

                if (ratingSize != 0) {
                    avgStar = 100 * temp / ratingSize;
                } else {
                    avgStar = 0L;
                }

                if (avgStar <= 20) {
                    color = "danger";
                } else if (avgStar > 20 && avgStar <= 40) {
                    color = "warning";
                } else if (avgStar > 40 && avgStar <= 60) {
                    color = "info";
                } else if (avgStar > 60 && avgStar <= 80) {
                    color = "stripped";
                } else {
                    color = "success";
                }

                stars.add(new Star(i, avgStar, color));
                average += i * temp;
            }

            modelAndView.addObject("average", (float)Math.round((float) 10 * average / ratingSize) / 10);
        } else {
            for (Long i = 5L; i > 0; i --) {
                stars.add(new Star(i, 0L, ""));
            }

            modelAndView.addObject("average", 0);
        }

        modelAndView.addObject("total", ratingSize);
        modelAndView.addObject("stars", stars);

        return modelAndView;
    }

    @GetMapping("/favorite")
    public ModelAndView favoriteToys(Principal principal) {

        ModelAndView modelAndView = new ModelAndView("/favorite");

        if (principal != null) {
            Account account = accountService.findAccountByUsername(principal.getName());
            modelAndView.addObject("toys", account.getFavoriteToys());
        }

        return modelAndView;
    }

    @PostMapping("/remove-favorite")
    public ModelAndView removeToy(@RequestParam("removes") String[] removes, Principal principal) {

        ModelAndView modelAndView = new ModelAndView("/favorite");

        if (principal != null) {

            Account account = accountService.findAccountByUsername(principal.getName());

            for (String s : removes) {
                account.removeToy(Long.parseLong(s));
            }

            accountService.save(account);

            modelAndView.addObject("message", "Remove toys out of favorite list success!");
            modelAndView.addObject("toys", account.getFavoriteToys());

        }


        return modelAndView;
    }

    @GetMapping("/contact")
    public ModelAndView contact() {

        ModelAndView modelAndView = new ModelAndView("contact");
        modelAndView.addObject("feedbackEmail",new CustomerFeedbackEmail());
        return modelAndView;
    }

    @PostMapping("/contact")
    public ModelAndView responseEmail(@ModelAttribute(name = "feedbackEmail") CustomerFeedbackEmail customerFeedbackEmail){
        ModelAndView modelAndView = new ModelAndView("contact");

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(CustomerFeedbackEmail.TARGET_EMAIL);
        message.setFrom(CustomerFeedbackEmail.FROM_EMAIL);
        message.setSubject(customerFeedbackEmail.SUBJECT_EMAIL);
        message.setSentDate(new Date(System.currentTimeMillis()));
        message.setText("Customer Email: " + customerFeedbackEmail.getEmail() + "\n"
                + "Content: " + customerFeedbackEmail.getContent() + "\n"
                + "Sending Date: " + message.getSentDate());
        javaMailSender.send(message);

        modelAndView.addObject("message","Send Successfully!!");

        return modelAndView;
    }

    @GetMapping("/denied")
    public String denied() {
        return "denied";
    }
}
