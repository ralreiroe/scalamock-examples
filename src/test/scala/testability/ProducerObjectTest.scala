package testability

import org.scalatest.{FlatSpec, Matchers}

object ProducerObject {

  val co = new ConfigObjectsTrait {}      //<========= not replaceable for testing
  import co._

  def someMethod() = {

    getString("queries.client")

  }
}

import com.typesafe.config.{Config, ConfigFactory}

trait ConfigObjectsTrait {

  lazy val config: Config = ConfigFactory.load()

  def getString(str: String) = config.getString(str)
  def getObject(str: String) = config.getObject(str)

}

class ProducerTest extends FlatSpec with Matchers {

  "testing a ProducerObject I cannot change" should "work" in {

    ProducerObject.someMethod() shouldBe "some query"
  }
}
