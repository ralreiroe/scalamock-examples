package testability

class ProducerClass(val co: ConfigObjectsTrait2 = new ConfigObjectsTrait2 {}) {     //<===== replaceable for testing

  def someMethod() = {

    co.getString("queries.client")

  }
}

import com.typesafe.config.{Config, ConfigFactory}

trait ConfigObjectsTrait2 {

  lazy val config: Config = ConfigFactory.load()

  def getString(str: String) = config.getString(str)
  def getObject(str: String) = config.getObject(str)

}

class ProducerClassTest extends utils.Spec {

  "testing a ProducerClass I can change" in {

    new ProducerClass(new ConfigObjectsTrait2 {
      override lazy val config: Config = ConfigFactory.parseString("queries { client = \"select\" }") //replaced for testing
    }).someMethod() mustBe "select"
  }
}
