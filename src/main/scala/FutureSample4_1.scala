import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent._
import scala.concurrent.duration.Duration

// 4-2と比較。不思議な動き
object FutureSample4_1 extends App {
  val sleepTime = 1000
  println(s"thread(main), id = ${Thread.currentThread().getId()}")

  val pureFuture = Future {
    val name = "pure future"
    Thread.sleep(sleepTime * 2)
    println(s"thread($name), id = ${Thread.currentThread().getId()}")
    "I'm a Future."
  }

  val mappedFuture = pureFuture map {
    val name = "mapped future"
    println(s"thread($name), id = ${Thread.currentThread().getId()}")
    _.appended("Now, I am Mapped.")
  }

  val flattedFuture = mappedFuture flatMap { v =>
    Future {
      val name = "flatted"
      Thread.sleep(sleepTime * 2)
      println(s"thread($name), id = ${Thread.currentThread().getId()}")
      v.appended("And, I am flatMapped")
    }
  }
}