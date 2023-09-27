import com.gokdenizozkan.util.Input;
import com.gokdenizozkan.util.ModifyString;
import com.gokdenizozkan.util.Print;

public class GameManager {
    // BUG: DEATHSPOTMAP GENERATOR RANDOMIZER IS BOUND TO CLOCK
    // TODO: NINER => AVOID DEEP WATERS AND SPLINTERS THAT MAKE YOU FALL INTO THE WATER
    // TODO: FORTYNINER => CLASSIC MINESWEEPER (GOLD HUNTER)
    // TODO FEATURE: MARKING DEATHSPOTS: *1-1
    static Gamemodes gamemode;

    public static void launch() {
    	Print.title("FORTYNINER");
        if (Input.ask("A text-based game inspired by Minesweeper.", "Play", "Exit") == "Play") setup();
    }

    private static void setup() {
    	Print.title("SETUP");
    	gamemode = Input.ask("Please choose your gamemode:", Gamemodes.values());

        new Board(gamemode.getRatio());

        Print.title("SETUP: Difficulty");
        Board.setDifficulty(
        		Input.ask("Please enter the difficulty level you desire to play:", Board.Difficulties.values()));

        Board.placeDeathspots();
        GameLogic.printTutorial();
        
        Print.title("HAVE FUN!");
        Input.await();
        GameLogic.startLoop();
    }

    private static int[] newRatio() {
    	Print.title("SETUP: Ratio");
        int rows = Input.getInt("Please enter the number of rows the game board will generate:");
        int columns = Input.getInt("Please enter the number of columns the game board will generate:");

        return new int[]{rows, columns};
    }
    
    enum Gamemodes {
        NINER, FORTYNINER, CUSTOM;

        protected int[] getRatio() {
            return switch (name()) {
                case "NINER" -> new int[]{9, 9};
                case "FORTYNINER" -> new int[]{40, 9};
                default -> newRatio();
            };
        }

        protected String getName() {
            return ModifyString.capitalize(toString());
        }

    }
}
