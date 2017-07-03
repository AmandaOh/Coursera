package funsets

object Main extends App {
  import FunSets._
  println(contains(singletonSet(1), 1))
  val test1 = Set(1, 5, 9, 10, 1001)
  val test2 = Set(2,4)
  println(Set(1,2)(1))
}
