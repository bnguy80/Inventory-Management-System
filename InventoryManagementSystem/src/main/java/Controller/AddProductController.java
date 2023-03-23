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

import static Model.Inventory.getAllProducts;

/**
 * Controller class that provides control for add product screen of application.
 *
 * @author Brandon Nguyen
* */
public class AddProductController implements Initializable {

    //Add TextFields

    /**
     * Product inventory level textfield.
    * */
    @FXML
    private TextField AddProductInventory;

    /**
     * Product maximum textfield.
    * */
    @FXML
    private TextField AddProductMaximum;

    /**
     * Product minimum textfield.
    * */
    @FXML
    private TextField AddProductMinimum;

    /**
     * Product name textfield.
    * */
    @FXML
    private TextField AddProductName;

    /**
     * Product price textfield.
    * */
    @FXML
    private TextField AddProductPrice;


    /**
     * Un-associated parts tableview.
    * */
    @FXML
    private TableView<Parts> PartsTableView;

    /**
     * Part ID of un-associated parts table.
    * */
    @FXML
    private TableColumn<Parts, Integer> PartPIDColumn;

    /**
     * Part inventory level of un-associated parts table.
    * */
    @FXML
    private TableColumn<Parts, Integer> PartPInventoryColumn;

    /**
     *  Part name of un-associated parts table.
    * */
    @FXML
    private TableColumn<Parts, String> PartPNameColumn;

    /**
     * Part price of un-associated parts table.
    * */
    @FXML
    private TableColumn<Parts, Double> PartPPriceColumn;


    /**
     * Associated parts tableview
    * */
    @FXML
    private TableView<Parts> AssociatedPartsTable;

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
     * Part search textfield.
    * */
    @FXML
    private TextField ProductSearchArea;

    /**
     * List of original parts from MainWindowController.
    * */
    private ObservableList<Parts> originalParts = FXCollections.observableArrayList();

    /**
     * Lost of parts associated with product.
    * */
    private ObservableList<Parts> associatedParts = FXCollections.observableArrayList();

    /**
     * Generate product ID.
     *
     * @return return unique product ID
    * */
    private int getNewProductID() {
        int newID = 1;
        for (int i = 0; i < getAllProducts().size(); i++) {
            if (Inventory.getAllProducts().get(i).getId() == newID) {
                newID++;
            }
        }
        return newID;
    }

    /**
     * Update parts table in add product window.
     *This method updates un-associated parts table when parts modified or added in MainWindowController.
    * */
    public void updatePartsTable() {

        PartsTableView.setItems(Inventory.getAllParts());
    }

    /**
     * Update associated parts table.
     * This method updates the associated parts table with associated parts of the product.
    * */
    public void updateAssociatedPartsTable() {

        AssociatedPartsTable.setItems(associatedParts);
    }



    //Buttons and Fields

    /**
     * Add parts to associated parts table.
     *
     * @param event add part to product action
    * */
    @FXML
    public void addPartToProduct(ActionEvent event) {
            Parts partSelected = PartsTableView.getSelectionModel().getSelectedItem();
                try {
                    if (partSelected.getName() != null) {
                        associatedParts.add(partSelected);
                        updateAssociatedPartsTable();
                        updatePartsTable();
                    }
                }
            catch(Exception e) {
                MainWindowController.errorDialog("No Part Selected", "Please select part to add product");
            }
    }

    /**
     * Cancel add product.
     *
     * confirmDialog if confirm add product
     *
     * @param event cancel modify product action
     *
     * @throws IOException for FXMLLoader
    * */
    @FXML
    public void cancelAddProduct(ActionEvent event) throws IOException {
        if(MainWindowController.confirmDialog("Cancel Add Product","Are you sure?")) {
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = FXMLLoader.load(Main.class.getClassLoader().getResource("MainWindow.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**
     * Delete part from associated table.
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
            if (selectedPart != null) {
                MainWindowController.confirmDialog("Deleting " + selectedPart.getName() + " from Product", "Are you sure?");
                associatedParts.remove(selectedPart);
                updatePartsTable();
                updateAssociatedPartsTable();
            }
        }
        catch (Exception e) {
            MainWindowController.errorDialog("No Part Selected", "Please select part to delete");
        }
    }

    /**
     * Save product.
     *
     * errorDialog if associated parts table empty, or invalid values
     *
     * confirmDilog to load MainWindow
     *
     * @param event save product action
    * */
    @FXML
    public void saveProductButton(ActionEvent event) {
        try {
            String productName = AddProductName.getText();
            Integer productInventory = Integer.parseInt(AddProductInventory.getText());
            double productPrice = Double.parseDouble(AddProductPrice.getText());
            Integer productMax = Integer.parseInt(AddProductMaximum.getText());
            Integer productMin = Integer.parseInt(AddProductMinimum.getText());
            /*if (associatedParts.isEmpty()) {
                MainWindowController.errorDialog("Associated Parts Table Empty", "Please add parts");
                return;
            }*/
            if (productMax < productMin) {
                MainWindowController.errorDialog("Invalid Max Value", "Please enter valid Min value");
                return;
            }
            if (productInventory > productMax || productInventory < productMin) {
                MainWindowController.errorDialog("Invalid Inventory Value", "Please enter valid Inventory value");
                return;
            }
            int productID = getNewProductID();
            String name = productName.toLowerCase();
            int stock = productInventory;
            int min = productMin;
            int max = productMax;

            if (MainWindowController.confirmDialog("Adding Product", "Are you sure?")) {
                Products products = new Products(productID, name, productPrice, stock, min, max);

                //Not sure why typecasted...
                for (Parts part : associatedParts) {
                    products.addAssociatedPart(part);
                }
                Inventory.addProduct(products);

                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                Parent scene = FXMLLoader.load(Main.class.getClassLoader().getResource("MainWindow.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }
        }
        catch (Exception e) {
            MainWindowController.errorDialog("Invalid Values1", "Please enter valid values to add");
        }
    }

    /**
     * Search part.
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
            String searchPartString = ProductSearchArea.getText();
            foundPart = Inventory.lookupPart(searchPartString);

            if(foundPart.isEmpty()) {
                int searchPartInteger = Integer.parseInt(ProductSearchArea.getText());
                for (Parts part : allParts) {
                    if (String.valueOf(part.getId()).contains(Integer.toString(searchPartInteger))) {
                        temp = Inventory.lookUpPart(searchPartInteger);
                        foundPart.add(temp);
                    }
                }

                if (foundPart.isEmpty()) {
                    MainWindowController.errorDialog("Invalid Part ID", "Could not find part");
                }
            }
            PartsTableView.setItems(foundPart);
        }
        catch(RuntimeException e) {
            MainWindowController.errorDialog("Invalid Part Name", "Could not find part");
        }
    }

    /**
     * Populate associated and un-associated tables.
    * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        originalParts = Inventory.getAllParts();

        PartPIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        PartPNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        PartPInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        PartPPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        PartsTableView.setItems(originalParts);

        AssociatedPartIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        AssociatedPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        AssociatedPartInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        AssociatedPartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        AssociatedPartsTable.setItems(associatedParts);

        updatePartsTable();
        updateAssociatedPartsTable();
    }
}
