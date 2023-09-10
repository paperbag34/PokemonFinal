package app;

public class PokemonLegendary extends Pokemon {
    private PokemonType primaryType;
    private PokemonType secondaryType;

    public PokemonLegendary(String name, PokemonType pokemontype, int level, int maxHP, int attack, int defense) {
    	super(name, pokemontype, pokemontype, level, maxHP, attack, defense);
        this.primaryType = primaryType;
        this.secondaryType = secondaryType;
    }

    public PokemonType getPrimaryType() {
        return primaryType;
    }

    public PokemonType getSecondaryType() {
        return secondaryType;
    }
}