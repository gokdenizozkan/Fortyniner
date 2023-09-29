package fortyniner;
import com.gokdenizozkan.util.Input;
import com.gokdenizozkan.util.Print;

public class GameLogic {
    // TODO FEATURE: FIRST TILE OPENING IS DEATHSPOT-FREE
	
    // Değerlendirme formu 9
    protected static void startLoop() {
    	printBoardWithDeathspotsFramed();
    	
    	while (true) {
    		printBoardFramed();
    		int[] coords = parseInput(Input.getLineEmptySafe());
    		
    		// if coords are not on the board OR parseInput failed to parse, continue
        	if (!Radar.isOnBoard(coords)) {
        		System.out.println("The coordinates you have entered are out of board or typed in an invalid format!\nTry again. (Make sure you follow this pattern: 1/2)");
        		continue;
        	}
        	
        	// Değerlendirme formu 11
        	if (checkEnd(
        			openTileAt(coords))) {
        		break;
        	};
    	}
    }
    
    private static int[] parseInput(String input) {
    	String[] coordsInStr = input.split("/");
        int[] coordsInInt = new int[coordsInStr.length];

        for (int i = 0; i < 2; i++) {
        	try {
        		coordsInInt[i] = Integer.parseInt(coordsInStr[i]) - 1;
        	}
        	catch (Exception e) {
        		return new int[] {-1, -1};
        	}
        }
        return coordsInInt;
        // TODO: OTHER POSSIBILITIES
    }
    
    // Değerlendirme formu 10
    // Değerlendirme formu 12 <- title.reveal() + proceed()
    protected static boolean openTileAt(int[] coords) {
        Tile tile = Board.getTileAt(coords);
        return tile.reveal();
    }

    // Değerlendirme formu 13 <- boolean gameOver bilgisi openTileAt() metodunda
    // tile.reveal() 'ın çağrısı ile elde edilir.
    // Değerlendirme formu 14
    protected static boolean checkEnd(boolean gameOver) {
        if (gameOver) {
        	end(false);
        	return true;
        }
        else if (Board.isBoardCleared()) {
        	end(true);
        	return true;
        }
        else {
        	return false;
        }
    }
    
    // Değerlendirme formu 15
    private static void end(boolean won) {
    	printBoardWithDeathspotsFramed();

        String message;
        if (won) message = "Board cleared, you win.";
        else message = "Could not see the end.";

        System.out.println(message);
    }
    
    // PRINTS
    
    private static void printBoardFramed() {
    	System.out.println();
    	
        int row = Board.getRowCount();
        int column = Board.getColumnCount();

        printFrameColumns(column);

        for (int r = 0; r < row; r++) {
            System.out.print((r + 1) + " "); // Prints frame of Rows for user

            for (int c = 0; c < column; c++) {
                // Additional space to preserve the shape of the board
                System.out.print(" " + Board.getTileAt(r, c).getVisual());
            }
            System.out.println();
        }
    }
    
    private static void printBoardWithDeathspotsFramed() {
        int row = Board.getRowCount();
        int column = Board.getColumnCount();

        printFrameColumns(column);

        for (int r = 0; r < row; r++) {
            System.out.print((r + 1) + " "); // Prints frame of Rows for user

            for (int c = 0; c < column; c++) {
                // Additional space to preserve the shape of the board
                System.out.print(" " + (Board.getTileAt(r, c).isDeathspot() ? "*" : Board.getTileAt(r, c).getVisual()));
            }
            System.out.println();
        }
    }
    
    private static void printFrameColumns(int columnCount) {
        Print.lineOfChar(' ', 2, 0);

        for (int c = 0; c < columnCount; c++) {
            System.out.print(" ");
            System.out.print(c + 1);
        }
        System.out.println();
    }
    
    public static void printTutorial() {
        Print.title("TUTORIAL");
        System.out.print("To open a tile, type its coordinates with a forward-slash, like: 2/3\nIf you enter 2/3, it will open the tile at 2nd row and 3rd column.\n");
    }
}