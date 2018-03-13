package nl.calmamity.search.util

object IdentifierCache {
	private var latestStateId: Long = 0
	
	def createStateId: Long = {
		latestStateId += 1
		latestStateId
	}
}
