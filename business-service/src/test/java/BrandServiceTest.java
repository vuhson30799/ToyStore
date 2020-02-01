import model.Brand;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import repository.BrandRepository;
import service.BrandServiceImpl;

import javax.validation.constraints.AssertTrue;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BrandServiceTest {
    @Mock
    private BrandRepository brandRepository;

    @Mock
    private List<Brand> brands;

    @InjectMocks
    private BrandServiceImpl brandService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        brands =  new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Brand brand = new Brand();
            brand.setName("brand" + i);
            brand.setId((long) i);
            brands.add(brand);
        }
    }

    @Test
    public void testFindBrandByName(){
        String name = "brand1";
        Brand brand = new Brand();
        brand.setName("brand1");
        when(brandRepository.findFirstByName(name)).thenReturn(brand);
        Assert.assertEquals("brand1", brandService.findBrandByName("brand1").getName());
    }

    @Test
    public void testFindAll(){
        when(brandRepository.findAll()).thenReturn(brands);
        Assert.assertEquals(10, brandService.findAll().size());
    }

    @Test
    public void testFindBrandByNullName(){
        when(brandRepository.findFirstByName(null)).thenThrow(new Exception("Name is null"));
        try{
            brandService.findBrandByName(null);
        }catch (Exception e){
            Assert.assertEquals("Name is null", e.getMessage());
        }
    }
}
