package com.phase3.phase3;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;

public class MartyrScreen {
    private static Label inslb, delb, shlb, printlb, insertlb, depthlb, sizelb;
    private static Button insert, delete, update, search, show, print, insertstg, delstg, upstg;
    private static TextField stxt, namestg, agestg;
    private static ComboBox<String> districtstg, locationstg;
    private static AVLTree tree;
    private static VBox mainvb, insvb, delvb, shvb, printvb, vbstg, vbsearch;
    private static RadioButton sort, level, male, female;
    private static ToggleGroup tg, tg2;
    private static GridPane grid;
    private static TableView<Martyr> tv, tvstg;
    private static ObservableList<Martyr> data, datastg;
    private static MaxHeap heap;
    private static Stage stage;

    public MartyrScreen() {
        heap = new MaxHeap();

        inslb = new Label("Insert New Martyr");
        inslb.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");
        insert = new Button("Insert");
        insert.setStyle("-fx-color: #7C15D8; -fx-font-size: 16; -fx-font-weight: bold; -fx-background-radius: 10px;");
        insertlb =  new Label("Insert New Martyr");
        insertlb.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        namestg = new TextField();
        namestg.setStyle("-fx-font-size: 13px;");
        namestg.setPromptText("Enter Name");
        agestg = new TextField();
        agestg.setStyle("-fx-font-size: 13px;");
        agestg.setPromptText("Enter Age");
        agestg.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    agestg.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        male = new RadioButton("M");
        male.setStyle("-fx-font-size: 13;");
        female = new RadioButton("F");
        female.setStyle("-fx-font-size: 13;");
        tg2 = new ToggleGroup();
        male.setToggleGroup(tg2);
        female.setToggleGroup(tg2);
        districtstg = new ComboBox<>();
        districtstg.setStyle("-fx-font-size: 13;");
        districtstg.setPromptText("District");
        districtstg.setOnAction(e->{
            fillCombo(MenuFX.getDhash().search(districtstg.getValue()).getLocations());
        });
        locationstg = new ComboBox<>();
        locationstg.setStyle("-fx-font-size: 13;");
        locationstg.setPromptText("Location");

        Alert alert2 = new Alert(Alert.AlertType.ERROR);
        alert2.setTitle("Error Dialog");
        alert2.setHeaderText("An error has occurred!");
        Alert alert3 = new Alert(Alert.AlertType.INFORMATION);
        alert3.setTitle("Success Dialog");
        alert3.setHeaderText("This is a congratulation!");
        insertstg = new Button("Insert");
        insertstg.setStyle("-fx-color: #7C15D8; -fx-font-size: 16; -fx-font-weight: bold; -fx-background-radius: 10px;");
        vbstg = new VBox(10);
        HBox hb4 = new HBox(10);
        Label gender = new Label("Gender");
        gender.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");
        hb4.getChildren().addAll(gender, male, female);
        vbstg.getChildren().addAll(insertlb, namestg, agestg, hb4, districtstg, locationstg, insertstg);
        vbstg.setPadding(new Insets(20,20,20,20));
        stage = new Stage();
        stage.setResizable(false);
        stage.setTitle("Insert Martyr");
        stage.setScene(new Scene(vbstg));
        insert.setOnAction(e->{
            inslb.setText("Insert New Martyr");
            namestg.setText("");
            agestg.setText("");
            districtstg.setValue(null);
            locationstg.setValue(null);
            male.setSelected(false);
            insertstg.setText("Insert");
            insertstg.setOnAction(ee->{
                if(namestg.getText().isBlank() || agestg.getText().isBlank() || districtstg.getValue()==null ||
                        locationstg.getValue()==null || (!male.isSelected() && !female.isSelected())){
                    alert2.setContentText("Please fill all fields!");
                    alert2.show();
                }
                else{
                    if(Integer.parseInt(agestg.getText()) > 130){
                        alert2.setContentText("The age can't be more than 130");
                        alert2.show();
                    }
                    else{
                        MenuFX.setSaved(false);
                        String g="";
                        if(male.isSelected()) g="M";
                        else g="F";
                        tree.insert(new Martyr(namestg.getText(), g, districtstg.getValue(),
                                locationstg.getValue(), Integer.parseInt(agestg.getText())));
                        depthlb.setText("The Height of the Tree is: "+(tree.getDepth(tree.getRoot())-1));
                        sizelb.setText("The Size of the Tree is: "+tree.size());
                        data.clear();
                        heap.clear();
                        fillTv(tree.getRoot());
                        alert3.setContentText("Inserted Successfully!");
                        alert3.show();
                    }
                }
            });
            stage.show();
        });
        insvb = new VBox(15);
        insvb.setPadding(new Insets(10, 10, 10, 10));
        insvb.setStyle("-fx-border-color: black; -fx-border-width: 2px; -fx-border-radius: 5px;");
        insvb.getChildren().addAll(inslb, insert);

        delb = new Label("Search About Martyrs to Delete or Update");
        delb.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        stxt = new TextField();
        stxt.setStyle("-fx-font-size: 16px;");
        stxt.setPromptText("Enter Martyr Name");
        search = new Button("Search");
        search.setStyle("-fx-color: #7C15D8; -fx-font-size: 16; -fx-font-weight: bold; -fx-background-radius: 10px;");
        delvb = new VBox(15);
        delvb.setPadding(new Insets(10, 10, 10, 10));
        delvb.setStyle("-fx-border-color: black; -fx-border-width: 2px; -fx-border-radius: 5px;");
        delvb.getChildren().addAll(delb, stxt, search);
        delstg = new Button("Delete");
        delstg.setStyle("-fx-color: #7C15D8; -fx-font-size: 16; -fx-font-weight: bold; -fx-background-radius: 10px;");
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);
        alert.setTitle("Warning Dialog");
        alert.setHeaderText("This is a warning!");
        ButtonType okButton = alert.getButtonTypes().get(0);
        delstg.setOnAction(e->{
            Martyr m = tvstg.getSelectionModel().getSelectedItem();
            if(m!=null){
                alert.setContentText("Are you sure to delete "+m.getName()+"?!");

                alert.setOnCloseRequest(event -> {
                    if (alert.getResult() == okButton) {
                        MenuFX.setSaved(false);
                        tree.deleteNode(tree.getRoot(), m);
                        data.remove(m);
                        datastg.remove(m);
                        depthlb.setText("The Height of the Tree is: "+(tree.getDepth(tree.getRoot())-1));
                        sizelb.setText("The Size of the Tree is: "+tree.size());
                    }
                });
                alert.showAndWait();
            }
            else{
                alert2.setContentText("Please select a martyr");
                alert2.show();
            }
        });
        upstg = new Button("Update");
        upstg.setStyle("-fx-color: #7C15D8; -fx-font-size: 16; -fx-font-weight: bold; -fx-background-radius: 10px;");
        upstg.setOnAction(e->{
            Martyr m = tvstg.getSelectionModel().getSelectedItem();
            if(m!=null){
                inslb.setText("Update Martyr");
                namestg.setText(m.getName());
                agestg.setText(m.getAge()+"");
                districtstg.setValue(m.getDistrict());
                fillCombo(MenuFX.getDhash().search(districtstg.getValue()).getLocations());
                locationstg.setValue(m.getLocation());
                male.setSelected(true);
                insertstg.setText("Update");
                insertstg.setOnAction(eee->{
                    if(namestg.getText().isBlank() || agestg.getText().isBlank() || districtstg.getValue()==null ||
                            locationstg.getValue()==null || (!male.isSelected() && !female.isSelected())){
                        alert2.setContentText("Please fill all fields!");
                        alert2.show();
                    }
                    else{
                        if(Integer.parseInt(agestg.getText()) > 130){
                            alert2.setContentText("The age can't be more than 130");
                            alert2.show();
                        }
                        else{
                            alert.setContentText("Are you sure to delete "+m.getName()+"?!");

                            alert.setOnCloseRequest(event -> {
                                if (alert.getResult() == okButton) {
                                    MenuFX.setSaved(false);
                                    String g="";
                                    if(male.isSelected()) g="M";
                                    else g="F";
                                    tree.deleteNode(tree.getRoot(), m);
                                    tree.insert(new Martyr(namestg.getText(), g, districtstg.getValue(),
                                            locationstg.getValue(), Integer.parseInt(agestg.getText())));
                                    depthlb.setText("The Height of the Tree is: "+(tree.getDepth(tree.getRoot())-1));
                                    sizelb.setText("The Size of the Tree is: "+tree.size());
                                    data.clear();
                                    datastg.clear();
                                    heap.clear();
                                    fillTvStg(tree.getRoot(), stxt.getText());
                                    fillTv(tree.getRoot());
                                    alert3.setContentText("Updated Successfully!");
                                    alert3.show();
                                }
                            });
                            alert.showAndWait();
                        }
                    }
                });
                stage.show();
            }
            else{
                alert2.setContentText("Please choose a martyr to update!");
                alert2.show();
            }
        });
        HBox hbstg = new HBox(15);
        hbstg.getChildren().addAll(upstg, delstg);
        hbstg.setAlignment(Pos.CENTER);
        tvstg = new TableView<>();
        datastg = FXCollections.observableArrayList();
        TableColumn<Martyr, String> nameColumn1 = new TableColumn<>("Name");
        TableColumn<Martyr, Integer> ageColumn1 = new TableColumn<>("Age");
        TableColumn<Martyr, String> genderColumn1 = new TableColumn<>("Gender");
        TableColumn<Martyr, String> districtColumn1 = new TableColumn<>("District");
        TableColumn<Martyr, String> locationColumn1 = new TableColumn<>("Location");

