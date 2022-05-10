package aiss.api.resources;

import java.util.Comparator;

import aiss.model.Game;

public class CompareScoreReversed implements Comparator<Game> {

	@Override
	public int compare(Game p1, Game p2) {
		
		return p2.getScore().compareTo(p1.getScore());
	}

}
