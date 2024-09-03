package com.phase3.phase3;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Callback;

public class DateScreen {
    private static VBox vb, vb2, vb3, vb4, vblist, vb5, vbstg;
    private static Button update, delete, insert, next, prev, showList, updatestg, print;
    private static HBox hb, hb2, hb3, datesHb, hbstg;
    private static Label mainlb, up_de, inslb, datelb, avglb, totalb, dislb, loclb, lblist, enterlb, uplbstg, namestg,
            agestg,genderstg, districtstg, locationstg;
    private static TextField uptxt;
    private static DatePicker insdp, deldp, dpstg;
    private Alert alert, alert2;
    private Stage stg1, stg2, upstg;
    private static ListView<String> list1;
    private static Stack stack1, stack2;
    private static Node node, nodestg;
    private static ListView<Node> list, liststg;
    private static ObservableList<Node> dates;
    private static DateTimeFormatter formatter;
    private static BorderPane pane;
    private static Pane pane2;
    private static final int VERTICAL_GAP = 50;
    private static int HORIZONTAL_GAP = 50;
    private static VBox vstg;


    public DateScreen() throws IOException {
        stack1 = new Stack();
        stack2 = new Stack();

        pane = new BorderPane();
        dates = FXCollections.observableArrayList();
        liststg = new ListView<>(dates);
        liststg.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");
        pane.setLeft(liststg);
        pane2 = new Pane();
        pane.setCenter(pane2);
        stg1 = new Stage();
        stg1.setScene(new Scene(pane));
        stg1.setMaximized(true);
        namestg = new Label();
        namestg.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");
        agestg = new Label();
        agestg.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");
        genderstg = new Label();
        genderstg.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");
        districtstg = new Label();
        districtstg.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");
        locationstg = new Label();
        locationstg.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");
        vstg = new VBox(10);
        vstg.getChildren().addAll(namestg, agestg, genderstg, districtstg, locationstg);
        vstg.setPadding(new Insets(10, 10, 40, 10));
        pane.setBottom(vstg);
        vstg.setAlignment(Pos.CENTER);
        Locale.setDefault(Locale.ENGLISH);

        vb = new VBox(20);
        vb.setPadding(new Insets(10,10,10,10));
        vb.setAlignment(Pos.CENTER);
        vb.setStyle("-fx-background-radius: 10px; -fx-border-color: grey; -fx-border-radius: 10px;");
        mainlb = new Label();
        mainlb.setStyle("-fx-font-size: 30; -fx-font-weight: bold;");
        alert = new Alert(AlertType.WARNING);
        alert.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);
        alert.setTitle("Warning Dialog");
        alert.setHeaderText("This is a warning!");
        alert2 = new Alert(AlertType.ERROR);
        alert2.setTitle("Error Dialog");
        alert2.setHeaderText("An error has occurred!");
        enterlb = new Label("Enter the new name");
        enterlb.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");
        uptxt = new TextField();
        uptxt.setStyle("-fx-color: #7C15D8; -fx-font-size: 16; -fx-font-weight: bold;");
        updatestg = new Button("Update");
        updatestg.setStyle("-fx-color: #7C15D8; -fx-font-size: 16; -fx-font-weight: bold; -fx-background-radius: 10px;");
        upstg = new Stage();
        upstg.setTitle("Update District");
        vb5 = new VBox(15);
        vb5.setPadding(new Insets(25,25,25,25));
        vb5.getChildren().addAll(enterlb, uptxt, updatestg);
        upstg.setScene(new Scene(vb5));
        update = new Button("Update");
        update.setStyle("-fx-color: #7C15D8; -fx-font-size: 16; -fx-font-weight: bold; -fx-background-radius: 10px;");
        Alert alert3 = new Alert(AlertType.INFORMATION);
        alert3.setTitle("Success Dialog");
        alert3.setHeaderText("This is a congratulation!");
        ButtonType okButton = alert.getButtonTypes().get(0);
        stg2 = new Stage();
        stg2.setTitle("Update Date");
        uplbstg = new Label("Enter the new date");
        dpstg = new DatePicker();
        dpstg.getEditor().setDisable(true);
        dpstg.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if (date.isAfter(LocalDate.now())) {
                    setDisable(true); // Disable future dates
                    setStyle("-fx-background-color: #c3c2c1;"); // Change color for disabled dates (optional)
                }
            }
        });
        updatestg = new Button("Update");
        vbstg = new VBox(10);
        hbstg = new HBox(10);
        hbstg.setPadding(new Insets(25,25,25,25));
        hbstg.getChildren().addAll(uplbstg, dpstg);
        vbstg.getChildren().addAll(hbstg, updatestg);
        stg2.setScene(new Scene(vbstg));
        updatestg.setOnAction(e->{
            if(dpstg.getValue()!=null && !dpstg.getValue().isAfter(LocalDate.now())){
                String d = formatDate(deldp.getValue().toString());
                alert.setContentText("Are you sure to update "+d+"?!");

                alert.setOnCloseRequest(event -> {
                    if (alert.getResult() == okButton) {
                        Driver.getHash().update(d, formatDate(dpstg.getValue().toString()));
                        int index = list.getSelectionModel().getSelectedIndex()+1;
                        list.scrollTo(index);
                        list.getSelectionModel().select(index);
                        node=dates.get(index);
                        deldp.setValue(LocalDate.parse(node.getDate(), formatter));
                        refresh();
                    }
                });
                alert.showAndWait();
            }
            else{
                alert2.setContentText("Please select an exist date");
                alert2.show();
            }
        });
        update.setOnAction(e->{
            if(deldp.getValue()!=null && Driver.getHash().search(formatDate(deldp.getValue().toString()))!=-1){
                dpstg.setValue(null);
                stg2.show();
            }
            else{
                alert2.setContentText("Please select an exist date");
                alert2.show();
            }
        });
        delete = new Button("Delete");

        alert = new Alert(AlertType.WARNING);
        alert.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);
        alert.setTitle("Warning Dialog");
        alert.setHeaderText("This is a warning!");
        alert2 = new Alert(AlertType.ERROR);
        alert2.setTitle("Error Dialog");
        alert2.setHeaderText("An error has occurred!");
        deldp = new DatePicker();
        deldp.getEditor().setDisable(true);
        deldp.setStyle("-fx-color: #7C15D8; -fx-font-size: 15; -fx-font-weight: bold;");
        deldp.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if (date.isAfter(LocalDate.now()) || Driver.getHash().search(formatDate(date.toString()))==-1) {
                    setDisable(true);
                    setStyle("-fx-background-color: #c3c2c1;");
                }
            }
        });
        deldp.setOnAction(e->{
            refreshDate(formatDate(deldp.getValue().toString()));
        });
        delete.setOnAction(e->{
            if(deldp.getValue()!=null && Driver.getHash().search(formatDate(deldp.getValue().toString()))!=-1){
                String d = formatDate(deldp.getValue().toString());
                alert.setContentText("Are you sure to delete "+d+"?!");

                alert.setOnCloseRequest(event -> {
                    if (alert.getResult() == okButton) {
                        Driver.getHash().delete(d);
                        int index = list.getSelectionModel().getSelectedIndex()+1;
                        list.scrollTo(index);
                        list.getSelectionModel().select(index);
                        node=dates.get(index);
                        deldp.setValue(LocalDate.parse(node.getDate(), formatter));
                        refresh();
                    }
                });
                alert.showAndWait();
            }
            else{
                alert2.setContentText("Please select an exist date");
                alert2.show();
            }
        });
        up_de = new Label("Choose a Date to update or delete");
        up_de.setStyle("-fx-color: #7C15D8; -fx-font-size: 18; -fx-font-weight: bold;");
        delete.setStyle("-fx-font-color: #7C15D8; -fx-font-size: 16; -fx-font-weight: bold; -fx-background-radius: 10px; -fx-border-radius: 10px; -fx-border-color: #7C15D8;");
        insert = new Button("Insert");
        insert.setStyle("-fx-color: #7C15D8; -fx-font-size: 16; -fx-font-weight: bold; -fx-background-radius: 10px;");
        inslb = new Label("Insert new Date");
        inslb.setStyle("-fx-color: #7C15D8; -fx-font-size: 18; -fx-font-weight: bold;");
        insdp = new DatePicker();
        insdp.getEditor().setDisable(true);
        insdp.setStyle("-fx-color: #7C15D8; -fx-font-size: 15; -fx-font-weight: bold;");
        insdp.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if (date.isAfter(LocalDate.now())) {
                    setDisable(true); // Disable future dates
                    setStyle("-fx-background-color: #c3c2c1;"); // Change color for disabled dates (optional)
                }
            }
        });
        insert.setOnAction(e->{
            if(insdp.getValue()==null || insdp.getValue().isAfter(LocalDate.now())){
                alert2.setContentText("Please Choose a Valid Date");
                alert2.show();
            }
            else{
                if(Driver.getHash().search(formatDate(insdp.getValue().toString()))!=-1){
                    alert2.setContentText("The Date is already exist");
                    alert2.show();
                }
                else{
                    Driver.getHash().add(null, formatDate(insdp.getValue().toString()));
                    System.out.println(Driver.getHash().search("2/8/2006")+" "+formatDate(insdp.getValue().toString()));
                    alert3.setContentText("Added Successfully");
                    alert3.show();
                    refresh();
                }
            }
        });
        datelb = new Label("District name");
        datelb.setStyle("-fx-font-size: 24; -fx-font-weight: bold;");
        totalb = new Label("Total Number Of Martyrs");
        totalb.setStyle("-fx-font-size: 17; -fx-font-weight: bold;");
        avglb = new Label("Average Ages");
        avglb.setStyle("-fx-font-size: 17; -fx-font-weight: bold;");
        dislb = new Label("District With Maximum Martyrs ");
        dislb.setStyle("-fx-font-size: 17; -fx-font-weight: bold;");
        loclb = new Label("Location With Maximum Martyrs ");
        loclb.setStyle("-fx-font-size: 17; -fx-font-weight: bold;");
        next = new Button("Next");
        next.setStyle("-fx-font-color: #7C15D8; -fx-font-size: 16; -fx-font-weight: bold; -fx-border-width: 2.6px; -fx-border-color: #7C15D8; -fx-border-style: hidden hidden solid hidden;");
        print = new Button("Print Table");
        print.setStyle("-fx-font-color: #7C15D8; -fx-font-size: 16; -fx-font-weight: bold; -fx-border-width: 2.6px; -fx-border-color: #7C15D8; -fx-border-style: hidden hidden solid hidden;");
        liststg.setPadding(new Insets(10, 10, 10, 10));
        print.setOnAction(e->{
            int index = 0;
            liststg.scrollTo(index);
            liststg.getSelectionModel().select(index);
            nodestg=dates.get(index);
            pane2.getChildren().clear();
            Martyr m = (Martyr)nodestg.getTree().getRoot().getData();
            namestg.setText("The name is: "+m.getName());
            agestg.setText("The age is: "+m.getAge());
            genderstg.setText("The gender is: "+m.getGender());
            districtstg.setText("The district is: "+m.getDistrict());
            locationstg.setText("The location is: "+m.getLocation());
            HORIZONTAL_GAP = 50*(nodestg.getTree().getDepth(nodestg.getTree().getRoot())-1);
            drawAVLTree(pane2, nodestg.getTree().getRoot(), 700, 50, HORIZONTAL_GAP, VERTICAL_GAP);
            stg1.show();
        });
        prev = new Button("Previous");
        list = new ListView<>(dates);
        list.setOrientation(Orientation.HORIZONTAL);
        list.setStyle("-fx-color: #7c15D8; -fx-font-size: 16; -fx-font-weight: bold;");
        list.setMaxWidth(700);
        list.setMaxHeight(180);
        list.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.RIGHT ||
                    event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN) {
                event.consume();
            }
        });
        liststg.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.RIGHT ||
                    event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN) {
                event.consume();
            }
        });

        list.setCellFactory(new Callback<ListView<Node>, ListCell<Node>>() {
            public ListCell<Node> call(ListView<Node> listView) {
                return new ListCell<Node>() {
                    @Override
                    protected void updateItem(Node item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item.getDate());
                        } else {
                            setText(null);
                        }
                    }
                };
            }
        });
        hb3 = new HBox(15);
        hb3.getChildren().addAll(prev, print, next);
        hb3.setAlignment(Pos.CENTER);
        vb3 = new VBox(15);
        vb3.getChildren().addAll(list, totalb, avglb, dislb, loclb, hb3);
        vb3.setAlignment(Pos.CENTER);
        formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
        next.setOnAction(e->{
            int index = list.getSelectionModel().getSelectedIndex()+1;
            if (index<dates.size()) {
                list.scrollTo(index);
                list.getSelectionModel().select(index);
                node=dates.get(index);
                MartyrScreen.setTree(node.getTree());
                totalb.setText("The total martyrs is: "+node.getTree().size());
                avglb.setText("The average ages is: "+node.getTree().avgAges());
                String max[] = node.getTree().maxDistrictLocation().split(",");
                dislb.setText("The District of Max Martyrs is: "+max[0]);
                loclb.setText("The Location of Max Martyrs is: "+max[1]);
                deldp.setValue(LocalDate.parse(node.getDate(), formatter));
            }

        });

        prev.setStyle("-fx-font-color: #7C15D8; -fx-font-size: 16; -fx-font-weight: bold; -fx-border-width: 2.6px; -fx-border-color: #7C15D8; -fx-border-style: hidden hidden solid hidden;");
        prev.setOnAction(e->{
            int index = list.getSelectionModel().getSelectedIndex()-1;
            if (index >= 0) {
                list.scrollTo(index);
                list.getSelectionModel().select(index);
                node=dates.get(index);
                MartyrScreen.setTree(node.getTree());
                totalb.setText("The total martyrs is: "+node.getTree().size());
                avglb.setText("The average ages is: "+node.getTree().avgAges());
                String max[] = node.getTree().maxDistrictLocation().split(",");
                dislb.setText("The District of Max Martyrs is: "+max[0]);
                loclb.setText("The Location of Max Martyrs is: "+max[1]);
                deldp.setValue(LocalDate.parse(node.getDate(), formatter));
            }
        });

        list.setOnMouseClicked(e->{
            int index = list.getSelectionModel().getSelectedIndex();
            list.scrollTo(index);
            list.getSelectionModel().select(index);
            node=dates.get(index);
            MartyrScreen.setTree(node.getTree());
            totalb.setText("The total martyrs is: "+node.getTree().size());
            avglb.setText("The average ages is: "+node.getTree().avgAges());
            String max[] = node.getTree().maxDistrictLocation().split(",");
            dislb.setText("The District of Max Martyrs is: "+max[0]);
            loclb.setText("The Location of Max Martyrs is: "+max[1]);
            deldp.setValue(LocalDate.parse(node.getDate(), formatter));
        });
        liststg.setOnMouseClicked(e->{
            int index = liststg.getSelectionModel().getSelectedIndex();
            liststg.scrollTo(index);
            liststg.getSelectionModel().select(index);
            nodestg=dates.get(index);
            pane2.getChildren().clear();
            Martyr m = (Martyr)nodestg.getTree().getRoot().getData();
            namestg.setText("The name is: "+m.getName());
            agestg.setText("The age is: "+m.getAge());
            genderstg.setText("The gender is: "+m.getGender());
            districtstg.setText("The district is: "+m.getDistrict());
            locationstg.setText("The location is: "+m.getLocation());
            HORIZONTAL_GAP = 50*(nodestg.getTree().getDepth(nodestg.getTree().getRoot())-1);
            drawAVLTree(pane2, nodestg.getTree().getRoot(), 700, 50, HORIZONTAL_GAP, VERTICAL_GAP);
        });

        vb2 = new VBox(20);
        vb2.setPadding(new Insets(10,10,10,10));
        vb2.setAlignment(Pos.CENTER);
        vb2.setStyle("-fx-background-radius: 10px; -fx-border-color: grey; -fx-border-radius: 10px;");
        vb2.getChildren().addAll(inslb, insdp, insert);
        hb = new HBox(15);
        hb.setAlignment(Pos.CENTER);
        hb.getChildren().addAll(update, delete);
        vb.getChildren().addAll(up_de, deldp, hb);
        hb2 = new HBox(15);
        hb2.setAlignment(Pos.CENTER);
        hb2.getChildren().addAll(vb, vb2);
        vb4 = new VBox(25);
        vb4.getChildren().addAll(mainlb, hb2);
        vb4.setAlignment(Pos.CENTER);
        mainlb.setText("Free Palestine");
        VBox.setMargin(mainlb, new Insets(60, 0, 0, 0));
    }

    public static VBox getVb() {
        return vb4;
    }
    public Label getDatelb() {
        return datelb;
    }
    public static void refresh(){
        int index=0;
        if(dates.size()!=0) {
            index = list.getSelectionModel().getSelectedIndex();
            dates.clear();
        }
        fillData();
        setNode(index);
    }
    private static void fillData(){
        for(int i=0; i<Driver.getHash().size(); i++){
            if(Driver.getHash().getTable()[i].getFlag()=='F')
                dates.add(Driver.getHash().getTable()[i]);
        }
    }
    private static void setNode(int index){
        list.getSelectionModel().select(index);
        list.scrollTo(index);
        node = dates.get(index);
        MartyrScreen.setTree(node.getTree());
        datelb.setText(node.getDate());
        deldp.setValue(LocalDate.parse(node.getDate(), formatter));
        totalb.setText("The total martyrs is: "+node.getTree().size());
        avglb.setText("The average ages is: "+node.getTree().avgAges());
        String max[] = node.getTree().maxDistrictLocation().split(",");
        dislb.setText("The District of Max Martyrs is: "+max[0]);
        loclb.setText("The Location of Max Martyrs is: "+max[1]);
        vb4.getChildren().remove(vb3);
        vb4.getChildren().add(vb3);
    }
    private static void refreshDate(String date){
        for(int i=0; i<dates.size(); i++){
            if(dates.get(i).getDate().equals(date)) {
                list.scrollTo(i);
                list.getSelectionModel().select(i);
                node=dates.get(i);
                MartyrScreen.setTree(node.getTree());
                totalb.setText("The total martyrs is: "+node.getTree().size());
                avglb.setText("The average ages is: "+node.getTree().avgAges());
                String max[] = node.getTree().maxDistrictLocation().split(",");
                dislb.setText("The District of Max Martyrs is: "+max[0]);
                loclb.setText("The Location of Max Martyrs is: "+max[1]);
                deldp.setValue(LocalDate.parse(node.getDate(), formatter));
            }
        }
    }
    private String formatDate(String date){
        String data[] = date.split("-");
        String res = data[1]+"/"+data[2]+"/"+data[0];
        if(res.charAt(0)=='0') res = res.substring(1);
        if(res.charAt(2)=='0') res = res.replaceFirst("0", "");
        if(res.length()==10 && res.charAt(3)=='0') res = res.replaceFirst("0", "");
        return res;
    }
    private void drawAVLTree(Pane pane, TNode root, double x, double y, double hGap, double vGap) {
        if (root != null) {
            if (root.getLeft() != null) {
                pane.getChildren().add(new Line(x - hGap, y + vGap, x, y));
                drawAVLTree(pane, root.getLeft(), x - hGap, y + vGap, hGap / 2, vGap);
            }

            if (root.getRight() != null) {
                pane.getChildren().add(new Line(x + hGap, y + vGap, x, y));
                drawAVLTree(pane, root.getRight(), x + hGap, y + vGap, hGap / 2, vGap);
            }
            Button button = new Button(String.valueOf(root.getData()));
            button.setStyle("-fx-color: #c8e1cc; -fx-font-size: 13; -fx-font-weight: bold; -fx-background-radius: 10px;");
            button.setLayoutX(x-5);
            button.setLayoutY(y-5);
            button.setUserData(root.getData());
            button.setMaxWidth(50);
            button.setOnAction(e->{
                Martyr m = (Martyr)button.getUserData();
                namestg.setText("The name is: "+m.getName());
                agestg.setText("The age is: "+m.getAge());
                genderstg.setText("The gender is: "+m.getGender());
                districtstg.setText("The district is: "+m.getDistrict());
                locationstg.setText("The location is: "+m.getLocation());
            });
            pane.getChildren().addAll(button);
        }
    }

}

