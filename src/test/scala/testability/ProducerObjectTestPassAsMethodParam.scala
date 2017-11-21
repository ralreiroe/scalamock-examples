package testability

import utils.Spec

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

class ProducerTest4 extends Spec {

  "I can test some method in ProducerObject by passing in a different config" in {

    implicit val co = new ConfigObjectsTrait4 {           //<========= replaced for testing
      override def getString(str: String): String = "blah"
    }

    ProducerObject4.someMethod mustBe "blah"
  }
}
