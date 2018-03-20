package org.vap.webapp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.vap.webapp.data.DataManager;
import org.vap.webapp.data.Product;
import org.vap.webapp.data.mongo.MongoConfig;

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
    private DataManager<Product> productDataManager;

    @Test
    public void productCRUD() {
        Product product = new Product();
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
        Product load = productDataManager.getById(id);
        assertEquals("Test_Product", load.getName());
        assertEquals(20.0, load.getPrice(), 0.001);

        // Delete
        assertTrue(productDataManager.deleteById(id));
        assertNull(productDataManager.getById(id));
    }

}
