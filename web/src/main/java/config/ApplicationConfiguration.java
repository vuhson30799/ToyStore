package config;

import formatter.DateSqlFormatter;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.format.FormatterRegistry;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import service.*;

import java.util.Properties;

@Configuration
@ComponentScan({"controller","service","validation","config","security.config"})
@EnableWebMvc
@EnableTransactionManagement
@EnableSpringDataWebSupport
@EnableJpaRepositories("repository")
public class ApplicationConfiguration extends WebMvcConfigurerAdapter implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    // Spring Injection
    @Bean
    public CategoryService categoryService() {
        return new CategoryServiceImpl();
    }

    @Bean
    public BrandService brandService() {
        return new BrandServiceImpl();
    }

    @Bean
    public ToyService toyService() {
        return new ToyServiceImpl();
    }

    @Bean
    public ImageService imageService() {
        return new ImageServiceImpl();
    }

    @Bean
    public AdSlideService adSlideService() {
        return new AdSlideServiceImpl();
    }

    @Bean
    public AccountService accountService() {
        return new AccountServiceImpl();
    }

    @Bean
    public OrderedService orderedService() {
        return new OrderedServiceImpl();
    }

    @Bean
    public RatingService ratingService() {
        return new RatingServiceImpl();
    }

    @Bean
    public ProvinceService provinceService() {
        return new ProvinceServiceImpl();
    }

    @Bean
    public DistrictService districtService() {
        return new DistrictServiceImpl();
    }

    @Bean
    public VillageService villageService() {
        return new VillageServiceImpl();
    }

    @Bean
    public SellerService sellerService(){return new SellerServiceImpl();}

    @Bean
    public BusinessFieldService businessFieldService(){return new BusinessFieldServiceImpl();}

    // Thymeleaf Configuration
    @Bean
    public SpringResourceTemplateResolver templateResolver(){
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(applicationContext);
        templateResolver.setPrefix("/WEB-INF/view/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding("UTF-8");
        return templateResolver;
    }

    @Bean
    public TemplateEngine templateEngine(){
        TemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        templateEngine.addDialect(new SpringSecurityDialect());
        return templateEngine;
    }

    @Bean
    public ThymeleafViewResolver viewResolver(){
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(templateEngine());
        viewResolver.setCharacterEncoding("UTF-8");
        return viewResolver;
    }

    // Resource Files
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resource/**").addResourceLocations("/resource/theme/");
    }

    // Formatter
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(new DateSqlFormatter());
    }

    //email
    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername("toeic.t35@gmail.com");
        mailSender.setPassword("toeic12345");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }
}