        tvstg.getColumns().addAll(nameColumn1, ageColumn1, genderColumn1, districtColumn1, locationColumn1);
        nameColumn1.setCellValueFactory(new PropertyValueFactory<>("name"));
        ageColumn1.setCellValueFactory(new PropertyValueFactory<>("age"));
        genderColumn1.setCellValueFactory(new PropertyValueFactory<>("gender"));
        districtColumn1.setCellValueFactory(new PropertyValueFactory<>("district"));
        locationColumn1.setCellValueFactory(new PropertyValueFactory<>("location"));

        nameColumn1.setStyle( "-fx-alignment: CENTER;");
        ageColumn1.setStyle( "-fx-alignment: CENTER;");
        genderColumn1.setStyle( "-fx-alignment: CENTER;");
        districtColumn1.setStyle( "-fx-alignment: CENTER;");
        locationColumn1.setStyle( "-fx-alignment: CENTER;");

        tvstg.setItems(datastg);
        tvstg.setMinWidth(700);
        tvstg.setMaxWidth(700);
        tvstg.setMaxHeight(500);
        tvstg.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tvstg.setStyle("-fx-font-size: 14.5px; -fx-border-color: #7C15D8; -fx-border-width: 2px;");
        vbsearch = new VBox(15);
        vbsearch.setPadding(new Insets(10, 10, 10, 10));
        vbsearch.getChildren().addAll(tvstg, hbstg);
        vbsearch.setAlignment(Pos.CENTER);
        Stage sstage = new Stage();
        sstage.setScene(new Scene(vbsearch));
        search.setOnAction(e->{
            if(stxt.getText().isBlank()){
                alert2.setContentText("Please enter a part of name");
                alert2.show();
            }
            else {
                datastg.clear();
                fillTvStg(tree.getRoot(), stxt.getText());
                if (datastg.isEmpty()) {
                    alert2.setContentText("No martyrs exist");
                    alert2.show();
                }
                else{
                    sstage.setTitle("Martyrs who has "+ stxt.getText()+" on their names");
                    sstage.show();
                }
            }
        });

