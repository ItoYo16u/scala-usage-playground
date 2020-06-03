package com.github.ItoYo16u.parsers.md_to_html

sealed abstract trait Inline

abstract trait InlineText extends Inline {
  def content:String

  override def toString: String = content
}

abstract trait InlineTag extends Inline{
  def tag:Tag
  def content: Inline
  override def toString= {
    tag.toHTMLTags match {
      case (start,end) => start + content.toString + end
    }
  }
}
case class Tag(value:String,options:Map[String,String]=Map.empty[String,String]) {
  val optionsAsString = options.iterator.foldLeft(""){
    case (acc,(k,v))=> {
      acc + s"$k='$v'"
    }
  }
  def toHTMLTags:(String,String) = (s"<$value $optionsAsString>",s"</$value>")
  def toHTMLTag:String = s"<$value $optionsAsString/>"
}
// Inline element
case class Strong(content:Inline) extends InlineTag{val tag = Tag("strong")}
case class Emphasis(content:Inline) extends InlineTag{val tag=Tag("em")}
case class Italic(content:Inline) extends InlineTag{val tag=Tag("i")}
case class Link(content:Inline,href:String) extends InlineTag{val tag=Tag("a",options = Map("href"->href))}
case class PlainText(content:String) extends InlineText


sealed abstract trait Block{
  def tag:Tag
}

abstract trait BlockContent extends Block {
  def content: Inline
  override def toString: String = {
    tag.toHTMLTags match {
      case (start,end) => start + content.toString + end
    }
  }
}

case class Heading(level:Int,content:Inline) extends BlockContent{
  val tag=Tag(s"h$level")
}
case class P(content:Inline) extends BlockContent{val tag=Tag("p")}
case object Empty extends Block{val tag=Tag("")}
case object HR extends Block{val tag=Tag("hr")}
