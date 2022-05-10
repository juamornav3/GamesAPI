package aiss.api.resources;

import java.util.Comparator;

import aiss.model.Game;

public class CompareScoreName implements Comparator<Game> {

    @Override
    public int compare(Game o1, Game o2) {
        int cont = o1.getScore().compareTo(o2.getScore());
        if (cont ==0) {
            cont = o1.getName().compareTo(o2.getName());
        }
        return cont;
    }

}
