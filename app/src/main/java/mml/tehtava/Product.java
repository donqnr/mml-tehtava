package mml.tehtava;

public class Product {

    // Class for the product, holds the name of the product and its base price

    private String productName;
    private int productPrice;
    
    public Product(String name, int price) {
        this.productName = name;
        this.productPrice = price;
    }

    public String getName() {
        return this.productName;
    }

    public int getPrice() {
        return this.productPrice;
    }

    public void setPrice(int newPrice) {
        this.productPrice = newPrice;
    }
}
