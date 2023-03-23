package Model;

/**
 *Models parts, abstract class of Inhouse and Outsourced models.
 *
 * @author
* */
public abstract class Parts {
    /**
     *ID of part.
    * */
    private int id;

    /**
     *Name of part.
    * */
    private String name;

    /**
     *price of price.
    * */
    private double price;

    /**
     * Inventory level of part.
    * */
    private int stock;

    /**
     * Minimum of part.
    * */
    private int min;

    /**
     * Maximum of part.
    * */
    private int max;

    /**
     * New parts instance constructor.
     *
     * @param id part ID
     * @param name part name
     * @param price part name
     * @param stock part inventory level
     * @param min part minimum
     * @param max part maximum
    * */
    public Parts(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * Setter for part ID.
    * */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Setter for part name.
    * */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Setter for part price.
    * */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Setter for part inventory level.
    * */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Setter for part minimum.
    * */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * Setter for part maximum.
    * */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * Getter for part ID.
     *
     * @return part ID
    * */
    public int getId() {
        return this.id;
    }

    /**
     * Getter for part name.
     *
     * @return part name
    * */
    public String getName() {

        return this.name;
    }

    /**
     * Getter for part price.
     *
     * @return part price
    * */
    public double getPrice() {

        return this.price;
    }

    /**
     * Getter for part inventory level.
     *
     * @return part inventory level
    * */
    public int getStock() {

        return this.stock;
    }

    /**
     * Getter for part minimum.
     *
     * @return part minimum
    * */
    public int getMin() {

        return this.min;
    }

    /**
     * Getter for part maximum
     *
     * @return part maximum
    * */
    public int getMax() {

        return this.max;
    }
}


