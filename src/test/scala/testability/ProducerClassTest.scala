package testability

import org.scalatest.{FlatSpec, Matchers}

class ProducerClass(val co: ConfigObjectsTrait2 = new ConfigObjectsTrait2 {}) {     //<===== replaceable for testing

  import co._

  def someMethod() = {

    getString("queries.client")

  }
}

import com.typesafe.config.{Config, ConfigFactory}

trait ConfigObjectsTrait2 {

  lazy val config: Config = ConfigFactory.load()

  def getString(str: String) = config.getString(str)
  def getObject(str: String) = config.getObject(str)

}

class ProducerClassTest extends FlatSpec with Matchers {

  "testing a ProducerClass I can change by passing another Config" should "work" in {

    new ProducerClass(new ConfigObjectsTrait2 {
      override lazy val config: Config = ConfigFactory.parseString("queries { client = \"select\" }") //replaced for testing
    }).someMethod() shouldBe "select"
  }
}
