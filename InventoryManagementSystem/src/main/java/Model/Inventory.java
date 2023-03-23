package Model;

import Controller.MainWindowController;
import com.example.c482.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *Model inventory of parts and products.
 *
 *@author Brandon Nguyen
* */
public class Inventory {

    /**
     *List of all parts in inventory.
    * */
    private static ObservableList<Parts> allParts = FXCollections.observableArrayList();

    /**
     * List of all products in inventory.
     * */
    private static ObservableList<Products> allProducts = FXCollections.observableArrayList();

    /**
     * Add parts to inventory.
     *
     * @param newPart part object to add
    * */
    public static void addPart(Parts newPart) {

        allParts.add(newPart);
    }

    /**
     *Search list of part by ID.
     *
     * @param  partID part ID
     *
     * @return return part object, errorDialog if no match
    * */
/*    public static ObservableList<Parts> lookupPart(int partID) {
        ObservableList<Parts> foundPart = FXCollections.observableArrayList();
        ObservableList<Parts> allParts = Inventory.getAllParts();

        try {
            for (Parts fp : allParts) {
                if (fp.getId() == partID) {
                    foundPart.add(fp);
                }
            }
        }
        catch(Exception e) {
            MainWindowController.errorDialog("No Match", "Please try another search");
        }
        return foundPart;
    }*/

    public static Parts lookUpPart(int partID) {
        int index = 0;
        try {
            for (int i = 0; i < allParts.size(); i++) {
                if (partID == allParts.get(i).getId()) {
                    index = i;
                }
            }
        }
        catch (Exception e) {
            MainWindowController.errorDialog("No Match", "Please try another search");
        }
        return allParts.get(index);
    }

    /**
     *Searches part by full or partial name.
     *
     * @param partialName part name
     *
     * @return return part object, errorDialog if no match
    * */
    public static ObservableList<Parts> lookupPart(String partialName) {
        ObservableList<Parts> foundPart = FXCollections.observableArrayList();
        ObservableList<Parts> allParts = Inventory.getAllParts();

            for (Parts fp : allParts) {
                if (fp.getName().contains(partialName)) {
                    foundPart.add(fp);
                }
            }
        return foundPart;
    }

    /**
     * Add product to inventory.
     *
     * @param newProduct product object to add
    * */
    public static void addProduct(Products newProduct) {

        allProducts.add(newProduct);
    }

    /**
     * Searches product by product ID.
     *
     * @param productID product ID
     *
     * @return return product object
    * */
/*    public static ObservableList<Products> lookUpProduct(int productID) {
        ObservableList<Products> foundProduct = FXCollections.observableArrayList();
        ObservableList<Products> allProducts = Inventory.getAllProducts();

        for (Products fProduct : allProducts) {
            if (fProduct.getId() == productID) {
                foundProduct.add(fProduct);
            }
        }
        return foundProduct;
    }*/

    public static Products lookUpProduct(int productID) {
        int index = 0;

        for (int i = 0; i < allProducts.size(); i++) {
            if (productID == allProducts.get(i).getId()) {
                index = i;
            }
        }
        return allProducts.get(index);
    }

    /**
     * Searches product by full or partial name.
     *
     * @param name product name
     *
     * @return return product object
    * */
    public static ObservableList<Products> lookUpProduct(String name) {
        ObservableList<Products> foundProduct = FXCollections.observableArrayList();
        ObservableList<Products> allProducts = Inventory.getAllProducts();

        for (Products fProduct : allProducts) {
            if (fProduct.getName().contains(name)) {
                foundProduct.add(fProduct);
            }
        }
        return foundProduct;
    }

    /**
     * Update part in list of parts.
     *
     * @param index index of part to replace
     *
     * @param selectedPart part object replacing it
    * */
    public static void updatePart(int index, Parts selectedPart) {

        allParts.set(index, selectedPart);
    }

    /**
     * Update product in list of products.
     *
     * @param index index of product ot replace
     *
     * @param newProduct product object replacing it
    * */
    public static void updateProduct(int index, Products newProduct) {

        allProducts.set(index, newProduct);
    }

    /**
     *Delete part from list of parts.
     *
     * @param selectedPart part to be deleted
     *
     * @return return boolean indicating result of deletion
    * */
    public static boolean deletePart(Parts selectedPart) {
        if(allParts.contains(selectedPart)) {
            allParts.remove(selectedPart);
            return true;
        } else {
            return false;
        }
    }

    /**
    * Delete product from list of products.
     *
     * @param selectedProduct product to be deleted
     *
     * @return return boolean indicating result of deletion
    * */
    public static boolean deleteProduct(Products selectedProduct) {
        if(allProducts.contains(selectedProduct)) {
            allProducts.remove(selectedProduct);
            return true;
        } else {
            return false;
        }
    }

    /**
     *Get list of all parts in inventory.
     *
     * @return return list of parts
    * */
    public static ObservableList<Parts> getAllParts() {

        return allParts;
    }

    /**
     * Get list of all products in inventory.
     *
     * @return return list of products
    * */
    public static ObservableList<Products> getAllProducts() {

        return allProducts;
    }
}
