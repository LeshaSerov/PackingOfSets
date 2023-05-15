package project;

import java.util.concurrent.locks.LockSupport;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.*;

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
            for (var element : subsetList) {
                executor.execute(() -> process(element, new Pack(), new CopyOnWriteArrayList<>(subsetList)));
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

    private void process(Set<Integer> subset, Pack pack, List<Set<Integer>> availableSubsets){
        if (pack.add(subset) && mainSet.equals(pack.getSet())) {
            packSet.add(new HashSet<>(pack.getList()));
            return;
        }

        availableSubsets.remove(subset);
        if (availableSubsets.isEmpty()) {
            return;
        }

        for (var element : new ArrayList<>(availableSubsets)) {
            process(element, new Pack(pack), new CopyOnWriteArrayList<>(availableSubsets));
        }
    }
}