object DoubleExtensions {

  implicit class Hat(d: Double) {
    def ^(i: Int) = List.fill(i)(d).fold(1.0: Double)((v1, v2) => v1 * v2)
  }
}
