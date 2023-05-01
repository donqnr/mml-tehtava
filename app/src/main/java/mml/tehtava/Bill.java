package mml.tehtava;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

// This class will compile all billing instances of the specified customer, 
// set the price of each product according to customer's group,
// and then print out the final bill.

public class Bill {

    Customer billCustomer;
    ArrayList<BillingInstance> billingInstances;
    Product maintenanceCost;

    public Bill(String customerId) throws IOException {
        this.billCustomer = CsvReader.getCustomerById(customerId); // Find the specified customer
        this.billingInstances = new ArrayList<BillingInstance>(); // Inititalize list of the customer's billing instances
        this.maintenanceCost = CsvReader.getProductByName("Ylläpitomaksu"); // Append each bill with the maintenance cost

        // Fetch the price list of the group the customer belongs to
        HashMap<String, Integer> groupPriceMap = CsvReader.getPricelistByGroup(billCustomer.getGroup()); 
        // Set the maintenance cost according to the customer's group
        this.maintenanceCost.setPrice(groupPriceMap.get("Ylläpitomaksu"));

        // Get the customer's billing instances from the file,
        // apply the price of the product according the customer's group
        // and then add it to the list of instances
        for (BillingInstance i : CsvReader.getBillingInstances()) {
            if (i.getCustomerId().matches(billCustomer.getId())) {
                i.getProduct().setPrice(groupPriceMap.get(i.getProduct().getName()));
                if (i.getProduct().getPrice() != 0) { // If the product has a cost to the customer, add it. Otherwise skip.
                    this.billingInstances.add(i);
                }
            }
        }

    }

    // Print out the final bill for the customer.
    public void print() {
        if (this.getTotalCost() != 0) {
            System.out.println(this.billCustomer.getId() + " | " + this.billCustomer.getGroup() + "\n-"); 
            for (BillingInstance i : this.billingInstances) {
                System.out.println(i.getProduct().getName() + ", " + 
                    i.getProductAmount() + "kpl, " + 
                    i.getProduct().getPrice() + " e/kpl, yhteensä " + 
                    i.getTotalPrice() + " euroa");
            }
            // If the customer has to pay for maintenance costs, print it. Otherwise skip.
            if (this.maintenanceCost.getPrice() != 0) {
                System.out.println(maintenanceCost.getName() + ", " + maintenanceCost.getPrice() + " euroa"); 
            }
            System.out.println("-\nMaksettava summa: " + this.getTotalCost() + " euroa");
        } else {
            System.out.println("Asiakkaalla \"" + this.billCustomer.getId() + "\"" + " ei ole maksullisia tapahtumia");
        }
    }

    // Return the bill as a string
    public String toString() {

        String str = "";

        if (this.getTotalCost() != 0) {
            str = this.billCustomer.getId() + " | " + this.billCustomer.getGroup() + "\n - \n";

            for (BillingInstance i : this.billingInstances) { 
                str += i.getProduct().getName() + ", " + 
                i.getProductAmount() + "kpl, " + 
                i.getProduct().getPrice() + " e/kpl, yhteensä " + 
                i.getTotalPrice() + " euroa\n";
            }
            if (this.maintenanceCost.getPrice() != 0) {
                str += maintenanceCost.getName() + ", " + maintenanceCost.getPrice() + " euroa\n";
            }
    
            str += " -\nMaksettava summa: " + this.getTotalCost() + " euroa";
        } else {
            str = "Asiakkaalla \"" + this.billCustomer.getId() + "\"" + " ei ole maksullisia tapahtumia";
        }
        return str;
    }

    // Calculate the total cost of the customer's bill
    public int getTotalCost() {
        int total = 0;
        for (BillingInstance i : this.billingInstances) {
            total += i.getTotalPrice();
        }
        total += this.maintenanceCost.getPrice();
        return total;
    }

    public ArrayList<BillingInstance> getBillingInstances() {
        return this.billingInstances;
    }
}
