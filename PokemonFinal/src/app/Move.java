package app;

public class Move {
	private String name;
	private PokemonType pokemontype;
	private int power;
	private double accuracy;

	public Move(String name, PokemonType pokemontype, int power, double accuracy) {
		this.name = name;
		this.pokemontype = pokemontype;
		this.power = power;
		this.accuracy = accuracy;
	}

	public String getName() {
		return name;
	}

	public PokemonType getType() {
		return pokemontype;
	}

	public int getPower() {
		return power;
	}

	public double getAccuracy() {
		return accuracy;
	}
}