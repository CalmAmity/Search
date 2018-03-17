package nl.calmamity.search

import org.scalatest.FlatSpec
import org.slf4j.{Logger, LoggerFactory}

trait SearchTest extends FlatSpec {
	val log: Logger = LoggerFactory.getLogger(this.getClass.getSimpleName)
}
