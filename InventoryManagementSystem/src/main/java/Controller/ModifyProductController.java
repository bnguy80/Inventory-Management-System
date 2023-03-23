package Controller;

import Model.Inventory;
import Model.Parts;
import Model.Products;
import com.example.c482.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
*Controller class that provides control for modify product screen of application.
*
* @author Brandon Nguyen
* */
public class ModifyProductController implements Initializable {

    //Add TextFields

    /**
     *Product ID textfield.
     * */
    @FXML
    private TextField ModProductID;

    /**
     * Product inventory level textfield.
    * */
    @FXML
    private TextField ModProductInventory;

    /**
     * Product maximum textfield.
    * */
    @FXML
    private TextField ModProductMaximum;

    /**
     * Product minimum textfield.
    * */
    @FXML
    private TextField ModProductMinimum;

    /**
     * Product name textfield.
    * */
    @FXML
    private TextField ModProductName;

    /**
     * Product price textfield.
    * */
    @FXML
    private TextField ModProductPrice;

    /**
     * Part search textfield.
    * */
    @FXML
    private TextField PartProductSearchArea;

    /**
     *Part ID of un-associated parts table.
    * */
    @FXML
    private TableColumn<Parts, Integer> PartPIDColumn;

    /**
    *Part inventory level of un-associated parts table.
    * */
    @FXML
    private TableColumn<Parts, Integer> PartPInventoryColumn;

    /**
     * Part name of un-associated parts table.
    * */
    @FXML
    private TableColumn<Parts, String> PartPNameColumn;

    /**
     * Part price of un-associated parts table.
    * */
    @FXML
    private TableColumn<Parts, Double> PartPPriceColumn;

    /**
     * Un-associated parts tableview.
    * */
    @FXML
    private TableView<Parts> PartsTableView;

    /**
     * Part ID of associated parts table.
    * */
    @FXML
    private TableColumn<Parts, Integer> AssociatedPartIDColumn;

    /**
     * Part inventory level of associated parts table.
    * */
    @FXML
    private TableColumn<Products, Integer> AssociatedPartInventoryColumn;

    /**
     * Part name of associated parts table.
    * */
    @FXML
    private TableColumn<Products, String> AssociatedPartNameColumn;

    /**
     * Part price of associated parts table.
    * */
    @FXML
    private TableColumn<Products, Double> AssociatedPartPriceColumn;

    /**
     *List of parts associated with product.
    * */
    private ObservableList<Parts> associatedParts = FXCollections.observableArrayList();

    /**
     * Associated parts tableview.
    * */
    @FXML
    private TableView<Parts> AssociatedPartsTable;

    /**
     * Products selected in MainWindowController.
    * */
    public Products productSelected;

    /**
     * Product ID from MainWindowController.
    * */
    private int productID;

    /**
     *Update associated parts table.
     * This method updates the associated parts table with associated parts of the product.
    * */
    public void updateAssociatedPartsTable() {

        AssociatedPartsTable.setItems(associatedParts);
    }

    /**
    * Update parts table in add product window.
     * This method updates un-associated parts table when parts modified or added in MainWindowController.
    * */
    public void updatePartsTable() {

        PartsTableView.setItems(Inventory.getAllParts());
    }

    /**
    *Populate textfields from selected product in MainWindowController.
    *
    *@param productSelected set products textfield
    * */
    public void setProducts(Products productSelected) {
        this.productSelected = productSelected;
        productID = Inventory.getAllProducts().indexOf(productSelected);
        ModProductID.setText(Integer.toString(productSelected.getId()));
        ModProductName.setText(productSelected.getName());
        ModProductInventory.setText(Integer.toString(productSelected.getStock()));
        ModProductPrice.setText(Double.toString(productSelected.getPrice()));
        ModProductMaximum.setText(Integer.toString(productSelected.getMax()));
        ModProductMinimum.setText(Integer.toString(productSelected.getMin()));
        associatedParts.addAll(productSelected.getAllAssociatedParts());
    }

    //Buttons and Fields


    /**
    * Add parts to associated parts table.
     *
     * errorDialog if no part selected
    *
    * @param event add part to product action
    * */
    @FXML
    public void addPartToProduct(ActionEvent event) {
        try{
            Parts partSelected = PartsTableView.getSelectionModel().getSelectedItem();
            if(partSelected.getName() != null) {
                associatedParts.add(partSelected);
                updateAssociatedPartsTable();
                updatePartsTable();
            }
        }
        catch (Exception e) {
            MainWindowController.errorDialog("No Part Selected", "Please select a part to add");
        }
    }


