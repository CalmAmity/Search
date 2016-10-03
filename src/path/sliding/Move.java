package path.sliding;

public enum Move {
	SLIDE_UP(0, 1),
	SLIDE_DOWN(0, -1),
	SLIDE_LEFT(1, 0),
	SLIDE_RIGHT(-1, 0);
	
	public final int relPosX;
	public final int relPosY;
	
	Move(int x, int y) {
		relPosX = x;
		relPosY = y;
	}
}
