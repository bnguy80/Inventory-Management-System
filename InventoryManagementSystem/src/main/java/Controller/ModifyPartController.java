package Controller;

import Model.InHouse;
import Model.Outsourced;
import Model.Parts;
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

/**
 * Controller class that provides control for modify part screen of application.
 *
 * @author Brandon Nguyen
* */
public class ModifyPartController implements Initializable {

    /**
     *Part company name or machine ID textfield.
    * */
    @FXML
    private TextField CompanyNameOrMachineID;

    /**
     *Part ID textfield.
    * */
    @FXML
    private TextField ID;

    /**
     * Part inventory level textfield.
    * */
    @FXML
    private TextField Inventory;

    /**
     * Part maximum textfield.
    * */
    @FXML
    private TextField Maximum;

    /**
     * Part minimum textfield.
    * */
    @FXML
    private TextField Minimum;

    /**
     * Part name textfield.
    * */
    @FXML
    private TextField Name;

    /**
     * Part price textfield.
    * */
    @FXML
    private TextField Price;

    /**
     *In-house radio button.
    * */
    @FXML
    private RadioButton inHouse;

    /**
     * Outsourced radio button.
    * */
    @FXML
    private RadioButton outsourced;

    /**
     *Part machine ID or company name label by radio button selection.
    * */
    @FXML
    private Label InhouseOrOutsourced;

    /**
     *Part selected from MainWindowController.
    * */
    public Parts partSelected;

    /**
     * Part ID from MainWindowController.
    * */
    private int partID;

    //private Stage stage;

    //private Object scene;


    /**
     * Set company name/machine ID for InhouseOrOutsourced label.
     *
     * @param actionEvent outsourced/in-house radio button action
    * */
    public void radioadd(ActionEvent actionEvent) {
        if (outsourced.isSelected()) {
            this.InhouseOrOutsourced.setText("Company Name");
        } else {
            this.InhouseOrOutsourced.setText("Machine ID");
        }
    }

    /**
     * Populate tables from selected part in MainWindowController.
     *
     * @param partSelected set product textfield
    * */
     public void setParts(Parts partSelected) {
        this.partSelected = partSelected;
        partID = Model.Inventory.getAllParts().indexOf(partSelected);
        ID.setText(Integer.toString(partSelected.getId()));
        Name.setText(partSelected.getName());
        Inventory.setText(Integer.toString(partSelected.getStock()));
        Price.setText(Double.toString(partSelected.getPrice()));
        Maximum.setText(Integer.toString(partSelected.getMax()));
        Minimum.setText(Integer.toString(partSelected.getMin()));

        if(partSelected instanceof InHouse) {
            InHouse ih = (InHouse) partSelected;
            inHouse.setSelected(true);
            this.InhouseOrOutsourced.setText("Machine ID");
            CompanyNameOrMachineID.setText(Integer.toString(ih.getMachineID()));
        } else {
            Outsourced os = (Outsourced) partSelected;
            outsourced.setSelected(true);
            this.InhouseOrOutsourced.setText("Company Name");
            CompanyNameOrMachineID.setText(os.getCompanyName());
        }
     }

     /**
      * Modify part in inventory and loads MainWindowController.
      *
      *errorDialog if invalid name or values
      *
      * @param actionEvent save modify part action
      *
      * @throws Exception for FXMLLoader
      * */
    public void onActionSave(ActionEvent actionEvent) throws Exception{
        try {
            String name = Name.getText();
            try{
                Integer.parseInt(name);
                MainWindowController.errorDialog("Name Invalid", "Please enter valid name");
                return;

            } catch (Exception e) {
                //Catching means it works and name is valid
            }

            int partInventory = Integer.parseInt(Inventory.getText());
            int partMax = Integer.parseInt(Maximum.getText());
            int partMin = Integer.parseInt(Minimum.getText());
            if ((partInventory > partMax || partInventory < partMin) || (partMin > partMax || partMax < partMin)) {
                MainWindowController.errorDialog("Invalid Values", "Please enter valid values");
            }
             else {
                int id = Integer.parseInt(ID.getText());
                double price = Double.parseDouble(Price.getText());
                int stock = Integer.parseInt(Inventory.getText());
                int min = Integer.parseInt(Minimum.getText());
                int max = Integer.parseInt(Maximum.getText());

                if (outsourced.isSelected()) {
                    String companyName = CompanyNameOrMachineID.getText();
                    Outsourced temp = new Outsourced(id, name, price, stock, min, max, companyName);
                    Model.Inventory.updatePart(partID, temp);

                    Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                    Parent scene = FXMLLoader.load(getClass().getClassLoader().getResource("MainWindow.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.show();
                } else {
                    int machineId = Integer.parseInt(CompanyNameOrMachineID.getText());
                    InHouse temp = new InHouse(id, name, price, stock, min, max, machineId);
                    Model.Inventory.updatePart(partID, temp);

                    Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                    Parent scene = FXMLLoader.load(getClass().getClassLoader().getResource("MainWindow.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.show();
                }
            }
        }
        catch (Exception e) {
            MainWindowController.errorDialog("Invalid Values", "Please enter valid values to modify part");
        }
    }

        /**
         *Cancel modify part.
         *
         * confirmDialog to load MainWindow
         *
         * @param actionEvent cancel modify part action
         *
         * @throws IOException for FXMLLoader
        * */
        public void onActionCancel (ActionEvent actionEvent) throws IOException {
            if(MainWindowController.confirmDialog("Cancel?", "Are you sure?")) {
                Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                Parent scene = FXMLLoader.load(Objects.requireNonNull(Main.class.getClassLoader().getResource("MainWindow.fxml")));
                stage.setScene(new Scene(scene));
                stage.show();
            }
        }

        @Override
        public void initialize (URL location, ResourceBundle resourceBundle){

        }
    }

