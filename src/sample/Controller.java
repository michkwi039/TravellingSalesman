package sample;

import com.polsl.model.City;
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
    public PathMaker pathMaker = new PathMaker();
    public CopyOnWriteArrayList<City> cities = new CopyOnWriteArrayList<>();
    public CopyOnWriteArrayList<Path> paths = new CopyOnWriteArrayList<>();
    private Integer generation;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        generationComboBox.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        generationComboBox.getSelectionModel().selectFirst();

        crossoverComboBox.getItems().addAll(0, 1, 2, 3, 4, 5);
        crossoverComboBox.getSelectionModel().selectFirst();

        mutationComboBox.getItems().addAll(0, 1, 2, 3);
        mutationComboBox.getSelectionModel().selectFirst();
    }

    public void chooseFile(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            if (cities.isEmpty() == false) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Add data or overwrite");
                alert.setHeaderText("Do you want to add data to the list or overwrite existing data");
                alert.setContentText("Choose your option.");
                ButtonType buttonTypeOne = new ButtonType("Add");
                ButtonType buttonTypeTwo = new ButtonType("Overwrite");
                alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == buttonTypeTwo) {
                    listView.getItems().remove(0, listView.getItems().size());
                    fileChooser.getExtensionFilters().add(extFilter);
                    listView.getItems().remove(0, listView.getItems().size());
                    cities.clear();
                }
                String path = selectedFile.getAbsolutePath();
                getData(path);
            } else {
                String path = selectedFile.getAbsolutePath();
                getData(path);
            }
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
            try {
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
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Wrong data format  ");
                alert.setHeaderText("Not all data has been correctly read. Please select another data set or correct this one");
                String s = "Click 'Choose file...' button and select file with data ";
                alert.setContentText(s);
                alert.show();
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
            if(cities.isEmpty()){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Just one city");
                alert.setHeaderText("Please select different data set");
                String s = "Click 'Choose file...' button and select file with data ";
                alert.setContentText(s);
                alert.show();
            }else {
                paths = pathMaker.CreateFirstGeneration(cities, startingCity);
                if (stepCheckBox.isSelected() == false) {
                    doFullCycle();
                } else {
                    stepButton.setDisable(false);
                    doGeneration();
                }
                cities.add(startingCity);
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

    public void doFullCycle() {

        for (int j = 0; j < generation; j++) {
            doGeneration();
        }
        printEndResult();
    }

    public void doStep(ActionEvent event) throws IOException {
        if (generation > 0) {
            generation--;
            doGeneration();
        } else {
            stepButton.setDisable(true);
            printEndResult();
        }
    }

    public void doGeneration() {
        for (int i = 0; i < crossoverComboBox.getValue(); i++) {
            Path path1 = pathMaker.choosePath(paths);
            Path path2 = pathMaker.choosePath(paths);
            while (path1 == path2) {
                path2 = pathMaker.choosePath(paths);
            }
            paths.add(pathMaker.pathCrosser(path1, path2));
        }
        for (int i = 0; i < mutationComboBox.getValue(); i++) {
            Path path = pathMaker.choosePath(paths);
            paths.add(pathMaker.pathMutator(path));
        }
        paths = pathMaker.subGeneration(paths, 10);
        if (stepCheckBox.isSelected() == true) {
            printResult();
        }
    }

    public void printResult() {

        StringBuilder stringBuilder = new StringBuilder();
        for (Path p : paths) {
            stringBuilder.append(p.toString() + "\n");
        }
        resultTextField.setText(stringBuilder.toString());

    }

    public void printEndResult() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Result: \n");
        for (Path p : paths) {
            stringBuilder.append(p.toString() + "\n");
        }
        resultTextField.setText(stringBuilder.toString());
    }
}





