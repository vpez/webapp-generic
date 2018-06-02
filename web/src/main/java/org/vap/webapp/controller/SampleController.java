package org.vap.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.vap.webapp.data.Repository;
import org.vap.webapp.model.Product;

import java.util.Arrays;
import java.util.List;

/**
 * @author Vahe Pezeshkian
 * March 20, 2018
 */

@RestController
public class SampleController {

    @Autowired
    private Repository<Product> productRepository;

    @RequestMapping("/list")
    public List<String> getList() {
        List<String> list = Arrays.asList("One", "Two", "Three");
        return list;
    }

    @RequestMapping("/insert")
    public Product insert() {
        Product product = new Product();
        product.setName("Wine");
        product.setPrice(8.0);

        productRepository.insert(product);
        return product;
    }

    @RequestMapping("/find/{id}")
    public Product find(@PathVariable("id") String id) {
        return productRepository.getById(id);
    }

    @RequestMapping("/findAll")
    public List<Product> findAll() {
        return productRepository.getAll();
    }
}
