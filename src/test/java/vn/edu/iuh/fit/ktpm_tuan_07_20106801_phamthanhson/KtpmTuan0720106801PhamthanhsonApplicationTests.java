package vn.edu.iuh.fit.ktpm_tuan_07_20106801_phamthanhson;

import net.datafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.core.JmsTemplate;
import vn.edu.iuh.fit.ktpm_tuan_07_20106801_phamthanhson.converts.CodingEncryption;
import vn.edu.iuh.fit.ktpm_tuan_07_20106801_phamthanhson.converts.ConvertObjectWithGson;
import vn.edu.iuh.fit.ktpm_tuan_07_20106801_phamthanhson.models.Product;
import vn.edu.iuh.fit.ktpm_tuan_07_20106801_phamthanhson.models.ProductReceive;
import vn.edu.iuh.fit.ktpm_tuan_07_20106801_phamthanhson.repositories.ProductRepository;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class KtpmTuan0720106801PhamthanhsonApplicationTests {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private JmsTemplate jmsTemplate;
    private final ConvertObjectWithGson gson = new ConvertObjectWithGson();
    private final CodingEncryption encryption = new CodingEncryption();

    @Test
    void contextLoads() {
        Faker faker = new Faker();
        List<ProductReceive> productReceives = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            Product product = productRepository.findById((long) faker.number().numberBetween(1, 25)).get();
            ProductReceive productReceive = new ProductReceive();
            productReceive.setProduct(product);
            productReceive.setEmail("thanhson280502@gmail.com");
            productReceive.setQuantity(faker.number().numberBetween(0, 100));
            productReceives.add(productReceive);
        }
        String json = gson.ListObjectToJson(productReceives);
        jmsTemplate.convertAndSend("animal_Shop", encryption.enCode(json));
    }
}
