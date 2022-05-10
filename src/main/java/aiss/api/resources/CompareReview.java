package aiss.api.resources;

import java.util.Comparator;

import aiss.model.Game;



public class CompareReview implements Comparator <Game>{

	@Override
	public int compare(Game r1, Game r2) {
		
		return r1.getReview().compareTo(r2.getReview());
	}

}
