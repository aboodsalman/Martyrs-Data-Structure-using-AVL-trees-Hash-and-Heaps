package com.phase3.phase3;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Driver extends Application {
    private static File file;
    private static HashTable hash;
    public void start(Stage stg) throws Exception {
        stg.setScene(new Scene(new MenuFX().getVb()));
        stg.setMaximized(true);
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);
        alert.setTitle("Warning Dialog");
        alert.setHeaderText("This is a warning!");
        alert.setContentText("The last changes aren't saved, are you sure to close the program");
        ButtonType okButton = alert.getButtonTypes().get(0);
        stg.setTitle("FREE PALESTINE");
        stg.setOnCloseRequest(e->{
            if(!MenuFX.isSaved()) {
                alert.showAndWait();
                if (alert.getResult() == okButton) {
                    MenuFX.writeToFile();
                    stg.close();
                }
                else stg.close();
            }
        });
        stg.show();
    }
    public static void main(String[] args){
        launch(args);
    }

    public static HashTable getHash() {
        return hash;
    }

    public static void setHash(HashTable hash) {
        Driver.hash = hash;
    }

    public static File getFile() {
        return file;
    }

    public static void setFile(File file) {
        Driver.file = file;
    }
}
