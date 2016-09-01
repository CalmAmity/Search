package sliding;

public enum Action
{
	SLIDE_UP(0, 1),
	SLIDE_DOWN(0, -1),
	SLIDE_LEFT(1, 0),
	SLIDE_RIGHT(-1, 0);
	
	int relPosX;
	int relPosY;
	
	Action(int x, int y)
	{
		relPosX = x;
		relPosY = y;
	}
}
