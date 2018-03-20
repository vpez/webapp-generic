package org.vap.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.vap.webapp.data.DataManager;
import org.vap.webapp.data.Product;

import java.util.Arrays;
import java.util.List;

/**
 * @author Vahe Pezeshkian
 * March 20, 2018
 */

@RestController
public class SampleController {

    @Autowired
    private DataManager<Product> productDataManager;

    @RequestMapping("/list")
    public List<String> getList() {
        List<String> list = Arrays.asList("One", "Two", "Three");
        return list;
    }

    @RequestMapping("/insert")
    public Product insert() {
        Product product = new Product();
        product.setName("Beer");
        product.setPrice(12.50);

        productDataManager.insert(product);
        return product;
    }

    @RequestMapping("/find/{id}")
    public Product find(@PathVariable("id") String id) {
        return productDataManager.getById(id);
    }
}
