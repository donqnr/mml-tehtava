package mml.tehtava;

public class Customer {

    // Class got the customer, holds the id and the group they belong to

    private String customerId;
    private String customerGroup;

    public Customer(String id, String group) {
        this.customerId = id;
        this.customerGroup = group;
    }

    public String getId() {
        return this.customerId;
    }

    public String getGroup() {
        return this.customerGroup;
    }
}
