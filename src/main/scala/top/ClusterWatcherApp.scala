package top

import java.nio.file.{Files, Paths}

import akka.actor.typed._
import akka.actor.typed.scaladsl.Behaviors
import akka.util.Timeout
import com.typesafe.config.ConfigFactory

import scala.concurrent.Await
import scala.concurrent.duration.DurationLong

object ClusterWatcherApp {

  def main(args: Array[String]): Unit = {

    def watcherBeh(watcheeRef: ActorRef[Any]): Behavior[Any] =
      Behaviors.setup[Any] { ctx ⇒
        ctx.watch(watcheeRef)
        watcheeRef ! "stop"
        Behaviors.receiveSignal {
          case (_, Terminated(`watcheeRef`)) =>
            println(s"actor died: $watcheeRef")
            Behaviors.same
        }
      }

    implicit val timeout: Timeout = Timeout(5.seconds)

    val watcherSystem = ActorSystem(
      Behaviors.empty[Any],
      "WatcherSystem",
      ConfigFactory.load().getConfig("watcher")
    )

    val refString  = Files.readAllLines(Paths.get("ref.txt")).get(0)
    val watcheeRef = ActorRefResolver(watcherSystem).resolveActorRef(refString)

    Await.result(watcherSystem.systemActorOf(watcherBeh(watcheeRef), "watcher"), 5.seconds)
  }

}
