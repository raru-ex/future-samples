import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent._
import scala.concurrent.duration.Duration

// 4-2と比較。不思議な動き
object FutureSample5 extends App {
  val sleepTime = 1000
  println(s"thread(main), id = ${Thread.currentThread().getId()}")

  val pureFuture = Future {
    val name = "pure future"
    Thread.sleep(sleepTime * 2)
    println(s"thread($name), id = ${Thread.currentThread().getId()}")
    "I'm a Future."
  }

  val mainThreadMappedFuture = pureFuture map {
    val name = "main thread mapped future"
    println(s"thread($name), id = ${Thread.currentThread().getId()}")
    _.appended("Now, I am Mapped.")
  }

  val mappedFuture = pureFuture map { v =>
    val name = "mapped future"
    println(s"thread($name), id = ${Thread.currentThread().getId()}")
    v.appended("Now, I am Mapped.")
  }

  val flattedFuture = mappedFuture flatMap { v =>
    Future {
      val name = "flatted"
      Thread.sleep(sleepTime * 2)
      println(s"thread($name), id = ${Thread.currentThread().getId()}")
      v.appended("And, I am flatMapped")
    }
  }

  Await.result(flattedFuture, Duration.Inf)
}