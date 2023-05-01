package mml.tehtava;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class CsvReader {

    // The class that handles the fetching of data from .csv files

    // Get the specified product from the product file
    public static Product getProductByName(String search) throws IOException {
        try {
            BufferedReader csvReader = new BufferedReader(new FileReader("app\\src\\main\\resources\\Tuotteet.csv"));
            String row;
            try {
                while ((row = csvReader.readLine()) != null) {
                    String[] data = row.split(";");
                    // When matching row is found, create the class and return it
                    if (search.matches(data[0])) { 
                        csvReader.close();
                        return new Product(data[0], Integer.parseInt(data[1]));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // If nothing is found, print a warning and return null
        // This will most certainly cause a crash, but as long the items in the files match
        // the program won't try to search for non-existent items.
        System.out.println("VIRHE: Tuote \"" + search + "\"" + " ei ole tuotelistassa.");
        return null; 
    }

    // Returns all the products found in the file as an array
    public static ArrayList<Product> getProducts() throws IOException {
        ArrayList<Product> productList = new ArrayList<Product>();
        BufferedReader reader = new BufferedReader(new FileReader("app\\src\\main\\resources\\Tuotteet.csv"));
        String row;
        while ((row = reader.readLine()) != null) { 
            String[] data = row.split(";");
            productList.add(new Product(data[0], Integer.parseInt(data[1])));
        }
        reader.close();
        return productList; 
    }

    // Same as getProductByName, but for customers
    public static Customer getCustomerById(String search) throws IOException {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("app\\src\\main\\resources\\Asiakkaat.csv"));
            String row;
            try {
                while ((row = reader.readLine()) != null) {
                    String[] data = row.split(";");
                    if (search.matches(data[0])) { 
                        reader.close();
                        return new Customer(data[0], data[1]);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("VIRHE: Asiakas \"" + search + "\"" + " ei ole asiakaslistassa.");
        return null;
    }

    // Returns all the customers found in the file as an array
    public static ArrayList<Customer> getCustomers() throws IOException {
        ArrayList<Customer> customerList = new ArrayList<Customer>();

        BufferedReader reader = new BufferedReader(new FileReader("app\\src\\main\\resources\\Asiakkaat.csv"));
        String row;
        while ((row = reader.readLine()) != null) { 
            String[] data = row.split(";");
            if (!data[0].matches("Asiakastunnus")) {
                customerList.add(new Customer(data[0], data[1]));
            }
        }
        reader.close();

        return customerList;
    }

    // Returns all the billing instances found in the file as an array
    public static ArrayList<BillingInstance> getBillingInstances() throws IOException {
        ArrayList<BillingInstance> instanceList = new ArrayList<BillingInstance>();

        BufferedReader reader = new BufferedReader(new FileReader("app\\src\\main\\resources\\Laskutustapahtumat.csv"));
        String row;
        while((row = reader.readLine()) != null) {
            String[] data = row.split(";");
            // Create the instance class and add it to the list
            instanceList.add(new BillingInstance(
                getCustomerById(data[0]),
                getProductByName(data[1]),
                Integer.parseInt(data[2]),
                data[3]));
        }
        reader.close();

        return instanceList;
    }

    // Get the price list for the specified group and return it as a hashmap
    public static HashMap<String, Integer> getPricelistByGroup(String groupName) throws IOException {
        HashMap<String, Integer> priceMap = new HashMap<String, Integer>();

        BufferedReader reader = new BufferedReader(new FileReader("app\\src\\main\\resources\\hinnastot\\" + groupName + ".csv"));
        String row;

        while((row = reader.readLine()) != null) {
            String[] data = row.split(";");
            priceMap.put(data[0], Integer.parseInt(data[1]));
        }
        reader.close();

        return priceMap;
    }


    // Get the price for the specified product for the specified group
    public static int getProductPriceForGroup(String groupName, String productName) throws NumberFormatException, IOException {
        BufferedReader reader = new BufferedReader(new FileReader("app\\src\\main\\resources\\hinnastot\\" + groupName + ".csv"));
        String row;
        while((row = reader.readLine()) != null) {
            String[] data = row.split(";");
            if (data[0].matches(productName)) {
                reader.close();
                return Integer.parseInt(data[1]);
            }
        }
        reader.close();
        return 0;
    }
}
