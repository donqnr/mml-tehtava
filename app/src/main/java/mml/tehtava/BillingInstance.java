package mml.tehtava;

public class BillingInstance {

    // The class for billing instances
    // Holds the customer, product, the amount of the product and the date of the instance

    private Customer billingCustomer;
    private Product billingProduct;
    private int productAmount;
    private String billingDate;

    public BillingInstance(Customer customer, Product product, int amount, String date) {
        this.billingCustomer = customer;
        this.billingProduct = product;
        this.productAmount = amount;
        this.billingDate = date;
    }

    // Get total combined price of the products
    public int getTotalPrice() {
        return this.billingProduct.getPrice() * this.productAmount;
    }

    public Customer getCustomer() {
        return this.billingCustomer;
    }

    public String getCustomerId() {
        return this.billingCustomer.getId();
    }

    public Product getProduct() {
        return this.billingProduct;
    }

    public int getProductAmount() {
        return this.productAmount;
    }

    public String getDate() {
        return this.billingDate;
    }
}
