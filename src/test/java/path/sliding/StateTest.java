package path.sliding;

import nl.calmamity.search.path.sliding.Move;
import nl.calmamity.search.path.sliding.State;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.Enumeration;
import scala.collection.immutable.Set;
import util.Point;
import util.TestUtils;

public class StateTest {
	private static Logger log = LoggerFactory.getLogger(StateTest.class);
	
	@Test
	public void equals() {
		State state1 = State.apply(5, 5);
		State state2 = State.apply(5, 5);
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
//		State state4 = State.apply(state3.tiles(), scala.Option.apply(state3), 0);
//		Assert.assertEquals(state3, state4);
	}
	
	/** Creates a randomised state and tests that the result is properly randomised. */
	@Test
	public void constructRandom() {
		State baseState = State.apply(4, 4);
		State randomisedState = State.apply(4, 4, 99);
		Assert.assertNotEquals(baseState, randomisedState);
	}
	
	/** Creates a 5x5 start state and randomises it. Checks after every step that the state has been changed. Every random move is logged at DEBUG level. */
	@Test
	public void randomMove() {
		State stateBeforeMove;
		State stateAfterMove = State.apply(4, 4);
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
		final State state = State.apply(3, 3);
		// Check that only 'up' and 'left' are legal moves.
		state.performMove(Move.SlideUp());
		state.performMove(Move.SlideLeft());
		TestUtils.executeMethodCallExpectException(() -> state.performMove(Move.SlideDown()), IndexOutOfBoundsException.class);
		TestUtils.executeMethodCallExpectException(() -> state.performMove(Move.SlideRight()), IndexOutOfBoundsException.class);
		// Check that determinePossibleMoves() agrees.
		Set<Enumeration.Value> availableMoves = state.determinePossibleMoves();
		Assert.assertTrue(availableMoves.contains(Move.SlideUp()));
		Assert.assertTrue(availableMoves.contains(Move.SlideLeft()));
		Assert.assertFalse(availableMoves.contains(Move.SlideDown()));
		Assert.assertFalse(availableMoves.contains(Move.SlideRight()));

		// Perform the 'left' move.
		final State state2 = state.performMove(Move.SlideLeft());
		// Check that 'right' has now joined the available moves.
		state2.performMove(Move.SlideUp());
		state2.performMove(Move.SlideLeft());
		state2.performMove(Move.SlideRight());
		TestUtils.executeMethodCallExpectException(() -> state2.performMove(Move.SlideDown()), IndexOutOfBoundsException.class);
		// Check that determinePossibleMoves() agrees.
		availableMoves = state2.determinePossibleMoves();
		Assert.assertTrue(availableMoves.contains(Move.SlideUp()));
		Assert.assertTrue(availableMoves.contains(Move.SlideLeft()));
		Assert.assertFalse(availableMoves.contains(Move.SlideDown()));
		Assert.assertTrue(availableMoves.contains(Move.SlideRight()));

		// Perform the 'up' move.
		final State state3 = state2.performMove(Move.SlideUp());
		// Check that all moves are now available.
		state3.performMove(Move.SlideUp());
		state3.performMove(Move.SlideLeft());
		state3.performMove(Move.SlideRight());
		state3.performMove(Move.SlideDown());
		// Check that determinePossibleMoves() agrees.
		availableMoves = state3.determinePossibleMoves();
		Assert.assertTrue(availableMoves.contains(Move.SlideUp()));
		Assert.assertTrue(availableMoves.contains(Move.SlideLeft()));
		Assert.assertTrue(availableMoves.contains(Move.SlideDown()));
		Assert.assertTrue(availableMoves.contains(Move.SlideRight()));

		// Perform the 'left' move.
		final State state4 = state3.performMove(Move.SlideLeft());
		// Check that 'left' is no longer available.
		state4.performMove(Move.SlideUp());
		state4.performMove(Move.SlideRight());
		state4.performMove(Move.SlideDown());
		TestUtils.executeMethodCallExpectException(() -> state4.performMove(Move.SlideLeft()), IndexOutOfBoundsException.class);
		// Check that determinePossibleMoves() agrees.
		availableMoves = state4.determinePossibleMoves();
		Assert.assertTrue(availableMoves.contains(Move.SlideUp()));
		Assert.assertFalse(availableMoves.contains(Move.SlideLeft()));
		Assert.assertTrue(availableMoves.contains(Move.SlideDown()));
		Assert.assertTrue(availableMoves.contains(Move.SlideRight()));
	}
	
	/** Tests both search directions ({@link State#findTileAt(int, int)} and {@link State#findTilePosition(Integer)}). */
	@Test
	public void findTiles() {
		State state = State.apply(4, 2);
		Assert.assertEquals(3, state.findTileAt(3, 0));
		Assert.assertEquals(6, state.findTileAt(2, 1));
//		Assert.assertEquals(new Point.IntegerPoint(1, 0), state.findTilePosition(1));
		state = state.performMove(Move.SlideUp());
		Assert.assertEquals(0, state.findTileAt(0, 1));
//		Assert.assertEquals(new Point.IntegerPoint(0, 0), state.findTilePosition(4));
//		Assert.assertEquals(new Point.IntegerPoint(3, 1), state.findTilePosition(7));
	}
	
	@Test
	public void isGoalState() {
		State state = State.apply(3, 3);
		Assert.assertTrue(state.isGoalState());
		state = state.performMove(Move.SlideUp());
		Assert.assertFalse(state.isGoalState());
		state = state.performMove(Move.SlideLeft());
		Assert.assertFalse(state.isGoalState());
		state = state.performMove(Move.SlideRight());
		Assert.assertFalse(state.isGoalState());
		state = state.performMove(Move.SlideDown());
		Assert.assertTrue(state.isGoalState());
	}
}