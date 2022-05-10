package aiss.model;


import java.util.Set;

public class Game {

	@Override
	public String toString() {
		return "Game [console=" + console + ", name=" + name + ", review=" + review + ", score=" + score + "]";
	}

	private String id;
	private Set<String> console;
	private String name;
	private EnumRev review;
	private Integer score;
	
	public Game(Set<String> console, String name, EnumRev review, Integer score) {
		super();
		this.console = console;
		this.name = name;
		this.review = review;
		this.score = score;
	}

	public Game() {}
	
	public Game(String name) {
		this.name = name;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<String> getConsole() {
		return console;
	}

	public void setConsole(Set<String> console) {
		this.console = console;
	}

	public EnumRev getReview() {
		return review;
	}

	public void setReview(EnumRev review) {
		this.review = review;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}
	
	

}
