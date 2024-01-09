package com.example.coursework;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.IOException;

public class CourseworkApplication extends Application {

  java.io.File selectedFile;

  @Override
  public void start(Stage stage) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(CourseworkApplication.class.getResource("hello-view.fxml"));
    // Create MenuBar
    MenuBar menuBar = new MenuBar();
    // Create menus
    Menu fileMenu = new Menu("File");
    Menu helpMenu = new Menu("Help");
    MenuItem helpItem = new MenuItem("Help");
    helpItem.setOnAction(e -> handleHelpAction());
    helpMenu.getItems().add(helpItem);
    // Create MenuItems
    MenuItem openFileItem = new MenuItem("Open file");
    MenuItem saveFileItem = new MenuItem("Save file");
    saveFileItem.setDisable(true);
    MenuItem saveAsFileItem = new MenuItem("Save file as ...");
    MenuItem exitItem = new MenuItem("Exit");
    openFileItem.setOnAction(e -> handleOpenFileAction(saveFileItem));
    saveFileItem.setOnAction(e -> handleSaveFileAction());
    saveAsFileItem.setOnAction(e -> handleSaveAsFileAction(saveFileItem));
    exitItem.setOnAction(e -> handleExitAction());
    // Add menuItems to the Menus
    fileMenu.getItems().addAll(openFileItem, saveFileItem, saveAsFileItem, exitItem);
    // Add Menus to the MenuBar
    menuBar.getMenus().addAll(fileMenu, helpMenu);
    BorderPane root = new BorderPane();
    root.setTop(menuBar);
    root.setCenter(fxmlLoader.load());

    Scene scene = new Scene(root, 500, 380);
    stage.setTitle("Поиск минимальной выпуклой оболочки!");
    stage.setScene(scene);
    stage.show();
  }

  private void handleHelpAction() {
    // Add logic to open help (CHM file, URI, etc.)
    HostServices hostServices = getHostServices();
    hostServices.showDocument("help.chm");

  }

  private void handleOpenFileAction(MenuItem saveFileItem) {
    FormController controller = FormController.controller;
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open File");
    fileChooser.getExtensionFilters().addAll(
        new FileChooser.ExtensionFilter("Text Files", "*.txt")
    );

    selectedFile = fileChooser.showOpenDialog(new Stage());

    if (selectedFile != null) {
      saveFileItem.setDisable(false);

      // Чтение из файла и установка текста в TextArea
      try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
        StringBuilder content = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
          content.append(line).append("\n");
        }
        controller.getAreaManual().setText(content.toString());
      } catch (IOException e) {
        // Обработка ошибок чтения файла
        e.printStackTrace();
      }
    }
  }

  private void handleSaveFileAction() {
    FormController controller = FormController.controller;
    if (selectedFile != null) {
      // Запись текста в файл
      try (BufferedWriter writer = new BufferedWriter(new FileWriter(selectedFile))) {
        writer.write(controller.getAreaManual().getText());
      } catch (IOException e) {
        // Обработка ошибок записи в файл
        e.printStackTrace();
      }
    }
  }

  private void handleSaveAsFileAction(MenuItem saveFileItem) {
    FormController controller = FormController.controller;
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Save As");
    fileChooser.getExtensionFilters().addAll(
        new FileChooser.ExtensionFilter("Text Files", "*.txt")
    );

    selectedFile = fileChooser.showSaveDialog(new Stage());

    if (selectedFile != null) {
      saveFileItem.setDisable(false);

      // Запись текста в новый файл
      try (BufferedWriter writer = new BufferedWriter(new FileWriter(selectedFile))) {
        writer.write(controller.getAreaManual().getText());
      } catch (IOException e) {
        // Обработка ошибок записи в файл
        e.printStackTrace();
      }
    }
  }

  private void handleExitAction() {
    System.out.println("Exit action triggered!");
    // Add your logic for handling the "Exit" action here
    Platform.exit();
  }

  public static void main(String[] args) {
    launch();
  }
}