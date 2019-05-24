package sample;

import com.polsl.model.City;
import com.polsl.model.Path;
import com.polsl.model.PathMaker;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Traveling Salesman");
        primaryStage.setScene(new Scene(root, 800, 400));
        primaryStage.show();
        /*PathMaker pathMaker=new PathMaker();
        ArrayList<Path> paths = new ArrayList<>();
        Path path=new Path("samolot",100);

        City starting=new City("samolot",paths);
        paths.add(path);
        ArrayList<City> cities =new ArrayList<>();
        City city =new City("auto",paths);
        cities.add(starting);
        int i=0;
        cities.forEach(e -> e.distances.add(city.distances.get(i)));*/
    }

    public static void main(String[] args) {
        launch(args);
    }
}
