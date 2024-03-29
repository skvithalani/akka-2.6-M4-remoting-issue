package top

import java.nio.file.{Files, Paths}
import java.util.Collections

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRefResolver, ActorSystem, Behavior}
import akka.util.Timeout
import com.typesafe.config.ConfigFactory

import scala.concurrent.Await
import scala.concurrent.duration.DurationLong

object RemoteWatcheeApp {

  def main(args: Array[String]): Unit = {

    val watcheeBeh: Behavior[Any] = Behaviors.receiveMessage[Any] {
      case "stop" =>
        println("*******Stopped*********")
        Behaviors.stopped
    }

    implicit val timeout: Timeout = Timeout(5.seconds)

    val watcheeSystem = ActorSystem(
      Behaviors.empty[Any],
      "WatcheeSystem",
      ConfigFactory.load().getConfig("watchee")
    )

    val watcheeRef = Await.result(watcheeSystem.systemActorOf(watcheeBeh, "watchee"), 5.seconds)
    val refString  = ActorRefResolver(watcheeSystem).toSerializationFormat(watcheeRef)

    Files.write(Paths.get("ref.txt"), Collections.singletonList(refString))
  }
}