    /**
    *Cancel add product.
    *
     * confirmDialog if cancel modify product
     *
    * @param event cancel modify product action
     *
    * @throws IOException for FXMLLoader
    * */
    @FXML
    public void cancelAddProduct(ActionEvent event) throws IOException {
        if(MainWindowController.confirmDialog("Cancel?", "Are you sure?")) {
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = FXMLLoader.load(Main.class.getClassLoader().getResource("MainWindow.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**
    *Delete part from associated table.
    *
     * confirmDialog to delete part
     *
     * errorDialog if no part selected
     *
    * @param event delete part action
    * */
    @FXML
    public void deleteProductButton(ActionEvent event) {
        try {
            Parts selectedPart = AssociatedPartsTable.getSelectionModel().getSelectedItem();
            if(selectedPart.getName() != null) {
                MainWindowController.confirmDialog("Deleting "+ selectedPart.getName() + " from Product",  "Are you sure?");
                associatedParts.remove(selectedPart);
                updateAssociatedPartsTable();
            }
        }
        catch (Exception e) {
            MainWindowController.errorDialog("No Part Selected", "Please select a part to delete");
        }
    }


    /**
    *Save product.
    *
     * errorDialog if associated parts table empty or invalid values
     *
     * confirmDialog to load MainWindow
     *
    *@param event save product action
     * */
    @FXML
    public void saveProductButton(ActionEvent event) {
        try {
            try {
             String name = ModProductName.getText();
             Integer.parseInt(name);
                MainWindowController.errorDialog("Name Invalid", "Please enter valid name");
                return;
            }
            catch(NumberFormatException e) {
                //Catching means it works and name is valid
            }
            String productName = ModProductName.getText();
            int productInventory = Integer.parseInt(ModProductInventory.getText());
            double productPrice = Double.parseDouble(ModProductPrice.getText());
            int productMax = Integer.parseInt(ModProductMaximum.getText());
            int productMin = Integer.parseInt(ModProductMinimum.getText());
            /*if (associatedParts.isEmpty()) {
                MainWindowController.errorDialog("Associated Parts Table Empty", "Please add part");
                return;
            }*/
            if (productMax <= productMin) {
                MainWindowController.errorDialog("Invalid Max Value", "Please enter valid Min value");
                return;
            }
            if (productInventory > productMax || productInventory < productMin) {
                MainWindowController.errorDialog("Invalid Inventory Value", "Please enter valid Inventory value");
                return;
            }
            if (MainWindowController.confirmDialog("Modifying Product", "Are you sure?")) {
                int id = Integer.parseInt(ModProductID.getText());
                Products modifyProduct = new Products(id, productName, productPrice, productInventory, productMin, productMax);

                for (Parts part : associatedParts) {
                    modifyProduct.addAssociatedPart(part);
                }
                Inventory.updateProduct(productID, modifyProduct);

                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                Parent scene = FXMLLoader.load(Main.class.getClassLoader().getResource("MainWindow.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }
        }
        catch (Exception e) {
            MainWindowController.errorDialog("Invalid Values", "Please enter valid values");
        }
    }

    /**
    *Search part.
     *
     * errorDialog if invalid part ID or part name
    *
    * @param event search part action
    * */
    @FXML
    public void searchPartButton(ActionEvent event) {
        try {
            ObservableList<Parts> foundPart;
            ObservableList<Parts> allParts = Inventory.getAllParts();
            Parts temp;
            String searchPartString = PartProductSearchArea.getText();
            foundPart = Inventory.lookupPart(searchPartString);

            if (foundPart.isEmpty()) {
                int searchPartInteger = Integer.parseInt(PartProductSearchArea.getText());
                for (Parts part : allParts) {
                    if (String.valueOf(part.getId()).contains(Integer.toString(searchPartInteger))) {
                        temp = Inventory.lookUpPart(searchPartInteger);
                        foundPart.add(temp);
                    }
                }
                if(foundPart.isEmpty()) {
                    MainWindowController.errorDialog("Invalid Part ID", "Could not find part");
                }
            }
            PartsTableView.setItems(foundPart);
        }
        catch (Exception e) {
            MainWindowController.errorDialog("Invalid Part Name", "Could not find part");
        }
    }

    /**
     * Populate associated and un-associated tables.
    * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        PartPIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        PartPNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        PartPInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        PartPPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        AssociatedPartIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        AssociatedPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        AssociatedPartInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        AssociatedPartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        AssociatedPartsTable.setItems(associatedParts);

        updatePartsTable();
        updateAssociatedPartsTable();
    }
}
