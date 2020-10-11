package class_usages.sealed_abstract_with_case_class

// case classの機能を保ったまま,Hoge(),Hoge.apply()を制限したい
sealed abstract case class User(id: Int, name: String)

object User {

  // use as factory method
  def create(i: Int, s: String): Option[User] = {
    if (i > 0) Some(new User(i, s) {})
    else None
  }
}
