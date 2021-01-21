package basic.strings

object IconHTMLGenService {
  def run () = ???

  private def iconDiv(path: IconPath) : String = s"""
  <div class="flex-child">
      <a class="img-wrap" href="https://novera.co.jp" target="_blank"><img
          src="<%= require('./assets/images/${path.name}').default %>"
          alt="noveraのアイコン" />
      </a>
  </div>
  """.stripMargin
}

private [this] case class IconPath(name:String,path: String,option: IconPathOption)

private [this] case class IconPathOption(klass: String,style:String)