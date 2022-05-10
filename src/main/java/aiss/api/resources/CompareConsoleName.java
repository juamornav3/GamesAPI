package aiss.api.resources;

import java.util.Comparator;
import java.util.stream.Collectors;

import aiss.model.Game;

public class CompareConsoleName implements Comparator<Game> {

    @Override
    public int compare(Game o1, Game o2) {
        int cont = 0;
        cont = o1.getConsole().stream().collect(Collectors.toList()).get(0)
                .compareTo(o2.getConsole().stream().collect(Collectors.toList()).get(0));
        if (cont ==0) {
            cont = o1.getName().compareTo(o2.getName());
        }
        return cont;
    }


}