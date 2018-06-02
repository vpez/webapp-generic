package org.vap.webapp;

import com.mongodb.MongoClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.vap.webapp.data.Persistent;
import org.vap.webapp.data.Repository;
import org.vap.webapp.data.mongo.MongoConfig;
import org.vap.webapp.data.mongo.MongoRepositoryFactory;

import java.util.Objects;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Vahe Pezeshkian
 * March 20, 2018
 */

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {MongoConfig.class})
public class DataTest {

    @Autowired
    private MongoConfig mongoConfig;
    @Autowired
    private MongoClient mongoClient;

    private MongoRepositoryFactory dataRepositoryFactory;

    @Before
    public void setup() {
        dataRepositoryFactory = new MongoRepositoryFactory(mongoClient, mongoConfig);
    }

    @Test
    public void productCRUD() {

        Repository<TestProduct> productRepository = dataRepositoryFactory.create(TestProduct.class);

        TestProduct product = new TestProduct();
        product.setName("Test_Product");
        product.setPrice(10);

        // Create
        product = productRepository.insert(product);
        final String id = product.getId();
        assertNotNull(id);

        // Update
        product.setPrice(20.0);
        productRepository.update(product);

        // Read
        TestProduct load = productRepository.getById(id);
        assertEquals("Test_Product", load.getName());
        assertEquals(20.0, load.getPrice(), 0.001);

        // Delete
        assertTrue(productRepository.deleteById(id));
        assertNull(productRepository.getById(id));
    }

    @Test
    public void uniqueAddressTest() {
        Repository<TestAddress> dataManager = dataRepositoryFactory.create(TestAddress.class, TestAddress::getAddress);

        TestAddress address1 = new TestAddress();
        address1.city = "Yerevan";
        address1.street = "Republic";

        TestAddress address2 = new TestAddress();
        address2.city = "Yerevan";
        address2.street = "Republic";

        TestAddress address3 = new TestAddress();
        address3.city = "Yerevan";
        address3.street = "Liberty";

        TestAddress one = dataManager.insert(address1);
        assertNotNull(one.getId());

        TestAddress two = dataManager.insert(address2);
        assertNull(two);

        TestAddress three = dataManager.insert(address3);
        assertNotNull(three.getId());

        // Cleanup
        Stream.of(address1, address2, address3)
                .map(TestAddress::getId)
                .filter(Objects::nonNull)
                .forEach(dataManager::deleteById);
    }

    /**
     * A test class to perform database actions
     */
    class TestProduct extends Persistent {
        private String name;
        private double price;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }
    }

    /**
     * A test class that uses composite unique key
     */
    class TestAddress extends Persistent {
        private String city;
        private String street;

        public String getAddress() {
            return city + ":" + street;
        }
    }
}
