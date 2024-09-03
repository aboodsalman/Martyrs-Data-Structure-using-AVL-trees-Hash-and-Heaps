package com.phase3.phase3;

import java.io.*;
import java.util.Scanner;

import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.*;

public class MenuFX {
    private Menu file, dis;
    private static Menu mar;
    private MenuItem newFile, save, open, saveas;
    private MenuBar menu;
    private FileChooser fc;
    private VBox vb;
    private Alert alert;
    private static boolean saved;
    private static DistrictsHash dhash;

    public MenuFX() throws IOException{
        dhash = new DistrictsHash();
        file = new Menu("File");
        dis = new Menu();
        mar = new Menu();
        mar.setDisable(true);
        newFile = new MenuItem("New");
        save = new MenuItem("Save");
        save.setDisable(true);
        saveas = new MenuItem("Save As");
        open = new MenuItem("Open");
        menu = new MenuBar();
        menu.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");
        fc = new FileChooser();
        saved=true;

        file.getItems().addAll(newFile, saveas, save, open);
        menu.getMenus().addAll(file,dis,mar);

        alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Success Dialog");
        alert.setHeaderText("This is a congratulation!");

        vb = new VBox();
//        DistrictScreen ds = new DistrictScreen();
        DateScreen ds = new DateScreen();
        vb.getChildren().addAll(menu, DateScreen.getVb());
//        dis.setDisable(false);
//        DistrictScreen.setDistrict();
//        newFile.setOnAction(e->{
//            fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV File", "*.csv"));
//            Stage stg = new Stage();
//            File file = fc.showSaveDialog(stg);
//            if(file!=null) {
//                try {
//                    boolean created = file.createNewFile();
//                    if (created) {
//                        Driver.setFile(file);
//                    }
//                }catch(IOException ex) {}
//            }
//
//            try {
//                readFromFile();
//                dis.setDisable(false);
//                save.setDisable(false);
//                DistrictScreen.setDistrict();
//                LocationScreen.setDistrict(DistrictScreen.getDistrict());
//                mar.setDisable(true);
//                vb.getChildren().removeAll(DistrictScreen.getVb());
//                vb.getChildren().add(DistrictScreen.getVb());
//            } catch(NullPointerException ex) {}
//        });
        save.setOnAction(e->{
            writeToFile();
            saved=true;
            alert.setContentText("Saved successfully");
            alert.showAndWait();
        });
        saveas.setOnAction(e->{
            fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV File", "*.csv"));
            Stage stg = new Stage();
            File file = fc.showSaveDialog(stg);
            if(file!=null) {
                try {
                    boolean created = file.createNewFile();
                    if (created) {
                        Driver.setFile(file);
                        writeToFile();
                        saved=true;
                        alert.setContentText("Saved successfully");
                        alert.showAndWait();
                    }
                }catch(IOException ex) {}
            }
        });
        MartyrScreen mr = new MartyrScreen();
        open.setOnAction(e->{
            fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV File", "*.csv"));
            Stage stg = new Stage();

            Driver.setFile(fc.showOpenDialog(stg));
            try {
                readFromFile();
                save.setDisable(false);
                DateScreen.refresh();
                mar.setDisable(false);
            } catch(NullPointerException ex) {}
        });
        dis.setGraphic(new Text("Dates"));
        dis.getGraphic().setOnMouseClicked(e->{
            vb.getChildren().clear();
            vb.getChildren().addAll(menu, DateScreen.getVb());
        });

        mar.setGraphic(new Text("Martyrs"));
        mar.getGraphic().setOnMouseClicked(e->{
            vb.getChildren().clear();
            vb.getChildren().addAll(menu, MartyrScreen.getVb());
        });

    }
    public void readFromFile() {
        try {
            Scanner scan = new Scanner(Driver.getFile());
            Driver.setHash(new HashTable());
            if (scan.hasNext()) scan.nextLine();
            int count=0;
            while (scan.hasNext()) {
                String[] data = scan.nextLine().split(",");
                if(data.length==6 && !data[2].isBlank()) {
                    Martyr m = new Martyr(data[0], data[5], data[4], data[3], Integer.parseInt(data[2]));
                    Driver.getHash().add(m, data[1]);
                    dhash.add(data[4], data[3]);
                    if(!MartyrScreen.getDistrictstg().getItems().contains(data[4]))
                        MartyrScreen.getDistrictstg().getItems().add(data[4]);
                }
            }
            System.out.println(count);
            scan.close();
        } catch (IOException e) {
            System.out.println("Something wrong occured");
        }
    }

    public VBox getVb() {
        return vb;
    }

    public static Menu getMar() {
        return mar;
    }
    public static boolean isSaved() {
        return saved;
    }
    public static void setSaved(boolean saved) {
        MenuFX.saved = saved;
    }
    public static DistrictsHash getDhash() {
        return dhash;
    }
    public static void writeToFile(){
        try {
            PrintWriter pw = new PrintWriter(Driver.getFile());
            pw.println("Name,event,Age,location,District,Gender");
            for(int i=0; i<Driver.getHash().size(); i++){
                Node node = Driver.getHash().getNode(i);
                if(node.getFlag()=='F'){
                    if(node.getTree().getRoot()==null) continue;
                    Queue queue = new Queue();
                    queue.enQueue(node.getTree().getRoot());
                    while(!queue.isEmpty()) {
                        TNode temp = (TNode)queue.deQueue();
                        if(temp.getLeft()!=null) queue.enQueue(temp.getLeft());
                        if(temp.getRight()!=null)queue.enQueue(temp.getRight());
                        Martyr m = (Martyr)temp.getData();
                        pw.println(m.getName()+","+node.getDate()+","+m.getAge()+","+m.getLocation()+
                                ","+m.getDistrict()+","+m.getGender());
                    }
                }
            }
            pw.close();
        } catch(IOException ex) {
            System.out.println("Something wrong occured");
        }
    }

}

