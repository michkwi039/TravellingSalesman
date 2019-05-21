package sample;

import com.polsl.model.City;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import java.io.*;
import java.net.URL;
import java.util.*;

public class Controller implements Initializable {

    public Button button1;
    public ListView<String> listView;
    public ComboBox<Integer> mutationComboBox;
    public ComboBox<Integer> crossoverComboBox;
    public ComboBox<Integer> generationComboBox;
    public TextArea resultTextField;

    public LinkedList<City> cities = new LinkedList<City>();


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

    public void fillList(LinkedList<City> cities) {
        ObservableList<String> items = FXCollections.observableArrayList();
        for (City city : cities) {
            items.add(city.getName() + "(" + city.getxLength() + " , " + city.getyLength() + ")");
        }
        listView.setItems(items);
    }

    public void makeAlgorythm(ActionEvent event) throws IOException {

        Integer generation = (Integer) generationComboBox.getValue();
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
/*
               // TEST CZY DANE SIE POPRAWNIE WYSYLAJA

            for ( City c :cities){
                System.out.println(c.getName() + c.getxLength() + c.getyLength());
            }
            System.out.println("miasto poczatkowe:  " +  startingCity.getName() + startingCity.getxLength() +
                    startingCity.getyLength());
            System.out.println(generation + " "+  mutation + "  " + cross );
*/
            //  wywolanie algorytmu
            //  parametry:
            //   LinkedList<City> cities; <= bez początkowego miasta
            //   Integer generation, mutation, cross
            //   City  starting City = > miasto, z którego wychodzimy

            printResult( "tu bedzie wynik ");

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

    public void printResult ( String result )
    {
        resultTextField.setText(result);
    }
}









