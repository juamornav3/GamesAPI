package aiss.api.resources;

import java.util.Comparator;
import java.util.stream.Collectors;

import aiss.model.Game;


public class CompareConsole implements Comparator<Game> {

	@Override
public int compare(Game s1, Game s2) {
		
		return s1.getConsole().stream().collect(Collectors.toList()).get(0)
				.compareTo(s2.getConsole().stream().collect(Collectors.toList()).get(0));
	}

}
