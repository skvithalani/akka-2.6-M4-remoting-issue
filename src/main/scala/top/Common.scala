package top

import akka.actor.{BootstrapSetup, ProviderSelection}
import com.typesafe.config.ConfigFactory

object Common {
  def setup(port: Int, providerSelection: ProviderSelection): BootstrapSetup = {
    val config = ConfigFactory
      .parseString(s"akka.remote.artery.canonical.port = $port")
      .withFallback(ConfigFactory.load())

    BootstrapSetup()
      .withConfig(config)
      .withActorRefProvider(providerSelection)

  }
}
