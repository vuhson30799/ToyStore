package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import service.*;

@Configuration
@ComponentScan({"controller"})
public class WebServiceConfig {
    // Spring Injection

    @Bean
    public ToyService toyService() {
        return new ToyServiceImpl();
    }


    @Bean
    public AccountService accountService() {
        return new AccountServiceImpl();
    }

    @Bean
    public RatingService ratingService() {
        return new RatingServiceImpl();
    }

    @Bean
    public ProvinceService provinceService(){ return new ProvinceServiceImpl();}

    @Bean
    public DistrictService districtService() { return new DistrictServiceImpl();}

    @Bean
    public VillageService villageService(){ return new VillageServiceImpl();}

    @Bean
    public OrderedService orderedService(){ return new OrderedServiceImpl();}

    @Bean
    public BrandService brandService(){ return new BrandServiceImpl();}

    @Bean
    public CategoryService categoryService() { return new CategoryServiceImpl();}
}
