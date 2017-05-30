package path.sliding;

/**
 * Defines the possible moves in a sliding puzzle. In any state, at most one tile can slide in a given direction, which means that only the direction is required to define the
 * move.
 */
public enum Move {
	SLIDE_UP(0, 1),
	SLIDE_DOWN(0, -1),
	SLIDE_LEFT(1, 0),
	SLIDE_RIGHT(-1, 0);
	
	/** The relative position along the X axis of the empty space after this move is performed. */
	public final int relPosX;
	/** The relative position along the Y axis of the empty space after this move is performed. */
	public final int relPosY;
	
	Move(int x, int y) {
		relPosX = x;
		relPosY = y;
	}
}
