package alexey.serov;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@AllArgsConstructor
@Slf4j
public class Packer {
    private final Set<Integer> mainSet;
    private final List<Set<Integer>> subSetList;

    @NoArgsConstructor
    @Getter
    private class Package{
        Set<Integer> set = new HashSet<Integer>();
        List<Set<Integer>> list = new ArrayList<Set<Integer>>();

        public boolean add(Set<Integer> set)
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

    private void pack(){

    }




}


