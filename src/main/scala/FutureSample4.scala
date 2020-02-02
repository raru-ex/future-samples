import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent._
import scala.concurrent.duration.Duration

object FutureSample4 extends App {
  val sleepTime = 1000
  println(s"thread(main), id = ${Thread.currentThread().getId()}")

  val pureFuture = Future {
    val name = "pure future"
    Thread.sleep(sleepTime * 2)
    println(s"thread($name), id = ${Thread.currentThread().getId()}")
    "I'm a Future."
  }

  // pure futureを使いまわさないで試すのもある
  val mainThreadMappedFuture = pureFuture map {
    val name = "main thread mapped future"
    println(s"thread($name), id = ${Thread.currentThread().getId()}") // この2行が上手く解析できておらずmapの引数に渡せていない可能性がある
    _.concat("Now, I am Mapped.")
  }

  val mappedFuture = pureFuture map { v =>
    val name = "mapped future"
    println(s"thread($name), id = ${Thread.currentThread().getId()}")
    v.concat("Now, I am Mapped.")
  }

  val flattedFuture = mappedFuture flatMap { v =>
    Future {
      val name = "flatted"
      Thread.sleep(sleepTime)
      println(s"thread($name), id = ${Thread.currentThread().getId()}")
      v.concat("And, I am flatMapped")
    }
  }

  Await.result(flattedFuture, Duration.Inf)
}