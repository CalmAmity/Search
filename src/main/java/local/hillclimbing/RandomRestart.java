package local.hillclimbing;

import java.util.function.Supplier;

import core.Heuristic;
import core.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Implements random restart hill climbing. */
public class RandomRestart<S extends State<S>> {
	private static Logger log = LoggerFactory.getLogger(RandomRestart.class);
	/** The heuristic that this algorithm will use to judge the quality of states. */
	private Heuristic<S> heuristic;
	/**
	 * The maximum number of consecutive hill-climbing runs this algorithm is allowed to make before quitting. If {@code null}, the algorithm will run until an acceptable end state
	 * has been found.
	 */
	private final Integer maximumNrIterations;
	/** The number of consecutive hill-climbing runs that have been performed, including the currently running one. */
	int nrIterationsPerformed;
	/**
	 * The acceptable margin between the quality score of an end state and the best possible score for the employed heuristic (as defined by
	 * {@link Heuristic#getBestPossibleScore()}). If the end state of an iteration has a score closer than this to the optimal score, the algorithm will stop.
	 */
	private final double qualityMargin;
	/** The maximum number of consecutive moves a single iteration of the algorithm is allowed to make among states with the same quality. */
	protected final int maximumNrPlateauMoves;
	
	public RandomRestart(Heuristic<S> heuristic, Integer maximumNrIterations, double qualityMargin, int maximumNrPlateauMoves) {
		this.heuristic = heuristic;
		this.maximumNrIterations = maximumNrIterations;
		this.qualityMargin = qualityMargin;
		this.maximumNrPlateauMoves = maximumNrPlateauMoves;
	}
	
	/**
	 * Runs the algorithm to completion.
	 * @param stateConstructor The constructor of the relevant {@code State} class that constructs a random state.
	 * @return The best state that could be found.
	 */
	public S run(Supplier<S> stateConstructor) {
		S currentEndState = null;
		nrIterationsPerformed = 0;
		do {
			if (maximumNrIterations != null && nrIterationsPerformed >= maximumNrIterations) {
				// A maximum number of iterations has been provided and has now been reached.
				break;
			}
			nrIterationsPerformed++;
			log.debug("Starting iteration #{}", nrIterationsPerformed);
			// Randomly create a start state for this iteration, and perform a steepest ascent hill climbing search starting from this state.
			SteepestAscent<S> search = new SteepestAscent<>(stateConstructor.get(), heuristic, maximumNrPlateauMoves);
			currentEndState = search.run();
		} while (currentEndState.getQualityScore() < heuristic.getBestPossibleScore() - qualityMargin);
		
		log.info("Stopped after {} iterations.", nrIterationsPerformed);
		return currentEndState;
	}
}
