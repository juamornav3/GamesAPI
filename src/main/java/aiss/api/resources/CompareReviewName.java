package aiss.api.resources;

import java.util.Comparator;

import aiss.model.Game;

public class CompareReviewName implements Comparator<Game> {

    @Override
    public int compare(Game o1, Game o2) {
        int cont = o1.getReview().compareTo(o2.getReview());
        if (cont ==0) {
            cont = o1.getName().compareTo(o2.getName());
        }
        return cont;
    }

}
