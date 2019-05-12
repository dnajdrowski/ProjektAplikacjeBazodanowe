package com.dnajdrowski;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.ini4j.Ini;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main extends Application {


    private static Scene scene;

    public static Stage stage;

    public static Connection con;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        Parent root = loadFXML("login");
        scene = new Scene(root, 600, 800);
        primaryStage.setTitle("Kliniczka by dnajdrowski");
        primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("images/applogo.jpg")));
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setResizable(false);
    }

    @Override
    public void init() {
        connect();
    }

    @Override
    public void stop() {
        disconnect();
    }

    private static void connect() {
        new Thread(() -> {
            try {
                Ini ini = new Ini(new File("kliniczka.ini"));
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection(ini.get("ITEMS").get("URL"),
                        ini.get("ITEMS").get("login"),
                        ini.get("ITEMS").get("password"));
                System.out.println("Connected");
            } catch (SQLException | ClassNotFoundException | IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void disconnect() {
        try {
            if (con != null) {
                con.close();
                System.out.println("Disconnected");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("Layouts/" + fxml + ".fxml"));
        return loader.load();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
