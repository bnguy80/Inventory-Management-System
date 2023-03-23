package Controller;


import Model.InHouse;
import Model.Outsourced;
import com.example.c482.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import static Model.Inventory.addPart;
import static Model.Inventory.getAllParts;

/**
 * Controller class that provides control for add part screen of application.
 *
 * @author Brandon Nguyen
* */
public class AddPartController implements Initializable {

    /**
     * Part name textfield.
    * */
    @FXML
    private TextField Name;

    /**
     * Part inventory level textfield.
    * */
    @FXML
    private TextField Inventory;

    /**
     * Part price textfield.
    * */
    @FXML
    private TextField Price;

    /**
     * Part maximum textfield.
    * */
    @FXML
    private TextField Maximum;

    /**
     * Part company name or machine ID textfield.
    * */
    @FXML
    private TextField CompanyOrMachineID;

    /**
     * Part minimum textfield.
    * */
    @FXML
    private TextField Minimum;

    /**
     * Part machine ID or company name label by radio button selection.
    * */
    @FXML
    private Label InHouseOrOutsourced;

    /**
     * Part outsourced radio button.
    * */
    @FXML
    private RadioButton Outsourced;

    /**
     * Create new part ID.
     *
     * @return part ID
    * */
    public static int getNewPartID() {
        int newID = 1;
        for (int i = 0; i < getAllParts().size(); i++) {
            newID++;
        }
        return newID;
    }

    /**
     * Set company name/machine ID for InhouseOrOutsoucred label.
     *
     * @param actionEvent outsourced/in-house radio button action
    * */
    public void radioadd(ActionEvent actionEvent) {
        if(Outsourced.isSelected()) {
            this.InHouseOrOutsourced.setText("Company Name");
        } else {
            this.InHouseOrOutsourced.setText("Machine ID");
        }
    }

    /**
     * Add part in inventory and loads MainWindowController.
     *
     * errorDialog if invalid values
     *
     * confirmDialog for confirm add part
     *
     * @param actionEvent save add part action
    * */
    @FXML
    public void onActionSave(ActionEvent actionEvent) {
        try {
            String partName = Name.getText();
            int partInventory = Integer.parseInt(Inventory.getText());
            double partPrice = Double.parseDouble(Price.getText());
            int partMax = Integer.parseInt(Maximum.getText());
            int partMin = Integer.parseInt(Minimum.getText());
            if (partMin >= partMax) {
                MainWindowController.errorDialog("Invalid Min Value", "Please enter valid Min value");
            }
            if (partInventory < partMin || partInventory > partMax) {
                MainWindowController.errorDialog("Invalid Inv Value", "Please enter valid Inv value");
            } else {
                int partId = getNewPartID();
                String name = partName.toLowerCase();
                int stock = partInventory;
                double price = partPrice;
                int min = partMin;
                int max = partMax;

                if (Outsourced.isSelected()) {
                    String companyName = CompanyOrMachineID.getText();
                    Outsourced temp = new Outsourced(partId, name, price, stock, min, max, companyName);
                    addPart(temp);
                }else {
                    int machineId = Integer.parseInt(CompanyOrMachineID.getText());
                    InHouse temp = new InHouse(partId, name, price, stock, min, max, machineId);
                    addPart(temp);
                }
                if (MainWindowController.confirmDialog("Adding Part","Are you sure?")) {
                    Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                    Parent scene = FXMLLoader.load(Main.class.getClassLoader().getResource("MainWindow.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.show();
                }
            }
        }
        catch(Exception e) {
            MainWindowController.errorDialog("Invalid Values", "Please enter valid values to add part");
        }
    }

    /**
     * Cancel add part.
     *
     * confirmDialog if confirm cancel
     *
     * @param actionEvent cancel add part action
     *
     * @throws IOException for FXMLLoader
    * */
    @FXML
    public void onActionCancel(ActionEvent actionEvent) throws IOException {
        if (MainWindowController.confirmDialog("Cancel Add Part","Are you sure?")) {
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Parent scene = FXMLLoader.load(Objects.requireNonNull(Main.class.getClassLoader().getResource("MainWindow.fxml")));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}
