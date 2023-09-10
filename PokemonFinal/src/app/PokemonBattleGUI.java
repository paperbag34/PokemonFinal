package app;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineEvent.Type;

import javax.swing.*;

public class PokemonBattleGUI extends JFrame implements Serializable {

	private static final long serialVersionUID = 1L;
	private Pokemon playerPokemon;
	private Pokemon opponentPokemon;
	//Heres all the jframe elements used. The battle log uses a jtext area further down
	private JButton[] moveButtons;
	private JButton btn;
	private JTextArea battleLog;
	private JFrame nameFrame;
	private boolean playerWon = false;
	private boolean opponentWon = false;
	private boolean thanksMessageShown = false;
	private int battleCount = 0;
	
	public PokemonBattleGUI() {
		
		loadBattleCount();		
		
		// Initialize player and opponent Pokémon
		playerPokemon = new PokemonBasic("Bulbasaur", new TypeGrass(), new TypePoison(), 25, 50, 10, 5);
		opponentPokemon = new PokemonBasic("Charmander", new TypeFire(), null, 20, 40, 12, 6);

		if (playerPokemon != null) {
			setNameForPlayerPokemon();
		}

		Move move1 = new Move("Vine Whip", new TypeGrass(), 45, 100);
		Move move2 = new Move("Tackle", new TypeNormal(), 40, 100);
		Move move3 = new Move("Leech Seed", new TypeGrass(), 20, 90);
		Move move4 = new Move("Growl", new TypeNormal(), 0, 100);

		// Teach moves to playerPokemon
		playerPokemon.learnMove(move1);
		playerPokemon.learnMove(move2);
		playerPokemon.learnMove(move3);
		playerPokemon.learnMove(move4);

		// Create Move objects for opponentPokemon
		Move move5 = new Move("Ember", new TypeFire(), 40, 100);
		Move move6 = new Move("Scratch", new TypeNormal(), 40, 100);
		Move move7 = new Move("SmokeScreen", new TypeNormal(), 0, 100);
		Move move8 = new Move("Dragon Rage", new TypeDragon(), 80, 100);

		// Teach moves to opponentPokemon.
		opponentPokemon.learnMove(move5);
		opponentPokemon.learnMove(move6);
		opponentPokemon.learnMove(move7);
		opponentPokemon.learnMove(move8);


		//Here is the animation used
		// show Pokemon
		JLabel pkm001Label = new JLabel();
		ImageIcon pkm001Icon = new ImageIcon("C:\\Users\\Xavier\\eclipse-workspace\\GUIPokemon\\src\\app\\bulbasaurBack.gif");
		pkm001Label.setIcon(pkm001Icon);

		JLabel pkm004Label = new JLabel();
		ImageIcon pkm004Icon = new ImageIcon(
				"C:\\Users\\Xavier\\eclipse-workspace\\GUIPokemon\\src\\app\\charmander.gif");
		pkm004Label.setIcon(pkm004Icon);

		JPanel pokemonPanel = new JPanel(new GridLayout(1, 2)); // 1 row, 2 columns
		pokemonPanel.add(pkm001Label); // Add the first Pokemon image
		pokemonPanel.add(pkm004Label); // Add the second Pokemon image

		// Create and configure the main window
		setTitle("Pokemon Battle");
		setSize(1000, 700);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		// Create a panel for move buttons
		JPanel buttonPanel = new JPanel(new GridLayout(2, 2));
		moveButtons = new JButton[4];
		for (int i = 0; i < 4; i++) {
			moveButtons[i] = new JButton(playerPokemon.getMove(i).getName());
			moveButtons[i].addActionListener(new MoveButtonListener(i));
			buttonPanel.add(moveButtons[i]);

			//This is the part of the overloaded method. I used it in order to give a thank you message when the game is closed.
			setDefaultCloseOperation(DO_NOTHING_ON_CLOSE); // Prevent the default close operation

			addWindowListener(new java.awt.event.WindowAdapter() {
				@Override
				public void windowClosing(java.awt.event.WindowEvent windowEvent) {
					if (!thanksMessageShown) {
						showThanksForPlayingMessage();
						thanksMessageShown = true;
					}
				}
			});

		}
		
		
		//Create a jmenu
		JMenu menu = new JMenu("Menu");

	    JMenuItem battleCountItem = new JMenuItem("Show Battle Count");
	    battleCountItem.addActionListener(e -> showBattleCount());

	    menu.add(battleCountItem);
	    JMenuBar menuBar = new JMenuBar();
	    menuBar.add(menu);
	    setJMenuBar(menuBar);
		
		

		// Create a battle log text area
		battleLog = new JTextArea("Welcome to the world of pokemon!                   ");
		battleLog.setEditable(false);
		JScrollPane logScrollPane = new JScrollPane(battleLog);

		//Here is the layout manager used.
		// Add components to the main window
		add(buttonPanel, BorderLayout.SOUTH);
		add(logScrollPane, BorderLayout.WEST);

		add(pokemonPanel, BorderLayout.CENTER);

	}
	
	
	
	private void showBattleCount() {
	    JFrame battleCountFrame = new JFrame("Battle Count");
	    JLabel countLabel = new JLabel("Total Battles: " + battleCount);
	    battleCountFrame.add(countLabel);
	    battleCountFrame.setSize(300, 100);
	    battleCountFrame.setLocationRelativeTo(this);
	    battleCountFrame.setVisible(true);
	}
	
	
	

