package testability

import org.scalatest.{FlatSpec, Matchers}

class ProducerClassInherit extends ConfigObjectsTrait3 {



  def someMethod() = {

    getString("queries.client")

  }
}

import com.typesafe.config.{Config, ConfigFactory}

trait ConfigObjectsTrait3 {

  lazy val config: Config = ConfigFactory.load()

  def getString(str: String) = config.getString(str)
  def getObject(str: String) = config.getObject(str)

}

class ProducerClassInheritTest extends FlatSpec with Matchers {

  "testing a ProducerClass I can change by passing another Config" should "work" in {

    (new ProducerClassInherit {
      override lazy val config: Config = ConfigFactory.parseString("queries { client = \"select\" }")
    }).someMethod() shouldBe "select"
  }
}
