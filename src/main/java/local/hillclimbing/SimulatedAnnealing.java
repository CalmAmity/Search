package local.hillclimbing;

import java.util.List;
import java.util.Random;

import core.Heuristic;
import core.State;

/**
 * Implements the simulated annealing variant of hill-climbing: prefer quality-improving moves, but allow random quality-decreasing moves. Slowly lower the tolerance for these
 * quality-decreasing moves until it hits zero, turning this algorithm into first choice hill-climbing.
 */
public class SimulatedAnnealing<S extends State<S>> extends AbstractHillClimbing<S> {
	/** The virtual temperature of the search space, which is being annealed. The higher the temperature, the more random the movement through the search space. */
	protected double temperature;
	/** The rate at which the temperature falls. */
	protected final double coolingRate;
	
	public SimulatedAnnealing(S startState, Heuristic<S> heuristic, double coolingRate) {
		super(startState, heuristic, 0);
		this.coolingRate = coolingRate;
		temperature = 1;
		allowDownhillMoves = true;
	}
	
	@Override
	protected S determineSuccessorState(List<S> possibleSuccessors) {
		Random rng = new Random();
		while (true) {
			if (possibleSuccessors.isEmpty()) {
				// No acceptable successors were in the list. Return null to signify that the search has ended.
				return null;
			}
			
			// Decrease the temperature.
			temperature -= coolingRate;
			
			// Choose a prospective successor randomly from all possible successors, and remove it from the list.
			S possibleSuccessor = possibleSuccessors.remove(rng.nextInt(possibleSuccessors.size()));
			if (heuristic.determineQualityScore(possibleSuccessor) > heuristic.determineQualityScore(currentState)
					|| rng.nextDouble() < temperature) {
				// Either this successor is superior to the current state, or choosing an inferior successor is acceptable. Accept it as the next state.
				return possibleSuccessor;
			}
		}
	}
}
