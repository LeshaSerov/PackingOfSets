package project;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Application {

  static List<Set<Integer>> setList = new ArrayList<>(List.of(
      Set.of(1),
      Set.of(2),
      Set.of(3),
      Set.of(4),
      Set.of(1, 2),
      Set.of(3, 4),
      Set.of(5, 6),
      Set.of(2, 4, 6),
      Set.of(1, 3, 5),
      Set.of(3, 4, 5, 6)
  ));

  static List<Set<Integer>> setList2 = new ArrayList<>(List.of(
      Set.of(1),
      Set.of(2),
      Set.of(1, 2),
      Set.of(1, 3),
      Set.of(1, 4),
      Set.of(2, 4),
      Set.of(2, 5),
      Set.of(3, 4),
      Set.of(3, 5),
      Set.of(3, 6),
      Set.of(5, 6)
  ));

  static List<Set<Integer>> setList3 = new ArrayList<>(List.of(
      Set.of(1),
      Set.of(2),
      Set.of(3),
      Set.of(4),
      Set.of(1, 2),
      Set.of(1, 3),
      Set.of(1, 4),
      Set.of(2, 4),
      Set.of(2, 3),
      Set.of(3, 4)
  ));

  static List<Set<Integer>> setList4 = new ArrayList<>(List.of(
      Set.of(1),
      Set.of(2),
      Set.of(3),
      Set.of(4),
      Set.of(5),
      Set.of(6),
      Set.of(7),
      Set.of(8),
      Set.of(9),
      Set.of(10),
      Set.of(11)
  ));

  static List<Set<Integer>> setList5 = new ArrayList<>(List.of(
      Set.of(1, 8),
      Set.of(2, 3),
      Set.of(2, 4),
      Set.of(2, 5),
      Set.of(2, 6),
      Set.of(2, 7),
      Set.of(2, 8),
      Set.of(3, 4),
      Set.of(3, 5),
      Set.of(3, 6),
      Set.of(3, 7),
      Set.of(3, 8),
      Set.of(4, 5),
      Set.of(4, 6),
      Set.of(4, 7),
      Set.of(4, 8),
      Set.of(5, 6),
      Set.of(5, 7),
      Set.of(5, 8),
      Set.of(6, 7),
      Set.of(6, 8),
      Set.of(7, 8)
  ));

  static List<Set<Integer>> setList6 = new ArrayList<>(List.of(
      Set.of(1),
      Set.of(2),
      Set.of(3),
      Set.of(4),
      Set.of(5),
      Set.of(6),
      Set.of(7),
      Set.of(8),
      Set.of(9),
      Set.of(10),
      Set.of(11),
      Set.of(12),
      Set.of(13),
      Set.of(14),
      Set.of(15),
      Set.of(16),
      Set.of(17),
      Set.of(18),
      Set.of(19),
      Set.of(20),
      Set.of(21),
      Set.of(22),
      Set.of(23),
      Set.of(24),
      Set.of(25),
      Set.of(26),
      Set.of(27)
  ));

  static Set<Integer> set = new HashSet<>(Set.of(1, 2, 3, 4, 5, 6));
  static Set<Integer> set3 = new HashSet<>(Set.of(1, 2, 3, 4));
  static Set<Integer> set4 = new HashSet<>(Set.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11));
  static Set<Integer> set5 = new HashSet<>(Set.of(1, 2, 3, 4, 5, 6, 7, 8));
  static Set<Integer> set6 = new HashSet<>(
      Set.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24,
          25, 26, 27));

  public static void main(String[] args) {
    //Настройка отображения всех найденных упаковок
    boolean showAllPackagesInConsole = false;
    //Количество потоков
    int nThreads = Runtime.getRuntime().availableProcessors();

//    log.atInfo().log("Поиск максимальной упаковки\n");
//    getMaxPack(nThreads, set, setList, showAllPackagesInConsole);
//    getMaxPack(6, set, setList, showAllPackagesInConsole);
//
//    log.atInfo().log("Поиск максимальной упаковки\n");
////    //Этот код очень медленный 32 сек
//    getMaxPack(1, set, setList2, showAllPackagesInConsole);
//    getMaxPack(6, set, setList2, showAllPackagesInConsole);
//
//    log.atInfo().log("Поиск максимальной упаковки\n");
//    getMaxPack(1, set3, setList3, showAllPackagesInConsole);
//    getMaxPack(6, set3, setList3, showAllPackagesInConsole);

    //Этот код медленный
    log.atInfo().log("Поиск максимальной упаковки\n");
    //102 сек
    getMaxPack(1, set4, setList4, showAllPackagesInConsole);
    //26 сек
    getMaxPack(6, set4, setList4, showAllPackagesInConsole);

    log.atInfo().log("Поиск максимальной упаковки\n");
    getMaxPack(nThreads, set5, setList5, showAllPackagesInConsole);
    getMaxPack(6, set5, setList5, showAllPackagesInConsole);

    log.atInfo().log("Поиск максимальной упаковки\n");
    getMaxPack(nThreads, set6, setList6, showAllPackagesInConsole);
    getMaxPack(6, set6, setList6, showAllPackagesInConsole);

  }

  private static void getMaxPack(int nThreads, Set<Integer> set, List<Set<Integer>> setList,
      boolean showAllPackagesInConsole) {
//    log.atInfo().log(setList.toString() + " : " + nThreads);

    long start = System.nanoTime();
    List<Set<Integer>> maxPack = new Packer(set, setList)
        .maxPack(nThreads, showAllPackagesInConsole);
    long finish = System.nanoTime();
    long elapsed = finish - start;
    //Вывод времени в консоль
    log.atInfo().log(String.valueOf((double) elapsed / 1000000 / 1000));
    //Вывод найденной упаковки в консоль
    log.atInfo().log(maxPack.toString());
    //Вывод размера максимальной упаковки
    log.atInfo().log("MaxPack: " + maxPack.size());
    //Дополнительный отступ после
    log.atInfo().log("\n");
  }

}
