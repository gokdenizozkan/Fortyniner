public class Tile {
    private final int row;
    private final int column;
    private boolean deathspot;
    private boolean revealed;
    private char visual;

    Tile(int row, int column) {
        this.row = row;
        this.column = column;
        this.visual = '-';
    }

    public boolean reveal() {
    	this.revealed = true;
        this.updateVisual();
        Board.incrementRevealedTileCount();
        if (visual == ' ') revealPlainTileChain();
        return this.deathspot;
    }

    public void revealPlainTileChain() {
    	Tile[] neighbourTiles = Radar.getNeighbourTiles(this);

        for (Tile tile : neighbourTiles) {
            if (tile == null) continue; // Null check
            if (tile.revealed == false && Radar.countNeighbouringDeathspots(tile) == 0) {
            	tile.revealed = true;
            	tile.updateVisual();
            	Board.incrementRevealedTileCount();
            	tile.revealPlainTileChain();
            }
        }
    }

    private void updateVisual() {
        char c;

        if (deathspot) { c = '*'; }
        else { c = Character.forDigit(getNeighbouringDeathspots(), 10); }

        visual = (c == '0') ? ' ' : c;
    }

    // Getters and Setters

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public int getNeighbouringDeathspots() {
        return Radar.countNeighbouringDeathspots(this);
    }

    public boolean isDeathspot() {
        return deathspot;
    }

    public void setDeathspot(boolean deathspot) {
        this.deathspot = deathspot;
    }

    public char getVisual() {
        return visual;
    }
}