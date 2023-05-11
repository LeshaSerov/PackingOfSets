package alexey.serov;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.var;

import java.util.*;

@Slf4j
@AllArgsConstructor
public class Packer {
    private AbstractSet<Package> packs = new HashSet<Package>();
    private final AbstractSet<Integer> mainSet;
    private final AbstractList<AbstractSet<Integer>> subsetList;
    @NoArgsConstructor
    @Getter
    private static class Package{
        AbstractSet<Integer> set = new HashSet<Integer>();
        List<AbstractSet<Integer>> list = new ArrayList<AbstractSet<Integer>>();

        public boolean add(AbstractSet<Integer> set)
        {
            if (Collections.disjoint(this.set, set))
            {
                this.set.addAll(set);
                this.list.add(set);
                return true;
            }
            return false;
        }
    }

    public AbstractList<AbstractSet<Integer>> maxPack(){
        for (var subset : subsetList)
        {
            var a = new ArrayList<HashSet<Integer>>();
            process(subset, a);
//            , Collections.copy(subsetList, new ArrayList<Set<Integer>>())
        }
//    private Set<Package> packageSet = new HashSet<Package>();
    return null;
    }

    void process(AbstractSet<Integer> subset, AbstractList<AbstractSet<Integer>> resultSet)
    {
//, List<Set<Integer>> ignoredSet
    }


}