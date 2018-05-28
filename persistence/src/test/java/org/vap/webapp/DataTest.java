package org.vap.webapp;

import com.mongodb.MongoClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.vap.webapp.data.DataManager;
import org.vap.webapp.data.Persistent;
import org.vap.webapp.data.mongo.MongoConfig;
import org.vap.webapp.data.mongo.MongoDataManagerFactory;

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

    private MongoDataManagerFactory dataManagerFactory;

    @Before
    public void setup() {
        dataManagerFactory = new MongoDataManagerFactory(mongoClient, mongoConfig);
    }

    @Test
    public void productCRUD() {

        DataManager<TestProduct> productDataManager = dataManagerFactory.create(TestProduct.class);

        TestProduct product = new TestProduct();
        product.setName("Test_Product");
        product.setPrice(10);

        // Create
        product = productDataManager.insert(product);
        final String id = product.getId();
        assertNotNull(id);

        // Update
        product.setPrice(20.0);
        productDataManager.update(product);

        // Read
        TestProduct load = productDataManager.getById(id);
        assertEquals("Test_Product", load.getName());
        assertEquals(20.0, load.getPrice(), 0.001);

        // Delete
        assertTrue(productDataManager.deleteById(id));
        assertNull(productDataManager.getById(id));
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
}
