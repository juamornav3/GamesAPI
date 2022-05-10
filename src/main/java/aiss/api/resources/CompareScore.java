package aiss.api.resources;

import java.util.Comparator;

import aiss.model.Game;

public class CompareScore implements Comparator<Game> {

	@Override
	public int compare(Game p1, Game p2) {
		
		return p1.getScore().compareTo(p2.getScore());
	}

}
