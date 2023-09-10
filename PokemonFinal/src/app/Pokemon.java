package app;

import java.util.ArrayList;
import java.util.List;

//This is the parent class that gets extended from. The classes that extend it are PokemonBasic, PokemonLegendary, and PokemonMythical. In the original game these classifications seperate basic pokemon from one of a kind legendary pokemon, and one of a kind event mythical pokemon.
public class Pokemon {
    protected String name;
    private PokemonType pokemontype;
    private PokemonType secondaryType;
    private int level;
    private int maxHP;
    private int currentHP;
    private int attack;
    private int defense;
    private List<Move> moves;

    //This is the constructor that is going to be overloaded.
    public Pokemon(String name, PokemonType pokemontype, int level, int maxHP, int attack, int defense) {
        this.name = name;
        this.pokemontype = pokemontype;
        this.level = level;
        this.maxHP = maxHP;
        this.currentHP = maxHP;
        this.attack = attack;
        this.defense = defense;
        this.moves = new ArrayList<>();
    }

    //Here it is being overloaded. In the original game, pokemon can have either on or two types.
    public Pokemon(String name, PokemonType primaryType, PokemonType secondaryType, int level, int maxHP, int attack, int defense) {
        this.name = name;
        this.pokemontype = primaryType;
        this.secondaryType = secondaryType;
        this.level = level;
        this.maxHP = maxHP;
        this.currentHP = maxHP;
        this.attack = attack;
        this.defense = defense;
        this.moves = new ArrayList<>();
    }

    public void setName(String name) throws NameTooLongException {
        if (name.length() <= 12) {
            this.name = name;
        } else {
            throw new NameTooLongException("Name is too long, must be 12 characters or less.");
        }
    }
    
    public String getName() {
        return name;
    }

    public PokemonType getType() {
        return pokemontype;
    }

    public int getLevel() {
        return level;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public int getCurrentHP() {
        return currentHP;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public void learnMove(Move move) {
        if (moves.size() < 4) {
            moves.add(move);
        } else {
            // Handle case where the Pokemon already knows four moves
        }
    }

    public boolean isAlive() {
        return currentHP > 0;
    }

    public void takeDamage(int damage) {
        currentHP -= damage;
        if (currentHP < 0) {
            currentHP = 0;
        }
    }

    public int getMoveCount() {
        return moves.size();
    }

    public Move getMove(int index) {
        if (index >= 0 && index < moves.size()) {
            return moves.get(index);
        } else {
            // Handle invalid index in case move is not there.
            return null;
        }
    }
}
