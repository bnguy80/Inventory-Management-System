package Controller;

import Model.InHouse;
import Model.Inventory;
import Model.Parts;
import Model.Products;
import com.example.c482.Main;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 *Controller class that provides control for MainWindow screen of application.
 *
 * FUTURE ENHANCEMENT have the ability to cancel modify product when associated table is empty without errorDialog("Associated part table empty") as currently must add part to associated part table
 *
 * RUNTIME ERROR addAssociatedPart not able to be defined with Parts part variable, must use ObservableList(Parts) part to be able to save associated parts of product
 *
 *@author Brandon Nguyen
* */
public class MainWindowController implements Initializable {

    /**
     *Parts tableview.
    * */
    @FXML
    private TableView<Parts> partsTableView;

    /**
     * Part inventory level of parts table.
    * */
    @FXML
    private TableColumn<Parts, Integer> partInventoryColumn;

    /**
     * Part ID of parts table.
    * */
    @FXML
    private TableColumn<Parts, Integer> partIDColumn;

    /**
     * Part name of parts table.
    * */
    @FXML
    private TableColumn<Parts, String> partNameColumn;

    /**
     * Part price of parts table.
    * */
    @FXML
    private TableColumn<Parts, Integer> partPriceColumn;

    /**
     * Part search textfield.
    * */
    @FXML
    private TextField partSearchArea;

    /**
     * Product tableview.
    * */
    @FXML
    private TableView<Products> productsTableView;

    /**
     * Product inventory level of product table.
    * */
    @FXML
    private TableColumn<Products, Integer> productInventoryColumn;

    /**
     * Product price of product table.
     * */
    @FXML
    private TableColumn<Products, Integer> productPriceColumn;

    /**
     * Product ID of product table.
    * */
    @FXML
    private TableColumn<Products, Integer> productIDColumn;

    /**
     *Product name of product table.
    * */
    @FXML
    private TableColumn<Products, String> productNameColumn;

    /**
     * Product search textfield.
    * */
    @FXML
    private TextField productSearchArea;

    //Buttons and Fields

