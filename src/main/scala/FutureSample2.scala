import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent._

// 事故る並行処理の例
object FutureSample2 extends App {
  val sleepTime = 1000
  println(s"thread(main), id = ${Thread.currentThread().getId()}")

  // 並列実行
  // 先に実行で、後に出力
  val parallel1 = Future {
    val name = "parallel-1"
    Thread.sleep(sleepTime * 2)
    println(s"thread($name), id = ${Thread.currentThread().getId()}")
    1
  }

  // 後に実行で、先に出力
  val parallel2 =  Future {
    val name = "parallel-2"
    Thread.sleep(sleepTime)
    println(s"thread($name), id = ${Thread.currentThread().getId()}")
    2
  }

  // 結果を取得
  val sum = for {
    v1 <- parallel1
    v2 <- parallel2
  } yield v1 + v2

  println(sum)

  // 結果を得る前にmain threadが死ぬので結果が取れない
  // Future(<not completed>) となる (例外はthrowされていない)
  // Lambdaを利用した実装などで稀に事故るので注意
}