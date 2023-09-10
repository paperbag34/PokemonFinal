package app;

public class PokemonBasic extends Pokemon {
    private PokemonType primaryType;
    private PokemonType secondaryType;

    public PokemonBasic(String name, PokemonType primaryType, PokemonType secondaryType, int level, int maxHP, int attack, int defense) {
        super(name, primaryType, level, maxHP, attack, defense);
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