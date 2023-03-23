package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Models product that contain associated parts.
 *
 * @author Brandon Nguyen
* */
public class Products {

    /**
     *List of associated parts for product.
    * */
    private final ObservableList<Parts> associatedParts = FXCollections.observableArrayList();

    /**
     *ID of product.
    * */
    private int id;

    /**
     * Name of product.
    * */
    private String name;

    /**
     * Price of product.
    * */
    private Double price;

    /**
     * Inventory level of product.
    * */
    private int stock;

    /**
     *Minimum of product.
    * */
    private int min;

    /**
     * Maximum of product.
    * */
    private int max;

    /**
     *New product instance constructor.
     *
     * @param id product ID
     * @param name product name
     * @param price product price
     * @param stock product inventory level
     * @param min product minimum
     * @param max product maximum
    * */
    public Products(int id, String name, Double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     *Setter for product ID.
     *
     * @param id product ID
    * */
    public void setId(int id) {

        this.id = id;
    }

    /**
     * Setter for product name.
     *
     * @param name product name
    * */
    public void setName(String name) {

        this.name = name;
    }

    /**
     * Setter for product price.
     *
     * @param price product price
    * */
    public void setPrice(Double price) {

        this.price = price;
    }

    /**
     * Setter for product inventory level.
     *
     * @param stock product inventory level
    * */
    public void setStock(int stock) {

        this.stock = stock;
    }

    /**
     * Setter for product minimum.
     *
     * @param min product minimum
    * */
    public void setMin(int min) {

        this.min = min;
    }

    /**
     * Setter for product maximum.
     *
     * @param max product minimum
    * */
    public void setMax(int max) {

        this.max = max;
    }

    /**
     * Getter for product ID.
     *
     * @return product ID
    * */
    public int getId() {

        return this.id;
    }

    /**
     * Getter for product name.
     *
     * @return product name
    * */
    public String getName() {

        return this.name;
    }

    /**
     * Getter for product price.
     *
     * @return product price
    * */
    public Double getPrice() {

        return this.price;
    }

    /**
     * Getter for product inventory level.
     *
     * @return product inventory level
    * */
    public int getStock() {

        return this.stock;
    }

    /**
     * Getter for product minimum.
     *
     * @return product minimum
    * */
    public int getMin() {

        return this.min;
    }

    /**
     * Getter for product maximum.
     *
     * @return product maximum
    * */
    public int getMax() {

        return this.max;
    }

    /**
     *Add part to associated part list of product.
     *
     * @param part part to add
    * */
    public void addAssociatedPart(Parts part) {

        this.associatedParts.add(part);
    }

    /**
     *Delete part from associated list of product.
     *
     * @param selectedAssociatedPart part to delete
     *
     * @return return boolean indicating result of deletion
    * */
    public boolean deleteAssociatedPart(Parts selectedAssociatedPart) {
        if (associatedParts.contains(selectedAssociatedPart)) {
           associatedParts.remove(selectedAssociatedPart);
           return true;
        } else {
            return false;
        }
    }

    /**
     *Get list of associated parts of product.
     *
     * @return list of associated parts of product
    * */
    public ObservableList<Parts> getAllAssociatedParts() {

        return associatedParts;
    }
}
