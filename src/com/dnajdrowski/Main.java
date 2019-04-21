package com.dnajdrowski;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class Main extends Application {


    private static Scene scene;

    public static Stage xd;

    private Connection con = null;

    @Override
    public void start(Stage primaryStage) throws Exception{
        xd = primaryStage;
        Parent root = loadFXML("login");
        scene = new Scene(root, 600, 800);
        primaryStage.setTitle("Your Speedway");
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

    private void connect() {
//        try {
//            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//            Connection con = DriverManager.getConnection("jdbc:sqlserver://" +
//                    somedatabase);
//            System.out.println("Connected");
//        } catch (SQLException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }
    }

    private void disconnect() {
        try {
            if(con != null) {
                con.close();
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
