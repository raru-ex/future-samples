import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent._
import scala.concurrent.duration.Duration

object FutureSample3 extends App {
  val sleepTime = 1000
  // 並列実行
  println("===== 並行実行 =====")

  // 先に実行で、後に出力
  val parallel1 = Future {
    val name = "parallel-1"
    Thread.sleep(sleepTime * 2)
    println(s"thread($name), id = ${Thread.currentThread().getId()}")
  }

  // 後に実行で、先に出力
  val parallel2 =  Future {
    val name = "parallel-2"
    Thread.sleep(sleepTime)
    println(s"thread($name), id = ${Thread.currentThread().getId()}")
  }

  // 結果を取得
  Await.result(for {
    _ <- parallel1
    _ <- parallel2
  } yield (), Duration.Inf)

  println("===== 直列実行 =====")
  // 直列に実行される
  Await.result(for {
    _ <- Future {
      val name = "parallel-3"
      Thread.sleep(sleepTime * 2)
      println(s"thread($name), id = ${Thread.currentThread().getId()}")
    }
    _ <- Future {
      val name = "parallel-4"
      Thread.sleep(sleepTime)
      println(s"thread($name), id = ${Thread.currentThread().getId()}")
    }
  } yield (), Duration.Inf)

}