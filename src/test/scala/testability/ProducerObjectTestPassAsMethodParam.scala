package testability

import org.scalatest.{FlatSpec, Matchers}

object ProducerObject4 {

  def someMethod(implicit co: ConfigObjectsTrait4) = {    //<==== arg rather than object member; makes it replaceable while keeping object

    co.getString("queries.client")

  }
}

import com.typesafe.config.{Config, ConfigFactory}

trait ConfigObjectsTrait4 {

  lazy val config: Config = ConfigFactory.load()

  def getString(str: String) = config.getString(str)
  def getObject(str: String) = config.getObject(str)

}

class ProducerTest4 extends FlatSpec with Matchers {

  "I can test some method with a different config by passing one in" should "work" in {

    implicit val co = new ConfigObjectsTrait4 {           //<========= replaced for testing
      override def getString(str: String): String = "blah"
    }

    ProducerObject4.someMethod shouldBe "blah"
  }
}
