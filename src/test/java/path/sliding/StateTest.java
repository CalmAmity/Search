package path.sliding;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Point;
import util.TestUtils;

public class StateTest {
	private static Logger log = LoggerFactory.getLogger(StateTest.class);
	
	@Test
	public void copy() {
		State state1 = new State(5, 5);
		State state2 = new State(state1);
		Assert.assertEquals(state1, state2);
		State state3 = state2.randomMove();
		// Check that state2 has not been affected by the copy action.
		Assert.assertEquals(state1, state2);
		Assert.assertNotEquals(state2, state3);
		
		// Thoroughly randomise a state.
		for (int step = 0; step < 999; step++) {
			state3 = state3.randomMove();
		}
		// Check that copying still works for a non-starting state.
		State state4 = new State(state3);
		Assert.assertEquals(state3, state4);
	}
	
	/** Creates a randomised state and tests that the result is properly randomised. */
	@Test
	public void constructRandom() {
		State baseState = new State(4, 4);
		State randomisedState = State.createRandomisedState(4, 4, 99);
		Assert.assertNotEquals(baseState, randomisedState);
	}
	
	/** Creates a 5x5 start state and randomises it. Checks after every step that the state has been changed. Every random move is logged at DEBUG level. */
	@Test
	public void randomMove() {
		State stateBeforeMove;
		State stateAfterMove = new State(4, 4);
		for (int iteration = 0; iteration < 100; iteration++) {
			stateBeforeMove = stateAfterMove;
			stateAfterMove = stateBeforeMove.randomMove();
			log.debug("\n{}", stateAfterMove.toString());
			Assert.assertNotEquals(stateBeforeMove, stateAfterMove);
		}
	}
	
	@Test
	public void determineAvailableMoves() {
		// Construct a 3x3 start state.
		final State state = new State(3, 3);
		// Check that only 'up' and 'left' are legal moves.
		state.performMove(Move.SLIDE_UP);
		state.performMove(Move.SLIDE_LEFT);
		TestUtils.executeMethodCallExpectException(() -> state.performMove(Move.SLIDE_DOWN), IndexOutOfBoundsException.class);
		TestUtils.executeMethodCallExpectException(() -> state.performMove(Move.SLIDE_RIGHT), IndexOutOfBoundsException.class);
		// Check that determinePossibleMoves() agrees.
		List<Move> availableMoves = state.determinePossibleMoves();
		Assert.assertTrue(availableMoves.contains(Move.SLIDE_UP));
		Assert.assertTrue(availableMoves.contains(Move.SLIDE_LEFT));
		Assert.assertFalse(availableMoves.contains(Move.SLIDE_DOWN));
		Assert.assertFalse(availableMoves.contains(Move.SLIDE_RIGHT));

		// Perform the 'left' move.
		final State state2 = state.performMove(Move.SLIDE_LEFT);
		// Check that 'right' has now joined the available moves.
		state2.performMove(Move.SLIDE_UP);
		state2.performMove(Move.SLIDE_LEFT);
		state2.performMove(Move.SLIDE_RIGHT);
		TestUtils.executeMethodCallExpectException(() -> state2.performMove(Move.SLIDE_DOWN), IndexOutOfBoundsException.class);
		// Check that determinePossibleMoves() agrees.
		availableMoves = state2.determinePossibleMoves();
		Assert.assertTrue(availableMoves.contains(Move.SLIDE_UP));
		Assert.assertTrue(availableMoves.contains(Move.SLIDE_LEFT));
		Assert.assertFalse(availableMoves.contains(Move.SLIDE_DOWN));
		Assert.assertTrue(availableMoves.contains(Move.SLIDE_RIGHT));

		// Perform the 'up' move.
		final State state3 = state2.performMove(Move.SLIDE_UP);
		// Check that all moves are now available.
		state3.performMove(Move.SLIDE_UP);
		state3.performMove(Move.SLIDE_LEFT);
		state3.performMove(Move.SLIDE_RIGHT);
		state3.performMove(Move.SLIDE_DOWN);
		// Check that determinePossibleMoves() agrees.
		availableMoves = state3.determinePossibleMoves();
		Assert.assertTrue(availableMoves.contains(Move.SLIDE_UP));
		Assert.assertTrue(availableMoves.contains(Move.SLIDE_LEFT));
		Assert.assertTrue(availableMoves.contains(Move.SLIDE_DOWN));
		Assert.assertTrue(availableMoves.contains(Move.SLIDE_RIGHT));

		// Perform the 'left' move.
		final State state4 = state3.performMove(Move.SLIDE_LEFT);
		// Check that 'left' is no longer available.
		state4.performMove(Move.SLIDE_UP);
		state4.performMove(Move.SLIDE_RIGHT);
		state4.performMove(Move.SLIDE_DOWN);
		TestUtils.executeMethodCallExpectException(() -> state4.performMove(Move.SLIDE_LEFT), IndexOutOfBoundsException.class);
		// Check that determinePossibleMoves() agrees.
		availableMoves = state4.determinePossibleMoves();
		Assert.assertTrue(availableMoves.contains(Move.SLIDE_UP));
		Assert.assertFalse(availableMoves.contains(Move.SLIDE_LEFT));
		Assert.assertTrue(availableMoves.contains(Move.SLIDE_DOWN));
		Assert.assertTrue(availableMoves.contains(Move.SLIDE_RIGHT));
	}
	
	/** Tests both search directions ({@link State#findTileAt(int, int)} and {@link State#findTilePosition(Integer)}). */
	@Test
	public void findTiles() {
		State state = new State(4, 2);
		Assert.assertEquals(Integer.valueOf(3), state.findTileAt(3, 0));
		Assert.assertEquals(Integer.valueOf(6), state.findTileAt(2, 1));
		Assert.assertEquals(new Point.IntegerPoint(1, 0), state.findTilePosition(1));
		state = state.performMove(Move.SLIDE_UP);
		Assert.assertEquals(Integer.valueOf(0), state.findTileAt(0, 1));
		Assert.assertEquals(new Point.IntegerPoint(0, 0), state.findTilePosition(4));
		Assert.assertEquals(new Point.IntegerPoint(3, 1), state.findTilePosition(7));
	}
	
	@Test
	public void isGoalState() {
		State state = new State(3, 3);
		Assert.assertTrue(state.isGoalState());
		state = state.performMove(Move.SLIDE_UP);
		Assert.assertFalse(state.isGoalState());
		state = state.performMove(Move.SLIDE_LEFT);
		Assert.assertFalse(state.isGoalState());
		state = state.performMove(Move.SLIDE_RIGHT);
		Assert.assertFalse(state.isGoalState());
		state = state.performMove(Move.SLIDE_DOWN);
		Assert.assertTrue(state.isGoalState());
	}
}