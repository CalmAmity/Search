package local.hillclimbing;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import core.Heuristic;
import core.State;
import util.Util;

public class Stochastic<S extends State<S>> extends AbstractHillClimbing<S> {
	public Stochastic(S startState, Heuristic<S> heuristic) {
		super(startState, heuristic);
	}
	
	public Stochastic(S startState, Heuristic<S> heuristic, int maximumNrPlateauMoves) {
		super(startState, heuristic, maximumNrPlateauMoves);
	}
	
	@Override
	protected S determineSuccessorState(List<S> possibleSuccessors) {
		// Start by collecting only those successors that have an equal or better quality compared to the current state.
		List<S> nonDownhillMoves = possibleSuccessors.stream()
				.filter(state -> heuristic.determineQualityScore(state) >= heuristic.determineQualityScore(currentState) - Util.ERROR_MARGIN_FOR_FLOAT_COMPARISON)
				.collect(Collectors.toList());
		
		// Determine the individual weights of the states.
		List<Double> weights = determineWeights(nonDownhillMoves);
		
		// Determine the cumulative weights of the states in this list.
		List<Double> cumulativeScores = new ArrayList<>(nonDownhillMoves.size());
		double currentCumulativeScore = 0;
		for (Double weight : weights) {
			currentCumulativeScore += weight;
			cumulativeScores.add(currentCumulativeScore);
		}
		
		// Pick a random number between 0 and the total weight.
		double totalWeight = cumulativeScores.get(cumulativeScores.size() - 1);
		double random = Math.random() * totalWeight;
		// Use binary search to find the position of this random number in the list of cumulative weights.
		int selectedStateIndex = Util.binarySearch(random, cumulativeScores);
		// Return the state at this index.
		return nonDownhillMoves.get(selectedStateIndex);
	}
	
	/**
	 * Determines individual non-negative weights for every state in {@code states}. The function from a state's quality to its weight is monotonic.
	 * @param states The list of states to determine weights for.
	 * @return A list of weights in the same order as {@code states}.
	 */
	private List<Double> determineWeights(List<S> states) {
		// Determine the minimal score in the list.
		final double minimalScore = states.stream().mapToDouble(S::getQualityScore).min().getAsDouble();
		
		List<Double> weights = new ArrayList<>(states.size());
		for (S state : states) {
			// The weight for a state is its quality score minus the lowest score in the set. This ensures that the lowest-scoring state still has a non-negative weight (namely 0).
			weights.add(state.getQualityScore() - minimalScore);
		}
		
		return weights;
	}
}
