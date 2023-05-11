package alexey.serov;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Application {
    List<Set<Integer>> setList = new ArrayList<>(
            List.of(
                    Set.of(1),
                    Set.of(2),
                    Set.of(3),
                    Set.of(4),
                    Set.of(5),
                    Set.of(6),
                    Set.of(7),
                    Set.of(8)
            )
    );
    Set<Integer> set = new HashSet<>(Set.of(1,2,3,4,5,6,7,8,9,10));

    public static void main(String[] args) {


    }

}
