package jpa;

import config.DaoConfig;
import model.Brand;
import model.Toy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.stereotype.Component;
import repository.ToyRepository;

//for test
public class App {
    public static void main( String[] args ) throws InterruptedException
    {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(DaoConfig.class);
        ToyRepository toyRepository = ctx.getBean(ToyRepository.class);

        Toy toy = toyRepository.findOne(1L);

        System.out.println();
        System.out.println(toy.getName());

        Brand brand = toy.getBrand();

        System.out.println(brand.getName());
        System.out.println();

        brand.setName("hello");
        toy.setBrand(brand);
        Thread.sleep(1000);

        toyRepository.save(toy);
        Thread.sleep(1000);
        toy = toyRepository.findOne(1L);
        System.out.println();
        brand = toy.getBrand();
        System.out.println(brand.getName());

    }
}
