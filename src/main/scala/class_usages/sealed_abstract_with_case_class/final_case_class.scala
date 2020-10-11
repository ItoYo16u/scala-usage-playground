package class_usages.sealed_abstract_with_case_class

// final case classを使った場合、createメソッドを使わないでも
// new Hoge(...)でインスタンスを生成できてしまう
final case class FinalUser(i: Int, name: String)

object FinalUser {

  def create(i: Int, s: String): Option[FinalUser] = {
    if (i > 0) Some(new FinalUser(i, s))
    else None
  }
}
