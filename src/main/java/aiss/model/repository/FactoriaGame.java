package aiss.model.repository;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import aiss.model.EnumRev;
import aiss.model.Game;
import utils.Checkers;
import utils.Ficheros;

public class FactoriaGame {
	
	public static List<Game> juegos = leeGame("ficheros/Games_1.txt");
	
	public static Game parseGame(String s) {

		Game res = null;
		Checkers.checkNoNull(s);
		String []trozos = s.split(",");
		Set<String> console = new HashSet<String>();
		String []trozos2 = trozos[0].split(";");
		for(String c : trozos2) {
			console.add(c.trim());
		}
		String name = trozos[1].replace(";", ",").trim();
		EnumRev review = EnumRev.valueOf(trozos[2].trim());
		Integer score = Integer.parseInt(trozos[3].replace(";", "").trim());
		res = new Game(console,  name,  review,  score);
		return res;
		
		
	}
	
	public static List<Game> leeGame(String fichero){
		List<Game> res = new ArrayList<Game>();
		List<String> lineas = Ficheros.leeFichero("Error", fichero);
		if (lineas.size()>1) {
			lineas.remove(0);
			for (String linea : lineas) {
				Game juego = parseGame(linea);
				res.add(juego);
			}
		}
		return res;
	}
	
}
