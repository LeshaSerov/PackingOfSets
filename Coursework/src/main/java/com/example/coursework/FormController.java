package com.example.coursework;

import alex.Packer;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FormController {

  @FXML public Button buttonManual;
  @FXML public Label labelManual;
  @FXML public TextArea areaManual;
  @FXML public TextField labelSetAuto;
  @FXML public TextField labelCountAuto;
  @FXML public TextArea AreaAuto;
  @FXML public Button buttonAuto;
  @FXML public Label labelAuto;

  @FXML public void initialize() {
    controller = this;
  }

  public static FormController controller;

  public void onClickButtonManual(ActionEvent actionEvent) {
    try {
      Set<Integer> setElements = new HashSet<>();
      List<Set<Integer>> listSubset = new ArrayList<>();
      for (String s : areaManual.getText().split("\\n")) {
        Set<Integer> integerSet = new HashSet<>();
        for (String i : s.split("[() ]")) {
          if (i.equals("")) {
            continue;
          }
          setElements.add(Integer.parseInt(i));
          integerSet.add(Integer.parseInt(i));
        }
        if (integerSet.size() > 0) {
          listSubset.add(integerSet);
        }
      }
      Packer manualPacker = new Packer(setElements, listSubset);
      List<Set<Integer>> resultListSubset = manualPacker.maxPack(
          Runtime.getRuntime().availableProcessors(), true);
      labelManual.setText(resultListSubset.toString());
    } catch (NumberFormatException e) {
      labelManual.setText("Ошибка в введенном выражении.");
    }
  }

  public void onClickButtonAuto(ActionEvent actionEvent) {
    try {
      Set<Integer> setElements = new HashSet<>();
      for (String i : labelSetAuto.getText().split(" ")) {
        setElements.add(Integer.parseInt(i));
      }
      int count = Integer.parseInt(labelCountAuto.getText());
      if (((int) Math.pow(2, setElements.size())) <= count)
        throw new ArithmeticException();

      List<Set<Integer>> listSubset = new ArrayList<>();
      while (!canPackSetFromSubsets(new HashSet<>(setElements), new ArrayList<>(listSubset))) {
        listSubset = new ArrayList<>();
        for (int i = 0; i < count; i++) {
          listSubset.add(randSubset(setElements.stream().toList(), listSubset));
        }
      }

      StringBuilder stringBuilder = new StringBuilder();
      for (Set<Integer> i : listSubset) {
        stringBuilder.append("(");
        for (Integer j : i.stream().toList()) {
          stringBuilder.append(" ");
          stringBuilder.append(j);
          stringBuilder.append(" ");
        }
        stringBuilder.append(")");
        stringBuilder.append("\n");
      }
      AreaAuto.setText(stringBuilder.toString());

      Packer autoPacker = new Packer(setElements, listSubset);
      List<Set<Integer>> resultListSubset = autoPacker.maxPack(
          Runtime.getRuntime().availableProcessors(), true);
      labelAuto.setText(resultListSubset.toString());
    } catch (NumberFormatException e) {
      labelAuto.setText("Ошибка в введенном выражении.");
    } catch (ArithmeticException e){
      labelAuto.setText("Невозможно создать такое количество подмножеств!");
    }
  }

  private Set<Integer> randSubset(List<Integer> listElements,
      List<Set<Integer>> listSubset) {
    Set<Integer> integerSet = new LinkedHashSet<>();

    while (listSubset.contains(integerSet) || integerSet.isEmpty()) {
      integerSet = new LinkedHashSet<>();
      int randNumber = new Random().nextInt(1, listElements.size() + 1);
      for (int j = 0; j < randNumber; j++) {
        int randPosition = new Random().nextInt(0, listElements.size());
        while (integerSet.contains(listElements.get(randPosition))) {
          randPosition = new Random().nextInt(0, listElements.size());
        }
        integerSet.add(listElements.get(randPosition));
      }
    }
    return integerSet;
  }

  private boolean canPackSetFromSubsets(Set<Integer> setElements, List<Set<Integer>> listSubset) {
    if (setElements.isEmpty() && listSubset.isEmpty()) {
      return true; // Упаковка пустых множеств всегда возможна
    }
    for (Set<Integer> subset : listSubset) {
      if (subset.isEmpty()) {
        continue; // Пропустить пустые подмножества
      }
      if (setElements.containsAll(subset)) {
        setElements.removeAll(subset); // Удаляем из упаковки элементы, которые уже использовали
      }
      if (setElements.isEmpty()) {
        return true; // Упаковка была успешно собрана из подмножеств
      }
    }
    return false; // Не удалось собрать упаковку из подмножеств
  }

  public TextArea getAreaManual() {
    return areaManual;
  }
}