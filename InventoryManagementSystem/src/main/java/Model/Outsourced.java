package Model;

/**
 * Models outsourced part.
 *
 * @author Brandon Nguyen
* */
public class Outsourced extends Parts{

    /**
     * Company name of part.
    * */
    private String companyName;

    /**
     * New outsourced part instance constructor.
     *
     * @param id part ID
     * @param name part name
     * @param price part price
     * @param stock part inventory level
     * @param min part minimum
     * @param max part minimum
     * @param companyName part company name
    * */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * Getter for part company name.
     *
     * @return part company name
    * */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * Setter for part company name.
     *
     * @param companyName part company name
    * */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
