package alexey.serov;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Application {
    static List<Set<Integer>> setList = new ArrayList<>(List.of(
            Set.of(1),
            Set.of(2),
            Set.of(1, 2),
            Set.of(3, 4),
            Set.of(5, 6),
            Set.of(1, 3),
            Set.of(2, 4),
            Set.of(2, 4, 6),
            Set.of(1, 3, 5),
            Set.of(2, 3, 4)
//            Set.of(3, 4, 5, 6)
//            Set.of(1, 2, 5, 6)
    ));
    static Set<Integer> set = new HashSet<>(Set.of(1, 2, 3, 4, 5, 6));

    public static void main(String[] args) {
        new Packer(set, setList).maxPack();
    }

}
