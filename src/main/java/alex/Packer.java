package alex;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Packer {

  private final Set<Integer> mainSet;
  private final List<Set<Integer>> subsetList;
  private Set<Set<Set<Integer>>> packSet;

  public Packer(Set<Integer> mainSet, List<Set<Integer>> subsetList) {
    this.mainSet = Set.copyOf(mainSet);
    this.subsetList = List.copyOf(subsetList);
    this.packSet = Collections.newSetFromMap(new ConcurrentHashMap<>());
  }

  @Getter
  @ToString
  private static class Pack {

    Set<Integer> set;
    List<Set<Integer>> list;

    public Pack() {
      this.set = new HashSet<>();
      this.list = new ArrayList<>();
    }

    public Pack(Pack pack) {
      this.set = new HashSet<>(pack.getSet());
      this.list = new ArrayList<>(pack.getList());
    }

    public boolean add(Set<Integer> set) {
      if (Collections.disjoint(this.set, set)) {
        this.set.addAll(set);
        this.list.add(set);
        return true;
      }
      return false;
    }
  }

  //int nThreads = Runtime.getRuntime().availableProcessors()
  public List<Set<Integer>> maxPack(int nThreads, boolean showAllPackagesInConsole) {
    try {
      ExecutorService executor = Executors.newFixedThreadPool(nThreads);
      List<Set<Integer>> sets = new CopyOnWriteArrayList<>(subsetList);

      for (var element : subsetList) {
        List<Set<Integer>> currentSets = new CopyOnWriteArrayList<>(sets);
        executor.execute(
            () -> process(element, new Pack(), new CopyOnWriteArrayList<>(currentSets)));
        sets.remove(element);
      }

      executor.shutdown();
      executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      log.error("Error occurred while waiting for executor to terminate.", e);
    }

    if (showAllPackagesInConsole) {
      packSet.forEach(x -> log.info(x.toString()));
    }
    return packSet.stream()
        .max(Comparator.comparingInt(Set::size))
        .map(ArrayList::new)
        .orElseGet(ArrayList::new);
  }

  private void process(Set<Integer> subset, Pack pack, List<Set<Integer>> availableSubsets) {
    pack.add(subset);
    availableSubsets.remove(subset);
    if (availableSubsets.isEmpty()) {
      if (mainSet.equals(pack.getSet())) {
        packSet.add(new HashSet<>(pack.getList()));
      }
      return;
    }

    List<Set<Integer>> sets = new ArrayList<>(availableSubsets);
    for (var element : availableSubsets) {
      sets.remove(element);
      process(element, new Pack(pack), new CopyOnWriteArrayList<>(sets));
    }
  }
}