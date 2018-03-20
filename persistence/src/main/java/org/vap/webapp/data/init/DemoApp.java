package org.vap.webapp.data.init;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.vap.webapp.data.DataManager;
import org.vap.webapp.data.Product;
import org.vap.webapp.data.mongo.MongoConfig;

/**
 * @author Vahe Pezeshkian
 * March 20, 2018
 */
public class DemoApp {

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(MongoConfig.class);
        DataManager<Product> productDataManager = (DataManager<Product>) applicationContext.getBean("productDataManager");

        Product product = new Product();
        product.setName("Beer");
        product.setPrice(12.50);
        productDataManager.insert(product);

        product = new Product();
        product.setName("Wine");
        product.setPrice(8.0);
        productDataManager.insert(product);
    }
}
