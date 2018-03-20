package org.vap.webapp.data;

/**
 * @author Vahe Pezeshkian
 * March 20, 2018
 */
public class Product extends Persistent {
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
