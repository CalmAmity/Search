package nl.calmamity.search.local.supereffective

/**
  * Represents one of the Satchel Creatures.
 *
  * @param firstType The type of the creature. Each creature has at least one type.
  * @param secondType The optional second type of the creature. Each creature has at most two types.
  * @param manoeuvreTypes The types of the individual manoeuvres the creature can perform. Each creature can have at
  * most four manoeuvres available to it, and because having fewer manoeuvres available confers no advantage, we model
  * creatures as always having exactly four available.
  */
case class SatchelCreature(
	firstType: Type.Value
	, secondType: Option[Type.Value]
	, manoeuvreTypes: Seq[Type.Value]
) {
	if (manoeuvreTypes.size != 4) {
		throw new IllegalArgumentException(
			s"The list of manoeuvre types should have a size of exactly 4. Provided: $manoeuvreTypes"
		)
	}
	
	def createMutationsIterator(): Iterator[SatchelCreature] = new Iterator[SatchelCreature]() {
		var currentComponentIndex = 0
		var alternateTypeValuesForCurrentComponent: Seq[Option[Type.Value]] =
			determineTypeAlternatives(currentComponentIndex)
		
		var typeIndex = 0
		
		override def hasNext: Boolean = currentComponentIndex < SatchelCreature.numComponents
		
		override def next(): SatchelCreature = {
			val newCreature = createMutation(currentComponentIndex, alternateTypeValuesForCurrentComponent, typeIndex)
			
			typeIndex += 1
			if (typeIndex >= alternateTypeValuesForCurrentComponent.size) {
				typeIndex = 0
				currentComponentIndex += 1
				if (currentComponentIndex < SatchelCreature.numComponents) {
					alternateTypeValuesForCurrentComponent = determineTypeAlternatives(currentComponentIndex)
				}
			}
			
			newCreature
		}
	}
	
	def createMutation(componentToMutateIndex: Int, typeOptions: Seq[Option[Type.Value]], typeIndex: Int): SatchelCreature = {
		def replaceWithAlternativeTypeIfNecessary(componentIndex: Int, currentType: Option[Type.Value]): Option[Type.Value] = {
			if (componentIndex == componentToMutateIndex) {
				typeOptions(typeIndex)
			} else {
				currentType
			}
		}
		
		SatchelCreature(
			replaceWithAlternativeTypeIfNecessary(0, Some(firstType)).get
			, replaceWithAlternativeTypeIfNecessary(SatchelCreature.secondTypeIndex, secondType)
			, for (componentIndex <- 2 until SatchelCreature.numComponents) yield
				replaceWithAlternativeTypeIfNecessary(componentIndex, Some(manoeuvreTypes(componentIndex - 2))).get
		)
	}
	
	def determineTypeAlternatives(currentComponentIndex: Int): Seq[Option[Type.Value]] = {
		((Type.values.map(Option.apply) + None) -- typesToExclude(currentComponentIndex)).toSeq
	}
	
	def typesToExclude(componentIndex: Int): Seq[Option[Type.Value]] = {
		componentIndex match {
			case SatchelCreature.secondTypeIndex =>
				Seq(Some(firstType), secondType)
			case _ =>
				Seq(None, determineComponentType(componentIndex))
		}
	}
	
	def determineComponentType(componentIndex: Int): Option[Type.Value] = {
		componentIndex match {
			case 0 =>
				Some(firstType)
			case SatchelCreature.secondTypeIndex =>
				secondType
			case someOtherIndex =>
				Some(manoeuvreTypes(someOtherIndex - 2))
		}
	}
	
	override def toString: String = {
		val secondTypeString = secondType match {
			case Some(typeValue) =>
				s"/$typeValue"
			case None =>
				""
		}
		s"Creature($firstType$secondTypeString, Manoeuvres(${manoeuvreTypes.mkString(",")}))"
	}
}

object SatchelCreature {
	val numComponents = 6
	val secondTypeIndex = 1
	val randomNumbers = new scala.util.Random()
	
	/** Creates a creature with random types for all components. */
	def apply(): SatchelCreature = {
		val firstType = pickRandomType
		val availableSecondTypes = ((Type.values - firstType).map(Option.apply) + None).toSeq
		val secondType = availableSecondTypes(randomNumbers.nextInt(availableSecondTypes.size))
		SatchelCreature(
			firstType
			, secondType
			, Seq(pickRandomType, pickRandomType, pickRandomType, pickRandomType)
		)
	}
	
	def pickRandomType: Type.Value = {
		val typeIndex = randomNumbers.nextInt(Type.values.size)
		Type.values.toSeq(typeIndex)
	}
}
