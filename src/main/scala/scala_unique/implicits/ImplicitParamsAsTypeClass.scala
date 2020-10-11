package scala_unique.implicits

import java.io._
import java.nio.charset.StandardCharsets
// 型クラスを使った処理の一般化

// "加算処理"をあらわす処理
trait Additive[A] {
  def plus(one: A, theOther: A): A
  def zero: A
}

object Additive {

  // Additive[A]のAにstringが入った場合、処理をこのobjectに移譲する
  implicit object StringAdditive extends Additive[String] {
    override def plus(one: String, theOther: String): String = one + theOther

    override def zero: String = ""
  }

  // Additive[A]のAにstringが入った場合、処理をこのobjectに移譲する
  implicit object IntAdditive extends Additive[Int] {
    override def plus(one: Int, theOther: Int): Int = one + theOther

    override def zero: Int = 0
  }
}

// sumはlistの型に応じて対応するAdditiveの実装を探索する
// 具体的な処理はAdditiveの実装に移譲する
// このように実装することで処理を型に対して一般化できる
object ImplicitParamsAsTypeClass {
  def sum[A](list: List[A])(implicit m: Additive[A]) = list.foldLeft(m.zero)((x, y) => m.plus(x, y))
}

// より実用的な例: fileのinput/output
// usingブロックで囲むことで自動でストリームを閉じる

// disposeはリソースをdisposeする. 具体的な処理は実装に移譲する
trait Disposer[-A] {
  def dispose(resource: A): Unit
}

// 実装(implicitなinputStreamDisposeを参照)
object Disposer {

  implicit val inputStreamDispose = new Disposer[InputStream] {
    override def dispose(resource: InputStream): Unit = resource.close()
  }
}

object FileManager {

  def using[A: Disposer, B](resource: A)(f: A => B): B =
    try {
      f(resource)
    } finally {
      implicitly[Disposer[A]].dispose(resource)
    }

  def getFileBody(file: String): String =
    using(new FileInputStream(file)) { fileInputStream: InputStream =>
      val text = new Array[Byte](fileInputStream.available)
      fileInputStream.read()
      new String(text, StandardCharsets.UTF_8)
    }
}