    /**
     *Load AddPartController.
     *
     * @param event add part action
     *
     * @throws IOException for FXMLLoader
    * */
    @FXML
    public void addPartButton(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(Objects.requireNonNull(Main.class.getClassLoader().getResource("AddPart.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Delete part from part table.
     *
     * errorDialog if no part selected
     *
     * confirmDialog if delete part
     *
     * @param actionEvent delete part action
    * */
    public void deletePartButton(ActionEvent actionEvent) {
        if(partsTableView.getSelectionModel().isEmpty()) {
            errorDialog("No Part Selected", "Please select part to delete");
            return;
        } if (confirmDialog("Are You Sure?", "This cannot be undone")){
            Parts selectedPart = partsTableView.getSelectionModel().getSelectedItem();
            Inventory.deletePart(selectedPart);
        }
    }

    /**
     * Load ModifyPartController.
     *
     * errorDialog if no part selected
     *
     * @param event modify part action
    * */
    public void modifyPartButton(ActionEvent event) {
        try {
            Parts partSelected = partsTableView.getSelectionModel().getSelectedItem();
            if (partSelected.getName() != null) {
                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(Main.class.getClassLoader().getResource("ModifyPart.fxml")));
                Parent scene = loader.load();
                ModifyPartController controller = loader.getController();
                controller.setParts(partSelected);
                stage.setScene(new Scene(scene));
                stage.show();
            }
        }
        catch(Exception e) {
            errorDialog("Error!", "No part to modify selected");
        }
    }

    /**
     * Search part.
     *
     * errorDialog if invalid part ID or name
     *
     * @param event search part action
    * */
    @FXML
    public void searchPartButton(ActionEvent event) {
        try {
            ObservableList<Parts> foundPart;
            ObservableList<Parts> allParts = Inventory.getAllParts();
            Parts temp;
            String searchPartString = partSearchArea.getText();
            foundPart = Inventory.lookupPart(searchPartString);

            if(foundPart.isEmpty()) {
                int searchPartInteger = Integer.parseInt(partSearchArea.getText());
                for (Parts part : allParts) {
                    if (String.valueOf(part.getId()).contains(Integer.toString(searchPartInteger))) {
                        temp = Inventory.lookUpPart(searchPartInteger);
                        foundPart.add(temp);
                    }
                }
                if (foundPart.isEmpty()) {
                    errorDialog("Invalid Part ID", "Could not find part");
                }
            }
            partsTableView.setItems(foundPart);
        } catch (NumberFormatException e) {
            errorDialog("Invalid Part Name", "Could not find part");
        }
    }

    /**
     * Display parts if search empty.
     *
     * @param event search part action
    * */
    @FXML
    public void partSearchAreaKeyPressed(KeyEvent event) {
        if(partSearchArea.getText().isEmpty()) {
            partsTableView.setItems(Inventory.getAllParts());
        }
    }

    /**
     * Display products if search empty.
     *
     * @param event search product action
    * */
    @FXML
    public void productSearchKeyPressed(KeyEvent event) {
        if(productSearchArea.getText().isEmpty()) {
            productsTableView.setItems(Inventory.getAllProducts());
        }
    }

    /**
     * Load AddProductController.
     *
     * @param actionEvent add product action
     *
     * @throws IOException for FXMLLoader
    * */
    public void addProductButton(ActionEvent actionEvent) throws IOException{
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(Objects.requireNonNull(Main.class.getClassLoader().getResource("AddProduct.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Load ModifyProductController.
     *
     * errorDialog if no product selected
     *
     * @param actionEvent modify product action
    * */
    public void modifyProductButton(ActionEvent actionEvent) {
        try {
            Products productSelected = productsTableView.getSelectionModel().getSelectedItem();
            if (productSelected.getName() != null) {
                Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(Main.class.getClassLoader()).getResource("ModifyProduct.fxml"));
                Parent scene = loader.load();
                ModifyProductController controller = loader.getController();
                controller.setProducts(productSelected);
                stage.setScene(new Scene(scene));
                stage.show();
            }
        }
        catch (Exception e) {
            errorDialog("Error!", "No product to modify selected");
        }
    }

    /**
     * Delete product from product table.
     *
     * errorDialog if no product selected or product contains associated parts
     *
     * confirmDialog if delete product
     *
     * @param actionEvent delete product action
    * */
    public void deleteProductButton(ActionEvent actionEvent) {
        if(productsTableView.getSelectionModel().isEmpty()) {
            errorDialog("No Product Selected", "Please select product to delete");
            return;
        }
        Products selectedProduct = productsTableView.getSelectionModel().getSelectedItem();
        ObservableList<Parts> associatedParts = selectedProduct.getAllAssociatedParts();
        if(associatedParts.size() >= 1) {
            MainWindowController.errorDialog("Product contains associated parts","Cannot delete product until associated parts deleted");
            return;

        } else {
            if (confirmDialog("Are You Sure?", "This cannot be undone")) {
                Inventory.deleteProduct(selectedProduct);
            }
        }
    }

    /**
     * Search part.
     *
     * errorDialog if invalid product ID or name
     *
     * @param event search product action
     * */
    @FXML
    void searchProductButton(ActionEvent event) {
        try {
            ObservableList<Products> foundProduct;
            ObservableList<Products> allProducts = Inventory.getAllProducts();
            Products temp;
            String searchPartString = productSearchArea.getText();
            foundProduct = Inventory.lookUpProduct(searchPartString);

            if(foundProduct.isEmpty()) {
                int searchProductInteger = Integer.parseInt(productSearchArea.getText());
                for (Products product : allProducts) {
                    if (String.valueOf(product.getId()).contains(Integer.toString(searchProductInteger))) {
                        temp = Inventory.lookUpProduct(searchProductInteger);
                        foundProduct.add(temp);
                    }
                }
                if (foundProduct.isEmpty()) {
                    errorDialog("Invalid Product ID", "Could not find product");
                }
            }
            productsTableView.setItems(foundProduct);
        } catch (NumberFormatException e) {
            errorDialog("Invalid Product Name", "Could not find product");
        }
    }

    /**
    * Exit application.
     *
     * confirmDialog to exit application
     *
     * @param actionEvent exit application
    * */
    public void exitButton(ActionEvent actionEvent) {
        if (confirmDialog("Are You Sure?", "")) {
            System.exit(0);
        }
    }

    /**
     *Display confirm dialog.
     *
     * @param header confirmDialog header
     *
     * @param content confirmDialog content
    * */
    public static boolean confirmDialog(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setTitle("Confirmation");
        alert.setContentText(content);
        alert.setHeaderText(header);

        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Display error dialog.
     *
     * @param header errorDialog header
     *
     * @param content errorDialog content
    * */
    public static boolean errorDialog( String header,String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);

        alert.setTitle("Error!");
        alert.setContentText(content);
        alert.setHeaderText(header);

        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     *Initializes MainWindowController and populates part/product table.
     *
     * @param url path for root objects
     *
     * @param resourceBundle localize root objects
    * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        partsTableView.setItems(Inventory.getAllParts());

        partIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        partInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));

        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));


        productsTableView.setItems(Inventory.getAllProducts());

        productIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        productInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));

        productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}