	private void showThanksForPlayingMessage() {
		// Show a new JFrame with a "Thanks for playing" message
		JFrame thanksFrame = new JFrame("Thanks for playing");
		JLabel messageLabel = new JLabel("Thanks for playing!");
		thanksFrame.add(messageLabel);
		thanksFrame.setSize(300, 100);
		thanksFrame.setLocationRelativeTo(null); // Center the new JFrame
		thanksFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close only the "Thanks for playing" frame

		// Add a window listener to close the main JFrame when the "Thanks for playing" frame is closed
		thanksFrame.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				dispose(); // Close the main JFrame
			}
		});

		thanksFrame.setVisible(true);
	}


	private void setNameForPlayerPokemon() {
	    if (!SwingUtilities.isEventDispatchThread()) {
	        SwingUtilities.invokeLater(this::setNameForPlayerPokemon);
	        return;
	    }

	    JTextField nameField = new JTextField(15);
	    JButton confirmButton = new JButton("Confirm");

	    confirmButton.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            String playerName = nameField.getText();
	            if (playerPokemon == null) {
	                // Initialize playerPokemon if it's null
	                playerPokemon = new PokemonBasic("DefaultName", new TypeNormal(), null, 1, 10, 1, 1);
	            }

	            try {
	                playerPokemon.setName(playerName); // Set the name using the setter method
	                // Close the name frame
	                nameFrame.dispose();
	            } catch (NameTooLongException ex) {
	                // Handle the exception here, e.g., show an error message to the user
	                JOptionPane.showMessageDialog(nameFrame, "Name exceeds the maximum length of 12 characters.", "Invalid Name", JOptionPane.ERROR_MESSAGE);
	            }
	        }
	    });

	    nameFrame = new JFrame("Enter Pokémon's Name");
	    nameFrame.setLayout(new FlowLayout());
	    nameFrame.add(new JLabel("Enter Pokémon's Name: "));
	    nameFrame.add(nameField);
	    nameFrame.add(confirmButton);
	    nameFrame.pack();
	    nameFrame.setLocationRelativeTo(null);
	    nameFrame.setVisible(true);
	}

	
	
	private class MoveButtonListener implements ActionListener {
		private int moveIndex;

		public MoveButtonListener(int moveIndex) {
			this.moveIndex = moveIndex;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			   // Check if the game has already been won by the player or opponent
	        if (playerWon || opponentWon) {
	            // If the game has been won, do nothing and return
	            return;
	        }
			
			
			// Player's turn
			Move playerMove = playerPokemon.getMove(moveIndex);
			String playersPokemonName = playerPokemon.getName(); // Get the player's Pokémon name
			battleLog.append(playersPokemonName + " uses " + playerMove.getName() + "\n");

			// Calculate damage and apply it to the opponent
			int damageToOpponent = calculateDamage(playerMove, opponentPokemon);
			opponentPokemon.takeDamage(damageToOpponent);
			battleLog.append("Opponent's " + opponentPokemon.getName() + " takes " + damageToOpponent + " damage\n");

			// Opponent's turn (AI)
			int opponentMoveIndex = (int) (Math.random() * opponentPokemon.getMoveCount());
			Move opponentMove = opponentPokemon.getMove(opponentMoveIndex);
			battleLog.append("Opponent's " + opponentPokemon.getName() + " uses " + opponentMove.getName() + "\n");

			// Calculate damage and apply it to the player's Pokémon
			int damageToPlayer = calculateDamage(opponentMove, playerPokemon);
			playerPokemon.takeDamage(damageToPlayer);
			battleLog.append(playersPokemonName + " takes " + damageToPlayer + " damage\n");

			// Check if the opponent fainted
			if (!opponentPokemon.isAlive()) {
				battleLog.append("Opponent's " + opponentPokemon.getName() + " fainted!\n");
				playerWon = true;
				
				displayGameResult();
				return; // End the turn since the opponent fainted
			} else if (!playerPokemon.isAlive()) {
				battleLog.append(playersPokemonName + " fainted!\n");
				opponentWon = true;
			
				displayGameResult();
				return; // End the turn since the player fainted
			}

		}
	}

	
	//In order to read/write to file, the game keeps track of how many time the player has battled using the application. This can be checked in the menu.
	private void saveBattleCount() {
	    try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("C:\\Users\\Xavier\\git\\ImprovedPokemonGUI\\GUIPokemon\\src\\app\\SaveData"))) {
	        outputStream.writeInt(battleCount);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	private void loadBattleCount() {
	    try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("C:\\Users\\Xavier\\git\\ImprovedPokemonGUI\\GUIPokemon\\src\\app\\SaveData"))) {
	        battleCount = inputStream.readInt();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	private void displayGameResult() {
	    if (playerWon) {
	        battleLog.append("Congratulations! You won the battle!\n");
	    } else if (opponentWon) {
	        battleLog.append("Oops! You lost the battle.\n");
	    }
	    battleCount++;
	    saveBattleCount(); // Save the updated battle count
	    // Add additional logic here for what should happen after a game ends
	}

	private int calculateDamage(Move move, Pokemon target) {
		double effectiveness = getTypeEffectiveness(move.getType(), target.getType());

		// Calculate damage using a simplified formula
		int damage = (int) (((2 * playerPokemon.getLevel() + 10) / 250.0)
				* (playerPokemon.getAttack() / target.getDefense() * move.getPower() + 2) * effectiveness);

		// Add some random variation to damage, move hit differently in the real game.
		// This is to simulate that.
		double randomFactor = Math.random() * 0.3 + 0.85; // Between 0.85 and 1.0
		damage = (int) (damage * randomFactor);

		return damage;
	}

	private double getTypeEffectiveness(app.PokemonType type, app.PokemonType type2) {
		// The type effectiveness is neutral at 1, Full type advantage chart can be
		// added later.
		return 1.0;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			PokemonBattleGUI game = new PokemonBattleGUI();
			game.setVisible(true); // Show the main frame after setting the name
		});
	}
}