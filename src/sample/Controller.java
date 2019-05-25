package sample;

import com.polsl.model.City;
import com.polsl.model.CityNotFoundException;
import com.polsl.model.Path;
import com.polsl.model.PathMaker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class Controller implements Initializable {

    public Button button1;
    public ListView<String> listView;
    public ComboBox<Integer> mutationComboBox;
    public ComboBox<Integer> crossoverComboBox;
    public ComboBox<Integer> generationComboBox;
    public Button stepButton;
    public CheckBox stepCheckBox;
    public TextArea resultTextField;
    public PathMaker pathMaker=new PathMaker();
    public CopyOnWriteArrayList <City> cities=new CopyOnWriteArrayList<>();
    public CopyOnWriteArrayList <Path> paths = new CopyOnWriteArrayList<>();
    private Integer generation;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        generationComboBox.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        generationComboBox.getSelectionModel().selectFirst();

        crossoverComboBox.getItems().addAll(1, 2, 3, 4, 5);
        crossoverComboBox.getSelectionModel().selectFirst();

        mutationComboBox.getItems().addAll(1, 2, 3);
        mutationComboBox.getSelectionModel().selectFirst();
    }

    public void chooseFile(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            String path = selectedFile.getAbsolutePath();
            getData(path);
        } else {
            System.out.println("WRONG FILE! ");
        }
    }

    public void getData(String path) throws IOException {
        File file = new File(path);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;

        while ((st = br.readLine()) != null) {

            String tmpName = " ";
            String tmpX = "0";
            String tmpY = "0";
            Double x = 0.0;
            Double y = 0.0;
            StringTokenizer tokenizer = new StringTokenizer(st, ";");

            if (tokenizer.hasMoreTokens()) {
                tmpName = (tokenizer.nextToken());
                if (tokenizer.hasMoreTokens()) {
                    tmpX = (tokenizer.nextToken());
                    x = Double.parseDouble(tmpX);

                    if (tokenizer.hasMoreTokens()) {
                        tmpY = (tokenizer.nextToken());
                        y = Double.parseDouble(tmpY);
                    }
                }
                City city = new City(tmpName, x, y);
                cities.add(city);
            }
        }
        fillList(cities);
    }

    public void fillList(CopyOnWriteArrayList<City> cities) {
        ObservableList<String> items = FXCollections.observableArrayList();
        for (City city : cities) {
            items.add(city.getName() + "(" + city.getxLength() + " , " + city.getyLength() + ")");
        }
        listView.setItems(items);
    }

    public void makeAlgorythm(ActionEvent event) throws IOException {

        generation = (Integer) generationComboBox.getValue();
        Integer cross = (Integer) crossoverComboBox.getValue();
        Integer mutation = (Integer) mutationComboBox.getValue();

        ObservableList<String> list = listView.getItems();
        String selectedItem = listView.getSelectionModel().getSelectedItem();
        City startingCity = new City(" ", 0.0, 0.0);

        if (selectedItem != null && list.isEmpty() == false) {
            StringTokenizer st = new StringTokenizer(selectedItem, "(");
            String selectedItemCity = st.nextToken();
            for (City c : cities) {
                if (c.getName().equals(selectedItemCity)) {
                    startingCity.setName(c.getName());
                    startingCity.setxLength(c.getxLength());
                    startingCity.setyLength(c.getyLength());
                    cities.remove(c);
                }
            }
            try {
                paths= pathMaker.CreateFirstGeneration(cities, startingCity);
            }catch (CityNotFoundException ex){
                System.out.println("aaaaaaaaa");
            }
            if(stepCheckBox.isSelected()==false) {
                doFullCycle();
            }else{
                stepButton.setDisable(false);
                doGeneration();
            }


        } else if (list.isEmpty() == true) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Empty data set");
            alert.setHeaderText("Please select data set");
            String s = "Click 'Choose file...' button and select file with data ";
            alert.setContentText(s);
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No starting city");
            alert.setHeaderText("Please select starting city ");
            String s = "Select a city by clicking on the list ";
            alert.setContentText(s);
            alert.show();
        }
    }
    public void doFullCycle(){

        for(int j=0;j<generation;j++) {
           doGeneration();
        }
        printEndResult();

    }
    public void doStep(ActionEvent event) throws IOException{
        if(generation>0) {
            generation--;
            doGeneration();
        }else{
            stepButton.setDisable(true);
            printEndResult();
        }
    }
    public void doGeneration(){
        for (int i = 0; i < crossoverComboBox.getValue(); i++) {
            System.out.println("aaaaaaaaa");
            Path path1 = pathMaker.choosePath(paths);
            Path path2 = pathMaker.choosePath(paths);
            while (path1 == path2) {
                path2 = pathMaker.choosePath(paths);
            }
            paths.add(pathMaker.pathCrosser(path1, path2));

        }
        for (int i = 0; i < mutationComboBox.getValue(); i++) {
            System.out.println("aaaaaaaaa");
            Path path = pathMaker.choosePath(paths);
            paths.add(pathMaker.pathMutator(path));

        }
        paths= pathMaker.subGeneration(paths,10);
        if(stepCheckBox.isSelected()==true){
            printResult();
        }

    }
    public void printResult ()
    {
        StringBuilder stringBuilder=new StringBuilder();
        for(Path p: paths){
            stringBuilder.append(p.toString()+"\n");
        }
        resultTextField.setText(stringBuilder.toString());
       // resultTextField.setText(result);
    }
    public void printEndResult(){
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append("Wynik: \n");
        for(Path p: paths){
            stringBuilder.append(p.toString()+"\n");
        }
        resultTextField.setText(stringBuilder.toString());
    }
}









