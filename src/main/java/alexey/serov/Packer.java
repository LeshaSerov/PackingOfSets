package alexey.serov;

import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class Packer {
    private final Set<Integer> mainSet;
    private final List<Set<Integer>> subsetList;
    private Set<Set<Set<Integer>>> packSet;

    public Packer(Set<Integer> mainSet, List<Set<Integer>> subsetList) {
        this.mainSet = Collections.synchronizedSet(mainSet);
        this.subsetList = Collections.synchronizedList(subsetList);
        this.packSet = Collections.synchronizedSet(new HashSet<>());
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

    public List<Set<Integer>> maxPack() {
        for (var element : subsetList) {
            process(element, new Pack(), new ArrayList<>(subsetList));
        }

        int length = 0;
        List<Set<Integer>> result = null;
        for (var element : packSet) {
            log.atInfo().log(element.toString());
            length = Integer.max(element.size(), length);
        }
        return result;
    }

    private void process(Set<Integer> subset, Pack pack, List<Set<Integer>> availableSubsets) {
//        log.atInfo().log(subset.toString() + " " + pack.toString() + " " + availableSubsets.toString());

        if (pack.add(subset) && mainSet.equals(pack.getSet())) {
//            log.atInfo().log(pack.getList().toString());
            packSet.add(new HashSet<>(pack.getList()));
            return;
        }
        availableSubsets.remove(subset);
        if (availableSubsets.isEmpty()) {
//            log.atInfo().log(pack.getList().toString());
            return;
        }
        for (var element : availableSubsets) {
            process(element, new Pack(pack), new ArrayList<>(availableSubsets));
        }
    }
}
