package nl.calmamity.search.core

import util.Util

import scala.collection.mutable

/**
  * Superclass of all heuristic functions.
  * @tparam StateImplementation The type of state that is checked by this heuristic.
  */
trait Heuristic[StateImplementation <: State[StateImplementation]] {
	/** The best score that a state can attain using this heuristic. */
	val optimalScore: Double
	
	val qualityScoreCache: mutable.Map[Long, Double] = mutable.Map()
	
	/**
	  * Determines the quality of the provided state using the heuristic function defined by this class, and stores it with the state.
	  * @param state The state for which to determine the quality.
	  * @return The quality score of the provided state, as determined by the heuristic function.
	  */
	def determineQualityScore(state: StateImplementation): Double = {
		qualityScoreCache.getOrElseUpdate(state.stateId, estimateQualityScore(state))
	}
	
	/**
	 * Implements the heuristic function, using it to determine the provided state's quality.
	 * @param state The state for which to determine the quality.
	 * @return The quality score for the provided state, as determined by the heuristic function.
	 */
	def estimateQualityScore(state: StateImplementation): Double
	
	def determineHasOptimalScore(state: StateImplementation, qualityMargin: Double): Boolean = {
		determineIsOptimalScore(determineQualityScore(state), qualityMargin)
	}
	
	/**
	  * Determines whether a given score is the best score that can be attained using this heuristic. A state with this
	  * score is usually a goal state.
	  * @param score The score to check.
	  * @return `true` if `score` is the best possible score a state can achieve with this heuristic.
	  */
	def determineIsOptimalScore(score: Double): Boolean = {
		determineIsOptimalScore(score, 0)
	}
	
	/**
	  * Determines whether a given score is the best score that can be attained using this heuristic. A state with this
	  * score is usually a goal state.
	  * @param score The score to check.
	  * @param qualityMargin The quality margin to employ; if the difference between `score` and the optimal score is
	  * less than this value, the score will be regarded as optimal.
	  * @return `true` if `score` is the best possible score a state can achieve with this heuristic.
	  */
	def determineIsOptimalScore(score: Double, qualityMargin: Double): Boolean = {
		score + qualityMargin + Util.ERROR_MARGIN_FOR_FLOAT_COMPARISON >= optimalScore
	}
}
