package alexey.serov;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.var;

import java.util.*;

@Slf4j
public class Packer {
    private Set<Pack> packs;
    private final Set<Integer> mainSet;
    private final List<Set<Integer>> subsetList;

    public Packer(Set<Integer> mainSet, List<Set<Integer>> subsetList) {
        this.mainSet = Collections.synchronizedSet(mainSet);
        this.subsetList = Collections.synchronizedList(subsetList);
        this.packs = Collections.synchronizedSet(new HashSet<Pack>());
    }

    @Getter
    private static class Pack {
        Set<Integer> set;
        ArrayList<HashSet<Integer>> list;

        public Pack(){
            set = new HashSet<Integer>();
            list = new ArrayList<HashSet<Integer>>();
        }
        public Pack(Pack pack) {
            set = pack.getSet();
            list = pack.getList();
        }

        public boolean add(Set<Integer> set) {
            if (Collections.disjoint(this.set, set)) {
                this.set.addAll(set);
                this.list.add((HashSet<Integer>) set);
                return true;
            }
            return false;
        }
    }

    ArrayList<HashSet<Integer>> maxPack() {


        for (var element : subsetList) {
            List<Set<Integer>> availableSubsets = new ArrayList<>();
            Collections.copy(subsetList, availableSubsets);
            process(element, new Pack(), availableSubsets);
        }


        int length = 0;
        ArrayList<HashSet<Integer>> result = null;
        for (var element : packs) {
            length = Math.max(element.getList().size(), length);
            result = element.getList();
        }
        return result;
    }

    void process(Set<Integer> subset, Pack pack, List<Set<Integer>> availableSubsets) {
        log.atInfo().log(subset.toString() + pack.toString() + availableSubsets.toString());
        if (pack.add((HashSet<Integer>) subset) && pack.getSet() == mainSet) {
            packs.add(pack);
            return;
        }
        availableSubsets.remove(subset);
        if (availableSubsets.isEmpty()) {
            return;
        }
        for (var element : availableSubsets) {
            process(element, new Pack(pack), availableSubsets);
        }
    }
}