        shlb = new Label("Show The Tree Statistics");
        shlb.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        depthlb  = new Label("Height Of the Tree");
        depthlb.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        sizelb = new Label("Size of the Tree");
        sizelb.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        shvb = new VBox(15);
        shvb.setPadding(new Insets(10, 10, 10, 10));
        shvb.setStyle("-fx-border-color: black; -fx-border-width: 2px; -fx-border-radius: 5px;");
        shvb.getChildren().addAll(shlb, depthlb, sizelb);

        printlb = new Label("Print Martyrs Tree");
        printlb.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        print = new Button("Print");
        print.setStyle("-fx-color: #7C15D8; -fx-font-size: 16; -fx-font-weight: bold; -fx-background-radius: 10px;");
        sort = new RadioButton("Sort By Age");
        sort.setStyle("-fx-font-color: #7C15D8; -fx-font-weight: bold;");
        sort.setOnAction(e->sortMartyrs());
        level = new RadioButton("Level By Level");
        level.setStyle("-fx-font-color: #7C15D8; -fx-font-weight: bold;");
        level.setOnAction(e->{
            data.clear();
            heap.clear();
            fillTv(tree.getRoot());
        });
        tg = new ToggleGroup();
        sort.setToggleGroup(tg);
        level.setToggleGroup(tg);
        printvb = new VBox(15);
        printvb.setPadding(new Insets(10, 10, 10, 10));
        printvb.setStyle("-fx-border-color: black; -fx-border-width: 2px; -fx-border-radius: 5px;");
        printvb.getChildren().addAll(printlb, level, sort);

        grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setHgap(15);
        grid.setVgap(15);
        grid.add(insvb, 0, 0);
        grid.add(delvb, 0, 1);
        grid.add(shvb, 1, 0);
        grid.add(printvb, 1,1);
        grid.setAlignment(Pos.CENTER);
        insvb.setAlignment(Pos.CENTER);
        delvb.setAlignment(Pos.CENTER);
        shvb.setAlignment(Pos.CENTER);
        printvb.setAlignment(Pos.CENTER);

        tv = new TableView<Martyr>();
        data = FXCollections.observableArrayList();
        TableColumn<Martyr, String> nameColumn = new TableColumn<>("Name");
        TableColumn<Martyr, Integer> ageColumn = new TableColumn<>("Age");
        TableColumn<Martyr, String> genderColumn = new TableColumn<>("Gender");
        TableColumn<Martyr, String> districtColumn = new TableColumn<>("District");
        TableColumn<Martyr, String> locationColumn = new TableColumn<>("Location");

        tv.getColumns().addAll(nameColumn, ageColumn, genderColumn, districtColumn, locationColumn);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        districtColumn.setCellValueFactory(new PropertyValueFactory<>("district"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));

        nameColumn.setStyle( "-fx-alignment: CENTER;");
        ageColumn.setStyle( "-fx-alignment: CENTER;");
        genderColumn.setStyle( "-fx-alignment: CENTER;");
        districtColumn.setStyle( "-fx-alignment: CENTER;");
        locationColumn.setStyle( "-fx-alignment: CENTER;");

        tv.setItems(data);
        tv.setMaxWidth(700);
        tv.setMaxHeight(500);
        tv.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tv.setStyle("-fx-font-size: 14.5px; -fx-border-color: #7C15D8; -fx-border-width: 2px;");

        mainvb = new VBox(15);
        mainvb.getChildren().addAll(grid, tv);
        mainvb.setAlignment(Pos.CENTER);
    }

    public static VBox getVb(){
        return mainvb;
    }
    public static AVLTree getTree(){
        return tree;
    }
    public static void setTree(AVLTree t){
        tree=t;
        data.clear();
        heap.clear();
        level.setSelected(true);
        sort.setSelected(false);
        depthlb.setText("The Height of the Tree is: "+(tree.getDepth(tree.getRoot())-1));
        sizelb.setText("The Size of the Tree is: "+tree.size());
        fillTv(tree.getRoot());
    }
    private static void fillTv(TNode node){
        if(node==null) return;
        Queue queue = new Queue();
        queue.enQueue(node);
        while(!queue.isEmpty()){
            TNode temp = (TNode)queue.deQueue();
            if(temp.getRight()!=null) queue.enQueue(temp.getRight());
            if(temp.getLeft()!=null) queue.enQueue(temp.getLeft());
            data.add((Martyr)temp.getData());
            heap.insertMaxHeap((Martyr)temp.getData());
        }
    }
    private static void sortMartyrs(){
        heap.heapSort();
        data.clear();
        for(int i=0; i<heap.size(); i++){
            data.add(heap.getMartyr(i));
        }
    }
    private static void fillCombo(ArrayList<String> locations){
        locationstg.getItems().clear();
        for(int i=0; i<locations.size(); i++){
            locationstg.getItems().add(locations.get(i));
        }
    }
    public static ComboBox<String> getDistrictstg(){
        return districtstg;
    }
    public static ComboBox<String> getLocationstg(){
        return locationstg;
    }
    private static void fillTvStg(TNode node, String search){
        if(node==null) return;
        fillTvStg(node.getRight(), search);
        if(((Martyr)node.getData()).getName().contains(search)) datastg.add((Martyr)node.getData());
        fillTvStg(node.getLeft(), search);
    }

}
