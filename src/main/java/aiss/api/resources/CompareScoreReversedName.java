package aiss.api.resources;

import java.util.Comparator;

import aiss.model.Game;

public class CompareScoreReversedName implements Comparator<Game> {

    @Override
    public int compare(Game o1, Game o2) {
        int cont = o2.getScore().compareTo(o1.getScore());
        if (cont ==0) {
            cont = o1.getName().compareTo(o2.getName());
        }
        return cont;
    }

}
