package fortyniner;
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

    protected boolean reveal() {
    	this.revealed = true;
        this.updateVisual();
        Board.incrementRevealedTileCount();
        if (visual == ' ') revealPlainTileChain();
        return this.deathspot;
    }

    private void revealPlainTileChain() {
    	Tile[] neighbourTiles = Radar.getNeighbourTiles(this);

        for (Tile tile : neighbourTiles) {
            if (tile == null) continue; // Null check
            if (tile.revealed) continue;
            else {
            	tile.revealed = true;
            	tile.updateVisual();
            	Board.incrementRevealedTileCount();
            	if (Radar.countNeighbouringDeathspots(tile) == 0) tile.revealPlainTileChain();
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

    protected int getRow() {
        return row;
    }

    protected int getColumn() {
        return column;
    }

    protected int getNeighbouringDeathspots() {
        return Radar.countNeighbouringDeathspots(this);
    }

    protected boolean isDeathspot() {
        return deathspot;
    }

    protected void setDeathspot(boolean deathspot) {
        this.deathspot = deathspot;
    }

    protected char getVisual() {
        return visual;
    }
}