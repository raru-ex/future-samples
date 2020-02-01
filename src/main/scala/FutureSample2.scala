import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent._

object FutureSample2 extends App {
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
  for {
    _ <- parallel1
    _ <- parallel2
  } yield ()

  // 結果を得る前にmain threadが死ぬので結果が取れない
  // Lambdaを利用した実装などで稀に事故るので注意
}