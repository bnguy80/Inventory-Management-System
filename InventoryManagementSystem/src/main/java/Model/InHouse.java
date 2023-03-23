package Model;

/**
 *Models in-house part.
 *
 * @author Brandon Nguyen
* */
public class InHouse extends Parts{

    /**
     *Machine ID of part.
    * */
    private int machineID;

    /**
     *New in-house part instance constructor.
     *
     * @param id part ID
     * @param name part name
     * @param price part price
     * @param stock part inventory level
     * @param min part minimum
     * @param max part minimum
     * @param machineID part machine ID
    * */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineID) {
        super(id, name, price, stock, min, max);
        this.machineID = machineID;
    }

    /**
     * Getter for part machine ID
     *
     * @return part machine ID
    * */
    public int getMachineID() {
        return machineID;
    }

    /**
     * Setter for part machine ID
     *
     * @param machineID part machine ID
    * */
    public void setMachineID(int machineID) {
        this.machineID = machineID;
    }
}
