package testability

object ProducerObject {

  val co = new ConfigObjectsTrait {}      //<========= not replaceable for testing

  def someMethod() = {

    co.getString("queries.client")

  }
}

import com.typesafe.config.{Config, ConfigFactory}

trait ConfigObjectsTrait {

  lazy val config: Config = ConfigFactory.load()

  def getString(str: String) = config.getString(str)
  def getObject(str: String) = config.getObject(str)

}

class ProducerTest extends utils.Spec {

  "testing a ProducerObject I cannot change" in {

    ProducerObject.someMethod() mustBe "some query"
  }
}
