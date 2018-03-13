package nl.calmamity.search.path.route

import java.util.NoSuchElementException

import _root_.path.route.Location
import core.Action
import nl.calmamity.search.path
import nl.calmamity.search.core
import util.Vertex

import scala.collection.JavaConverters._

/**
  * Represents a state in the route search problem.
  * @param currentLocation The location represented by this state.
  */
case class State(
	currentLocation: Location, predecessor: Option[State], cost: Double
) extends path.State[State] with core.State[State] {
	override def isGoalState: Boolean = currentLocation.getIsGoal
	
	override def createAvailableActionsIterator(): Iterator[Action[State]] = new Iterator[Action[State]]() {
		/** The current index of this iterator in the list of connections available from the current location. */
		var nextConnectionIndex: Int = 0
		
		override def hasNext: Boolean = {
			nextConnectionIndex < currentLocation.getConnections.size()
		}
		
		override def next(): Action[State] = {
			if (!hasNext) {
				throw new NoSuchElementException("All outgoing connections have been checked.")
			}
			
			// Get the next connection and use it to create an action. Also increment the connection index!
			val outgoingConnection: Vertex[Location] = currentLocation.getConnections.get(nextConnectionIndex)
			nextConnectionIndex += 1
			new Action[State](State(outgoingConnection.getDestination, Some(State.this), State.this.cost + outgoingConnection.getCost), outgoingConnection.getCost)
		}
	}
	
	override def randomlySelectAvailableAction: Action[State] = {
		// Select a random outgoing connection from this location.
		val randomNumbers = new scala.util.Random()
		val outgoingConnection = randomNumbers.shuffle(currentLocation.getConnections.asScala).head
		// Use the connection to create an action.
		new Action[State](State(outgoingConnection.getDestination, Some(this), this.cost + outgoingConnection.getCost), outgoingConnection.getCost)
	}
	
	override def equals(other: scala.Any): Boolean = {
		other match {
			case otherState: State =>
				// Only the location matters; defer to the default implementation of equals for Location.
				currentLocation.equals(otherState.currentLocation)
			case _ =>
				false
		}
	}
	
	override def hashCode(): Int = currentLocation.hashCode()
}
