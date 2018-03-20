package org.vap.webapp.boot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.vap.webapp.data.DataManager;
import org.vap.webapp.data.Product;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WebApplicationTests {

	@Autowired
	private DataManager<Product> productDataManager;

	@Test
	public void contextLoads() {
	}

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
