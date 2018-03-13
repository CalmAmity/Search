package nl.calmamity.search.local.hillclimbing

import nl.calmamity.search.core.{Heuristic, State}
import org.slf4j.{Logger, LoggerFactory}

/** Implements random restart hill climbing. */
	/**
	  * The maximum number of consecutive hill-climbing runs this algorithm is allowed to make before quitting. If
	  * `null`, the algorithm will run until an acceptable end state has been found.
	  */
	/**
	  * The acceptable margin between the quality score of an end state and the best possible score for the employed
	  * heuristic. If the end state of an iteration has a score closer than this to the optimal score, the algorithm
	  * will stop.
	  */
class RandomRestart[StateImplementation <: State[StateImplementation]](
	heuristic: Heuristic[StateImplementation]
	, maximumNumPlateauMoves: Int
	, maximumNumIterations: Option[Int]
	, qualityMargin: Double
) {
	val log: Logger = LoggerFactory.getLogger(this.getClass.getSimpleName)
	
	/**
	 * Runs the algorithm to completion.
	 * @param stateConstructor The constructor of the relevant {@code State} class that constructs a random state.
	 * @return The best state that could be found.
	 */
	def run(stateConstructor: () => StateImplementation): StateImplementation = {
		var currentEndState: StateImplementation = stateConstructor.apply()
		var numIterationsPerformed = 0
		do {
			numIterationsPerformed += 1
			log.debug(s"Starting iteration #$numIterationsPerformed")
			// Randomly create a start state for this iteration, and perform a steepest ascent hill climbing search starting from this state.
			val search: SteepestAscent[StateImplementation] = new SteepestAscent[StateImplementation](stateConstructor.apply(), heuristic, maximumNumPlateauMoves)
			currentEndState = search.run()
		} while (
			// Stop iterating if the end state is within the stated quality margin of the heuristic's optimal score.
			!heuristic.determineHasOptimalScore(currentEndState, qualityMargin)
			&& !maximumNumIterations.exists(_ <= numIterationsPerformed)
		)
		
		log.info("Stopped after {} iterations.", numIterationsPerformed)
		currentEndState
	}
}
