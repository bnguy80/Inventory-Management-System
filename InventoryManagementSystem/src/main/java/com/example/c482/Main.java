package com.example.c482;

import Model.*;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

/**
 *Inventory Management System implements application to manage parts and products consisting of parts.
 *
 * Javadocs located @javadocIMS
 *
 *@author Brandon Nguyen
* */
public class Main extends Application {

    /**
     *Starts method creates FXML stage and loads the scene.
     *
     * Loads sample data for application
     *
     * @param stage primary stage
     *
     * @throws Exception for FXMLLoader
    * */
    @Override
    public void start(Stage stage) throws Exception {

        //Add Parts in InHouse
        InHouse wrench = new InHouse(1, "wrench", 99.99, 1, 1, 5, 100);
        InHouse screwdriver = new InHouse(2, "screwdriver", 99.99, 1, 1, 5, 200);
        InHouse hammer = new InHouse(3, "hammer", 99.99, 1, 1, 5, 300);
        InHouse flashlight = new InHouse(4, "flashlight", 99.99, 1, 1, 5, 400);

        Inventory.addPart(wrench);
        Inventory.addPart(screwdriver);
        Inventory.addPart(hammer);
        Inventory.addPart(flashlight);

        //Add Parts in Outsourced
        Outsourced rope = new Outsourced(5, "rope", 99.99, 1, 1, 5, "Acme Co");
        Outsourced nails = new Outsourced(6, "nails", 99.99, 1, 1, 5, "Dunder Miflin");
        Outsourced screws = new Outsourced(7, "screws", 99.99, 1, 1, 5, "Big Stuff Co");
        Outsourced lightBulbs = new Outsourced(8, "light bulbs", 99.99, 1, 1, 5, "Cool Stuff Co");

        Inventory.addPart(rope);
        Inventory.addPart(nails);
        Inventory.addPart(screws);
        Inventory.addPart(lightBulbs);

        //Add Products
        Products jackhammer = new Products(1, "Jackhammer", 99.99, 1, 1, 5);
        Products snowblower = new Products(2, "Snowblower", 99.99, 1, 1, 5);
        Products drill = new Products(3, "Drill", 99.99, 1, 1, 5);
        Products powerSaw = new Products(4, "Power saw", 99.99, 1, 1, 5);

        Inventory.addProduct(jackhammer);
        Inventory.addProduct(snowblower);
        Inventory.addProduct(drill);
        Inventory.addProduct(powerSaw);

        /*jackhammer.addAssociatedPart((ObservableList<Parts>) wrench);
        snowblower.addAssociatedPart((ObservableList<Parts>) screws);
        drill.addAssociatedPart((ObservableList<Parts>) wrench);
        powerSaw.addAssociatedPart((ObservableList<Parts>) nails);*/


        Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getClassLoader().getResource("MainWindow.fxml")));
        Scene scene = new Scene(root, 939.0, 556.0);
        stage.setScene(scene);
        stage.show();
    }

    /**
     *Main method entry point of application and launches application.
    * */
    public static void main(String[] args) {
        launch(args);
    }
}