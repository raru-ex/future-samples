import scala.concurrent._
import ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

object FutureSample1 extends App {
  val sleepTime = 1000
  // 並列実行
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
  Await.result(parallel1, Duration.Inf)
  Await.result(parallel2, Duration.Inf)
}