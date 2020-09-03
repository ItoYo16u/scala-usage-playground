package web.circe.values

final case class FlatBookJson(id: BookId,title:String)
final case class BookId(value:Long) extends AnyVal