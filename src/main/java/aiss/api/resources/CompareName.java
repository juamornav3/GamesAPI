package aiss.api.resources;

import java.util.Comparator;

import aiss.model.Game;


public class CompareName implements Comparator <Game> {

	@Override
public int compare(Game s1, Game s2) {
		
		return s1.getName().compareTo(s2.getName());
	}

}
