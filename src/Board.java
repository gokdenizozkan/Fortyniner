import com.gokdenizozkan.util.ModifyString;
import com.gokdenizozkan.util.Tengri;

public class Board {
    private static Tile[][] board;
    private static int rowCount;
    private static int columnCount;
    private static int tileCount;
    private static int revealedTileCount;
    private static int deathspotCount;
    private static Difficulties difficulty;

    public Board(int[] ratio, boolean initializeWithDeathspots, Difficulties difficulty) {
        reset();
        Board.rowCount = ratio[0];
        Board.columnCount = ratio[1];
        Board.tileCount = rowCount * columnCount;
        Board.difficulty = difficulty;

        generateBoard(initializeWithDeathspots);
    }

    public Board(int[] ratio, boolean initializeWithDeathspots) {
        new Board(ratio, initializeWithDeathspots, Difficulties.NORMAL);
    }

    public Board(int[] ratio) {
        new Board(ratio, false, Difficulties.NORMAL);
    }

    private static void reset() {
        board = null;
    }

    // Management and tracking methods
    protected static boolean isBoardCleared() {
        return (tileCount - deathspotCount) == revealedTileCount;
    }

    protected static void incrementRevealedTileCount() {
        revealedTileCount++;
    }

    // Generation methods
    protected static void generateBoard(boolean initializeWithDeathspots) {
    	// init board
    	board = new Tile[rowCount][columnCount];
    	
    	// place tiles
        for (int r = 0; r < rowCount; r++) {
            for (int c = 0; c < columnCount; c++) {
                board[r][c] = new Tile(r, c);
            }
        }
        
        // place deathspots if it is asked for
        if (initializeWithDeathspots) placeDeathspots();
    }

    /**
     * Places death spots onto the board.
     */
    // Değerlendirme Formu 8: Yerleştirilen ölüm noktası sayısı kullanıcı tarafından belirlenen zorluk düzeyine bağlı.
    // Zorluk düzeyi "NORMAL" ise oyun tahtasındaki toplam kare miktarı 4'e bölünür ve elde edilen miktar kadar ölüm noktası yerleştirilir.
    protected static void placeDeathspots() {
        deathspotCount = tileCount / difficulty.getValue(); // calculate deathspotCount
        int deathspotsPlaced = 0;
        
        while (deathspotsPlaced < deathspotCount) {
        	// get a random tile coord to access it later on
        	int row = Tengri.range(rowCount);
        	int col = Tengri.range(columnCount);
        	
        	// if the randomly accessed tile is a deathspot, continue
        	if (getTileAt(row, col).isDeathspot()) continue;
        	// if not, make it a deathspot
        	else {
        		getTileAt(row, col).setDeathspot(true);
        		deathspotsPlaced++;
        	}
        }
    }

    // Access methods

    protected static Tile getTileAt(int row, int column) {
        return board[row][column];
    }

    protected static Tile getTileAt(int[] coords) {
        return getTileAt(
                Radar.getCoordRow(coords), Radar.getCoordColumn(coords));
    }

    protected static Tile[] getTilesInBulk(int[][] coords) {
        Tile[] tiles = new Tile[coords.length];

        for (int i = 0; i < coords.length; i++) {
            if (!Radar.isOnBoard(coords[i])) {
                continue;
            }
            tiles[i] = getTileAt(coords[i]);
        }

        return tiles;
    }

    // Getters and Setters
    protected static int getRowCount() {
        return rowCount;
    }

    protected static int getColumnCount() {
        return columnCount;
    }

    protected static void setDifficulty(Difficulties difficulty) {
        Board.difficulty = difficulty;
    }

    protected enum Difficulties {
        EASY, NORMAL, HARD, EXTREME;
        private static final int BASE_ORDINAL = 5;

        protected int getValue() {
            return BASE_ORDINAL - ordinal();
        }

        public String getName() {
            return ModifyString.capitalize(toString());
        }
    }
}